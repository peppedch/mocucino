package entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ricetta {
    private String titolo;
    private String procedimento;
    private int tempo;
    private ArrayList<Tag> tags;
    private ArrayList<RicettaIngrediente> ricettaIngredienteList;
    private boolean visibility;
    private ArrayList<Like> likes;
    private ArrayList<Commento> comments;
    private Date dataPublicazione;
    private Utente utente;
    private int id;

    public Ricetta(String titolo, String procedimento, int tempo, boolean visibility) {
        this.titolo = titolo;
        this.procedimento = procedimento;
        this.tempo = tempo;
        this.visibility = visibility;
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.ricettaIngredienteList = new ArrayList<>();
        this.dataPublicazione = new Date();  // Data corrente
    }


    public String getTitolo() {

        return this.titolo;
    }

    public Utente getUtente() {
        return this.utente;
    }


    public boolean gestisciToggleLike(String username, int idRicetta) {
        database.LikeDAO dao = new database.LikeDAO();
        return dao.toggleLike(username, idRicetta);
    }

    public boolean aggiungiCommento(String username, int idRicetta, String testo) {
        database.CommentoDAO dao = new database.CommentoDAO();
        return dao.inserisciCommento(username, idRicetta, testo);
    }

    /**
     * Imposta l'ID della ricetta
     * Entity: Gestione stato interno
     * Chiamato da Utente.creaRicetta() a riga 54
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Aggiunge i tag alla ricetta
     * Entity -> DAO: Richiesta aggiunta tag
     * Chiamato da Utente.creaRicetta() a riga 54
     * Implementato in TagDAO.aggiungiTagARicetta()
     */
    public boolean aggiungiTag(List<String> tag) {
        return new database.TagDAO().aggiungiTagARicetta(this.id, tag);
    }

    /**
     * Aggiunge gli ingredienti alla ricetta
     * Entity -> DAO: Richiesta aggiunta ingredienti
     * Chiamato da Utente.creaRicetta() a riga 55
     * Implementato in IngredienteDAO.aggiungiIngredientiARicetta()
     */
    public boolean aggiungiIngredienti(List<dto.IngredienteDTO> ingredienti) {
        return new database.IngredienteDAO().aggiungiIngredientiARicetta(this.id, ingredienti);
    }
}