package com.sky.exception;

/**
 * Base(Business) Exception
 */
public class BaseException extends RuntimeException {

    public BaseException() {
    }

    public BaseException(String msg) {
        super(msg);
    }

}
