package me.alanton.carshopcrm.service;

import me.alanton.carshopcrm.dto.request.RefreshTokenRequest;
import me.alanton.carshopcrm.dto.request.SignInRequest;
import me.alanton.carshopcrm.dto.request.SignUpRequest;
import me.alanton.carshopcrm.dto.response.SignInResponse;
import me.alanton.carshopcrm.dto.response.SignUpResponse;

public interface AuthService {
    SignUpResponse signUp(SignUpRequest signUpRequest);
    SignInResponse signIn(SignInRequest signInRequest);
    SignInResponse refreshToken(RefreshTokenRequest refreshToken);
    void logout(RefreshTokenRequest refreshToken);
}
