package com.college.library.service;

import com.college.library.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    // In-memory list to store book records (basic setup without extra DB config)
    private final List<Book> bookList = new ArrayList<>();

    // Sample initial data for easy testing
    public BookService() {
        bookList.add(new Book(101, "Clean Code", "Robert C. Martin", "Programming", 5));
        bookList.add(new Book(102, "Introduction to Algorithms", "Thomas H. Cormen", "Computer Science", 3));
    }

    // b) Add a new book
    public Book addBook(Book book) {
        bookList.add(book);
        return book;
    }

    // c1) Retrieve all books
    public List<Book> getAllBooks() {
        return bookList;
    }

    // c2) Retrieve a specific book by ID
    public Book getBookById(Integer id) {
        for (Book book : bookList) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    // d) Update a book's details by ID
    public Book updateBook(Integer id, Book updatedBook) {
        for (Book book : bookList) {
            if (book.getId().equals(id)) {
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                book.setCategory(updatedBook.getCategory());
                book.setAvailableCopies(updatedBook.getAvailableCopies());
                return book;
            }
        }
        return null;
    }

    // e) Delete a book by ID
    public boolean deleteBook(Integer id) {
        return bookList.removeIf(book -> book.getId().equals(id));
    }
}
