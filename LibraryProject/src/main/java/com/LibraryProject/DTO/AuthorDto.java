package com.LibraryProject.DTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AuthorDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AuthorId;

    @Column(nullable = false)
    private String AuthorName;


    @Column(nullable = false)
    private LocalDateTime AuthorDateOfBirth;

    @Column(nullable = false)
    private String AuthorCountry;
}
