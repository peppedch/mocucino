// IngredienteDAO.java
package database;

import dto.IngredienteDTO;

import java.sql.*;
import java.util.List;

public class IngredienteDAO {

    // Cerca per nome, se non esiste lo inserisce e restituisce l'id. potevo metterla anche private, infatti
    //la uso internamente a questa classe, a riga 58, internamente a aggiungiIngredientiARicetta
    private int getOrCreateIngredienteId(String nomeIngrediente) {
        int id = -1;

        String select = "SELECT idIngrediente FROM ingredienti WHERE nome = ?";
        String insert = "INSERT INTO ingredienti (nome) VALUES (?)";

        try (Connection conn = DBManager.openConnection();
             PreparedStatement selectStmt = conn.prepareStatement(select)) {

            selectStmt.setString(1, nomeIngrediente);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("idIngrediente");
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
                insertStmt.setString(1, nomeIngrediente);
                int rows = insertStmt.executeUpdate();
                if (rows > 0) {
                    ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        id = generatedKeys.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }


    //invocato a riga 86 di entity.Piattaforma, per salvare gli ingredienti nel db
    public boolean aggiungiIngredientiARicetta(int ricettaId, List<IngredienteDTO> lista) {
        String insert = "INSERT INTO ricette_has_ingredienti (ricette_idRicetta, ingredienti_idIngrediente, quantità, unità) VALUES (?, ?, ?, ?)";
        boolean success = true;

        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(insert)) {

            for (IngredienteDTO ing : lista) {
                // Recupera o crea l'id dell'ingrediente, richiama la funzione sopra
                int ingredienteId = getOrCreateIngredienteId(ing.getNome());

                if (ingredienteId == -1) {
                    System.err.println("Errore con ingrediente: " + ing.getNome());
                    continue;
                }

                stmt.setInt(1, ricettaId);
                stmt.setInt(2, ingredienteId);
                stmt.setString(3, ing.getQuantita());
                stmt.setString(4, ing.getUnita());
                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }

}
