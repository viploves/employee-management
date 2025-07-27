package com.employee.management;

import java.io.IOException;
import java.util.List;

import com.employee.management.models.Employee;
import com.employee.management.services.EmployeeService;
import com.employee.management.utils.ConfigManager;
import com.employee.management.utils.ExcelManager;

public class Main {
    public static void main(String[] args) {
        try
        {
            ExcelManager excelManager = new ExcelManager();
            ConfigManager configManager = new ConfigManager();
            String filePath = args.length > 0 ? args[0] : configManager.getEmployeesFilePath();

            List<Employee> employees = excelManager.readEmployees(filePath);
            
            EmployeeService employeeService = new EmployeeService();
            employeeService.printManagersEarningMoreOrLess(employees);
            employeeService.printEmployeesWithLongReportingLine(employees);
        } 
        catch (IOException e) {
            System.err.println("Error reading Excel file. Please check if the file exists at the specified path. " + e.getMessage());
        }
        catch (NumberFormatException e) {
            System.err.println("The salary of one or more employees in the Excel sheet is not in correct format. " + e.getMessage());
        }
        catch(Exception e)
        {
            System.err.println("Error occured while processing the requested information: " + e.getMessage());
        }
    }
}