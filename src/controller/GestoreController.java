package controller;

import dto.RicettaDTO;
import entity.Piattaforma;

import java.util.List;

public class GestoreController {


    //invocato a riga 262 di gui.NuovaRicettaFrame
    public List<String> getRaccolteUtente(String username) {
        return Piattaforma.getInstance(null, null).getTitoliRaccolteByUtente(username);
    }

    //invocato a riga 282 di gui.NuovaRicettaFrame, gli passa stringa nuova raccolta e username attuale
    public boolean creaNuovaRaccolta(String nome, String username) {
        return Piattaforma.getInstance(null, null).creaRaccoltaPerUtente(nome, username);
    }

    //invocata a riga 314 di gui.NuovaRicettaFrame
    public boolean creaRicetta(RicettaDTO dto) {
        return Piattaforma.getInstance(null, null).creaRicetta(dto);
    }

    //invocato a riga 278, 289, 299 di gui.NuovaRicettaFrame
    public int getIdRaccoltaByTitolo(String titolo, String username) {
        return Piattaforma.getInstance(null, null).getIdRaccoltaByTitolo(titolo, username);
    }
    //questa mi serve per gestirte i 3 casi di dove inserire la ricetta, per questo usata 3 volte.
    //è fondamentale recuperare l'id della raccolta che funge da fk per la ricetta e sapere la ricetta
    //in quale raccolta viene salvata. è stato fondamentale per fare "crea nuova raccolta" al momento
    //della pubblicazione di ricetta per permettere di crerare la raccolta e ad associargli subito dopo
    //la ricetta, il tutto in fase di creazione, senza mostrare form dopo. molto piu carino e compatto dal pov user


}

