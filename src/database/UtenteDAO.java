package database;

import dto.ProfiloUtenteDTO;
import dto.UtenteDTO;
import entity.Utente;
import entity.Raccolta;

import java.sql.*;

public class UtenteDAO {

    //invocato a riga 46 di entity.Piattaforma per il LOGIN,
    public UtenteDTO readUtente(String email, String password) {
        UtenteDTO utenteDTO = null;

        String query = "SELECT * FROM Utenti WHERE email = ? AND password = ?";    //  I ? sono placeholder usati nei PreparedStatement in Java. //prevenire SQL injection, affiancato a stmt.setstring dopo è piu sicuro

        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {    //se l'utente esiste nel db
                // usO username come chiave principale
                String username = rs.getString("username");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");           //ricordo che la raccolta di default viene creata dal trigger nel database, quindi poi in GestoreController si crea l'oggetto Raccolta e si passa il suo id.

                utenteDTO = new UtenteDTO(username, nome, cognome, email, password);    //costruisco il DTO dell'utente autenticato, MAI tipo Utente utente che violi il paradigma bced, quindi mi devo ricordare sempre sta cosa, guai a te. è poi in GestoreController che puoi creare l'oggetto Utente, congeniale con le sue responsabilità.
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utenteDTO;
    }


    //dopo la registrazione per creare l'utente. invocato a riga 59 di entity.Piattaforma
    public boolean createUtente(UtenteDTO utenteDTO) {
        String query = "INSERT INTO Utenti (username, nome, cognome, email, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, utenteDTO.getUsername());
            stmt.setString(2, utenteDTO.getNome());
            stmt.setString(3, utenteDTO.getCognome());
            stmt.setString(4, utenteDTO.getEmail());
            stmt.setString(5, utenteDTO.getPassword());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


     //DAO a Database: Query per ottenere i dati del profilo
     //Chiamata da entity.Utente.getProfiloUtente() linea 146

    public ProfiloUtenteDTO getProfiloUtente(String username) {
        String query = "SELECT * FROM Utenti WHERE username = ?";
        
        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new ProfiloUtenteDTO(            //creo il DTO del profilo utente
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


     //DAO a Database: Query per aggiornare i dati del profilo
     // Chiamata da Piattaforma.aggiornaProfiloUtente() riga 150

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


