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


      //Ottiene l'ID di una raccolta dato il suo titolo e l'username dell'utente
      //Entity -> DAO: Richiesta ID raccolta
      //invocato a riga 87 di controller.GestoreController
    public static int getIdByTitolo(String titolo, String username) {
        return new database.RaccoltaDAO().getIdRaccoltaByTitolo(titolo, username);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    /**
     * Aggiunge una ricetta alla raccolta
     * Entity -> DAO: Richiesta aggiunta ricetta
     * Chiamato da Utente.creaRicetta() a riga 50 di entity.Utente
     * Implementato in RaccoltaDAO.aggiungiRicettaARaccolta()
     */
    public boolean aggiungiRicetta(int ricettaId) {
        return new database.RaccoltaDAO().aggiungiRicettaARaccolta(this.id, ricettaId);
    }
}
