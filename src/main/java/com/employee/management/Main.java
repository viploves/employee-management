package com.employee.management;

import java.io.IOException;
import java.util.List;

import com.employee.management.models.Employee;
import com.employee.management.services.EmployeeService;
import com.employee.management.utils.ExcelManager;

public class Main {
    public static void main(String[] args) {
        String filePath = args.length > 0 ? args[0] : "/Employee Data.xlsx";
        
        try
        {
            ExcelManager excelReader = new ExcelManager();
            List<Employee> employees = excelReader.readEmployees(filePath);

            EmployeeService employeeService = new EmployeeService();
            employeeService.printManagersEarningMoreOrLess(employees);
            employeeService.printEmployeesWithLongReportingLine(employees);
        } 
        catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
        }
        catch(Exception e)
        {
            System.err.println("Error occured while processing the requested information: " + e.getMessage());
        }
    }
}