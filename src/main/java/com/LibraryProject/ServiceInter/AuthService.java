package com.LibraryProject.ServiceInter;

import com.LibraryProject.DTO.LoginResponseDto;
import com.LibraryProject.DTO.Login;
import com.LibraryProject.DTO.SignUp;
import com.LibraryProject.DTO.SignupResponseDto;

public interface AuthService {

    SignupResponseDto signup(SignUp request);

    LoginResponseDto login(Login loginRequest);

    LoginResponseDto refreshToken(String refreshToken);
}