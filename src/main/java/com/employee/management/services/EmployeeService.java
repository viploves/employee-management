package com.employee.management.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.employee.management.models.Employee;

public class EmployeeService {
    public EmployeeService() {
    }
    
    /**
     * Prints managers who earn more than 50% of the average salary of their direct reports
     * OR managers who earn less than at least 20% of the average salary of their direct reports
     *
     * @param employees List of Employee objects
     * @throws IllegalArgumentException if either input is null (though in this simple case,
     *                                  integers cannot be null).
     */
    public void printManagersEarningMoreOrLess(List<Employee> employees) {
        System.out.println("-------------------- Managers earning more or less --------------------");
        Map<String, List<Employee>> managerToReports = employees.stream()
            .filter(e -> !StringUtils.isBlank(e.getManagerId()))
            .collect(Collectors.groupingBy(Employee::getManagerId));
        
        for (Employee manager : employees) {
            List<Employee> directReports = managerToReports.get(manager.getId());
            
            if (directReports != null && !directReports.isEmpty()) {
                double averageSalary = directReports.stream()
                    .mapToDouble(Employee::getSalary)
                    .average().orElse(0.0);
                
                if (manager.getSalary() > (averageSalary * 1.5)) {
                    System.out.println(manager.getName() + "'s salary is higher than the average salary by "
                        + (manager.getSalary() - averageSalary) / averageSalary * 100 + "%");
                } else if (manager.getSalary() < (averageSalary * 1.2)) {
                    System.out.println(manager.getName() + "'s salary is lower than the average salary by "
                        + (averageSalary - manager.getSalary()) / averageSalary * 100 + "%");
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
        System.out.println("--------------- Employees having too long reporting line --------------");
        Map<String, Employee> employeeMap = employees.stream()
            .collect(Collectors.toMap(Employee::getId, e -> e));
        Map<String, Integer> levelMap = new HashMap<>();

        for (Employee employee : employees) {
            int level = getEmployeeReportingLevel(employee, employeeMap, levelMap);
            
            if (level > 4) {
                System.out.println(employee.getName() + " | Level: " + level);
            }
        }

        System.out.println("-----------------------------------------------------------------------");
    }

    /**
     * Recursively gets employee's reporting level
     *
     * @param employee The employee whose reporting level is to be found
     * @param employeeMap Map for employee lookups
     * @param levelMap Map for employee levels
     * @return The reporting level of the employee in the current recursion
     */
    private int getEmployeeReportingLevel(Employee employee, 
                                                   Map<String, Employee> employeeMap,
                                                   Map<String, Integer> levelMap) {
        if (levelMap.containsKey(employee.getId())) {
            return levelMap.get(employee.getId());
        }
        
        if (StringUtils.isBlank(employee.getManagerId())) {
            levelMap.put(employee.getId(), 0);
            return 0;
        }
        
        Employee manager = employeeMap.get(employee.getManagerId());
        if (manager == null) {
            levelMap.put(employee.getId(), 0);
            return 0;
        }
        
        int level = 1 + getEmployeeReportingLevel(manager, employeeMap, levelMap);
        levelMap.put(employee.getId(), level);
        return level;
    }
}