package dto;

public class ReportTagDTO {
    private String tag;
    private int conteggio;
    public ReportTagDTO(String tag, int conteggio) {
        this.tag = tag;
        this.conteggio = conteggio;
    }
    public String getTag() {
        return tag;
    }
    public int getConteggio() {
        return conteggio;
    }
} 