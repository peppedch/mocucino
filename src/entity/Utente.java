package entity;

import java.util.ArrayList;
import java.util.List;

public class Utente {
    //tutti a private.
    private String nome;
    private String cognome;
    private String email;
    private String username;
    private String password;
    private ProfiloPersonale profiloPersonale;
    private ArrayList<Raccolta> raccolteList;
    private boolean visibility;

    public Utente(String username, String nome, String cognome, String email, String password, Raccolta raccoltaDefault) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.raccolteList = new ArrayList<>();
        this.raccolteList.addFirst(raccoltaDefault);
    }


    /**
     * Crea una ricetta a partire da un RicettaDTO, salva ingredienti e tag, e la associa alla raccolta.
     * Responsabilità dell'Utente secondo GRASP.
     * Chiamato da GestoreController.creaRicetta() -> Utente.creaRicetta() A RIGA 83!!!
     * Entity -> DAO: RicettaDAO, RaccoltaDAO, TagDAO, IngredienteDAO
     */
    public boolean creaRicetta(dto.RicettaDTO dto) {
        database.RicettaDAO ricettaDAO = new database.RicettaDAO();
        database.RaccoltaDAO raccoltaDAO = new database.RaccoltaDAO();

        // Step 1 – creo ricetta e ottiengo ID
        int ricettaId = ricettaDAO.createRicetta(dto);
        if (ricettaId == -1) return false;

        // Step 2 – uso l'ID della raccolta già presente nel DTO che già abbiamo recuperato, non ho bisogno di fare una nuova query, basta settarlo solo.
        int raccoltaId = dto.getIdRaccolta();
        if (raccoltaId == -1) return false;

        // Step 3 – delego le responsabilità alle classi appropriate. L'utente essendo contenitore di raccolte, crea la raccolta a livello entity.
        // La raccolta si occupa di aggiungere la ricetta a se stessa
        Raccolta raccolta = new Raccolta(null, null, null);
        raccolta.setId(raccoltaId);
        boolean okAssoc = raccolta.aggiungiRicetta(ricettaId);

        //L'utente ha la responsabiltà di creare la ricetta, ne è l'autore. E La ricetta si occupa di gestire i propri tag e ingredienti
        Ricetta ricetta = new Ricetta(dto.getTitolo(), dto.getDescrizione(), dto.getTempoPreparazione(), dto.getVisibilita());
        ricetta.setId(ricettaId);
        boolean okTag = ricetta.aggiungiTag(dto.getTag());
        boolean okIng = ricetta.aggiungiIngredienti(dto.getIngredienti());

        return okTag && okIng && okAssoc;
    }

    /**
     * Ottiene i titoli delle raccolte dell'utente
     * Entity -> DAO: Richiesta titoli raccolte utente
     * Chiamato da GestoreController.getRaccolteUtente() a riga 71, per 2 cose diverse
     * Implementato in RaccoltaDAO.getTitoliRaccolteByUtente() a riga 15
     */
    public List<String> getTitoliRaccolte() {
        return new database.RaccoltaDAO().getTitoliRaccolteByUtente(this.username);
    }

    /**
     * Crea una nuova raccolta per l'utente
     * Entity -> DAO: Richiesta creazione raccolta
     * Chiamato da GestoreController.creaNuovaRaccolta() a riga 77
     * Implementato in RaccoltaDAO.createRaccolta()
     */
    public boolean creaRaccolta(String nome) {
        return new database.RaccoltaDAO().createRaccolta(nome, this.username);
    }

    //getter e setter
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return this.cognome;
    }



    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //invocato a riga 136 di controller.GestoreController
    public dto.StatisticheDTO getStatisticheUtente(String username) {
        List<dto.RicettaDTO> ricette = new database.RicettaDAO().getRicetteByUtente(username);
        int totalLikes = 0;
        int totalComments = 0;
        String mostLikedRecipe = "Nessuna ricetta";
        int maxLikes = 0;

        for (dto.RicettaDTO ricetta : ricette) {
            totalLikes += ricetta.getNumeroLike();
            totalComments += ricetta.getNumCommenti();
            if (ricetta.getNumeroLike() > maxLikes) {
                maxLikes = ricetta.getNumeroLike();
                mostLikedRecipe = ricetta.getTitolo();
            }
        }

        return new dto.StatisticheDTO(totalLikes, totalComments, mostLikedRecipe);
    }

    //invocato a riga 142 di controller.GestoreController
    public dto.ProfiloUtenteDTO getProfiloUtente(String username) {
        return new database.UtenteDAO().getProfiloUtente(username);
    }
    //riga 147 di controller.GestoreController
    public boolean aggiornaProfiloUtente(dto.ProfiloUtenteDTO profilo) {
        return new database.UtenteDAO().aggiornaProfiloUtente(profilo);
    }

    /**
     * Ottiene la lista delle raccolte dell'utente
     * Entity -> Controller: Fornisce accesso alle raccolte
     * Chiamato da GestoreController.getIdRaccoltaDefault() a riga 87
     */
    public ArrayList<Raccolta> getRaccolteList() {
        return this.raccolteList;
    }
}
