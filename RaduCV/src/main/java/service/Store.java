package service;


import java.util.*;
import java.util.stream.IntStream;

import exception.InexistentProductException;
import exception.TooManyProductsException;
import model.Product;

public class Store {

    private static Store INSTANCE;

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public List<Product> getProducts(){
        return products;
    }

    private List<Product> products = new ArrayList<>();

    private Store(){}

    public static final Store getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Store();
        }

        return INSTANCE;
    }



    public void addProducts(Product product) {
        if(this.products.size() >=20){
            throw new TooManyProductsException();
        }else{
            this.products.add(product);
        }
    }



    public void displayProducts() {
        List productList = this.products;
        if(!productList.isEmpty()){
            IntStream.range(0, productList.size())
                    .forEach(i -> System.out.println("[" + i + "]" +": " + productList.get(i) + " "));
        }else{
            System.out.println("You don't have any products in your store");
        }
    }

    public void changeProduct(Product product, String productName, double productPrice, int productQuantity){
        for (Product p: products) {
            if(p == product){
                p.setName((productName));
                p.setPrice((productPrice));
                p.setQuantity((productQuantity));
                Scanner input = new Scanner(System.in);
            }
        }
    }

    public Product searchProduct(String productName){
        for (Product p: products){
            Product product;
          do{
              if(!products.iterator().hasNext()){
                  throw new InexistentProductException(productName);
              }

              product = products.iterator().next();
          }while(!p.getName().equalsIgnoreCase(productName));
            return product;
        }
        return (Product) products;
    }

    public void deleteProduct(Product product) {
        String productName = product.getName();
        for(int i = 0; i < this.products.size(); ++i) {
            if (((Product)this.products.get(i)).getName().equals(productName)) {
                this.products.remove(this.products.get(i));
            }
        }
    }





}
