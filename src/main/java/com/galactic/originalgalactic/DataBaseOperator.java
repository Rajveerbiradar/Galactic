package com.galactic.originalgalactic;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

import java.sql.*;
import java.util.Scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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



    public void CheckFileName(){
        System.out.print("enter the excel file path");
        Scanner ssc = new Scanner(System.in);
        File excelFile2 = new File(ssc.nextLine());
        String fileName = excelFile2.getName();
        System.out.println(fileName);
    }


}


class Temporary{
    public static void main(String[] args){
        DataBaseOperator d = new DataBaseOperator();
//        Connection hello = d.startConnect();
//
//        d.createTable();
//
//        d.deleteTable();
//        d.closeConnection();
        d.CheckFileName();
    }
}

