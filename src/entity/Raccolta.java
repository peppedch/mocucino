package entity;

import java.util.ArrayList;
import java.util.List;

public class Raccolta {
    private String titolo;
    private String descrizione;
    private ArrayList<Ricetta> ricetteList;
    private int id;
    private String username;

    public Raccolta(String titolo, String descrizione, Ricetta ricetta) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.ricetteList = new ArrayList<Ricetta>();
        if (ricetta != null) {
            this.ricetteList.add(ricetta);
        }
    }

    public void setUsername(String username) {
        this.username = username;
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


     //Aggiunge una ricetta alla raccolta
     //Entity -> DAO: Richiesta aggiunta ricetta
     //Chiamato da Utente.creaRicetta() a riga 50 di entity.Utente
     //Implementato in RaccoltaDAO.aggiungiRicettaARaccolta()

    public boolean aggiungiRicetta(int ricettaId) {     //raccolta ha visbilità sulle sue ricette, quindi è sua la responsabilota di aggiungere la ricetta nella rispettivaraccolta
        return new database.RaccoltaDAO().aggiungiRicettaARaccolta(this.id, ricettaId);
    }


     //Ottiene le ricette contenute nella raccolta
     //Entity -> DAO: Richiesta ricette della raccolta
     //Chiamato da GestoreController.getRicetteDaRaccolta()
     //Implementato in RicettaDAO.getRicetteByRaccolta()

    public List<dto.RicettaDTO> getRicette() {
        return new database.RicettaDAO().getRicetteByRaccolta(this.titolo, this.username);
    }
}
