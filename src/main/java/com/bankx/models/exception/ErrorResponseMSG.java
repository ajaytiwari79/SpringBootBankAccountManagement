package com.bankx.models.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponseMSG {
    private String message;
    private HttpStatus status;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }
    public void setStatus(HttpStatus status) {
        this.status = status;
    }
    public ErrorResponseMSG(String message, HttpStatus  status) {
        super();
        this.message = message;
        this.status = status;
    }
    public ErrorResponseMSG() {
        super();
        // TODO Auto-generated constructor stub
    }

}

