=========================================================
E-COMMERCE ORDER MANAGEMENT SYSTEM (MAVEN + JAVA JDBC + MYSQL)
=========================================================

FOLDER STRUCTURE:
OrderManagementSystem/
├── pom.xml
├── database/
│   └── schema.sql
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── ecommerce/
│                   ├── model/
│                   │   └── Order.java
│                   ├── util/
│                   │   └── DBConnection.java
│                   ├── dao/
│                   │   └── OrderDAO.java
│                   └── main/
│                       └── OrderManagementApp.java
└── README.txt

SETUP INSTRUCTIONS:
1. Extract the ZIP and open the `OrderManagementSystem` folder directly in IntelliJ IDEA (it will auto-detect `pom.xml` and download `mysql-connector-j`).
2. Run `database/schema.sql` in MySQL Workbench or Command Line (creates DB, table, and 10 sample rows).
3. Update your MySQL `PASSWORD` in `src/main/java/com/ecommerce/util/DBConnection.java`.
4. Run `OrderManagementApp.java`.
