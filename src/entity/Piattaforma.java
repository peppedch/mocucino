package entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import database.*;

import dto.RicettaDTO;
import dto.StatisticheDTO;
import dto.ProfiloUtenteDTO;
import dto.ReportAutoriDTO;
import dto.ReportTagDTO;
import dto.ReportTopRicetteDTO;


public class Piattaforma {

    private static Piattaforma piattaforma;
    private ArrayList<Ricetta> catalogoRicetta;
    private ArrayList<Utente> catalogoUtente;

    private Piattaforma(Utente utente, Ricetta ricetta) {
        this.catalogoRicetta = new ArrayList<>();
        this.catalogoUtente = new ArrayList<>();
        this.catalogoRicetta.add(ricetta);
        this.catalogoUtente.add(utente);
    }

    public static Piattaforma getInstance(Utente utente, Ricetta ricetta) {
        if (piattaforma == null) {
            piattaforma = new Piattaforma(utente, ricetta);
        }
        return piattaforma;
    }


    //faccio io, per fare login. invocato a riga 10 di controller.AccessoController
    public boolean autenticaUtente(String email, String password) {
        UtenteDAO dao = new UtenteDAO();
        return dao.readUtente(email, password) != null;
    }

    //invocato a riga 16 di controller.AccessoController
    public boolean registraUtente(Utente utente) {
        UtenteDAO dao = new UtenteDAO();
        boolean success = dao.createUtente(utente);
        if (success) {
            catalogoUtente.add(utente);  // aggiorna anche in memoria
        }
        return success;
    }

    //invocato a riga 19 controller.  i serve lo username per mostrargli il feed personalizzato
    public Utente getUtenteByCredenziali(String email, String password) {
        UtenteDAO dao = new UtenteDAO();
        return dao.readUtente(email, password); // ritorna oggetto Utente se valido, altrimenti null
    }

    //invocato a riga 39 di controller.GestoreController
    public List<RicettaDTO> getUltime5RicettePubbliche(String username) {
        RicettaDAO dao = new RicettaDAO();
        return dao.getUltime5RicettePubbliche(username);
    }

    /**
     * Ottiene le raccolte dell'utente
     * Entity -> DAO: Richiesta raccolte utente
     * Implementata in RaccoltaDAO.getTitoliRaccolteByUtente() [linea 15]
     */
    public List<String> getRaccolteUtente(String username) { 
        return new RaccoltaDAO().getTitoliRaccolteByUtente(username);
    }

    /**
     * Genera il report sul numero di ricette pubblicate in un intervallo
     * Entity -> DAO: Richiesta numero ricette
     * Chiamata da GestoreController.generaReportNumRicette() a riga 101
     * Implementata in RicettaDAO.getNumRicetteInIntervallo() a riga 184
     */
    public int generaReportNumRicette(java.sql.Date dataInizio, java.sql.Date dataFine) {
        return new RicettaDAO().getNumRicetteInIntervallo(dataInizio, dataFine);
    }

    /**
     * Genera il report sugli autori più attivi
     * Entity -> DAO: Richiesta autori più attivi
     * Chiamata da GestoreController.generaReportAutori() riga 112
     * Implementata in RicettaDAO.getAutoriPiuAttivi() riga 205
     */
    public java.util.List<ReportAutoriDTO> generaReportAutori() {
        return new RicettaDAO().getAutoriPiuAttivi();
    }

    /**
     * Genera il report sui tag più utilizzati
     * Entity -> DAO: Richiesta tag più usati
     * Chiamata da GestoreController.generaReportTag() riga 121
     * Implementata in TagDAO.getTagPiuUtilizzati() riga 81
     */
    public java.util.List<ReportTagDTO> generaReportTag() {
        return new TagDAO().getTagPiuUtilizzati();
    }

    /**
     * Genera il report sulle ricette con più interazioni
     * Entity -> DAO: Richiesta ricette top interazioni
     * Chiamata da GestoreController.generaReportTopRicette() riga 132
     * Implementata in RicettaDAO.getTopRicettePerInterazioni() riga 226
     */
    public java.util.List<ReportTopRicetteDTO> generaReportTopRicette() {
        return new RicettaDAO().getTopRicettePerInterazioni();
    }

    /**
     * Autentica un amministratore
     * Entity -> DAO: Richiesta autenticazione admin
     * Chiamata da AccessoController.autenticaAdmin() riga 31
     * Implementata in AmministratoreDAO.autenticaAdmin() riga 15
     */
    public boolean autenticaAdmin(String username, String password) {
        return new AmministratoreDAO().autenticaAdmin(username, password);
    }

}
