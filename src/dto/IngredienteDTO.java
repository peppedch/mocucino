package dto;

public class IngredienteDTO {
    private String nome;
    private String quantita;
    private String unita;

    public IngredienteDTO(String nome, String quantita, String unita) {
        this.nome = nome;
        this.quantita = quantita;
        this.unita = unita;
    }

    public String getNome() {
        return nome;
    }

    public String getQuantita() {
        return quantita;
    }

    public String getUnita() {
        return unita;
    }

    @Override
    public String toString() {
        return nome + ": " + quantita + " " + unita;
    }
}

//siccome abbiamo deciso di intendere l'ingrediente con nome, unita e misura, ritenuto opportuno di aggiungere
//questa classe dto, usata nella classe ricetta dto, in modo che dal db ricevo i dati in stringhe più organizzate

