package entity;

public class Tag {
    private int id;
    private String nome;

    public Tag(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }
}
