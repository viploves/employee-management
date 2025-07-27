package com.employee.management.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.employee.management.models.Employee;
import com.employee.management.utils.ConfigManager;

public class EmployeeService {
    private final ConfigManager configManager;
    
    public EmployeeService() {
        this.configManager = new ConfigManager();
    }
    
    /**
     * Prints managers who earn more than 50% of the average salary of their direct reports
     * OR managers who earn less than 20% of the average salary of their direct reports
     *
     * @param employees List of Employee objects
     */
    public void printManagersEarningMoreOrLess(List<Employee> employees) {
        if(employees == null) {
            System.out.println("-------------------- No employees provided in the sheet --------------------");
            return;
        }

        System.out.println("-------------------- Managers earning more or less --------------------");
        Map<String, List<Employee>> mgrReportsMap = employees.stream()
            .filter(e -> !StringUtils.isBlank(e.getManagerId()))
            .collect(Collectors.groupingBy(Employee::getManagerId));
        
        for (Employee manager : employees) {
            List<Employee> reports = mgrReportsMap.get(manager.getId());
            
            if (reports != null && !reports.isEmpty()) {
                double averageSalary = reports.stream()
                    .mapToDouble(Employee::getSalary)
                    .average().orElse(0.0);

                double highThreshold = averageSalary * (1 + (configManager.getSalaryHighThreshold() / 100));
                double lowThreshold = averageSalary * (1 + (configManager.getSalaryLowThreshold() / 100));
                
                if (highThreshold > 0 && manager.getSalary() > highThreshold) {
                    System.out.println(manager.getName() + "'s salary is higher than "
                        + Math.round((manager.getSalary() - highThreshold) / highThreshold * 100) + "% of the higher threshold");

                } else if (lowThreshold > 0 && manager.getSalary() < lowThreshold) {
                    System.out.println(manager.getName() + "'s salary is lower than "
                        + Math.round((lowThreshold - manager.getSalary()) / lowThreshold * 100) + "% of the lower threshold");
                } 
            }
        }

        System.out.println("-----------------------------------------------------------------------");
    }

    /**
     * Prints the employees that have a reporting line longer than 4 levels
     *
     * @param employees List of Employee objects
     */
    public void printEmployeesWithLongReportingLine(List<Employee> employees) {
        if(employees == null) {
            System.out.println("-------------------- No employees provided in the sheet --------------------");
            return;
        }
        
        System.out.println("--------------- Employees having too long reporting line --------------");
        Map<String, Employee> empMap = employees.stream()
            .collect(Collectors.toMap(Employee::getId, e -> e));
        Map<String, Integer> levelMap = new HashMap<>();

        for (Employee employee : employees) {
            int level = getEmployeeReportingLevel(employee, empMap, levelMap);
            
            // If there are X number of managers "between" the employee and the seniormost manager, 
            // then the difference between their levels would be X + 1
            if (level > (configManager.getMaxReportingLevels() + 1)) {
                System.out.println(employee.getName() + " | Level: " + level);
            }
        }

        System.out.println("-----------------------------------------------------------------------");
    }

    /**
     * Recursively gets employee's reporting level
     *
     * @param employee The employee whose reporting level is to be found
     * @param empMap Map for employee lookups
     * @param levelMap Map for employee levels
     * @return The reporting level of the employee in the current recursion
     */
    private int getEmployeeReportingLevel(Employee employee, 
                                                   Map<String, Employee> empMap,
                                                   Map<String, Integer> levelMap) {
        if (levelMap.containsKey(employee.getId())) {
            return levelMap.get(employee.getId());
        }
        
        if (StringUtils.isBlank(employee.getManagerId())) {
            levelMap.put(employee.getId(), 0);
            return 0;
        }
        
        Employee manager = empMap.get(employee.getManagerId());
        if (manager == null) {
            levelMap.put(employee.getId(), 0);
            return 0;
        }
        
        int level = 1 + getEmployeeReportingLevel(manager, empMap, levelMap);
        levelMap.put(employee.getId(), level);
        return level;
    }
}