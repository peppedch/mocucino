package controller;

import entity.Piattaforma;
import entity.Utente;


public class AccessoController {

    //invocato a riga 86 di gui.LoginFrame
    public boolean autenticaUtente(String email, String password) {
        return Piattaforma.getInstance(null, null).autenticaUtente(email, password);
    }

    //invocato a riga 194 di gui.RegisterFrame
    public boolean registraUtente(Utente nuovoUtente) {
        return Piattaforma.getInstance(null, null).registraUtente(nuovoUtente);
    }

    //invocato a riga 84 gui.LoginFrame
    public Utente getUtenteAutenticato(String email, String password) {
        return Piattaforma.getInstance(null, null).getUtenteByCredenziali(email, password);
    }



}

