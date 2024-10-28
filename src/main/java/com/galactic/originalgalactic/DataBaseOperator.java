package com.galactic.originalgalactic;

import java.sql.*;
import java.util.Scanner;

public class DataBaseOperator {
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



    public void closeConnection(){
        try {
            connection.close();
            System.out.println("Closed Successfully");
        }catch(SQLException e){

            System.out.println(e.getMessage());
        }
    }



    public void createTable(){
        System.out.print("Create Table, Enter the table name: ");
        Scanner sc = new Scanner(System.in);
        String tableName = sc.nextLine();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS "+tableName+" (" +
                "StudentId INTEGER PRIMARY KEY," +
                "RollNo INTEGER NOT NULL,"+
                "MobileNo INTEGER NOT NULL," +
                "Name TEXT NOT NULL," +
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



    public void deleteTable(){
        System.out.print("Deleting table, Enter the table name: ");
        Scanner sc = new Scanner(System.in);
        String tableName = sc.nextLine();
        String deleteTableSQL = "DROP TABLE IF EXISTS "+tableName+" ;";
        try {

            Statement stmt = connection.createStatement();
            stmt.execute(deleteTableSQL);
            System.out.println("Deleted Table " + tableName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}

/*
class Temporary{
    public static void main(String[] args){
        DataBaseOperator d = new DataBaseOperator();
        Connection hello = d.startConnect();

      p.createTable();

        d.deleteTable();
        d.closeConnection();
    }
}
*/
