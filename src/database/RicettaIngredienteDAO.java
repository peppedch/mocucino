package database;

import dto.IngredienteDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RicettaIngredienteDAO {

    //invocato a riga 86 di entity.Piattaforma, per salvare gli ingredienti nel db
    public boolean aggiungiIngredientiARicetta(int ricettaId, List<IngredienteDTO> lista) {
        String insert = "INSERT INTO ricette_has_ingredienti (ricette_idRicetta, ingredienti_idIngrediente, quantità, unità) VALUES (?, ?, ?, ?)";
        boolean success = true;

        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(insert)) {

            for (IngredienteDTO ing : lista) {
                // Recupera o crea l'id dell'ingrediente
                int ingredienteId = new IngredienteDAO().getOrCreateIngredienteId(ing.getNome());

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

    //invocato a riga 102 di RICETTADAO, per recuperare gli ingredienti di una ricetta
    public List<IngredienteDTO> getIngredientiByRicetta(int idRicetta) {
        List<IngredienteDTO> ingredienti = new ArrayList<>();

        String query = "SELECT i.nome, ri.quantità, ri.unità " +
                "FROM ingredienti i " +
                "JOIN ricette_has_ingredienti ri ON i.idIngrediente = ri.ingredienti_idIngrediente " +
                "WHERE ri.ricette_idRicetta = ?";

        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRicetta);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nome = rs.getString("nome");
                String quantita = rs.getString("quantità");
                String unita = rs.getString("unità");
                ingredienti.add(new IngredienteDTO(nome, quantita, unita));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredienti;
    }
} 