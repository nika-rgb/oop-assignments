package DBManager;

import Product.Product;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DBManager {
    private BasicDataSource source;
    private Connection conn;
    public DBManager(){
        source = new BasicDataSource();
        source.setUrl("jdbc:mysql://localhost:3306/BasicStore");
        source.setUsername("root");
        source.setPassword("Arqimede123@");
        try {
            conn =  source.getConnection();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }



    public List <Product> getAllProducts(){
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement prepStatement = conn.prepareStatement("SELECT *" +
                    "FROM products;");
            ResultSet res = prepStatement.executeQuery();
            while(res.next()){
                products.add(new Product(res.getString(1), res.getString(2),
                        res.getString(3), res.getInt(4)));
            }
            res.close();
            prepStatement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return products;
    }

    public Product getProduct(String itemId){
        Product res = null;
        try{
            PreparedStatement prpStm = conn.prepareStatement("SELECT * FROM products WHERE productid = ?;");
            prpStm.setString(1, itemId);
            ResultSet resultSet = prpStm.executeQuery();
            resultSet.next();
            res = new Product(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getInt(4));
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return res;
    }

}
