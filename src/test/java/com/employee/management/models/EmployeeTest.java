package com.employee.management.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class EmployeeTest {
    
    @Test
    void testEmployeeCreation() {
        Employee employee = new Employee("001", "John", "Doe", "12345", 75000.0);
        
        assertEquals("001", employee.getId());
        assertEquals("John Doe", employee.getName());
        assertEquals(75000.0, employee.getSalary());
    }
    
    @Test
    void testEmployeeSetters() {
        Employee employee = new Employee();
        employee.setId("002");
        employee.setFirstName("Jane");
        employee.setLastName("Smith");
        employee.setSalary(65000.0);
        
        assertEquals("002", employee.getId());
        assertEquals("Jane Smith", employee.getName());
        assertEquals(65000.0, employee.getSalary());
    }
    
    @Test
    void testToString() {
        Employee employee = new Employee("003", "Bob", "Johnson", null, 80000.0);
        String expected = "Employee{id='003', firstName='Bob', lastName='Johnson', managerId='null', salary='80000.00'}";
        assertEquals(expected, employee.toString());
    }
}