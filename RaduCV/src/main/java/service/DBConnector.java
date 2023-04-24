package service;
import model.AlcoholicBeverage;
import model.FoodProduct;
import model.Product;
import model.User;
import org.example.UserManager;

import java.sql.*;


public class DBConnector {

    private static DBConnector INSTANCE;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/storedb";
    private static final String USER = "root";
    private static final String PASSWORD = "";


    public static final DBConnector getInstance(){
        if(INSTANCE == null){
            INSTANCE =new DBConnector();
        }
        return INSTANCE;
    }
    Connection connection;
    PreparedStatement pStatement;
    Statement statement;


    public void addProductToDB(Product product) throws SQLException {
        connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        String sql = "INSERT INTO products (ID, Name, Price, Quantity, Type, AlcoholPercentage, Kcal) VALUES (?, ?, ?, ?, ?, ?, ?)";
        pStatement = connection.prepareStatement(sql);
        pStatement.setInt(1, product.getID());
        pStatement.setString(2, product.getName());
        pStatement.setDouble(3,product.getPrice());
        pStatement.setInt(4,product.getQuantity());

        if(product instanceof AlcoholicBeverage){
            pStatement.setString(5, "Alcoholic Beverage");
            pStatement.setDouble(6,((AlcoholicBeverage) product).getAlcoholPercentage());
            pStatement.setInt(7, 0);
        }else if(product instanceof FoodProduct){
            pStatement.setString(5, "Food Product");
            pStatement.setDouble(6, 0);
            pStatement.setInt(7, ((FoodProduct) product).getKcal());
        }else{
            pStatement.setString(5, "");
            pStatement.setDouble(6, 0);
            pStatement.setInt(7, 0);
        }
        pStatement.executeUpdate();
        pStatement.close();
        connection.close();
    }

    public void displayProductsFromDB() throws SQLException {
        connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);

        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM products");

        System.out.printf("%-5s %-20s %-10s %-8s %-20s %-20s %-10s\n",
                "ID", "Name", "Price", "Quantity", "Type", "AlcoholPercentage", "Kcal");


        while(resultSet.next()){
            System.out.printf("%-5s %-20s %-10.2f %-8d %-20s %-20.2f %-10d\n",
                    resultSet.getInt("ID"), resultSet.getString("Name"), resultSet.getDouble("Price"),
                    resultSet.getInt("Quantity"), resultSet.getString("Type"), resultSet.getDouble("AlcoholPercentage"),
                    resultSet.getInt("Kcal"));
        }
    }

    public void addUsersToDB(User user) throws SQLException{
        connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        String sql = "INSERT INTO users (ID, Username, Password, Name, Email, PhoneNumber) VALUES (?, ?, ?, ?, ?, ?)";
        pStatement = connection.prepareStatement(sql);
        pStatement.setInt(1, user.getId());
        pStatement.setString(2, user.getUsername());
        pStatement.setString(3, user.getPassword());
        pStatement.setString(4, user.getName());
        pStatement.setString(5, user.getEmail());
        pStatement.setString(6, user.getPhoneNumber());

        System.out.println("You succesfully created an account! ");

        pStatement.executeUpdate();
        pStatement.close();
        connection.close();
    }

    public void loginUsersIntoStore(User user) throws SQLException{
        connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        String sql = "SELECT id FROM users WHERE username=? AND password=?";
        pStatement = connection.prepareStatement(sql);
        pStatement.setString(1, user.getUsername());
        pStatement.setString(2, user.getPassword());

        ResultSet resultSet = pStatement.executeQuery();
        boolean found = false;

        if(resultSet.next()) {
            int id = resultSet.getInt("ID");
            if (id != 0) {
                found = true;
            }
        }

        if(found){
            System.out.println("Login was successful!");
            UserManager.getInstance().authenticate(user);
        }
        if(!found){
            System.out.println("Invalid login credentials");
        }
        pStatement.close();
        connection.close();
    }




}

