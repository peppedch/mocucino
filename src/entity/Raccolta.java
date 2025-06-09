package entity;

import java.util.ArrayList;

public class Raccolta {
    private String titolo;
    private String descrizione;
    private ArrayList<Ricetta> ricetteList;

    public Raccolta(String titolo, String descrizione, Ricetta ricetta) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.ricetteList = new ArrayList<Ricetta>();
        this.ricetteList.add(ricetta);
    }

    public void aggiungiRicettaARaccolta(Ricetta ricetta) {
        this.ricetteList.add(ricetta);
    }

    public ArrayList<Ricetta> getRicetteList() {
        return ricetteList;
    }


}
