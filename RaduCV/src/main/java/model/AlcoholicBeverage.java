package model;

import java.util.Objects;

public class AlcoholicBeverage extends Product {
    private double alcoholPercentage;

    public AlcoholicBeverage() {
    }
    public double getAlcoholPercentage() {
        return this.alcoholPercentage;
    }

    public void setAlcoholPercentage(double alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
    }
    public String toString() {
        String name = this.getName();
        return "Alcoholic Beverage: " + this.getName() + " price: " + this.getPrice() + " quantity: " + this.getQuantity() + " alcoholPercentage: " + this.alcoholPercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AlcoholicBeverage that = (AlcoholicBeverage) o;
        return Double.compare(that.alcoholPercentage, alcoholPercentage) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(alcoholPercentage);
    }

    public AlcoholicBeverage(int id, String name, double price, int quantity, double alcoholPercentage) {
        this.setPrice(price);
        this.setID(id);
        this.setName(name);
        this.setQuantity(quantity);
        this.setAlcoholPercentage(alcoholPercentage);


    }
}
