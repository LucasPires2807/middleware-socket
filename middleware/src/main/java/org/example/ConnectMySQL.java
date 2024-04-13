package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectMySQL {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/middleware";
        String username="root";
        String password="";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);


            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while(resultSet.next()){
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2));
            }

            connection.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
