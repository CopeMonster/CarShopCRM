package me.alanton.carshopcrm.exception.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.alanton.carshopcrm.exception.policy.ApplicationExceptionPolicy;

@Getter
@Setter
@ToString
public class ApplicationException extends RuntimeException implements ApplicationExceptionPolicy {
    private final String code;
    private final String message;

    public ApplicationException(ApplicationExceptionReason reason) {
        this(reason, (Object[]) null);
    }

    public ApplicationException(ApplicationExceptionReason reason, Object... args) {
        this.code = reason.getCode();

        this.message = (args == null || args.length == 0) ?
                reason.getMessage() : String.format(reason.getMessage(), args);
    }

    @Override
    public String getLocalizedMessage() {
        return message;
    }
}
