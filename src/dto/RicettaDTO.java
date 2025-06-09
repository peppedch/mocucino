package dto;

import java.util.List;

public class RicettaDTO {
    private String titolo;
    private String descrizione;
    private int tempoPreparazione;
    private List<IngredienteDTO> ingredienti;
    private List<String> tag;

    private String nomeRaccolta;

    private String autoreUsername;

    private int idRaccolta;

    public void setIdRaccolta(int idRaccolta) {
        this.idRaccolta = idRaccolta;
    }
    public int getIdRaccolta() {
        return idRaccolta;
    }



    public RicettaDTO(String titolo, String descrizione, int tempoPreparazione,
                      List<IngredienteDTO> ingredienti, List<String> tag) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.tempoPreparazione = tempoPreparazione;
        this.ingredienti = ingredienti;
        this.tag = tag;
    }

    // Getter
    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getTempoPreparazione() {
        return tempoPreparazione;
    }

    public List<IngredienteDTO> getIngredienti() {
        return ingredienti;
    }

    public List<String> getTag() {
        return tag;
    }

    //aggiunti per la raccolta selezionata in cui salvare la ricetta
    public String getNomeRaccolta() {
        return nomeRaccolta;
    }

    public void setNomeRaccolta(String nomeRaccolta) {
        this.nomeRaccolta = nomeRaccolta;
    }

    public void setAutoreUsername(String username) {
        this.autoreUsername = username;
    }

    public String getAutoreUsername() {
        return autoreUsername;
    }


    // toString per stampa/debug
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RicettaDTO{\n");
        sb.append("  Titolo: ").append(titolo).append("\n");
        sb.append("  Descrizione: ").append(descrizione).append("\n");
        sb.append("  Tempo: ").append(tempoPreparazione).append(" min\n");
        sb.append("  Ingredienti:\n");

        for (IngredienteDTO ing : ingredienti) {
            sb.append("    - ").append(ing).append("\n");
        }

        sb.append("  Tag: ").append(String.join(", ", tag)).append("\n");
        sb.append("}");

        return sb.toString();
    }
}
