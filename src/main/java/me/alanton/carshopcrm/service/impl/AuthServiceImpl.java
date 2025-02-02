package me.alanton.carshopcrm.service.impl;

import lombok.RequiredArgsConstructor;
import me.alanton.carshopcrm.dto.request.RefreshTokenRequest;
import me.alanton.carshopcrm.dto.request.SignInRequest;
import me.alanton.carshopcrm.dto.request.SignUpRequest;
import me.alanton.carshopcrm.dto.request.UserRequest;
import me.alanton.carshopcrm.dto.response.SignInResponse;
import me.alanton.carshopcrm.dto.response.SignUpResponse;
import me.alanton.carshopcrm.dto.response.UserResponse;
import me.alanton.carshopcrm.entity.RefreshToken;
import me.alanton.carshopcrm.entity.User;
import me.alanton.carshopcrm.exception.impl.BusinessException;
import me.alanton.carshopcrm.exception.impl.BusinessExceptionReason;
import me.alanton.carshopcrm.repository.RefreshTokenRepository;
import me.alanton.carshopcrm.security.JwtUtils;
import me.alanton.carshopcrm.service.AuthService;
import me.alanton.carshopcrm.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        UserRequest userRequest = UserRequest.builder()
                .email(signUpRequest.email())
                .firstname(signUpRequest.firstname())
                .lastname(signUpRequest.lastname())
                .password(passwordEncoder.encode(signUpRequest.password()))
                .build();

        UserResponse userResponse = userService.createUser(userRequest);

        User user = (User) userDetailsService.loadUserByUsername(signUpRequest.email());

        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);

        saveRefreshToken(refreshToken, user);

        SignInResponse signInResponse = new SignInResponse(accessToken, refreshToken);

        return new SignUpResponse(userResponse, signInResponse);
    }

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.email(),
                signInRequest.password()
        ));

        User user = (User) userDetailsService.loadUserByUsername(signInRequest.email());

        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);

        saveRefreshToken(refreshToken, user);

        return new SignInResponse(accessToken, refreshToken);
    }

    @Override
    public SignInResponse refreshToken(RefreshTokenRequest refreshToken) {
        String username = jwtUtils.extractUsername(refreshToken.token(), false);
        if (username == null) {
            throw new BusinessException(BusinessExceptionReason.INVALID_TOKEN);
        }

        User user = (User) userDetailsService.loadUserByUsername(username);
        if (!jwtUtils.isTokenValid(refreshToken.token(), user, false)) {
            throw new BusinessException(BusinessExceptionReason.INVALID_TOKEN);
        }

        String newAccessToken = jwtUtils.generateAccessToken(user);
        String newRefreshToken = jwtUtils.generateRefreshToken(user);

        saveRefreshToken(newRefreshToken, user);

        return new SignInResponse(newAccessToken, newRefreshToken);
    }

    @Override
    public void logout(RefreshTokenRequest refreshToken) {
        String username = jwtUtils.extractUsername(refreshToken.token(), false);
        if (username == null) {
            throw new BusinessException(BusinessExceptionReason.INVALID_TOKEN);
        }

        User user = (User) userDetailsService.loadUserByUsername(username);
        if (!jwtUtils.isTokenValid(refreshToken.token(), user, false)) {
            throw new BusinessException(BusinessExceptionReason.INVALID_TOKEN);
        }

        RefreshToken token = refreshTokenRepository.findByToken(refreshToken.token())
                .orElseThrow(() -> new BusinessException(BusinessExceptionReason.INVALID_TOKEN));

        token.setRevoked(true);
        refreshTokenRepository.save(token);
    }

    private void saveRefreshToken(String refreshToken, User user) {
        user.getRefreshTokens().forEach(token -> token.setRevoked(true));

        RefreshToken token = new RefreshToken();
        token.setToken(refreshToken);
        token.setUser(user);
        token.setTokenExpiredAt(jwtUtils.extractExpiration(refreshToken, false).toInstant());
        token.setRevoked(false);

        refreshTokenRepository.save(token);
        user.getRefreshTokens().add(token);
    }
}
