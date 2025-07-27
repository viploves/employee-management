# Employee Management System

A Java application that reads employee and hierarchy data from an Excel file and displays the following:

- Which managers earn less than they should, and by how much
- Which managers earn more than they should, and by how much
- Which employees have a reporting line which is too long, and by how much

## Features

- Read employee data from Excel (.xlsx) file
- Display manager and hierarchy information in a formatted console output
- Maven-based project structure
- JUnit 5 test coverage

## Project Structure

```
employee-management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/employee/management/
│   │   │       ├── models/
│   │   │       │   └── Employee.java
│   │   │       ├── services/
│   │   │       │   └── EmployeeService.java
│   │   │       ├── utils/
│   │   │       │   ├── ConfigManager.java
│   │   │       │   └── ExcelManager.java
│   │   │       └── Main.java
│   │   └── resources/
│   |       └── config.properties
│   |       └── Employee Data.xlsx
│   └── test/
│       ├── java/
│       │   └── com/employee/management/
│       │       ├── models/
│       │       │   └── EmployeeTest.java
│       │       ├── services/
│       │       │   └── EmployeeServiceTest.java
│       │       └── utils/
│       │           └── ExcelManagerTest.java
│       └── resources/
│   |       └── empty-test-file.xlsx
│   |       └── test-employees.xlsx
├── pom.xml
└── README.md
```

## Excel File Format

The Excel file should have the following columns in the first sheet:
- Column A: Id
- Column B: firstName
- Column C: lastName
- Column D: salary
- Column E: managerId

The first row should contain headers and will be skipped during processing.

## Build and Run

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Build
```bash
mvn clean compile
```

### Run Tests
```bash
mvn test
```

### Package
```bash
mvn package
```

### Run Application
```bash
java -cp target/classes com.employee.management.Main path/to/your/excel/file.xlsx

# Or use resource file:
java -cp target/classes com.employee.management.Main "/Employee Data.xlsx"
```

## Dependencies

- Apache POI 5.2.4 - For Excel file processing
- JUnit 5.9.2 - For unit testing