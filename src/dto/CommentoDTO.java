package dto;

import java.time.LocalDate;

public class CommentoDTO {
    private String autore;
    private String testo;
    private LocalDate data;

    public CommentoDTO(String autore, String testo, LocalDate data) {
        this.autore = autore;
        this.testo = testo;
        this.data = data;
    }

    public String getAutore() {
        return autore;
    }

    public String getTesto() {
        return testo;
    }

    public LocalDate getData() {
        return data;
    }
}

//servira nel costruttore DettaglioRicettaFrame, per modstrare lista 3 commenti piu recenti
//infatti siccome ho creato un package appositamente per le classi dto, in DettaglioRicettaFrame.java, che è
//nel package di gui, devo importare uesto modulo CommentoDTO che è in questo package "dto"!-> import dto.CommentoDTO;

