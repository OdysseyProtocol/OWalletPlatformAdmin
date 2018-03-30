package com.stormfives.admin.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * return  400
 * Created by zhaimi on 15/10/31.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidArgumentException extends Exception {

    /**
     *
     */
    public InvalidArgumentException() {
    }

    /**
     *
     *
     * @param s
     */
    public InvalidArgumentException(String s) {
        super(s);
    }
}
