package com.LibraryProject.ServiceInter;
import com.LibraryProject.DTO.BookDto;
import org.springframework.data.domain.Page;

public interface BookServiceInterface {

    Page<BookDto> getAllBooks(int page, int size, String sortBy);

    BookDto getBookById(Long id);

    BookDto createBook(BookDto bookDto);

    BookDto updateBook(Long id, BookDto bookDto);

    void deleteBook(Long id);
}
