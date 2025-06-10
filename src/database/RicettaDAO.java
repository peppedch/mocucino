package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.CommentoDTO;
import dto.IngredienteDTO;
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
            stmt.setBoolean(4, dto.getVisibilita()); //
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

    //invocato a riga 119 di entity.Piattaforma
    public List<RicettaDTO> getUltime5RicettePubbliche(String username) {
        List<RicettaDTO> ricette = new ArrayList<>();

        String query = "SELECT idRicetta, titolo, procedimento, tempo, dataPubblicazione, Utenti_username " +
                "FROM Ricette WHERE visibilita = true AND Utenti_username != ? " +
                "ORDER BY dataPubblicazione DESC LIMIT 5";

        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idRicetta = rs.getInt("idRicetta");
                String titolo = rs.getString("titolo");
                String procedimento = rs.getString("procedimento");
                int tempo = rs.getInt("tempo");
                String autore = rs.getString("Utenti_username");

                // Recupera tag e ingredienti
                List<IngredienteDTO> ingredienti = new IngredienteDAO().getIngredientiByRicetta(idRicetta);
                List<String> tag = new TagDAO().getTagByRicetta(idRicetta);

                //  Recupera i 3 commenti recenti
                List<CommentoDTO> commenti = new CommentoDAO().getUltimi3CommentiPerRicetta(idRicetta);

                RicettaDTO dto = new RicettaDTO(titolo, procedimento, tempo, ingredienti, tag);
                dto.setIdRicetta(idRicetta);
                dto.setAutoreUsername(autore);
                dto.setNumeroLike(new LikeDAO().getNumeroLikePerRicetta(idRicetta));
                dto.setCommentiRecenti(new CommentoDAO().getUltimi3CommentiPerRicetta(idRicetta));


                ricette.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ricette;

    }


    //invocato a riga 125 di entity.Piattaforma. DA CANCELLARE, NON SERVE PIU'!
    /*
    public RicettaDTO getRicettaCompletaByTitoloEAutore(String titolo, String autore) {
        RicettaDTO dto = null;
        String query = "SELECT idRicetta, procedimento, tempo FROM Ricette WHERE titolo = ? AND Utenti_username = ?";

        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, titolo);
            stmt.setString(2, autore);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idRicetta = rs.getInt("idRicetta");             // recupera l'id della ricetta, importante per ingredienti e tag
                String procedimento = rs.getString("procedimento");
                int tempo = rs.getInt("tempo");

                dto = new RicettaDTO(titolo, procedimento, tempo,
                        new IngredienteDAO().getIngredientiByRicetta(idRicetta),
                        new TagDAO().getTagByRicetta(idRicetta));
                dto.setAutoreUsername(autore);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dto;
    }

*/


}
