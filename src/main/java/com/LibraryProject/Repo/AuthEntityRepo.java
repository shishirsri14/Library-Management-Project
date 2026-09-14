package com.LibraryProject.Repo;

import com.LibraryProject.Entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AuthEntityRepo extends JpaRepository<AuthEntity,Long> {


    Optional<AuthEntity> findByEmail(String username);
}
