package com.LibraryProject.Controller;


import com.LibraryProject.DTO.BookDto;
import com.LibraryProject.Entity.BookEntity;
import com.LibraryProject.Repo.BookRepo;
import com.LibraryProject.Repo.TestContainerCong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.modelmapper.internal.bytebuddy.matcher.ElementMatchers.any;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;
import static reactor.core.publisher.Mono.when;

@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerCong.class)
class BookControllerTestIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BookEntity bookEntity;
    private BookDto bookDto;
    private BookRepo bookRepo;

    @BeforeEach
    void setup(){
        bookEntity = BookEntity.builder()
                .bookTitle("Maths")
                .bookPublishDate(LocalDateTime.now())
                .bookPrice(890)
                .bookPages(300)
                .build();

        bookDto = BookDto.builder() .bookTitle("Maths")
                .bookPublishDate(LocalDateTime.now())
                .bookPrice(890)
                .bookPages(300)
                .build();
    }
    @Test
    void testGetBookById(){

        //Arrange
        BookEntity savedBook =  bookRepo.save(bookEntity);
        // Act
        webTestClient.get()
                .uri("/book/{id}" , savedBook.getBookId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BookDto.class)
              .isEqualTo(bookDto);    // isko bhi  kr skte hai
//                   .value(bookDto -> {
//                               assertThat(bookDto.getBookId()).isEqualTo(savedBook.getBookId());
//                           }










}

}