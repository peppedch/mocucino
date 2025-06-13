package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dto.ReportTagDTO;


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

    //invocato a riga 103 di RICETTADAO, per recuperare i tag associati alla ricetta
    public List<String> getTagByRicetta(int idRicetta) {
        List<String> tagList = new ArrayList<>();

        String query = "SELECT t.nome FROM tags t " +
                "JOIN ricette_has_tags rt ON t.idTag = rt.tags_idTag " +
                "WHERE rt.ricette_idRicetta = ?";

        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRicetta);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tagList.add(rs.getString("nome"));      //sto macello per mostrare i tag associati alla ricetta per i dettagli della ricetta
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tagList;
    }

    /**
     * Report 3: Tag tematici più utilizzati
     * DAO -> Database: Query per ottenere i tag più usati
     * Chiamata da Piattaforma.generaReportTag() riga 228
     */
    public List<ReportTagDTO> getTagPiuUtilizzati() {
        List<ReportTagDTO> lista = new ArrayList<>();
        String query = "SELECT t.nome, COUNT(*) as conteggio FROM tags t JOIN ricette_has_tags rt ON t.idTag = rt.tags_idTag GROUP BY t.nome ORDER BY conteggio DESC";
        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new ReportTagDTO(rs.getString("nome"), rs.getInt("conteggio")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }



}
