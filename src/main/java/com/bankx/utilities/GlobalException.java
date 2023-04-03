package com.bankx.controllers.exceptionController;

import com.bankx.utilities.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> notValidEx(RuntimeException runtimeException) {
        ErrorResponse msg = new ErrorResponse(runtimeException.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

}
