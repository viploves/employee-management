# Employee Management System

A Java application that reads employee data from Excel files and displays it on the console.

## Features

- Read employee data from Excel (.xlsx) files
- Display employee information in a formatted console output
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
│   │   │       │   ├── ExcelManager.java
│   │   │       │   └── StringUtils.java
│   │   │       └── Main.java
│   │   └── resources/
│   └── test/
│       ├── java/
│       │   └── com/employee/management/
│       │       ├── models/
│       │       │   └── EmployeeTest.java
│       │       └── utils/
│       │           └── UtilsTest.java
│       └── resources/
├── pom.xml
└── README.md
```

## Excel File Format

The Excel file should have the following columns in the first sheet:
- Column A: Employee ID
- Column B: First Name
- Column C: Last Name
- Column D: Salary
- Column E: Manager ID

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