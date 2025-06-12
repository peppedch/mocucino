package dto;

public class ReportTopRicetteDTO {
    private String titoloRicetta;
    private int numeroInterazioni;
    public ReportTopRicetteDTO(String titoloRicetta, int numeroInterazioni) {
        this.titoloRicetta = titoloRicetta;
        this.numeroInterazioni = numeroInterazioni;
    }
    public String getTitoloRicetta() {
        return titoloRicetta;
    }
    public int getNumeroInterazioni() {
        return numeroInterazioni;
    }
} 