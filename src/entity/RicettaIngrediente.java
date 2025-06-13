package entity;

public class RicettaIngrediente {
    private int quantity;
    private int unit;
    private Ingrediente ingrediente;

    public RicettaIngrediente(int quantity, int unit) {
        this.quantity = quantity;
        this.unit = unit;
    }

    public int getUnit(){
        return unit;
    }

}
