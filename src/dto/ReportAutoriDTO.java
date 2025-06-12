package dto;

public class ReportAutoriDTO {
    private String autore;
    private int numeroRicette;
    public ReportAutoriDTO(String autore, int numeroRicette) {
        this.autore = autore;
        this.numeroRicette = numeroRicette;
    }
    public String getAutore() {
        return autore;
    }
    public int getNumeroRicette() {
        return numeroRicette;
    }
} 