package com.LibraryProject.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

@Table(
        name = "book_table",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_book_title" , //Book Title ke liye Unique Key Constraint
                        columnNames = "book_title"
                )
        }
)
public class BookEntity extends AutitableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long BookId;

    @Column(name ="book_table" ,nullable = false )
    private String bookTitle;

    @Column(nullable = false)
    private LocalDateTime bookPublishDate;

    @Column(nullable = false)
    private Integer bookPrice;

    @Column(nullable = false)
    private Integer bookPages;


@ManyToOne
@JoinColumn(name = "AuthorId")    // OWNING SIDE
    private AuthorEntity author;   // Yaha se connection banata hai
}


