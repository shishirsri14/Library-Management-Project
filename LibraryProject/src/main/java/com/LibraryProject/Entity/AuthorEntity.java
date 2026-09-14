package com.LibraryProject.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "author_table")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AuthorId;

    @Column(nullable = false)
    private String AuthorName;


    @Column(nullable = false)
    private LocalDateTime AuthorDateOfBirth;

    @Column(nullable = false)
    private String AuthorCountry;
@OneToMany(mappedBy = "author")
private Set<BookEntity> books = new HashSet<>();



}
