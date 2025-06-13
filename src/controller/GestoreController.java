package controller;

import dto.RicettaDTO;
import entity.Piattaforma;
import entity.Raccolta;
import entity.Ricetta;
import dto.StatisticheDTO;
import dto.ProfiloUtenteDTO;
import dto.ReportAutoriDTO;
import dto.ReportTagDTO;
import dto.ReportTopRicetteDTO;
import java.sql.Date;

import java.util.List;
import java.util.ArrayList;

public class GestoreController {


    // Istanza privata statica (singleton)
    private static GestoreController instance;
    // Riferimento all'utente corrente
    private entity.Utente utenteCorrente;

    // Costruttore privato per impedire istanziazione esterna
    private GestoreController() {
    }

    // Metodo pubblico per ottenere l'istanza
    public static GestoreController getInstance() {
        if (instance == null) {
            instance = new GestoreController();
        }
        return instance;
    }

    // Setter per l'utente corrente
    public void setUtenteCorrente(entity.Utente utente) {
        this.utenteCorrente = utente;
    }

    //invocato a riga 260 di gui.NuovaRicettaFrame e usato anche in gui.Areapersonaleframe a riga 257. SIMILE A getIdRaccoltaByTitolo PIU SOTTO, MA GIU HO SPIEGATO IL PERCHè DI AVERNE DUE SIMILI MA DIVERSE.
    public List<String> getRaccolteUtente(String username) {
        if (utenteCorrente == null) return new ArrayList<>();
        return utenteCorrente.getTitoliRaccolte();
    }

    //invocato a riga 282 di gui.NuovaRicettaFrame, gli passa la stringa della nuova raccolta che vuole creare username attuale. PER CREARE UNA RACCOLTA SERVE ANCHE IL TITOLO CHE VUOLE L'UTENTE, QUINDI NECESSARIAMENTE METODO DIVERSO.
    public boolean creaNuovaRaccolta(String nome, String username) {
        if (utenteCorrente == null) return false;
        return utenteCorrente.creaRaccolta(nome);
    }

    //invocata a riga 314 di gui.NuovaRicettaFrame
    public boolean creaRicetta(dto.RicettaDTO dto) {
        if (utenteCorrente == null) return false;
        return utenteCorrente.creaRicetta(dto);
    }

    //invocato a riga 278, 289, 299 di gui.NuovaRicettaFrame
    public int getIdRaccoltaByTitolo(String titolo, String username) {
        return Raccolta.getIdByTitolo(titolo, username);
    }
    //questa mi serve per gestirte i 3 casi di dove inserire la ricetta, per questo usata 3 volte.
    //è fondamentale recuperare l'id della raccolta che funge da fk per la ricetta e sapere la ricetta
    //in quale raccolta viene salvata. è stato fondamentale per fare "crea nuova raccolta" al momento
    //della pubblicazione di ricetta per permettere di crerare la raccolta e ad associargli subito dopo
    //la ricetta, il tutto in fase di creazione, senza mostrare form dopo. molto piu carino e compatto dal pov user.
    //LA NECESSITA DI QUESTO METODO è IN PARTICOLRE PER IL CASO IN CUI L'UTENTE CREA UNA NUOVA RACCOLTA. QUI IO PRIMA CREO LA RACCOLTA CON LO STESSO NOME CHE HA INSERITO L'UTENTE (percio c'è string Titolo, glielo passo come parametro) E POI LA VADO A RECUPERARE PER INSERIRCI DENTRO LA RICETTA!


    //invocato a riga 159 di gui.FeedFrame
    public List<RicettaDTO> getRicetteRecenti(String username) {
        return Piattaforma.getInstance(null, null).getUltime5RicettePubbliche(username);
    }

    //invocato a riga 155 di gui.DettaglioRicettaFrame
    public boolean toggleLike(String username, int idRicetta) {
        Ricetta ricetta = new Ricetta(null, null, 0, false); // Creo un'istanza temporanea per accedere al metodo
        return ricetta.gestisciToggleLike(username, idRicetta);
    }

    //invocato a riga 155 di gui.DettaglioRicettaFrame
    public boolean aggiungiCommento(String username, int idRicetta, String testo) {
        entity.Ricetta ricetta = new entity.Ricetta(null, null, 0, false); // Creo un'istanza temporanea per accedere al metodo
        return ricetta.aggiungiCommento(username, idRicetta, testo);
    }


    //invocato a riga 61 di gui.RicettaRaccoltaFrame
    public List<RicettaDTO> getRicetteDaRaccolta(String titoloRaccolta, String username) {
        entity.Utente utente = new entity.Utente(username, null, null, null, null, null);
        return utente.getRicetteByRaccolta(titoloRaccolta, username);
    }

    /**
     * Ottiene le statistiche dell'utente
     * Controller -> Entity: Richiesta statistiche utente
     * Chiamata da AreaPersonaleFrame.getStatisticheUtente() linea 200
     * Implementata in Utente.getStatisticheUtente() linea 147
     */
    public StatisticheDTO getStatisticheUtente(String username) {
        entity.Utente utente = new entity.Utente(username, null, null, null, null, null);
        return utente.getStatisticheUtente(username);
    }


    public ProfiloUtenteDTO getProfiloUtente(String username) {
        entity.Utente utente = new entity.Utente(username, null, null, null, null, null);
        return utente.getProfiloUtente(username);
    }


    //Aggiorna il profilo dell'utente
    public boolean aggiornaProfiloUtente(ProfiloUtenteDTO profilo) {
        entity.Utente utente = new entity.Utente(profilo.getUsername(), null, null, null, null, null);
        return utente.aggiornaProfiloUtente(profilo);
    }

    /**
     * Genera il report sul numero di ricette pubblicate in un intervallo
     * Controller -> Entity: Richiesta numero ricette
     * Chiamata da AdminReportFrame.generaReportNumRicette() a riga 86
     * Implementata in Piattaforma.generaReportNumRicette() a riga 208
     */
    public int generaReportNumRicette(java.sql.Date dataInizio, java.sql.Date dataFine) {
        return Piattaforma.getInstance(null, null).generaReportNumRicette(dataInizio, dataFine);
    }

    /**
     * Genera il report sugli autori più attivi
     * Controller -> Entity: Richiesta autori più attivi
     * Chiamata da AdminReportFrame.generaReportAutori() riga 124
     * Implementata in Piattaforma.generaReportAutori() riga 218
     */
    public List<ReportAutoriDTO> generaReportAutori() {
        return Piattaforma.getInstance(null, null).generaReportAutori();
    }

    /**
     * Genera il report sui tag più utilizzati
     * Controller -> Entity: Richiesta tag più usati
     * Chiamata da  AdminReportFrame.generaReportTag() riga 134
     * Implementata in Piattaforma.generaReportTag() riga
     */
    public List<ReportTagDTO> generaReportTag() {
        return Piattaforma.getInstance(null, null).generaReportTag();
    }

    /**
     * Genera il report sulle ricette con più interazioni
     * Controller -> Entity: Richiesta ricette top interazioni
     * Chiamata da AdminReportFrame.generaReportTopRicette() riga 143
     * Implementata in Piattaforma.generaReportTopRicette()
     */
    public List<ReportTopRicetteDTO> generaReportTopRicette() {
        return Piattaforma.getInstance(null, null).generaReportTopRicette();
    }


}

