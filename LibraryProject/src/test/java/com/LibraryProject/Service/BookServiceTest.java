package com.LibraryProject.Service;

import com.LibraryProject.DTO.BookDto;
import com.LibraryProject.Entity.BookEntity;
import com.LibraryProject.Repo.AuthorRepo;
import com.LibraryProject.Repo.BookRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepo bookRepo;

    @Mock
    private AuthorRepo authorRepo;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private BookService bookService;


    private BookEntity bookEntity;

    @BeforeEach
    void setUp() {

    //    title = "Maths";

        bookEntity = BookEntity.builder()
                .bookTitle("Maths")
                .bookPublishDate(LocalDateTime.now())
                .bookPrice(890)
                .bookPages(300)
                .build();
    }
    @Test
    void testGetBookByTitle_WhenBookTitleIsPresent_ThenReturnBookDto() {

        // Arrange
        String title = bookEntity.getBookTitle();

        //< ----- shift above side --------->




        when(bookRepo.findByBookTitle(title))
                .thenReturn(List.of(bookEntity));

        // Act
        List<BookDto> bookDtos =
                bookService.searchBook(title);

        // Assert
        assertThat(bookDtos)
                .hasSize(1);

        assertThat(bookDtos.get(0).getBookTitle())
                .isEqualTo(bookEntity.getBookTitle());

        assertThat(bookDtos.get(0).getBookPrice())
                .isEqualTo(bookEntity.getBookPrice());

        assertThat(bookDtos.get(0).getBookPages())
                .isEqualTo(bookEntity.getBookPages());

        verify(bookRepo, only()).findByBookTitle(title);
    }
    @Test
    void testsearchBookByPrice_WhenBookPriceIsPresent_ThenReturnBookDto() {

        //Arrange
        String title = bookEntity.getBookTitle() ;


        when(bookRepo.findByBookPrice(900))
                .thenReturn(List.of(bookEntity));
        // Act
        List<BookDto> bookDtos =
                bookService.searchBookByPrice(900);

        // Assert
        assertThat(bookDtos).hasSize(1);


        assertThat(bookDtos.get(0).getBookPrice())
                .isEqualTo(bookEntity.getBookPrice());


    }
    @Test
    void testcreateBook_WhenValidBookDto_thenReturnSavedBookDto(){
        //Arrange

        //((  (BookEntity savedEntity = BookEntity.builder()
        //        .bookTitle("Maths")
        //        .bookPrice(900)
        //        .bookPages(300)
        //        .build();    <Aise de skte hai aur jaha jaha bookentity hai vaha saved entity kr denge >> ))
        BookDto bookDto = BookDto.builder()
                .bookTitle("Maths")
                .bookPublishDate(LocalDateTime.now())
                .bookPrice(890)
                .bookPages(300)
                .build();
        //Jab bhi bookRepo.save() call hoga,
        // Mockito database/repository par actually nahi jayega. Ye savedEntity return kar dega.

        when(bookRepo.save(any(BookEntity.class))).thenReturn(bookEntity);
       // "Jab bhi bookRepo.save() kisi bhi BookEntity ke saath call ho," +
          //      " tab database me actually jaane ke bajay savedEntity return kar dena."


        // Act

        BookDto result = bookService.createBook(bookDto);
//Yahan tum bookService ka createBook() method call kar rahe ho aur usko:

        // Assert
        assertThat(result.getBookTitle()).isEqualTo(bookEntity.getBookTitle());

        assertThat(result.getBookId()).isEqualTo(bookEntity.getBookId());
// ArgumentCaptor === Kisi mocked method ko jo actual argument diya gaya tha,
// us argument ko pakad lena (capture kar lena)
// , taaki hum baad me usko check/inspect kar saken.
        ArgumentCaptor<BookEntity> bookEntityArgumentCaptor = ArgumentCaptor.forClass(BookEntity.class);
        verify(bookRepo).save(bookEntityArgumentCaptor.capture());

        BookEntity captureBook = bookEntityArgumentCaptor.capture();
        assertThat(captureBook.getBookTitle()).isEqualTo(bookEntity.getBookTitle());

        assertThat(result.getBookPages()).isEqualTo(bookEntity.getBookPages());
        verify(bookRepo).save(any(BookEntity.class));
        //"Check karo ki bookRepo.save()
        // method ko test ke during call kiya gaya tha ya nahi, aur usme koi bhi BookEntity pass hui thi ya nahi."
    }
}