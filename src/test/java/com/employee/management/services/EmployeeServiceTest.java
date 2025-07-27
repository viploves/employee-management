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
            new Employee("1", "John", "Doe", null, 100000),
            new Employee("2", "Jane", "Smith", "1", 50000),
            new Employee("3", "Bob", "Johnson", "1", 60000)
        );
        
        employeeService.printManagersEarningMoreOrLess(employees);
        
        String output = outputStream.toString();
        assertTrue(output.contains("John Doe"));
        assertTrue(output.contains("higher than the average"));
    }
    
    @Test
    void testPrintManagersEarningMoreOrLess_WithLowEarningManager() {
        List<Employee> employees = Arrays.asList(
            new Employee("1", "John", "Doe", null, 60000),
            new Employee("2", "Jane", "Smith", "1", 80000),
            new Employee("3", "Bob", "Johnson", "1", 90000)
        );
        
        employeeService.printManagersEarningMoreOrLess(employees);
        
        String output = outputStream.toString();
        assertTrue(output.contains("John Doe"));
        assertTrue(output.contains("lower than the average"));
    }

    @Test
    void testPrintManagersEarningMoreOrLess_WithEarningWithinRange() {
        List<Employee> employees = Arrays.asList(
            new Employee("1", "John", "Doe", null, 60000),
            new Employee("2", "Jane", "Smith", "1", 80000),
            new Employee("3", "Bob", "Johnson", "1", 90000)
        );
        
        employeeService.printManagersEarningMoreOrLess(employees);
        
        String output = outputStream.toString();
        assertTrue(output.contains("John Doe"));
        assertTrue(output.contains("lower than the average"));
    }

    @Test
    void testPrintManagersEarningMoreOrLess_WithEmptyList() {
        List<Employee> employees = Arrays.asList();
        
        assertDoesNotThrow(() -> {
            employeeService.printManagersEarningMoreOrLess(employees);
        });
    }
    
    @Test
    void testPrintEmployeesWithLongReportingLine_WithLongChain() {
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
    void testPrintEmployeesWithLongReportingLine_WithShortChain() {
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