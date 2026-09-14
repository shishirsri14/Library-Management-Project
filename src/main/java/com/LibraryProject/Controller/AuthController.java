package com.LibraryProject.Controller;

import com.LibraryProject.DTO.LoginResponseDto;
import com.LibraryProject.DTO.Login;
import com.LibraryProject.DTO.SignUp;
import com.LibraryProject.DTO.SignupResponseDto;
import com.LibraryProject.ServiceInter.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth/api")
public class AuthController {

    private final AuthService authService;



    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(
            @RequestBody SignUp request){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.signup(request));
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody Login loginRequest,
            HttpServletResponse response) {


        LoginResponseDto loginResponseDto =
                authService.login(loginRequest);

        // Refresh token cookie mein save
        Cookie cookie = new Cookie(
                "refreshtoken",
                loginResponseDto.getRefreshToken()
        );

        cookie.setHttpOnly(true);
        cookie.setSecure(false);       // localhost ke liye
        cookie.setPath("/auth/api");
        cookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDto);
    }


    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refreshToken(
            HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            throw new AuthenticationServiceException(
                    "No cookies found"
            );
        }

        String refreshToken = Arrays.stream(cookies)
                .filter(cookie ->
                        "refreshtoken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() ->
                        new AuthenticationServiceException(
                                "Refresh token not found"
                        )
                );

        LoginResponseDto loginResponseDto =
                authService.refreshToken(refreshToken);

        return ResponseEntity.ok(loginResponseDto);
    }
}