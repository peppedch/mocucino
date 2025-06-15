package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeDAO {

    //richiamato sotto per toggle like
    private boolean utenteHaGiaMessoLike(String username, int idRicetta) {                      //username è di quello che ha messo like, idRicetta è della ricetta a cui ha messo like
        String query = "SELECT * FROM Likes WHERE utenti_username = ? AND ricette_idRicetta = ?";
        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setInt(2, idRicetta);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true se esiste, ha messo like.
        } catch (SQLException e) {
            e.printStackTrace();
            return false;               //vuol dire che non esiste, quindi non ha messo like
        }
    }

        //invocato a riga 129 di controller.GestoreController
    public boolean toggleLike(String username, int idRicetta) {
        System.out.println("Toggling like su ricetta ID: " + idRicetta + " da utente: " + username); // Debugging: stampa l'ID della ricetta e l'utente che sta mettendo o togliendo il like
        if (utenteHaGiaMessoLike(username, idRicetta)) {
            System.out.println("Già messo like, rimuovo");
            String delete = "DELETE FROM Likes WHERE utenti_username = ? AND ricette_idRicetta = ?";
            try (Connection conn = DBManager.openConnection();
                 PreparedStatement stmt = conn.prepareStatement(delete)) {
                stmt.setString(1, username);
                stmt.setInt(2, idRicetta);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            aggiornaConteggioLike(idRicetta, -1);   //implementato sotto, private perchè non c'è bisogno di fornirlo all'esterno tipo API. con delta -1 lo tolgo. serve per filtreggio in ricette della ricetta con maggiorni numlike, quindi lo aggiorna ad ogni like messo o rimosso nel secondo caso
            return false;
        } else {
            System.out.println(" Non ancora messo like, aggiungo");
            String insert = "INSERT INTO Likes (utenti_username, ricette_idRicetta) VALUES (?, ?)";
            try (Connection conn = DBManager.openConnection();
                 PreparedStatement stmt = conn.prepareStatement(insert)) {
                stmt.setString(1, username);
                stmt.setInt(2, idRicetta);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            aggiornaConteggioLike(idRicetta, 1);    //secondo caso: con delta=+1 lo aggiungo!
            return true;
        }


    }

    // Metodo privato per aggiornare il conteggio dei like per una ricetta, USATO SOLO SOPRA!!!!!!!!!! riga 53 e 66 per i due casi di toggleLike
    private void aggiornaConteggioLike(int idRicetta, int delta) {
        String update = "UPDATE Ricette SET numLike = COALESCE(numLike, 0) + ? WHERE idRicetta = ?";  //prima avevo semplicemente mString update = "UPDATE Ricette SET numLike = numLike + ? WHERE idRicetta = ?"; provo così perche forse ho problemi se mi da null come valore default e quindi lo forzo
        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(update)) {
            stmt.setInt(1, delta);
            stmt.setInt(2, idRicetta);
            stmt.executeUpdate();
            System.out.println("→ Eseguito UPDATE su numLike");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Aggiorno numLike con delta=" + delta + " per ID " + idRicetta);

    }




}
