package controller;

import dto.RicettaDTO;
import dto.UtenteDTO;
import entity.Piattaforma;
import entity.Raccolta;
import entity.Ricetta;
import entity.Utente;
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
    public void setUtenteCorrente(UtenteDTO utenteDTO) {
        if (utenteDTO != null) {
            // Creiamo l'oggetto Utente qui, nel controller, perche è il controller che ha la responsabilità di creare l'oggetto Utente, e non posso farlo nella gui.
            // La raccolta di default viene creata dal trigger nel database, quindi recuperiamo semplicemente il suo ID
            int idRaccoltaDefault = Raccolta.getIdByTitolo("Default", utenteDTO.getUsername());     //come parametri il titolo della raccolta e lo usernme dell'utente
            //per debug
                    System.out.println("Id raccolta deafult: " + idRaccoltaDefault);    //checkka nel db nella table raccolte.
            if (idRaccoltaDefault != -1) {  // Verifichiamo che l'ID sia valido
                Raccolta raccoltaDefault = new Raccolta("Default", "Raccolta automatica", null);    //oltre a db abbiamo "persistenza" anche in memoria
                raccoltaDefault.setId(idRaccoltaDefault);
                this.utenteCorrente = new Utente(
                    utenteDTO.getUsername(),
                    utenteDTO.getNome(),
                    utenteDTO.getCognome(),
                    utenteDTO.getEmail(),
                    utenteDTO.getPassword(),
                    raccoltaDefault
                );
            } else {
                // Se non troviamo la raccolta di default, qualcosa è andato storto
                System.err.println("Errore: raccolta di default non trovata per l'utente " + utenteDTO.getUsername());
                this.utenteCorrente = null;
            }
        } else {
            this.utenteCorrente = null;
        }
    }

    //invocato a riga 265 di gui.NuovaRicettaFrame e usato anche in gui.Areapersonaleframe a riga 268. SIMILE A getIdRaccoltaByTitolo PIU SOTTO, MA GIU HO SPIEGATO IL PERCHè DI AVERNE DUE SIMILI MA DIVERSE.
    public List<String> getRaccolteUtente(String username) {
        if (utenteCorrente == null) return new ArrayList<>();
        return utenteCorrente.getTitoliRaccolte();      //è l'utente che deve accedere alle sue raccolte, non la piattaforma. Quindi è l'utente che ha la responsabilità di gestire le sue raccolte. Quinid a livello entity chiamo il metodo getTitoliRaccolte() dell'utente, che a sua volta chiama il DAO per recuperare i titoli delle raccolte dal database.
    }

    //invocato a riga 286 di gui.NuovaRicettaFrame, gli passa la stringa della nuova raccolta che vuole creare username attuale. PER CREARE UNA RACCOLTA SERVE ANCHE IL TITOLO CHE VUOLE L'UTENTE, QUINDI NECESSARIAMENTE METODO DIVERSO.
    public boolean creaNuovaRaccolta(String nome, String username) {
        if (utenteCorrente == null) return false;
        return utenteCorrente.creaRaccolta(nome);
    }

    //invocata a riga 328 di gui.NuovaRicettaFrame
    public boolean creaRicetta(dto.RicettaDTO dto) {
        if (utenteCorrente == null) return false;
        return utenteCorrente.creaRicetta(dto);
    }

    //invocato a riga 278, 289, 299 di gui.NuovaRicettaFrame
    public int getIdRaccoltaByTitolo(String titolo, String username) {
        return Raccolta.getIdByTitolo(titolo, username);    //l'id della raccolta mi serve come fk per la ricetta, quindi lo devo recuperare, per salvare la ricetta nella giusta raccolta.
    }
    //questa sopra mi serve per gestirte i 3 casi di dove inserire la ricetta, è necessario oltre a getRaccolteUtente(username)?
    // sì! qui devo necessariamente passare il titolo della raccolta per due casi in particolare:
    //CASO 1: Salvare in raccolta esistente. Perchè, proprio da getRaccolteUtente(), inizalmente all'utente mostro le sue raccolte sottoforma di titoli, non ID, essendo per lui piu intuitivo. DA qui lui deve scegliere in quale titolo di raccolta salvare la ricetta.  Quindi quando "seleziona" una raccolta in cui salvare la ricetta, sta "selezionando" il TITOLO della raccolta. Quindi devo passare il titolo come parametro per ottenerne l'id.
    //QUESTO PRECLUDE CHE NON CI SIANO PIU RACCOLTE CON LO STESSO NOME! ED INFATTI è UN VINCOLO GIA MESSO NEL DB, QUINDI NON PUò ESSERCI UN PROBLEMA DI AMBIGUITà.
    //CASO 2: Creare nuova raccolta. Perchè, al momento della creazione della ricetta, può creare anche la raccola in cui salvare la ricetta; devo passargli sempre il titolo che l'utente vuole

    // Metodo per ottenere l'ID della raccolta di default dell'utente corrente
    public int getIdRaccoltaDefault() {
        if (utenteCorrente != null && !utenteCorrente.getRaccolteList().isEmpty()) {
            return utenteCorrente.getRaccolteList().get(0).getId();  // La prima raccolta è sempre quella di default
        }
        return -1;
    }

    //invocato a riga 159 di gui.FeedFrame
    public List<RicettaDTO> getRicetteRecenti(String username) {
        return Piattaforma.getInstance(null, null).getUltime5RicettePubbliche(username);
    }

    //invocato a riga 156 di gui.DettaglioRicettaFrame
    public boolean toggleLike(String username, int idRicetta) {
        Ricetta ricetta = new Ricetta(null, null, 0, false); // Creo un'istanza temporanea per accedere al metodo
        return ricetta.gestisciToggleLike(username, idRicetta);
    }

    //invocato a riga 181 di gui.DettaglioRicettaFrame
    public boolean aggiungiCommento(String username, int idRicetta, String testo) {
        entity.Ricetta ricetta = new entity.Ricetta(null, null, 0, false); // Creo un'istanza temporanea per accedere al metodo
        return ricetta.aggiungiCommento(username, idRicetta, testo);
    }


    //invocato a riga 61 di gui.RicettaRaccoltaFrame
    public List<RicettaDTO> getRicetteDaRaccolta(String titoloRaccolta, String username) {
        Raccolta raccolta = new Raccolta(titoloRaccolta, null, null);
        raccolta.setUsername(username);
        return raccolta.getRicette();
    }

    /**
     * Ottiene le statistiche dell'utente
     * Controller -> Entity: Richiesta statistiche utente
     * Chiamata da AreaPersonaleFrame.getStatisticheUtente() linea 229
     * Implementata in Utente.getStatisticheUtente() lineea 125
     */
    public StatisticheDTO getStatisticheUtente(String username) {
        entity.Utente utente = new entity.Utente(username, null, null, null, null, null);
        return utente.getStatisticheUtente(username);
    }

    //invocato a riga 122 di gui.AreaPersonaleFrame
    public ProfiloUtenteDTO getProfiloUtente(String username) {
        entity.Utente utente = new entity.Utente(username, null, null, null, null, null);
        return utente.getProfiloUtente(username);
    }


    //Aggiorna il profilo dell'utente, inovocato a riga 206 di gui.AreaPersonaleFrame
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

