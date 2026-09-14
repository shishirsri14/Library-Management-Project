package com.LibraryProject.Repo;


import com.LibraryProject.Entity.User;
import com.LibraryProject.Entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepo extends JpaRepository<Session,Long> {

    List<Session> findAllByUsers(User users);

    Optional<Session> findByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);

}

