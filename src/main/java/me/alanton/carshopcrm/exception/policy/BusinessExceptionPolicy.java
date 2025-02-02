package me.alanton.carshopcrm.exception.policy;

import org.springframework.http.HttpStatus;

public interface BusinessExceptionPolicy extends ExceptionPolicy {
    HttpStatus getStatus();
}
