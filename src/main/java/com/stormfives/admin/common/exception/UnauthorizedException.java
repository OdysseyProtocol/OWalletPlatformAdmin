package com.stormfives.admin.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *   401
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    /**
     *  .
     */
    public UnauthorizedException() {
    }

    /**
     *
     *
     * @param message
     */
    public UnauthorizedException(String message) {
        super(message);
    }
}
