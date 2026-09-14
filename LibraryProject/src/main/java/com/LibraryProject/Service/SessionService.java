package com.LibraryProject.Service;



import com.LibraryProject.Entity.Session;
import com.LibraryProject.Entity.User;
import com.LibraryProject.Repo.SessionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepo sessionRepo;

    private static final int SESSION_LIMIT = 2;
    // Users type ka ek object method me receive ho raha hai. Is object ke andar
// ek particular user ki information (id, email, password, etc.) hoti hai.


    public void generateNewSession(User users, String refreshToken){
        List<Session> userSession = sessionRepo.findAllByUsers(users);
        // sessionRepo ke andar jo findAllByUsers() method hai usko call karo.
        //Us method ko current users object pass karo.
        //Ye method database me us user ki saari sessions search karega.
        //Jo sessions milengi, unhe List<Session> ke roop me return karega.
        //Us list ko userSession variable me store kar diya.
        if (userSession.size()>= SESSION_LIMIT){
            Session leastRecentUsed = userSession.get(0);
            sessionRepo.delete(leastRecentUsed);
        }
        //Ye sirf list ka pehla element nikal raha hai aur usko leastRecentUsed variable me store kar raha hai.
        //
        //Lekin ek baat aur:
        //
        //👉 Ye tabhi least recently used session hogi jab userSession list pehle se oldest-to-newest order me sorted ho.
        //
        //Agar sorting nahi ki hai, to get(0) sirf pehli session dega, zaroori nahi ki wo oldest hi ho.

        Session newSession = Session.builder()
                .users(users)
                .refreshToken(refreshToken).
                localDateTime(LocalDateTime.now())
                .build();

        sessionRepo.save(newSession);
    }

    public boolean validateSession(String refreshToken) {
        Session session = sessionRepo.findByRefreshToken(refreshToken).orElseThrow(()
                -> new SessionAuthenticationException("Session is not found for refreshToke"));
        session.setLocalDateTime(LocalDateTime.now());
        sessionRepo.save(session);
        return true;
    }
    public void logout(String refreshToken) {

        Session session = sessionRepo.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        sessionRepo.delete(session);
    }
}
