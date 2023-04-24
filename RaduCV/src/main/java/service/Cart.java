package service;

import model.AlcoholicBeverage;
import model.FoodProduct;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Cart {
    private static Cart INSTANCE;

    private List<Product> productCartList;

    private Cart(){
        this.productCartList = new ArrayList<>();
    }

    public static final Cart getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Cart();
        }
        return INSTANCE;
    }

    public void addProduct(Product product) {
        productCartList.add(product);
    }


}





