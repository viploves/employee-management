package com.employee.management.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.employee.management.models.Employee;
import com.opencsv.CSVReader;

public class CsvManager {
    
    public List<Employee> readEmployees(String filePath) throws IOException {
        List<Employee> employees = new ArrayList<>();
        
        InputStream inputStream = getClass().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IOException("File not found: " + filePath);
        }
        
        try(CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream))) 
        {
            // Skip header row
            String[] line = csvReader.readNext();
            
            while ((line = csvReader.readNext()) != null) {
                if (line.length >= 5) {
                    Employee emp = new Employee();
                    emp.setId(line[0].trim());
                    emp.setFirstName(line[1].trim());
                    emp.setLastName(line[2].trim());
                    emp.setSalary(Double.parseDouble(line[3].trim()));
                    emp.setManagerId(StringUtils.isBlank(line[4]) ? null : line[4].trim());
                    employees.add(emp);
                }
            }
        }
        catch(NumberFormatException e) {
            System.out.println("Invalid salary value " + e.getMessage());
        }
        catch(Exception e) {
            System.out.println("Error occured while reading the CSV. " + e.getMessage());
        }
        
        return employees;
    }
}