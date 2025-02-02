package me.alanton.carshopcrm.dto.response;

public record SignInResponse(
        String accessToken,
        String refreshToken
) {
}
