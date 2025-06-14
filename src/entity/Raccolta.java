package entity;

import java.util.ArrayList;

public class Raccolta {
    private String titolo;
    private String descrizione;
    private ArrayList<Ricetta> ricetteList;
    private int id;

    public Raccolta(String titolo, String descrizione, Ricetta ricetta) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.ricetteList = new ArrayList<Ricetta>();
        this.ricetteList.add(ricetta);
    }

    /**
     * Ottiene l'ID di una raccolta dato il suo titolo e l'username dell'utente
     * Entity -> DAO: Richiesta ID raccolta
     * Chiamato da GestoreController.getIdRaccoltaByTitolo()
     * Implementato in RaccoltaDAO.getIdRaccoltaByTitolo()
     */
    public static int getIdByTitolo(String titolo, String username) {
        return new database.RaccoltaDAO().getIdRaccoltaByTitolo(titolo, username);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
