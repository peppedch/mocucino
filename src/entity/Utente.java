package entity;

import java.util.ArrayList;
import java.util.List;
import dto.StatisticheDTO;
import dto.RicettaDTO;

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



     // Crea una ricetta a partire da un RicettaDTO, salva ingredienti e tag, e la associa alla raccolta.
     // Responsabilità dell'Utente secondo GRASP.
     //Chiamato da GestoreController.creaRicetta() A RIGA 83!!!
     // Entity a DAO: RicettaDAO, RaccoltaDAO, TagDAO, IngredienteDAO

    public boolean creaRicetta(dto.RicettaDTO dto) {
        database.RicettaDAO ricettaDAO = new database.RicettaDAO();
        database.RaccoltaDAO raccoltaDAO = new database.RaccoltaDAO();  //sue responsabilità. l'utente è autore di una ricetta e ha visibilità sulle SUE raccolte.

        // Step 1 – creo ricetta e ottiengo ID. è la responsabilità dell'utente creare una ricetta. creo a livello entity e db
        Ricetta ricetta = new Ricetta(dto.getTitolo(), dto.getDescrizione(), dto.getTempoPreparazione(), dto.getVisibilita());
        int ricettaId = ricettaDAO.createRicetta(dto);
        if (ricettaId == -1) return false;

        // Step 2 – uso l'ID della raccolta già presente nel DTO di ricetta che già abbiamo recuperato in fase di selezione della raccolta, non ho bisogno di fare una nuova query, basta settarlo solo.
        int raccoltaId = dto.getIdRaccolta();
        if (raccoltaId == -1) return false;

        // Step 3 – delego le responsabilità alle classi appropriate. L'utente essendo contenitore di raccolte, crea la raccolta a livello entity.
        // La raccolta si occupa di aggiungere la ricetta, appena creata, a se stessa
        Raccolta raccolta = new Raccolta(dto.getNomeRaccolta(), null, ricetta);     //la descrizione l'abbiamo gestita di default, sia se la crea utente che non, scelta progettuale, per evitare si inseriscano parolacce e implementare un sistema di moderazione. chiaramente si potrebbe fare diversamente si potrebbe pensare lo stesso per gli altri inserimenti, ma bisogna fare un trade off e valutarne costi-benefici, porof Fasolino docet.
        raccolta.setId(raccoltaId);
        boolean okAssoc = raccolta.aggiungiRicetta(ricettaId);  //creo a livello entity la raccolta e gli associo la ricetta. a livello db la raccolta già è stata creata, vedere in nuovaricettaframe.

        //L'utente ha la responsabiltà di creare la ricetta, fatto sopra a livello entity, ne è l'autore. E La ricetta, a sua volta, si occupa di gestire i propri tag e ingredienti, ha visbilità su di essi.

        ricetta.setId(ricettaId);   //restituita dal database sopra in fase di creazione della ricetta.
        boolean okTag = ricetta.aggiungiTag(dto.getTag());  //ricetta appena creata gestisce i suoi tag e ingredienti che gli sono stati passati come dto dal controller.
        boolean okIng = ricetta.aggiungiIngredienti(dto.getIngredienti());

        return okTag && okIng && okAssoc;       //scelta progettuale: se uno dei 3 fallisce, non creo la ricetta. aggiungo la ricetta nel mio sistema solo se sono sicuro che dopo la pubblicazione tengo traccia di tutto, raccogliendo tutti i dati.
    }


     //Ottiene i titoli delle raccolte dell'utente
     //Entity a DAO: Richiesta titoli raccolte utente
     //Chiamato da GestoreController.getRaccolteUtente() a riga 71, per 2 cose diverse
     //Implementato in RaccoltaDAO.getTitoliRaccolteByUtente() a riga 15

    public List<String> getTitoliRaccolte() {
        return new database.RaccoltaDAO().getTitoliRaccolteByUtente(this.username);
    }


     //Crea una nuova raccolta per l'utente
     //Entity a DAO: Richiesta creazione raccolta
     //Chiamato da GestoreController.creaNuovaRaccolta() a riga 77
     //Implementato in RaccoltaDAO.createRaccolta()

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


    public StatisticheDTO getStatisticheUtente(String username) {
    List<RicettaDTO> ricette = new database.RicettaDAO().getRicetteByUtente(username);
    return calcolaStatistiche(ricette);
    }

    public StatisticheDTO calcolaStatistiche(List<RicettaDTO> ricette) {
        int totalLikes = 0;
        int totalComments = 0;
        String mostLikedRecipe = "Nessuna ricetta";
        int maxLikes = 0;

        for (RicettaDTO ricetta : ricette) {
            totalLikes += ricetta.getNumeroLike();
            totalComments += ricetta.getNumCommenti();
            if (ricetta.getNumeroLike() > maxLikes) {
                maxLikes = ricetta.getNumeroLike();
                mostLikedRecipe = ricetta.getTitolo();
            }
        }

        return new StatisticheDTO(totalLikes, totalComments, mostLikedRecipe);
    }

    //invocato a riga 142 di controller.GestoreController
    public dto.ProfiloUtenteDTO getProfiloUtente(String username) {
        return new database.UtenteDAO().getProfiloUtente(username);
    }

     //Ottiene la lista delle raccolte dell'utente
     //Entity a Controller: Fornisce accesso alle raccolte
     //Chiamato da GestoreController.getIdRaccoltaDefault() a riga 87

    public ArrayList<Raccolta> getRaccolteList() {
        return this.raccolteList;
    }
}
