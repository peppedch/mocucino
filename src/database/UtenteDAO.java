package database;

import dto.ProfiloUtenteDTO;
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

    /**
     * Ottiene il profilo dell'utente
     * DAO -> Database: Query per ottenere i dati del profilo
     * Chiamata da Piattaforma.getProfiloUtente() [linea 147]
     * SQL: SELECT * FROM Utenti WHERE username = ?
     */
    public ProfiloUtenteDTO getProfiloUtente(String username) {
        String query = "SELECT * FROM Utenti WHERE username = ?";
        
        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new ProfiloUtenteDTO(
                    rs.getString("username"),
                    rs.getString("nome"),
                    rs.getString("cognome"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("biografia"),
                    rs.getString("immagine")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Aggiorna il profilo dell'utente
     * DAO -> Database: Query per aggiornare i dati del profilo
     * Chiamata da Piattaforma.aggiornaProfiloUtente() [linea 147]
     * SQL: UPDATE Utenti SET nome = ?, cognome = ?, email = ?, password = ?, biografia = ?, immagine = ? WHERE username = ?
     */
    public boolean aggiornaProfiloUtente(ProfiloUtenteDTO profilo) {
        String query = "UPDATE Utenti SET nome = ?, cognome = ?, email = ?, password = ?, biografia = ?, immagine = ? WHERE username = ?";
        
        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, profilo.getNome());
            stmt.setString(2, profilo.getCognome());
            stmt.setString(3, profilo.getEmail());
            stmt.setString(4, profilo.getPassword());
            stmt.setString(5, profilo.getBiografia());
            stmt.setString(6, profilo.getImmagine());
            stmt.setString(7, profilo.getUsername());
            
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

