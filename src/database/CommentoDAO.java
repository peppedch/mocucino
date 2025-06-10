package database;

import dto.CommentoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommentoDAO {

    public List<CommentoDTO> getUltimi3CommentiPerRicetta(int idRicetta) {
        List<CommentoDTO> commenti = new ArrayList<>();

        String query = "SELECT autore, testocommento, datapubblicazione " +
                "FROM commenti " +
                "WHERE ricette_idRicetta = ? " +
                "ORDER BY datapubblicazione DESC " +
                "LIMIT 3";

        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idRicetta);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String autore = rs.getString("autore");
                String testo = rs.getString("testocommento");
                LocalDate data = rs.getDate("datapubblicazione").toLocalDate();

                commenti.add(new CommentoDTO(autore, testo, data));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commenti;
    }

    //INvocato a riga 135 di entity.Piattaforma.java
    public boolean inserisciCommento(String username, int idRicetta, String testo) {
        String query = "INSERT INTO commenti (autore, testocommento, datapubblicazione, ricette_idRicetta, utenti_username) " +
                "VALUES (?, ?, NOW(), ?, ?)";

        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);  // autore
            stmt.setString(2, testo);     // testo commento
            stmt.setInt(3, idRicetta);    // ricetta
            stmt.setString(4, username);  // per la FK utenti

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
