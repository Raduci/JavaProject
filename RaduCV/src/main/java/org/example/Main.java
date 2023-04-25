package org.example;

import exception.InexistentProductException;
import model.*;
import service.DBConnector;
import service.Store;


import java.sql.*;
import java.util.Date;
import java.util.Scanner;
import java.util.*;




public class Main {

    private static List<Product> cartList = new ArrayList<>();

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/storedb";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    static Connection connection;
    static PreparedStatement pStatement;
    static Statement statement;
    public static Date currentDate = new Date();
    public static void main(String[] args) throws InterruptedException, SQLException {


            UserManager userManager = UserManager.getInstance();

            Scanner input = new Scanner(System.in);
            displayWelcomeMessage();
            displayBeforeLoginMenu();


            while (true) {
                while (UserManager.getAuthenticatedUser() == null) {
                    userManager.printUserIfAuthenticated();
                    System.out.print("Enter option: ");
                    int choice = input.nextInt();
                    switch (choice) {
                        case 0 -> displayBeforeLoginMenu();
                        case 1 -> {
                            displayManageAccountMenu();
                            System.out.println("Enter option");
                            choice = input.nextInt();
                            switch (choice) {
                                case 1 -> register(userManager, input);
                                case 2 -> login(userManager, input);
                                case 3 -> deleteAccount(input);
                                default -> System.out.println("Invalid Option");
                            }
                        }
                        case 2 -> {
                            displayManageStoreMenu();
                            System.out.println("Enter option");
                            choice = input.nextInt();
                            switch (choice) {
                                case 0 -> displayManageStoreMenu();
                                case 1 -> addProductStore(input);
                                case 2 -> displayProducts();
                                case 3 -> changeProduct(input);
                                case 4 -> deleteProduct(input);
                                default -> System.out.println("Invalid Option");
                            }
                        }
                        case 3 -> {
                            System.out.println("Have a great day!");
                            System.exit(0);
                        }
                        default -> System.out.println("Invalid option!");
                    }
                }
                displayAfterLoginMenu();
                while (UserManager.getAuthenticatedUser() != null) {
                    userManager.printUserIfAuthenticated();
                    System.out.println("Enter option: ");
                    int choice = input.nextInt();
                    switch (choice) {
                        case 0 -> displayAfterLoginMenu();
                        case 1 -> addProductToCart(input);
                        case 2 -> displayProductsFromCart();
                        case 3 -> calculateTotalPrice();
                        case 4 -> displayProducts();
                        case 5 -> Checkout(input);
                        case 6 -> userManager.logout();
                        default -> System.out.println("Invalid option");
                    }
                }
            }
        }


    private static void register(UserManager userManager, Scanner input) {
        System.out.println("Enter username: ");
        User user = new User();
        user.setUsername(input.next());

        System.out.println("Enter password: ");
        user.setPassword(input.next());

        System.out.println("Enter your name: ");
        input.nextLine();
        user.setName(input.nextLine());

        System.out.println("Enter your email: ");
        user.setEmail(input.next());

        System.out.println("Enter your phone number: ");
        user.setPhoneNumber(input.next());

        try {
            DBConnector.getInstance().addUsersToDB(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        userManager.addUser(user);
    }

    private static void login(UserManager userManager, Scanner input) throws SQLException {
        System.out.print("Enter username: ");
        String userName = input.next();

        System.out.print("Enter password: ");
        String password = input.next();

        User user = new User(userName, password);
        boolean isAuthenticated = userManager.authenticate(user);

        if (isAuthenticated) {
            DBConnector.getInstance().loginUsersIntoStore(user);
        } else {
            System.out.println("Invalid username or passwordl!");
        }
    }

    private static void Checkout(Scanner input) throws InterruptedException {
        if(UserManager.getAuthenticatedUser() != null){
            System.out.println("Enter the adress: ");
            input.nextLine();
            String adress = input.nextLine();

            System.out.println("Choose payment method: ");
            System.out.println("1. Card");
            System.out.println("2. Cash");

            int paymentMethod = input.nextInt();
            input.nextLine();
            switch (paymentMethod) {
                case 1 -> Card(input, adress);
                case 2 -> Cash(adress);
                default -> System.out.println("Invalid payment method!");
            }
        }else{
            System.out.println("You must be logged in to continue to checkout.");
        }
    }

    private static void Cash(String adress) {
        System.out.println("Cash payment has been selected.");
        System.out.println("Order completed and sent to the address: " + adress);
    }

    private static void Card(Scanner input, String adress) throws InterruptedException {
        System.out.println("Enter the card number (16 digits):");
        String cardNumber = input.nextLine();
        if (cardNumber.length() != 16) {
            System.out.println("The card number must have 16 digits");

        }

        System.out.println("Enter the card expiration date (month/year): ");
        String expDate = input.nextLine();
        if (!expDate.matches("^(0[1-9]|1[0-2])/\\d{2}$")) {
            System.out.println("The expiration date must be in the 'month/year' format!");

        }

        System.out.println("Enter the card's CVV code (3 digits):");
        String CVVCode = input.nextLine();
        if (CVVCode.length() != 3 || !CVVCode.matches("\\d+")) {
            System.out.println("The CVV code must have 3 digits.");
        }
        System.out.println("Processing payment..");
        for (int i = 0; i < 3; i++) {
            Thread.sleep(3000);
            System.out.println("...");
        }

        Thread.sleep(3000);
        System.out.println(currentDate);
        System.out.println("The card payment was successful.");
        System.out.println("Order completed and sent to the address: " + adress);
    }

    private static void displayWelcomeMessage() {
        System.out.println("Welcome to our store!");
        System.out.println("Please choose one of the options below.");
    }

    private static void displayBeforeLoginMenu() {
        System.out.println("0. Display the command menu again.");
        System.out.println("1. Manage account: ");
        System.out.println("2. Manage products: ");
        System.out.println("3. Exit program.");
    }

    private static void displayManageAccountMenu() {
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Delete account");
    }

    public static void displayManageStoreMenu() {
        System.out.println("1. Add a product to the store");
        System.out.println("2. Display all products");
        System.out.println("3. Change product");
        System.out.println("4. Delete product");
    }

    private static void displayAfterLoginMenu() {
        UserManager.printWelcomeMessageIfAuthenticated();
        System.out.println("0.Display the command menu agan.");
        System.out.println("1.Add a product from store to your shopping cart.");
        System.out.println("2.Display current items from shopping cart");
        System.out.println("3.Display the total price of your shopping cart.");
        System.out.println("4.Display the products that are available in the store.");
        System.out.println("5.Checkout.");
        System.out.println("6. Logout.");
    }

    private static void addProductStore(Scanner input) throws SQLException {
        System.out.println("Choose what product you want to add in the store");
        System.out.println("1. Food Product");
        System.out.println("2. AlcoholicBeverage");
        int choice = input.nextInt();
        if (choice == 1) {
            addFoodProduct(input);
        } else if (choice == 2) {
            addAlcoholicBeverrage(input);
        } else {
            System.out.println("You entered a wrong command");
        }
    }

    private static void addAlcoholicBeverrage(Scanner input) throws SQLException {
        System.out.println("Enter the alcoholic beverage name: ");
        AlcoholicBeverage ab = new AlcoholicBeverage();
        input.nextLine();
        ab.setName(input.nextLine());
        System.out.println("Enter price: ");
        ab.setPrice(input.nextDouble());
        System.out.println("Enter alcohol percentage: ");
        ab.setAlcoholPercentage(input.nextDouble());
        System.out.println("Enter quantity of products you want to add in the store: ");
        ab.setQuantity(input.nextInt());
        if (ab.getQuantity() != 0) {
            System.out.println("You added to the store " + ab.getQuantity() + " products");
            Store.getInstance().addProducts(ab);
            try {
                DBConnector.getInstance().addProductToDB(ab);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            throw new InexistentProductException(ab.getName());
        }
    }
    private static void addFoodProduct(Scanner input) throws SQLException {
        System.out.println("Enter the food product name");
        FoodProduct fp = new FoodProduct();
        fp.setName(input.next());
        System.out.println("Enter price: ");
        fp.setPrice(input.nextDouble());
        System.out.println("Enter kcal on the packaging: ");
        fp.setKcal(Integer.parseInt(input.next()));
        System.out.println("Enter quantity of products you want to add in the store: ");
        fp.setQuantity(input.nextInt());
        if(fp.getQuantity() != 0){
            System.out.println("You added to the store " + fp.getQuantity() + " products");
            Store.getInstance().addProducts(fp);
            try {
                DBConnector.getInstance().addProductToDB(fp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            throw new InexistentProductException(fp.getName());
        }
    }

    private static void displayProducts() throws SQLException {
        try {
            DBConnector.getInstance().displayProductsFromDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void changeProduct(Scanner input){
        System.out.println("Enter the  name of the product: ");
        String productName = input.next();

        System.out.println("Enter the name of the new product");
        String newName = input.next();

        System.out.println("Enter the new price of the product: ");
        int productPrice = input.nextInt();

        System.out.println("Enter the new quantity of the product: ");
        int productQuantity = input.nextInt();

        Store store = Store.getInstance();
        Product product = Store.getInstance().searchProduct(productName);

        store.changeProduct(product, newName, productPrice, productQuantity);
        System.out.println("You changed a product in your store");
    }

    private static void deleteProduct(Scanner input) throws SQLException {
        System.out.println("Enter the name of the product you want to remove from the store: ");
        connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        statement = connection.createStatement();
        String sql = "DELETE FROM products WHERE Name = ?";
        String name = input.next();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        statement.close();
        connection.close();
        System.out.println("You removed from the store the product " + name + " succesfully!");
    }



    private static void deleteAccount(Scanner input) throws SQLException {
        String userSecurityCode;

        StringBuilder securityCode = new StringBuilder();
        Random random = new Random();
        int length = 6;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int i = 0; i < length; i++) {
            char c = characters.charAt(random.nextInt(characters.length()));
            securityCode.append(c);
        }
        System.out.println(securityCode);
        System.out.println("Please enter the security code: ");
        userSecurityCode = input.next();

        if(userSecurityCode.contentEquals(securityCode)){

        System.out.println("Enter the username you want to delete: ");
        connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        statement = connection.createStatement();
        String sql = "DELETE FROM users WHERE Username = ?";
        String username = input.next();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        statement.close();
        connection.close();
        System.out.println("You deleted you accunt succesfully!");
        }else{
            System.out.println("Your code doesn't match");
        }


    }
    private static void addProductToCart(Scanner input) throws SQLException {

        System.out.println("Enter the name of the item you want to add to your cart");
         connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
         String sql = "SELECT * FROM products WHERE Name = ? AND ((AlcoholPercentage > 0 AND Kcal = 0) OR (AlcoholPercentage = 0 AND Kcal > 0))";
         input.nextLine();
         String productName = input.nextLine();


         pStatement = connection.prepareStatement(sql);
         pStatement.setString(1, productName);

        ResultSet resultSet = pStatement.executeQuery();

         while(resultSet.next()){
             int id = resultSet.getInt("ID");
             productName = resultSet.getString("Name");
             System.out.println("Enter the quantity of products you want to add to the cart:          (Max quantity is: " + resultSet.getInt("Quantity") + ")");
             int quantity = input.nextInt();
             double price = (resultSet.getDouble("Price") * quantity);
             double alcoholpercentage = resultSet.getDouble("AlcoholPercentage");
             int kcal = resultSet.getInt("Kcal");

             if(alcoholpercentage != 0 && quantity < resultSet.getInt("Quantity")){
                 AlcoholicBeverage alcoholicBeverage = new AlcoholicBeverage(id, productName, price, quantity, alcoholpercentage);
                 cartList.add(alcoholicBeverage);
             }else if(quantity < resultSet.getInt("Quantity")){
                 FoodProduct foodProduct = new FoodProduct(id, productName, price, quantity, kcal);
                 cartList.add(foodProduct);
             }else{
                 System.out.println("Invalid quantity");
             }

             System.out.println("You addeed to your cart: " + productName + " product!");
         }
    }

    private static void displayProductsFromCart(){
        System.out.println("In the shopping cart you have the following items: ");
        for (Product product : cartList){
//            System.out.println("In the shopping cart you have the following items: ");
            System.out.println(product.getName() + " - $" + product.getPrice() + " - Quantity: " + product.getQuantity());
        }
    }

    public static double calculateTotalPrice(){
        double totalPrice = 0;
        for (Product product : cartList){
            totalPrice += product.getPrice();
        }
        System.out.println("The total price of your items is: " + totalPrice);
        return totalPrice;
    }
}