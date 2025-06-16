package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dto.ReportTagDTO;

public class TagDAO {

    // Metodo per ottenere l'id di un tag dal nome
    public int getIdTagByNome(String nomeTag) {
        String query = "SELECT idTag FROM Tags WHERE nome = ?";
        
        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, nomeTag);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("idTag");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    //Ottiene i tag più utilizzati nelle ricette
    //Entity -> DAO: Richiesta tag più usati
    //Chiamata da GestoreController.generaReportTag() riga 121
    public List<ReportTagDTO> getTagPiuUtilizzati() {
        List<ReportTagDTO> report = new ArrayList<>();

        String query = "SELECT t.nome, COUNT(rt.Ricette_idRicetta) as numero_ricette " +
                "FROM Tags t " +
                "JOIN Ricette_has_Tags rt ON t.idTag = rt.Tags_idTag " +
                "GROUP BY t.idTag, t.nome " +
                "ORDER BY numero_ricette DESC " +
                "LIMIT 5";

        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                report.add(new ReportTagDTO(
                    rs.getString("nome"),
                    rs.getInt("numero_ricette")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return report;
    }
}
