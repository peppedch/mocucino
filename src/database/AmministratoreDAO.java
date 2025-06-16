package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AmministratoreDAO {

    public boolean autenticaAdmin(String username, String password) {
        String query = "SELECT * FROM Amministratori WHERE username = ? AND password = ?";
        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;           //se si verifica un errore, tipo non trova le credenziali nel db, consideriamo l'autenticazione fallita
        }
    }

} 