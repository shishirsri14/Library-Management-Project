package com.LibraryProject.Repo;

import com.LibraryProject.Entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<AuthorEntity,Long> {
}
