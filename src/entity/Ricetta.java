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

    public boolean toggleLike(Like like){
        if (this.likes.contains(like)) {
            this.likes.remove(like);
            return false;
        } else {
            likes.add(like);
            return true;
        }
    }

    public void addCommento(Commento commento){
        comments.add(commento);
    }

    public void aggiungiIntestatario(Utente utente) {
        this.utente = utente;
    }

    public void addTag(Tag tag){
        this.tags.add(tag);
    }

    public void addRicettaIngrediente(RicettaIngrediente ricettaIngrediente){
        this.ricettaIngredienteList.add(ricettaIngrediente);
    }

    ///  metodi getter
    public int getNumLikes(){
        return this.likes.size();
    }

    public int getNumComments(){
        return this.comments.size();
    }

    public String getTitolo() {
        return this.titolo;
    }

    public String getProcedimento() {
        return this.procedimento;
    }

    public int getTempo() {
        return this.tempo;
    }

    public boolean getVisibility() {
        return this.visibility;
    }

    public Date getDataPublicazione() {
        return this.dataPublicazione;
    }

    public Utente getUtente() {
        return this.utente;
    }

    public boolean isVisibility() {
        return visibility;
    }

}