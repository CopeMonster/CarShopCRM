package me.alanton.carshopcrm.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SignUpRequest(
        @NotBlank(message = "Email must not be blank")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Firstname must not be blank")

        String firstname,

        @NotBlank(message = "Lastname must not be blank")
        String lastname,

        @NotBlank(message = "Password must not be blank")
        @Size(min = 8, message = "Password's length must be at least 8 characters")
        String password,

        @NotBlank(message = "Confirm password must not be blank")
        @Size(min = 8, message = "Password's length must be at least 8 characters")
        String confirmPassword
) {
}
