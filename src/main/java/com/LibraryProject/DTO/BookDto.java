package com.LibraryProject.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(name ="book_table" ,nullable = false )
    private String bookTitle;

    @Column(nullable = false)
    private LocalDateTime bookPublishDate;

    @Column(nullable = false)
    private Integer bookPrice;

    @Column(nullable = false)
    private Integer bookPages;
    private Long authorId;


}
