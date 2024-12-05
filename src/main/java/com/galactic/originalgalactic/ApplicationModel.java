package com.galactic.originalgalactic;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class ApplicationModel {

    private Connection connection;
    DataFormatter dataFormatter = new DataFormatter();


    public Connection startConnect(){
        String filePath = "src/main/resources/com/galactic/originalgalactic/CollegeLibrary.db";
        String url = "jdbc:sqlite:" + filePath;

        try {
            this.connection = DriverManager.getConnection(url);
            System.out.println("Connected with the database");
        } catch(SQLException e){
            System.out.println("unable to connect");
        }

        try {
            if(connection == null ){
                System.out.println("pointed null");
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
            if(connection != null){
                connection.close();
                System.out.println("Closed Successfully");
            }
        }catch(SQLException e){

            System.out.println(e.getMessage());
        }
    }


    public void excelToDataBase(String TableName, String filepath){

        String createTableSQL = "CREATE TABLE IF NOT EXISTS "+ TableName +"(" +
                "student_id VARCHAR(20) PRIMARY KEY," +
                "roll_No VARCHAR(10),"+
                "name VARCHAR(42)" +
                ");";

        try {
            Statement stmt = connection.createStatement();
            // Create the table
            stmt.execute(createTableSQL);
            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }




        try {
            File excelFilePath = new File(filepath);
            FileInputStream excelFile = new FileInputStream(excelFilePath);
            Workbook excelWorkBook = new XSSFWorkbook(excelFile);
            System.out.println("file connected");

            Sheet sheet = excelWorkBook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();

            for(int i = 0; i < rowCount; i++){
                System.out.println("loop started");
                Row row = sheet.getRow(i);
                int cellCount = row.getPhysicalNumberOfCells();
                String addingQuery = "INSERT INTO "+ TableName +" (student_id, roll_No, name) VALUES (?, ?, ?)";
                PreparedStatement smt = connection.prepareStatement(addingQuery);
                for(int j = 0; j < cellCount; j++){
                    Cell cell = row.getCell(j);


                    if(j == 0){
                        double studentId = cell.getNumericCellValue();
                        String sStudentId = dataFormatter.formatCellValue(cell);
                        smt.setString(1, sStudentId);
                    }
                    else if(j == 1){
                        double rollNo = cell.getNumericCellValue();
                        String sRollNo = dataFormatter.formatCellValue(cell);
                        smt.setString(2, sRollNo);
                    }
                    else if(j == 2){
                        String studentName = cell.getStringCellValue();
                        smt.setString(3, studentName);
                    }
                }

                smt.executeUpdate();
                smt.close();
                System.out.println("added one row");

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
