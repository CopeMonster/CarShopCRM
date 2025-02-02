package me.alanton.carshopcrm.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignInRequest(
        @NotBlank(message = "Email must not be blank")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password must not be blank")
        @Size(min = 8, message = "Password's length must be at least 8 characters")
        String password
) {
}
