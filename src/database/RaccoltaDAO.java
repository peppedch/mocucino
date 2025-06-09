package database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

import database.DBManager;


public class RaccoltaDAO {

    //invocato a riga 91 di entity.Piattaforma
    public List<String> getTitoliRaccolteByUtente(String username) {
        List<String> titoli = new ArrayList<>();
        String query = "SELECT titolo FROM Raccolte WHERE Utenti_username = ?";


        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                titoli.add(rs.getString("titolo"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return titoli;
    }

    //invocato a riga 97 di entity.Piattaforma
    public boolean createRaccolta(String titolo, String username) {
        String query = "INSERT INTO Raccolte (titolo, descrizione, Utenti_username) VALUES (?, ?, ?)";

        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, titolo);
            stmt.setString(2, "Creata dall'utente");    //ci vuole sempre la descrizione nella raccolta. ipotizzando la pigrizia dell'utente, la impostiamo noi.
            stmt.setString(3, username);    //importante tenerne traccia dal login per associargli univocamente le sue ricette e raccolte

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //invocato a riga 81 di entity.Piattaforma
    public boolean aggiungiRicettaARaccolta(int raccoltaId, int ricettaId) {
        String query = "UPDATE Ricette SET Raccolte_idRaccolta = ? WHERE idRicetta = ?";

        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, raccoltaId);
            stmt.setInt(2, ricettaId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    //invocato a riga 113 di entity.Piattaforma
    public int getIdRaccoltaByTitolo(String titolo, String username) {
        String query = "SELECT idRaccolta FROM Raccolte WHERE titolo = ? AND Utenti_username = ?";
        int id = -1;

        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, titolo);
            stmt.setString(2, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("idRaccolta");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }



}
