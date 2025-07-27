package com.employee.management;

import java.io.IOException;
import java.util.List;

import com.employee.management.models.Employee;
import com.employee.management.services.EmployeeService;
import com.employee.management.utils.ConfigManager;
import com.employee.management.utils.CsvManager;

public class Main {
    public static void main(String[] args) {
        try
        {
            CsvManager csvManager = new CsvManager();
            ConfigManager configManager = new ConfigManager();
            String filePath = args.length > 0 ? args[0] : configManager.getEmployeesFilePath();

            List<Employee> employees = csvManager.readEmployees(filePath);
            
            EmployeeService employeeService = new EmployeeService();
            employeeService.printManagersEarningMoreOrLess(employees);
            employeeService.printEmployeesWithLongReportingLine(employees);
        } 
        catch (IOException e) {
            System.err.println("Error reading CSV file. Please check if the file exists at the specified path. " + e.getMessage());
        }
        catch(Exception e)
        {
            System.err.println("Error occured while processing the requested information: " + e.getMessage());
        }
    }
}