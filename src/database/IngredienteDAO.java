// IngredienteDAO.java
package database;

import java.sql.*;

public class IngredienteDAO {

    // Cerca per nome, se non esiste lo inserisce e restituisce l'id
    public int getOrCreateIngredienteId(String nomeIngrediente) {
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


}
