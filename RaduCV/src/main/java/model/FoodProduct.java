package model;

import java.util.Objects;

public class FoodProduct extends Product {
    private int kcal;

    public FoodProduct() {}

    public FoodProduct(String productName, double productPrice, int productQuantity, int kcal){
        this.setName(productName);
        this.setPrice(productPrice);
        this.setQuantity(productQuantity);
        this.setKcal(kcal);
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }



    @Override
    public String toString() {
        return "Food product: " + this.getName() + " " +
                "price: " + this.getPrice() + " " +
                "quantity: " + this.getQuantity() + " " +
                "kcal: " + this.getKcal();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FoodProduct that = (FoodProduct) o;
        return kcal == that.kcal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(kcal);
    }

    public FoodProduct(int id, String name, double price, int quantity, int kcal) {
        this.setPrice(price);
        this.setID(id);
        this.setName(name);
        this.setQuantity(quantity);
        this.setKcal(kcal);
    }
}
