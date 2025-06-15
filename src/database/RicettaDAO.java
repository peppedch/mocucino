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
import dto.ReportTagDTO;


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

    //invocato a riga 52 di entity.Piattaforma, per mostrare le ricette recenti pubbliche nel feed.
    public List<dto.RicettaDTO> getUltime5RicettePubbliche(String username) {
        List<dto.RicettaDTO> ricette = new ArrayList<>();
        String query = "SELECT r.*, u.username as autoreUsername, " +
                      "COUNT(DISTINCT l.Utenti_username) as numeroLike, " +
                      "COUNT(DISTINCT c.idCommento) as numCommenti " +
                      "FROM Ricette r " +
                      "LEFT JOIN Utenti u ON r.Utenti_username = u.username " +
                      "LEFT JOIN Likes l ON r.idRicetta = l.Ricette_idRicetta " +
                      "LEFT JOIN Commenti c ON r.idRicetta = c.Ricette_idRicetta " +
                      "WHERE r.visibilita = true AND r.Utenti_username != ? " +
                      "GROUP BY r.idRicetta " +
                      "ORDER BY r.dataPubblicazione DESC " +
                      "LIMIT 5";

        try (Connection conn = DBManager.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dto.RicettaDTO dto = new dto.RicettaDTO(
                    rs.getString("titolo"),
                    rs.getString("procedimento"),
                    rs.getInt("tempo"),
                    new ArrayList<>(),  // ingredienti vuoti inizialmente
                    new ArrayList<>()   // tag vuoti inizialmente. TAG, INGREDIENTI E COMMENTI TENERLI DISGIUNTI DALLA RICETTA. QUEST'ULTIMA COMUNQUE HA VISIBILITA SU QUESTI ELEMENTI.
                );
                dto.setIdRicetta(rs.getInt("idRicetta"));
                dto.setAutoreUsername(rs.getString("autoreUsername"));
                dto.setNumeroLike(rs.getInt("numeroLike"));
                dto.setNumCommenti(rs.getInt("numCommenti"));
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


     //DAO a Database: Query per ottenere le ricette dell'utente
     //Chiamata da Utente.getStatisticheUtente() 127 riga

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


     //Report 1: Numero totale di ricette pubblicate in un intervallo temporale
     //DAO a Database: Query per ottenere il numero di ricette
     //Chiamata da Piattaforma.generaReportNumRicette()  riga 209

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


     //Report 2: Elenco dei primi 5 autori più attivi
     //DAO a Database: Query per ottenere autori e numero ricette
     //Chiamata da Piattaforma.generaReportAutori() riga 218

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


     //Report 4: Prime 5 ricette con il maggior numero di interazioni (like + commenti)
     //DAO a Database: Query per ottenere le ricette più interattive
     //Chiamata da Piattaforma.generaReportTopRicette() riga 239
     //Volutamente, ne prendiamo solo la top 5. per sicurezza, usiamo COALESCE per evitare null come int di default nel database. Per eventuali nuovi admin e scalare il db, ricordiamo che in numike e numcommenti bisogna avere di deafult 0 e no NULL, altrimenti puo dare errori.

    public List<ReportTopRicetteDTO> getTopRicettePerInterazioni() {
        List<ReportTopRicetteDTO> lista = new ArrayList<>();
        String query = "SELECT titolo, (COALESCE(numLike,0) + COALESCE(numCommenti,0)) as interazioni FROM Ricette ORDER BY interazioni DESC LIMIT 5";      //buona idea DI inserire numlike e commenti e aggiornarle in automatico nella tabella ricette, perche cosi non dobbiamo fare join con le altre tabelle.
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

        //il report numero 3 per i tag in tagdao





}
