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
import dto.ReportAutoriDTO;
import dto.ReportTopRicetteDTO;


public class RicettaDAO {


    //invocata a riga 34 di entity.Utente, crea la ricetta e restituisce l'id
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

    //invocato a riga 119 di entity.Piattaforma, per mostrare le ricette recenti pubbliche nel feed.
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

    //invocato a riga 59 di entity.Raccolta
    //QUESTO PER OTTENERE TUTTE LE RICETTE IN UNA RACCOLTA SELEZIONATA DALL'UTENTE, quando dall'area personale, avendo la sezione delle sue racolte, clicca su una raccolta e vuole vedere le ricette che contiene.
    public List<RicettaDTO> getRicetteByRaccolta(String titoloRaccolta, String username) {
        List<RicettaDTO> ricette = new ArrayList<>();

        String query = "SELECT r.idRicetta, r.titolo, r.procedimento, r.tempo, r.numLike, r.Utenti_username " +
                "FROM Ricette r JOIN Raccolte ra ON r.Raccolte_idRaccolta = ra.idRaccolta " +
                "WHERE ra.titolo = ? AND ra.Utenti_username = ?";

        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, titoloRaccolta);
            stmt.setString(2, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RicettaDTO dto = new RicettaDTO(
                        rs.getString("titolo"),
                        rs.getString("procedimento"),
                        rs.getInt("tempo"),
                        new IngredienteDAO().getIngredientiByRicetta(rs.getInt("idRicetta")),
                        new TagDAO().getTagByRicetta(rs.getInt("idRicetta"))
                );
                dto.setIdRicetta(rs.getInt("idRicetta"));
                dto.setAutoreUsername(rs.getString("Utenti_username"));
                dto.setNumeroLike(rs.getInt("numLike"));
                dto.setCommentiRecenti(new CommentoDAO().getUltimi3CommentiPerRicetta(rs.getInt("idRicetta")));

                ricette.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ricette;
    }

    /**
     * Ottiene tutte le ricette di un utente
     * DAO -> Database: Query per ottenere le ricette dell'utente
     * Chiamata da Utente.getStatisticheUtente() 127 riga
     */
    public List<RicettaDTO> getRicetteByUtente(String username) {
        List<RicettaDTO> ricette = new ArrayList<>();

        String query = "SELECT idRicetta, titolo, procedimento, tempo, numLike, numCommenti " +
                "FROM Ricette WHERE Utenti_username = ?";

        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RicettaDTO dto = new RicettaDTO(
                        rs.getString("titolo"),
                        rs.getString("procedimento"),
                        rs.getInt("tempo"),
                        new IngredienteDAO().getIngredientiByRicetta(rs.getInt("idRicetta")),
                        new TagDAO().getTagByRicetta(rs.getInt("idRicetta"))
                );
                dto.setIdRicetta(rs.getInt("idRicetta"));
                dto.setAutoreUsername(username);
                dto.setNumeroLike(rs.getInt("numLike"));
                dto.setNumCommenti(rs.getInt("numCommenti"));

                ricette.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ricette;
    }

    /**
     * Report 1: Numero totale di ricette pubblicate in un intervallo temporale
     * DAO -> Database: Query per ottenere il numero di ricette
     * Chiamata da Piattaforma.generaReportNumRicette()  riga 209
     */
    public int getNumRicetteInIntervallo(java.sql.Date dataInizio, java.sql.Date dataFine) {
        String query = "SELECT COUNT(*) as totale FROM Ricette WHERE DATE(dataPubblicazione) >= ? AND DATE(dataPubblicazione) <= ?";    //in questo modo permette di fare anche il report giornaliero. es da 2023-10-01 a 2023-10-01, quindi solo le ricette pubblicate in quel giorno, se si vuole fare un report giornaliero. Se si vuole fare un report mensile, basta mettere da 2023-10-01 a 2023-10-31. è importante anche per i report giornalieri, ho avuto modo di constatarlo con il web scraping su vinted del trend dei sonny angels
        int totale = 0;
        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, dataInizio);
            stmt.setDate(2, dataFine);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totale = rs.getInt("totale");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totale;
    }

    /**
     * Report 2: Elenco dei primi 5 autori più attivi
     * DAO -> Database: Query per ottenere autori e numero ricette
     * Chiamata da Piattaforma.generaReportAutori() riga 218
     */
    public List<ReportAutoriDTO> getAutoriPiuAttivi() {
        List<ReportAutoriDTO> lista = new ArrayList<>();    //scegliamo volutamente top 5 come autori piu attivi.
        String query = "SELECT Utenti_username, COUNT(*) as numeroRicette FROM Ricette GROUP BY Utenti_username ORDER BY numeroRicette DESC LIMIT 5";
        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new ReportAutoriDTO(rs.getString("Utenti_username"), rs.getInt("numeroRicette")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Report 4: Prime 5 ricette con il maggior numero di interazioni (like + commenti)
     * DAO -> Database: Query per ottenere le ricette più interattive
     * Chiamata da Piattaforma.generaReportTopRicette() riga 239
     * Volutamente, ne prendiamo solo la top 5. per sicurezza, usiamo COALESCE per evitare null come int di default nel database. Per eventuali nuovi admin e scalare il db, ricordiamo che in numike e numcommenti bisogna avere di deafult 0 e no NULL, altrimenti puo dare errori.
     */
    public List<ReportTopRicetteDTO> getTopRicettePerInterazioni() {
        List<ReportTopRicetteDTO> lista = new ArrayList<>();
        String query = "SELECT titolo, (COALESCE(numLike,0) + COALESCE(numCommenti,0)) as interazioni FROM Ricette ORDER BY interazioni DESC LIMIT 5";
        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new ReportTopRicetteDTO(rs.getString("titolo"), rs.getInt("interazioni")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
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
