package entity;
import java.util.ArrayList;
import java.util.Date;

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
}