package controller;

import dto.RicettaDTO;
import entity.Piattaforma;
import dto.StatisticheDTO;
import dto.ProfiloUtenteDTO;
import dto.ReportAutoriDTO;
import dto.ReportTagDTO;
import dto.ReportTopRicetteDTO;
import java.sql.Date;

import java.util.List;

public class GestoreController {


    //invocato a riga 260 di gui.NuovaRicettaFrame e usato anche in gui.Areapersonaleframe a riga 257. SIMILE A getIdRaccoltaByTitolo PIU SOTTO, MA GIU HO SPIEGATO IL PERCHè DI AVERNE DUE SIMILI MA DIVERSE.
    public List<String> getRaccolteUtente(String username) {
        return Piattaforma.getInstance(null, null).getTitoliRaccolteByUtente(username);
    }

    //invocato a riga 282 di gui.NuovaRicettaFrame, gli passa la stringa della nuova raccolta che vuole creare username attuale. PER CREARE UNA RACCOLTA SERVE ANCHE IL TITOLO CHE VUOLE L'UTENTE, QUINDI NECESSARIAMENTE METODO DIVERSO.
    public boolean creaNuovaRaccolta(String nome, String username) {
        return Piattaforma.getInstance(null, null).creaRaccoltaPerUtente(nome, username);
    }

    //invocata a riga 314 di gui.NuovaRicettaFrame
    public boolean creaRicetta(RicettaDTO dto) {
        return Piattaforma.getInstance(null, null).creaRicetta(dto);
    }

    //invocato a riga 278, 289, 299 di gui.NuovaRicettaFrame
    public int getIdRaccoltaByTitolo(String titolo, String username) {
        return Piattaforma.getInstance(null, null).getIdRaccoltaByTitolo(titolo, username);
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
        return Piattaforma.getInstance(null, null).gestisciToggleLike(username, idRicetta);
    }

    //invocato a riga 155 di gui.DettaglioRicettaFrame
    public boolean aggiungiCommento(String username, int idRicetta, String testo) {
        return Piattaforma.getInstance(null, null).aggiungiCommento(username, idRicetta, testo);
    }


    //invocato a riga 61 di gui.RicettaRaccoltaFrame
    public List<RicettaDTO> getRicetteDaRaccolta(String titoloRaccolta, String username) {
        return Piattaforma.getInstance(null, null).getRicetteByRaccolta(titoloRaccolta, username);
    }

    /**
     * Ottiene le statistiche dell'utente
     * Controller -> Entity: Richiesta statistiche utente
     * Chiamata da AreaPersonaleFrame.getStatisticheUtente() [linea 200]
     * Implementata in Piattaforma.getStatisticheUtente() [linea 147]
     */
    public StatisticheDTO getStatisticheUtente(String username) {
        return Piattaforma.getInstance(null, null).getStatisticheUtente(username);
    }

    /**
     * Ottiene il profilo dell'utente
     * Controller -> Entity: Richiesta dati profilo utente
     * Chiamata da AreaPersonaleFrame.getProfiloUtente() [linea 147]
     * Implementata in Piattaforma.getProfiloUtente() [linea 147]
     */
    public ProfiloUtenteDTO getProfiloUtente(String username) {
        return Piattaforma.getInstance(null, null).getProfiloUtente(username);
    }

    /**
     * Aggiorna il profilo dell'utente
     * Controller -> Entity: Richiesta aggiornamento profilo utente
     * Chiamata da AreaPersonaleFrame.aggiornaProfiloUtente() [linea 147]
     * Implementata in Piattaforma.aggiornaProfiloUtente() [linea 147]
     */
    public boolean aggiornaProfiloUtente(ProfiloUtenteDTO profilo) {
        return Piattaforma.getInstance(null, null).aggiornaProfiloUtente(profilo);
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
     * Implementata in Piattaforma.generaReportTopRicette() [riga X]
     */
    public List<ReportTopRicetteDTO> generaReportTopRicette() {
        return Piattaforma.getInstance(null, null).generaReportTopRicette();
    }


}

