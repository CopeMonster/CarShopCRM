package me.alanton.carshopcrm.service;

import me.alanton.carshopcrm.dto.request.SignInRequest;
import me.alanton.carshopcrm.dto.request.SignUpRequest;
import me.alanton.carshopcrm.dto.response.SignUpResponse;

public interface AuthService {
    SignUpResponse signUp(SignUpRequest signUpRequest);
    SignInRequest signIn(SignInRequest signInRequest);
    SignInRequest refreshToken(String refreshToken);
    void logout(String refreshToken);
}
