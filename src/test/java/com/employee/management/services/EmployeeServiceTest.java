package com.employee.management.services;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.employee.management.models.Employee;

class EmployeeServiceTest {
    
    private EmployeeService employeeService;
    private ByteArrayOutputStream outputStream;
    
    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }
    
    @Test
    void testPrintManagersEarningMoreOrLess_WithHighEarningManager() {
        List<Employee> employees = Arrays.asList(
            new Employee("1", "Peter", "Parker", null, 100000),
            new Employee("2", "MJ", "Parker", "1", 50000),
            new Employee("3", "Chris", "Evans", "1", 60000)
        );
        
        employeeService.printManagersEarningMoreOrLess(employees);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Peter Parker"));
        assertTrue(output.contains("higher than"));
    }
    
    @Test
    void testPrintManagersEarningMoreOrLess_WithLowEarningManager() {
        List<Employee> employees = Arrays.asList(
            new Employee("1", "Peter", "Parker", null, 60000),
            new Employee("2", "MJ", "Parker", "1", 80000),
            new Employee("3", "Chris", "Evans", "1", 90000)
        );
        
        employeeService.printManagersEarningMoreOrLess(employees);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Peter Parker"));
        assertTrue(output.contains("lower than"));
    }

    @Test
    void testPrintManagersEarningMoreOrLess_WithEarningWithinRange() {
        List<Employee> employees = Arrays.asList(
            new Employee("1", "Peter", "Parker", null, 80000),
            new Employee("2", "MJ", "Parker", "1", 55000),
            new Employee("3", "Chris", "Evans", "1", 75000)
        );
        
        employeeService.printManagersEarningMoreOrLess(employees);
        
        String output = outputStream.toString();
        assertFalse(output.contains("Peter Parker"));
        assertFalse(output.contains("higher than"));
        assertFalse(output.contains("lower than"));
    }

    @Test
    void testPrintManagersEarningMoreOrLess_WithEmptyList() {
        List<Employee> employees = Arrays.asList();
        
        assertDoesNotThrow(() -> {
            employeeService.printManagersEarningMoreOrLess(employees);
        });
    }
    
    @Test
    void testPrintEmployeesWithLongReportingLine_MoreThanMax() {
        List<Employee> employees = Arrays.asList(
            new Employee("1", "CEO", "Boss", null, 200000),
            new Employee("2", "VP", "One", "1", 150000),
            new Employee("3", "Dir", "Two", "2", 120000),
            new Employee("4", "Mgr", "Three", "3", 100000),
            new Employee("5", "Lead", "Four", "4", 90000),
            new Employee("6", "Dev", "Five", "5", 80000)
        );
        
        employeeService.printEmployeesWithLongReportingLine(employees);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Dev Five"));
        assertTrue(output.contains("Level: 5"));
    }
    
    @Test
    void testPrintEmployeesWithLongReportingLine_LessThanMax() {
        List<Employee> employees = Arrays.asList(
            new Employee("1", "CEO", "Boss", null, 200000),
            new Employee("2", "Dev", "One", "1", 80000)
        );
        
        employeeService.printEmployeesWithLongReportingLine(employees);
        
        String output = outputStream.toString();
        assertFalse(output.contains("Dev One"));
    }
    
    @Test
    void testPrintEmployeesWithLongReportingLine_WithEmptyList() {
        List<Employee> employees = Arrays.asList();
        
        assertDoesNotThrow(() -> {
            employeeService.printEmployeesWithLongReportingLine(employees);
        });
    }
}