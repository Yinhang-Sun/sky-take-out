package com.sky.test;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.security.auth.callback.TextInputCallback;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Operate Excel file by Apache POI
 */
public class POITest {

    /**
     * Create an Excel file and write into content by Apache POI
     */
    public static void write() throws Exception{
        //create an Excel file in memory
        XSSFWorkbook excel = new XSSFWorkbook();
        //create a sheet page in the Excel file
        XSSFSheet sheet = excel.createSheet("info");
        //create row object in the sheet page
        XSSFRow row = sheet.createRow(1);
        //create cell object in the row and write into content
        row.createCell(1).setCellValue("name");
        row.createCell(2).setCellValue("city");

        //create a new row
        row = sheet.createRow(2);
        row.createCell(1).setCellValue("John");
        row.createCell(2).setCellValue("LA");

        //create a new row
        row = sheet.createRow(3);
        row.createCell(1).setCellValue("Smith");
        row.createCell(2).setCellValue("MA");

        //write the Excel file in the memory into disk by an FileOutputStream
        FileOutputStream out = new FileOutputStream(new File("/Users/yinhang_sun/Downloads/info.xlsx"));
        excel.write(out);

        //close resources
        out.close();
        excel.close();

    }

    /**
     * Read out the Excel file by Apache POI
     */
    public static void read() throws Exception{
        FileInputStream in = new FileInputStream(new File("/Users/yinhang_sun/Downloads/info.xlsx"));

        //Read the Excel file already exists in disk
        XSSFWorkbook excel = new XSSFWorkbook(in);
        //Read the first Sheet page in the Excel
        XSSFSheet sheet = excel.getSheetAt(0);

        //Get the last row number of the sheet page
        int lastRowNum = sheet.getLastRowNum();

        for (int i = 1; i <= lastRowNum; i++) {
            // Get a specific row
            XSSFRow row = sheet.getRow(i);
            // Get a specific cell in the row
            String cellValue1 = row.getCell(1).getStringCellValue();
            String cellValue2 = row.getCell(2).getStringCellValue();
            System.out.println(cellValue1 + " " + cellValue2);
        }

        excel.close();
        in.close();
    }

    public static void main(String[] args) throws Exception {
        // write();
        read();
    }
}