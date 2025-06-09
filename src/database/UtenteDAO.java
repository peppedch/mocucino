package database;

import entity.Utente;
import entity.Raccolta;

import java.sql.*;

public class UtenteDAO {

    //invocato a riga 53 di entity.Piattaforma per il LOGIN, autenticautnte() e a riga 82
    public Utente readUtente(String email, String password) {
        Utente utente = null;

        String query = "SELECT * FROM Utenti WHERE email = ? AND password = ?";    //  I ? sono placeholder usati nei PreparedStatement in Java. //prevenire SQL injection, affiancato a stmt.setstring dopo è piu sicuro

        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Puoi usare username come chiave principale
                String username = rs.getString("username");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");

                // Costruisco una raccolta di default come placeholder
                Raccolta raccoltaDefault = new Raccolta("Default", "Raccolta automatica", null);

                utente = new Utente(username, nome, cognome, email, password, raccoltaDefault);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utente;
    }


    //dopo la registrazione per creare l'utente. invocato a riga 59 di entity.Piattaforma
    public boolean createUtente(Utente utente) {
        String query = "INSERT INTO Utenti (username, nome, cognome, email, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, utente.getUsername());
            stmt.setString(2, utente.getNome());
            stmt.setString(3, utente.getCognome());
            stmt.setString(4, utente.getEmail());
            stmt.setString(5, utente.getPassword());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

