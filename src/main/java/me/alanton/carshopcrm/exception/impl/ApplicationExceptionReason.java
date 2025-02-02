package me.alanton.carshopcrm.exception.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApplicationExceptionReason {
    ;

    private final String code = this.getClass().getSimpleName();
    private final String message;
}
