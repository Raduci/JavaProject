package model;

import java.util.Objects;

public abstract class Product {


    private int ID = 0;
    private String name;
    private double price;
    private int quantity;

    private String productType;

    public Product (){}

    public Product(int id, String name, double price, int quantity) {
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && quantity == product.quantity && Objects.equals(name, product.name) && productType == product.productType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, quantity, productType);
    }

    public static Product createProduct(int id, String name, double price, int quantity, double alcoholPercentage, int kcal) {
        if (alcoholPercentage > 0) {
            return new AlcoholicBeverage(id, name, price, quantity, alcoholPercentage);
        } else if (kcal > 0) {
            return new FoodProduct(id, name, price, quantity, kcal);
        } else {
            return null;
        }
}
}
