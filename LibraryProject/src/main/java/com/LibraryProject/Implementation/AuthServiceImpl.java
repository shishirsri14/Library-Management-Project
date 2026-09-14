package com.LibraryProject.Implementation;

import com.LibraryProject.DTO.LoginResponseDto;
import com.LibraryProject.DTO.Login;
import com.LibraryProject.DTO.SignUp;
import com.LibraryProject.DTO.SignupResponseDto;
import com.LibraryProject.Entity.User;
import com.LibraryProject.Repo.UserRepository;
import com.LibraryProject.Security.JwtService;
import com.LibraryProject.ServiceInter.AuthService;
import com.LibraryProject.Service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SessionService sessionService;


    @Override
    public SignupResponseDto signup(SignUp request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        user.setRole("USER");

        userRepository.save(user);

        return new SignupResponseDto("Signup successful");
    }


    @Override
    public LoginResponseDto login(Login loginRequest) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()
                        )
                );

        String username = authentication.getName();

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        String accessToken =
                jwtService.generatedAccessToken(user);

        String refreshToken =
                jwtService.generatedRefreshToken(user);

        sessionService.generateNewSession(
                user,
                refreshToken
        );

        return new LoginResponseDto(
                accessToken,
                refreshToken
        );
    }


    @Override
    public LoginResponseDto refreshToken(String refreshToken) {

        sessionService.validateSession(refreshToken);

        String username =
                jwtService.extractUsername(refreshToken);

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        String accessToken =
                jwtService.generatedAccessToken(user);

        return new LoginResponseDto(
                accessToken,
                refreshToken
        );
    }
}