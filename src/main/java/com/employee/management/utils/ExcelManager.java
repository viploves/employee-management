package com.employee.management.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.employee.management.models.Employee;

public class ExcelManager {
    
    /**
     * Reads employee data from an Excel file and converts it to a list of Employee objects.
     * Supports reading from both classpath resources or absolute system path.
     * 
     * @param filePath the path to the Excel file - classpath resource starting with "/"
     *                 or absolute file path
     * @return a list of Employee objects parsed from the Excel file, never null but may be empty
     * @throws IOException if the file cannot be found, read, or parsed
     */
    public List<Employee> readEmployees(String filePath) throws IOException {
        List<Employee> employees = new ArrayList<>();
        
        InputStream inputStream = null;
        
        if (filePath.startsWith("/")) {
            // Classpath resource with leading slash
            inputStream = getClass().getResourceAsStream(filePath);
        } else if (filePath.contains(":") || filePath.contains("\\")) {
            // Absolute file path
            inputStream = new FileInputStream(filePath);
        }
            
        if (inputStream == null) {
            throw new IOException("File not found: " + filePath);
        }

        // Parse the file
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            // Only parse the first sheet
            Sheet sheet = workbook.getSheetAt(0);
            
            // Skip the header row and parse till the last non-blank row
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Employee employee = new Employee();
                    employee.setId(getCellValue(row.getCell(0)));
                    employee.setFirstName(getCellValue(row.getCell(1)));
                    employee.setLastName(getCellValue(row.getCell(2)));
                    employee.setSalary(getNumericValue(row.getCell(3)));
                    employee.setManagerId(getCellValue(row.getCell(4)));
                    employees.add(employee);
                }
            }
        }
        
        return employees;
    }
    
    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        return cell.getCellType() == CellType.STRING ? 
               cell.getStringCellValue() : 
               String.valueOf((long) cell.getNumericCellValue());
    }
    
    private double getNumericValue(Cell cell) {
        return cell != null && cell.getCellType() == CellType.NUMERIC ? 
               cell.getNumericCellValue() : 0.0;
    }
}