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
     * Chiamato da GestoreController.creaRicetta() -> Utente.creaRicetta()
     * Entity -> DAO: RicettaDAO, RaccoltaDAO, TagDAO, IngredienteDAO
     */
    public boolean creaRicetta(dto.RicettaDTO dto) {
        database.RicettaDAO ricettaDAO = new database.RicettaDAO();
        database.TagDAO tagDAO = new database.TagDAO();
        database.IngredienteDAO ingredienteDAO = new database.IngredienteDAO();
        database.RaccoltaDAO raccoltaDAO = new database.RaccoltaDAO();

        // Step 1 – crea ricetta e ottieni ID
        int ricettaId = ricettaDAO.createRicetta(dto);
        if (ricettaId == -1) return false;

        // Step 2 – ottieni ID raccolta in base al titolo e username
        int raccoltaId = raccoltaDAO.getIdRaccoltaByTitolo(dto.getNomeRaccolta(), this.username);
        if (raccoltaId == -1) return false;

        // Step 3 – aggiorna la FK della ricetta
        boolean okAssoc = raccoltaDAO.aggiungiRicettaARaccolta(raccoltaId, ricettaId);

        // Step 4 – salva ingredienti e tag
        boolean okTag = tagDAO.aggiungiTagARicetta(ricettaId, dto.getTag());
        boolean okIng = ingredienteDAO.aggiungiIngredientiARicetta(ricettaId, dto.getIngredienti());

        return okTag && okIng && okAssoc;
    }

    /**
     * Ottiene i titoli delle raccolte dell'utente
     * Entity -> DAO: Richiesta titoli raccolte utente
     * Chiamato da GestoreController.getRaccolteUtente()
     * Implementato in RaccoltaDAO.getTitoliRaccolteByUtente()
     */
    public List<String> getTitoliRaccolte() {
        return new database.RaccoltaDAO().getTitoliRaccolteByUtente(this.username);
    }

    /**
     * Crea una nuova raccolta per l'utente
     * Entity -> DAO: Richiesta creazione raccolta
     * Chiamato da GestoreController.creaNuovaRaccolta()
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


    public List<dto.RicettaDTO> getRicetteByRaccolta(String titoloRaccolta, String username) {
        return new database.RicettaDAO().getRicetteByRaccolta(titoloRaccolta, username);
    }

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

    public dto.ProfiloUtenteDTO getProfiloUtente(String username) {
        return new database.UtenteDAO().getProfiloUtente(username);
    }

    public boolean aggiornaProfiloUtente(dto.ProfiloUtenteDTO profilo) {
        return new database.UtenteDAO().aggiornaProfiloUtente(profilo);
    }
}
