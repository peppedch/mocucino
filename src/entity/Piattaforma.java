package entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import database.*;

import dto.RicettaDTO;
import dto.IngredienteDTO;


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


    public void ordinaRicette(){
        this.catalogoRicetta.sort(Comparator.comparing(Ricetta::getDataPublicazione).reversed());
    }

    public void mostraRicetteRecenti(){
        int limit=Math.min(5, this.catalogoRicetta.size());
        for (int i = 0; i < limit; i++) {
            if(catalogoRicetta.get(i).isVisibility())
                System.out.println(catalogoRicetta.get(i).toString());
        }
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

    //invocata a riga 23 di controller.GestoreController
    public boolean creaRicetta(RicettaDTO dto) {
        RicettaDAO ricettaDAO = new RicettaDAO();
        TagDAO tagDAO = new TagDAO();
        IngredienteDAO ingredienteDAO = new IngredienteDAO();
        RaccoltaDAO raccoltaDAO = new RaccoltaDAO();

        // Step 1 – crea ricetta e ottieni ID
        int ricettaId = ricettaDAO.createRicetta(dto);
        if (ricettaId == -1) return false;

        // Step 2 – ottieni ID raccolta in base al titolo e username
        int raccoltaId = raccoltaDAO.getIdRaccoltaByTitolo(dto.getNomeRaccolta(), dto.getAutoreUsername());
        if (raccoltaId == -1) return false;

        // Step 3 – aggiorna la FK della ricetta
        boolean okAssoc = raccoltaDAO.aggiungiRicettaARaccolta(raccoltaId, ricettaId);

        // Step 4 – salva ingredienti e tag
        boolean okTag = tagDAO.aggiungiTagARicetta(ricettaId, dto.getTag());
        boolean okIng = ingredienteDAO.aggiungiIngredientiARicetta(ricettaId, dto.getIngredienti());

        return okTag && okIng && okAssoc;   //returna false se qualcosa non è andato a buon fine
    }


    //invocato a riga 19 controller.  i serve lo username per mostrargli il feed personalizzato
    public Utente getUtenteByCredenziali(String email, String password) {
        UtenteDAO dao = new UtenteDAO();
        return dao.readUtente(email, password); // ritorna oggetto Utente se valido, altrimenti null
    }

    //invocato a riga 13 di controller.GestoreController, PER DUE CASI DIVERSI.
    public List<String> getTitoliRaccolteByUtente(String username) {
        RaccoltaDAO dao = new RaccoltaDAO();
        return dao.getTitoliRaccolteByUtente(username);
    }

    ////invocato a riga 18 di controller.GestoreController, gli passa stringa nuova raccolta e username attuale
    public boolean creaRaccoltaPerUtente(String nome, String username) {
        RaccoltaDAO dao = new RaccoltaDAO();
        return dao.createRaccolta(nome, username);
    }

    //invocato a riga 28 da controller.GestoreController
    public int getIdRaccoltaByTitolo(String titolo, String username) {
        RaccoltaDAO dao = new RaccoltaDAO();
        return dao.getIdRaccoltaByTitolo(titolo, username);
    }

    //invocato a riga 39 di controller.GestoreController
    public List<RicettaDTO> getUltime5RicettePubbliche(String username) {
        RicettaDAO dao = new RicettaDAO();
        return dao.getUltime5RicettePubbliche(username);
    }

    //invocato a riga 44 di controller.GestoreController
    /* DA ELIMINARE, NON USATO
    public RicettaDTO getRicettaCompletaByTitoloEAutore(String titolo, String autore) {
        RicettaDAO dao = new RicettaDAO();
        return dao.getRicettaCompletaByTitoloEAutore(titolo, autore);
    }
*/

    //invocato a riga 53 di controller.GestoreController
    public boolean gestisciToggleLike(String username, int idRicetta) {
        LikeDAO dao = new LikeDAO();
        return dao.toggleLike(username, idRicetta); // true se aggiunto, false se rimosso
    }

    //invocato a riga 57 di controller.GestoreController
    public boolean aggiungiCommento(String username, int idRicetta, String testo) {
        CommentoDAO dao = new CommentoDAO();
        return dao.inserisciCommento(username, idRicetta, testo);
    }

    //invocato a riga 64 di controller.GestoreController
    public List<RicettaDTO> getRicetteByRaccolta(String titoloRaccolta, String username) {
        return new RicettaDAO().getRicetteByRaccolta(titoloRaccolta, username);
    }





}
