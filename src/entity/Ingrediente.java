package entity;

public class Ingrediente {
    private int id;
    private String nome;

    public Ingrediente(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getNome() {

        return nome;
    }
}