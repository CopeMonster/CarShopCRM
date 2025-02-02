package me.alanton.carshopcrm.exception.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BusinessExceptionReason {
    USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),
    USER_IS_ALREADY_EXIST("User is already exist", HttpStatus.BAD_REQUEST),

    ROLE_NOT_FOUND("Role not found", HttpStatus.NOT_FOUND),
    ROLE_IS_ALREADY_EXIST("Role is alrady exist", HttpStatus.BAD_REQUEST),

    INVALID_TOKEN("Invalid token", HttpStatus.BAD_REQUEST);

    private final String code = this.getClass().getSimpleName();
    private final String message;
    private final HttpStatus status;
}
