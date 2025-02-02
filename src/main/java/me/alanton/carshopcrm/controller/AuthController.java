package me.alanton.carshopcrm.controller;

import lombok.RequiredArgsConstructor;
import me.alanton.carshopcrm.dto.request.RefreshTokenRequest;
import me.alanton.carshopcrm.dto.request.SignInRequest;
import me.alanton.carshopcrm.dto.request.SignUpRequest;
import me.alanton.carshopcrm.dto.response.SignInResponse;
import me.alanton.carshopcrm.dto.response.SignUpResponse;
import me.alanton.carshopcrm.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest) {
        SignInResponse signInResponse = authService.signIn(signInRequest);

        return ResponseEntity.ok(signInResponse);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = authService.signUp(signUpRequest);

        return ResponseEntity.ok(signUpResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<SignInResponse> refreshToken(@RequestBody RefreshTokenRequest refreshToken) {
        SignInResponse signInResponse = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(signInResponse);
    }

    @PostMapping("/log-out")
    public void logout(@RequestBody RefreshTokenRequest refreshToken) {
        authService.logout(refreshToken);
    }
}
