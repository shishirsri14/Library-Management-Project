package com.LibraryProject.Controller;

import com.LibraryProject.DTO.BookDto;
import com.LibraryProject.Service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")

public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long id) {
        BookDto book =  bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping
    public ResponseEntity<Page<BookDto>> getBookAll(@RequestParam (defaultValue = "0")int page,
                                    @RequestParam (defaultValue = "5")int size ,
                                    @RequestParam(defaultValue = "BookTitle")String sortBy){
        Page<BookDto> book = bookService.getBookByIdAll(page,size,sortBy);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookDto savedbook = bookService.createBook(bookDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedbook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {

        bookService.deleteBookById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<BookDto> searchBook(
            @RequestParam String title) {

        return bookService.searchBook(title);
    }

    @GetMapping("/SearchByPrice")
    public List<BookDto> searchBook(
            @RequestParam Integer price) {

        return bookService.searchBookByPrice(price);
    }

    @GetMapping("/SearchBy")
    public List<BookDto> searchBooktitle(
            @RequestParam String title) {

        return bookService.findByTitleContainingIgnoreCase(title);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBookById(
            @PathVariable Long id,
            @RequestBody BookDto bookDto) {

        BookDto updatedBook = bookService.updateBook(id, bookDto);

        return ResponseEntity.ok(updatedBook);
    }
}
