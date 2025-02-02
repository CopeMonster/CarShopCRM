package me.alanton.carshopcrm.dto.response;

public record SignUpResponse(
        UserResponse userResponse,
        SignInResponse signInResponse
) {
}
