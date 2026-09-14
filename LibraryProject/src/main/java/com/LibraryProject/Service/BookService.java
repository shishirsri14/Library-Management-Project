package com.LibraryProject.Service;

import com.LibraryProject.DTO.BookDto;
import com.LibraryProject.Entity.AuthorEntity;
import com.LibraryProject.Entity.BookEntity;
import com.LibraryProject.Repo.AuthorRepo;
import com.LibraryProject.Repo.BookRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.awt.print.Book;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepo bookRepo;
    private final ModelMapper modelMapper;
    private final AuthorRepo authorRepo;

    // ye ya use kro ya niche wala @Slf4j
   // private static final Logger log = LoggerFactory.getLogger(BookService.class);




    public BookDto getBookById(Long id) {
        log.info("Fetching book with id {}", id);
        BookEntity bookEntity = bookRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Book not found with id {}", id);
                    return new RuntimeException("Book Not Found");
                });

        log.info("Book fetched successfully with id {}", id);

            return modelMapper.map(bookEntity, BookDto.class);

    }

/* te bus post ke liye author id null aayega
    public BookDto createBook(BookDto bookDto) {
        BookEntity bookEntity = modelMapper.map(bookDto,BookEntity.class);
            bookEntity = bookRepo.save(bookEntity);
            return modelMapper.map(bookEntity,BookDto.class);
        } */ // aur ye Author id ko bhi krega store


public BookDto createBook(BookDto bookDto) {

    BookEntity bookEntity = modelMapper.map(bookDto, BookEntity.class);

    AuthorEntity author = authorRepo.findById(bookDto.getAuthorId())
            .orElseThrow(() -> new RuntimeException("Author not found"));

    bookEntity.setAuthor(author);

    bookEntity = bookRepo.save(bookEntity);

    return modelMapper.map(bookEntity, BookDto.class);
}

    public BookDto deleteBookById(Long id) {
        BookEntity bookEntity = bookRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Book Not Found" +id));
         bookRepo.delete(bookEntity);
        return modelMapper.map(bookEntity,BookDto.class);

    }


//Ye sare DB me  jo hai usko print kr vane ke liye
   /* public List<BookDto> getBookByIdAll() {

        List<BookEntity> bookEntities = bookRepo.findAll();

        return bookEntities.stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .toList();
    } */

   public Page<BookDto> getBookByIdAll(int page, int size, String sort) {
       Pageable pageable = PageRequest.of(page, size ,Sort.by(sort));
       Page<BookEntity> books = bookRepo.findAll(pageable);

       return books.map(book -> modelMapper.map(book, BookDto.class));
   }

   // Search ke liye

        public List<BookDto> searchBook(String title) {
            List<BookEntity> books = bookRepo.findByBookTitle(title);

            return books.stream()
                    .map(book -> modelMapper.map(book, BookDto.class))
                    .toList();
        }

    public List<BookDto> searchBookByPrice(Integer Price) {
        List<BookEntity> books = bookRepo.findByBookPrice(Price);

        return books.stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .toList();
    }


    public List<BookDto> findByTitleContainingIgnoreCase(String title) {
        log.debug("Search keyword : {}", title);
        List<BookEntity> books = bookRepo.searchByTitle(title);
        log.debug("Total books found : {}", books.size());
        log.info("Data fetch Successfully ");
        return books.stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .toList();
    }

    public BookDto updateBook(Long id, BookDto bookDto) {

        BookEntity book = bookRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Book Not Found: " + id));

        book.setBookTitle(bookDto.getBookTitle());
        book.setBookPublishDate(bookDto.getBookPublishDate());
        book.setBookPrice(bookDto.getBookPrice());
        book.setBookPages(bookDto.getBookPages());

        BookEntity updatedBook = bookRepo.save(book);

        return modelMapper.map(updatedBook, BookDto.class);
    }


}





