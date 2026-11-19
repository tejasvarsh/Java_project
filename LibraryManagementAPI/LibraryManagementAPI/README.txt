=========================================================
COLLEGE LIBRARY MANAGEMENT REST API (SPRING BOOT)
=========================================================

1. FOLDER & PACKAGE STRUCTURE:
LibraryManagementAPI/
 ├── pom.xml
 └── src/main/
      ├── resources/
      │    └── application.properties
      └── java/com/college/library/
           ├── LibraryManagementApplication.java
           ├── model/
           │    └── Book.java
           ├── service/
           │    └── BookService.java
           └── controller/
                └── BookController.java

2. HOW TO RUN IN INTELLIJ IDEA:
- Extract the ZIP file.
- Open IntelliJ IDEA -> File -> Open -> Select 'LibraryManagementAPI' folder.
- Wait for Maven to download dependencies.
- Run 'LibraryManagementApplication.java'.
- Server starts at: http://localhost:8080

3. POSTMAN API ENDPOINTS FOR TESTING:

a) Add a New Book (POST):
   URL: http://localhost:8080/api/books
   Body (raw JSON):
   {
       "id": 103,
       "title": "Spring Boot in Action",
       "author": "Craig Walls",
       "category": "Java",
       "availableCopies": 7
   }

b) Get All Books (GET):
   URL: http://localhost:8080/api/books

c) Get Book by ID (GET):
   URL: http://localhost:8080/api/books/101

d) Update Book by ID (PUT):
   URL: http://localhost:8080/api/books/101
   Body (raw JSON):
   {
       "title": "Clean Code (2nd Edition)",
       "author": "Robert C. Martin",
       "category": "Software Engineering",
       "availableCopies": 10
   }

e) Delete Book by ID (DELETE):
   URL: http://localhost:8080/api/books/101
   (Returns 200 OK if deleted, 404 Not Found if ID does not exist)
