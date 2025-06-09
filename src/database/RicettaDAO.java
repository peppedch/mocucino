package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dto.RicettaDTO;   // solo in IngredienteDAO


public class RicettaDAO {


    //invocata a riga 69 di entity.Piattaforma, crea la ricetta e restituisce l'id
    public int createRicetta(RicettaDTO dto) {
        String query = "INSERT INTO Ricette (titolo, procedimento, tempo, dataPubblicazione, visibilita, Utenti_username, Raccolte_idRaccolta)\n" +
                "VALUES (?, ?, ?, NOW(), ?, ?, ?)\n";

        int generatedId = -1;

        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, dto.getTitolo());
            stmt.setString(2, dto.getDescrizione());
            stmt.setInt(3, dto.getTempoPreparazione());
            stmt.setBoolean(4, true); // per ora fissi visibilità e autore
            stmt.setString(5, dto.getAutoreUsername()); //
            stmt.setInt(6, dto.getIdRaccolta());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedId;
    }

}
