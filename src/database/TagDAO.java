package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
