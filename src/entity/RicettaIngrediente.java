package entity;

public class RicettaIngrediente {
    private int quantity;
    private int unit;
    private Ingrediente ingrediente;

    public RicettaIngrediente(int quantity, int unit) {
        this.quantity = quantity;
        this.unit = unit;
    }

    public void aggiungiIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getUnit(){
        return unit;
    }

    public String getIngrediente() {
        return ingrediente.getNome();
    }
}
