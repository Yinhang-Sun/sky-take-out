package com.sky.handler;

import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler, handles business exceptions thrown in the project
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Catching business exceptions
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("Exception information：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

}
