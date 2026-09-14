package com.LibraryProject.Implementation;

import com.LibraryProject.DTO.BookDto;
import com.LibraryProject.Entity.BookEntity;
import com.LibraryProject.Repo.BookRepo;
import com.LibraryProject.ServiceInter.BookServiceInterface;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookImp implements BookServiceInterface {

    private final BookRepo bookRepo;
    private final ModelMapper modelMapper;

    @Override
    public Page<BookDto> getAllBooks(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy)
        );

        return bookRepo.findAll(pageable)
                .map(book -> modelMapper.map(book, BookDto.class));
    }

    @Override
    public BookDto getBookById(Long id) {

        BookEntity book = bookRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Book Not Found"));

        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public BookDto createBook(BookDto bookDto) {

        BookEntity book = modelMapper.map(bookDto, BookEntity.class);

        BookEntity savedBook = bookRepo.save(book);

        return modelMapper.map(savedBook, BookDto.class);
    }

    @Override
    public BookDto updateBook(Long id, BookDto bookDto) {

        BookEntity existingBook = bookRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Book Not Found"));

        modelMapper.map(bookDto, existingBook);

        BookEntity updatedBook = bookRepo.save(existingBook);

        return modelMapper.map(updatedBook, BookDto.class);
    }

    @Override
    public void deleteBook(Long id) {

        BookEntity book = bookRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Book Not Found"));

        bookRepo.delete(book);
    }

}
