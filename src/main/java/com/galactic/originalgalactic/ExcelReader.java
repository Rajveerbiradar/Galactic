package com.galactic.originalgalactic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
    public void addExcelData(){
        System.out.print("enter the excel file path");
        Scanner sc = new Scanner(System.in);
        try {
            FileInputStream excelFile = new FileInputStream(sc.nextLine());
            Workbook excelWorkBook = new XSSFWorkbook(excelFile);

            Sheet sheet = excelWorkBook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();

            for(int i = 0; i < rowCount; i++){
                Row row = sheet.getRow(i);
                int cellCount = row.getPhysicalNumberOfCells();

                for(int j = 0; j < cellCount; j++){


                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
