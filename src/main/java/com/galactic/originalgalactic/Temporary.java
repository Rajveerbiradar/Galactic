package com.galactic.originalgalactic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;


class Perform {
    public Connection connection;
    public Connection startConnect(){
        String filePath = "src/main/resources/com/galactic/originalgalactic/CollegeLibrary.db";
        String url = "jdbc:sqlite:" + filePath;

        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connected with the database");
        } catch(SQLException e){
            System.out.println("unable to connect");
        }

        try {
            if(connection == null ){
                connection.close();
                System.out.println("Closed caused pointed null");
            }
        }catch(SQLException e){

            System.out.println(e.getMessage());
        }


        return connection;
    }
;
    public void closeConnection(){
        try {
            if(connection != null ){
                connection.close();
                System.out.println("Closed Successfully");
            }
        }catch(SQLException e){

            System.out.println(e.getMessage());
        }
    }

    public void createTable(){
        System.out.print("Enter the table name: ");
        Scanner sc = new Scanner(System.in);
        String tableName = sc.nextLine();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS "+tableName+" (" +
                "StudentId INTEGER PRIMARY KEY," +
                "FirstName TEXT NOT NULL," +
                "LastName TEXT NOT NULL," +
                "BirthDate TEXT" +
                ");";

        try {
            Statement stmt = connection.createStatement();
            // Create the table
            stmt.execute(createTableSQL);
            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


}


public class Temporary {
    public static void main(String[] args){
        Perform p = new Perform();
        Connection hello = p.startConnect();

        p.createTable();

        p.closeConnection();
    }
}
