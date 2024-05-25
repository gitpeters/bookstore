# Bookstore API

## Table of Contents

- [Setup Instructions](#setup-instructions)
- [API Documentation](#api-documentation)
- [Unit Testing](#unit-testing)
- [Spring Security Setup](#spring-security-setup)
- [Dependencies](#dependencies)

## Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/bookstore-api.git
   
2. **Navigate to the project directory:**
   ```bash
   cd bookstore-api
   
3. **Install the dependencies and build the project**
   ```bash
   mvn clean install

4. **Setup your database connection:**
   - create `env.properties` file in `src/main/resources` directory, copy the configuration from `env-sample.properties` file and modify it to your database credentials

6. **Run the application**
   ```bash
   mvn spring-boot:run

7. **Access the application:**
   - The application will be running at `http://localhost:9090`

## API Documentation
### Endpoints
#### Get All Books
- **URL:** `/api/v1/books?page=1&pageSize=5`
- **Method:** `GET`
- **Response:**
  ```json
    [
    {
        "id": "53550451-d580-4770-bb76-a871aae0df03",
        "bookName": "To Kill a Mockingbird",
        "authorName": "Harper Lee",
        "publishedDate": "1960-07-11",
        "available": false
    },
    {
        "id": "19eeab8d-1385-4292-b063-78d38806aa72",
        "bookName": "1984",
        "authorName": "George Orwell",
        "publishedDate": "1949-06-08",
        "isbn": "9780451524935",
        "available": false
    },
    {
        "id": "20c7453b-3a9a-439c-ac2b-ddaf027cc51d",
        "bookName": "Pride and Prejudice",
        "authorName": "Jane Austen",
        "publishedDate": "1813-01-28",
        "isbn": "9780141439518",
        "available": false
    },
    {
        "id": "19d795d6-75d5-4675-93c3-8fe386af73c0",
        "bookName": "The Great Gatsby",
        "authorName": "F. Scott Fitzgerald",
        "publishedDate": "1925-04-10",
        "isbn": "9780743273565",
        "available": false
    },
    {
        "id": "434c1c77-32a8-4c41-bcb6-366e0201ab6c",
        "bookName": "Animal Farm",
        "authorName": "George Orwell",
        "publishedDate": "1945-08-17",
        "isbn": "9780451526342",
        "available": false
    }
 ]
#### Get Book By ID
- **URL:** `/api/v1/books/{bookId}`
- **Method:** `GET`
- **Response:**
  ```json
    {
        "id": "53550451-d580-4770-bb76-a871aae0df03",
        "bookName": "To Kill a Mockingbird",
        "authorName": "Harper Lee",
        "publishedDate": "1960-07-11",
        "available": false
    }
#### Search for book by either book name, author name, published date or ISBN
- **URL:** `/api/v1/books/search?searchKey=Lee`
- **Method:** `GET`
- **Response:**
  ```json
    [
     {
        "id": "53550451-d580-4770-bb76-a871aae0df03",
        "bookName": "To Kill a Mockingbird",
        "authorName": "Harper Lee",
        "publishedDate": "1960-07-11",
        "available": false
    }
  ]

#### Create new book
- **URL:** `/api/v1/books/create`
- **Method:** `POST`
- **Request body:** 
  ```json
    {
        "bookName": "To Kill a Mockingbird",
        "authorName": "Harper Lee",
        "publishedDate": "1960-07-11"
    }
- **Response:**
  ```json
    {
        "id": "53550451-d580-4770-bb76-a871aae0df03",
        "bookName": "To Kill a Mockingbird",
        "authorName": "Harper Lee",
        "publishedDate": "1960-07-11",
        "available": false
    }

#### Edit book
- **URL:** `/api/v1/books/{bookId}`
- **Method:** `PATCH`
- **Request body:** 
  ```json
    {
        "bookName": "To Kill a Mockingbird v2",
        "authorName": "Harper Lee",
        "publishedDate": "2000-07-11"
    }
- **Response:**
  ```json
    {
        "id": "53550451-d580-4770-bb76-a871aae0df03",
        "bookName": "To Kill a Mockingbird v2",
        "authorName": "Harper Lee",
        "publishedDate": "2000-07-11",
        "available": false
    }

#### Update book availability status
- **URL:** `/api/v1/books/{bookId}/update-book-status`
- **Method:** `PATCH`
- **Response:**
  ```json
    {
        "id": "53550451-d580-4770-bb76-a871aae0df03",
        "bookName": "To Kill a Mockingbird v2",
        "authorName": "Harper Lee",
        "publishedDate": "2000-07-11",
        "available": true
    }
  
#### Delete book
- **URL:** `/api/v1/books/{bookId}`
- **Method:** `DELETE`
- **Response:**
  ```json
   {
    "status": "success",
    "message": "book record deleted",
    "available": false
  }
## Unit Testing
### Libraries Used
- **Mockito:** For mocking dependencies and verifying interactions.
- **Spring Boot Test:** For writing and running the tests with Spring Boot.

### Test Sample
#### Create book record test
  ```java
      @Test
        void testCreateBook() {
            BookRequest request = new BookRequest("Test Book Name", "Test Author Name", "ISBN98434", "2024-05-25");
            when(bookRepository.findByBookNameAndAuthorName(anyString(), anyString())).thenReturn(Optional.empty());
            when(bookRepository.save(any(Book.class))).thenReturn(mockBook);
    
            BookResponse response = bookService.createBook(request);
    
            assertNotNull(response);
            assertEquals(mockBook.getId(), response.getId());
            verify(bookRepository, times(1)).save(any(Book.class));
        }
```
## Spring Security Setup
- Add the following dependencies to your pom.xml:
  ```xml
     <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
  </dependency>

## Dependencies
### Main Dependencies
- **Spring Boot Starter Data JPA:** For database interactions.
- **Spring Boot Starter Web:** For building the REST API.
- **Spring Boot Starter Security:** For securing the application.

### Test Dependencies
- **Spring Boot Starter Test:** For integration testing.
- **Mockito:** For unit testing and mocking.

#### Disclaimer:
This project is for findar assessment test. This project cannot be used in production environment.
