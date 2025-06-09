package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class TagDAO {


    //invocato a riga 85 di entity.Piattaforma dal suo metodo crearicetta, ne salviamo i tag sul database che servono per i report
    public boolean aggiungiTagARicetta(int ricettaId, List<String> tag) {
        String select = "SELECT idTag FROM Tags WHERE nome = ?";
        String insert = "INSERT INTO Ricette_has_Tags (Ricette_idRicetta, Tags_idTag) VALUES (?, ?)";
        boolean success = true;

        try (Connection conn = DBManager.openConnection();
             PreparedStatement selectStmt = conn.prepareStatement(select);
             PreparedStatement insertStmt = conn.prepareStatement(insert)) {

            for (String tagNome : tag) {
                selectStmt.setString(1, tagNome);
                ResultSet rs = selectStmt.executeQuery();

                if (rs.next()) {
                    int tagId = rs.getInt("idTag");
                    insertStmt.setInt(1, ricettaId);
                    insertStmt.setInt(2, tagId);
                    insertStmt.addBatch();
                } else {
                    System.err.println("Tag non trovato: " + tagNome);
                    success = false;
                }
            }

            insertStmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }


}
