package controller;

import entity.Piattaforma;

import dto.UtenteDTO;

public class AccessoController {

    // Istanza privata statica (singleton)
    private static AccessoController instance;

    // Costruttore privato per impedire istanziazione esterna
    private AccessoController() {
    }

    // Metodo pubblico per ottenere l'istanza
    public static AccessoController getInstance() {
        if (instance == null) {
            instance = new AccessoController();
        }
        return instance;
    }

    //invocato a riga 80 gui.LoginFrame
    public UtenteDTO getUtenteAutenticato(String email, String password) {
        return Piattaforma.getInstance(null, null).getUtenteByCredenziali(email, password);
    }


    //invocato a riga 187 di gui.RegisterFrame
    public boolean registraUtente(UtenteDTO utenteDTO) {
        return Piattaforma.getInstance(null, null).registraUtente(utenteDTO);
    }


    public boolean autenticaAdmin(String username, String password) {
        return Piattaforma.getInstance(null, null).autenticaAdmin(username, password);
    }

}

