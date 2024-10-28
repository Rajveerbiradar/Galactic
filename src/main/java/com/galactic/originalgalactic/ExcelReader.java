package com.galactic.originalgalactic;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ExcelReader {
    Connection connection;
    public void addExcelData(){

        System.out.print("enter the excel file path");
        Scanner ssc = new Scanner(System.in);
        File excelFile2 = new File(ssc.nextLine());
        String fileName = excelFile2.getName();
        System.out.println(fileName);


        String tableName = fileName;

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




        try {

            FileInputStream excelFile = new FileInputStream(excelFile2);
            Workbook excelWorkBook = new XSSFWorkbook(excelFile);

            Sheet sheet = excelWorkBook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();

            for(int i = 0; i < rowCount; i++){
                Row row = sheet.getRow(i);
                int cellCount = row.getPhysicalNumberOfCells();
                String addingQuery = "INSERT INTO "+tableName+" (StudentId, RollNo, MobileNo, Name) VALUES (?, ?, ?, ?)";
                for(int j = 0; j < cellCount; j++){
                    Cell cell = row.getCell(j);
                    PreparedStatement smt = connection.prepareStatement(addingQuery);
                    switch(j){
                        case 0:
                            String studentId = String.valueOf(cell.getNumericCellValue());
                            smt.setString(1, studentId);
                            break;
                        case 1:
                            String RollNo = String.valueOf(cell.getNumericCellValue());
                            smt.setString(2, RollNo);
                            break;
                        case 2:
                            String mobileNo = String.valueOf(cell.getNumericCellValue());
                            smt.setString(3, mobileNo);
                            break;
                        case 3:
                            String studentName = cell.getStringCellValue();
                            smt.setString(4, studentName);
                            break;
                    }
                    smt.executeUpdate();
                    smt.close();

                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
