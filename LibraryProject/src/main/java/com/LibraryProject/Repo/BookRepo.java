package com.LibraryProject.Repo;

import com.LibraryProject.Entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepo extends JpaRepository<BookEntity,Long> {
    //Ye Sb JPARepository Me nahi aata hai

    List<BookEntity> findByBookTitle(String bookTitle);

    List<BookEntity> findByBookPrice(Integer Price);

   // List<BookEntity> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT b FROM BookEntity b WHERE LOWER(b.bookTitle) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<BookEntity> searchByTitle(@Param("title") String title);
}
