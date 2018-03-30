package com.stormfives.admin.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  . 403
 * Created by zxb on 10/19/15.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class NoPermissionException extends RuntimeException {

    /**
     *
     */
    public NoPermissionException() {
    }

    /**
     *
     *
     * @param s
     */
    public NoPermissionException(String s) {
        super(s);
    }
}
