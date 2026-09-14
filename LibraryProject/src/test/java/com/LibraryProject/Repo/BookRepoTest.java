package com.LibraryProject.Repo;

import com.LibraryProject.Entity.BookEntity;
import com.LibraryProject.Service.BookService;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Import(TestContainerCong.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class BookRepoTest {

    @Autowired
    private BookRepo bookRepo;
    private BookEntity bookEntity;
    private BookService bookService;

    @BeforeEach
    void setup() {

        bookEntity = BookEntity.builder()
                .bookTitle("Java")
                .bookPublishDate(LocalDateTime.now())
                .bookPrice(500)
                .bookPages(500)
                .build();
    }
    @Test
    void testfindByBookTitle_whenTitleIsValid_thenReturnBook() {
        //Arrange Or Given
        bookRepo.save(bookEntity);
       // bookRepo.findByBookTitle("");
        // Act Or When
        List<BookEntity> entities = bookRepo.findByBookTitle(bookEntity.getBookTitle());
                // Assert or Then
        assertThat(entities).isNotNull();
    //    assertThat(entities).isNotEmpty();
        assertThat(entities.get(0).getBookTitle())
                .isEqualTo(bookEntity.getBookTitle());
    }

    @Test
   // void testFindByEmail_whenEmailNotFound_thenReturnEmptyEmployeeList(){
    void testFindByTitle_whenTitleNotFound_thenReturnEmptyBookList(){

    }
    @Test
    void testDeleteById_WhenBookIsNotFound_thenThrowException() {

        when(bookRepo.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.deleteBookById(1L))
                .isInstanceOf(NoResourceFoundException.class)
                .hasMessage("Book not found");

        verify(bookRepo, never()).deleteById(anyLong());
    }
    }
