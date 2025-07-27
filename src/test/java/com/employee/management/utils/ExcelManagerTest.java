package com.employee.management.utils;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.employee.management.models.Employee;

class ExcelManagerTest {
    
    private ExcelManager excelManager;
    
    @BeforeEach
    void setUp() {
        excelManager = new ExcelManager();
    }
    
    @Test
    void testReadEmployees_WithValidClasspathResource() throws IOException {
        String filePath = "/test-employees.xlsx";
        
        assertDoesNotThrow(() -> {
            List<Employee> employees = excelManager.readEmployees(filePath);
            assertNotNull(employees);
        });
    }

    @Test
    void testReadEmployees_WithAbsolutePathLogic() {
        String absolutePath = "C:\\Users\\sharmav2\\employees-list.xlsx";

        assertDoesNotThrow(() -> {
            List<Employee> employees = excelManager.readEmployees(absolutePath);
            assertNotNull(employees);
        });
    }
    
    @Test
    void testReadEmployees_WithNonExistentFile() {
        String filePath = "/non-existent-file.xlsx";
        
        IOException exception = assertThrows(IOException.class, () -> {
            excelManager.readEmployees(filePath);
        });
        
        assertTrue(exception.getMessage().contains("File not found"));
    }   
    
    @Test
    void testReadEmployees_ReturnsNonNullList() throws IOException {
        try {
            List<Employee> result = excelManager.readEmployees("/valid-test-file.xlsx");
            assertNotNull(result, "Result should never be null");
        } catch (IOException e) {
            assertTrue(e.getMessage().contains("File not found"));
        }
    }
    
    @Test
    void testReadEmployees_HandlesEmptyFile() {
        String emptyFilePath = "/empty-test-file.xlsx";
        
        assertDoesNotThrow(() -> {
            List<Employee> employees = excelManager.readEmployees(emptyFilePath);
        });
    }
}