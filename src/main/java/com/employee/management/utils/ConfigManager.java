package com.employee.management.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final String CONFIG_FILE = "/config.properties";
    private Properties properties;
    
    public ConfigManager() {
        loadProperties();
    }
    
    private void loadProperties() {
        properties = new Properties();
        try (InputStream input = getClass().getResourceAsStream(CONFIG_FILE)) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
        }
    }
    
    public double getSalaryHighThreshold() {
        return Double.parseDouble(properties.getProperty("salary.percentagethreshold.high", "50"));
    }
    
    public double getSalaryLowThreshold() {
        return Double.parseDouble(properties.getProperty("salary.percentagethreshold.low", "20"));
    }
    
    public int getMaxReportingLevels() {
        return Integer.parseInt(properties.getProperty("employee.maxreportinglevels", "4"));
    }

    public String getEmployeesFilePath() {
        return properties.getProperty("excelFilePath", "/Employee Data.xlsx");
    }
}