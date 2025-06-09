package entity;

import java.util.Date;

public class Commento {
    private String testo;
    private Date data;
    private Utente utente;

    public Commento(String testo, Utente utente ) {
        this.testo = testo;
        this.data = new Date();
        this.utente = utente;
    }

    public String getTesto() {
        return this.testo;
    }

    public Date getData() {
        return this.data;
    }

    public Utente getUtente() {
        return this.utente;
    }
}

