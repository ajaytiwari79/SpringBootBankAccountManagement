package com.bankx.models.exception;

import org.springframework.stereotype.Service;

public class NotValidException extends RuntimeException{
    private String msg;

    public NotValidException(String msg) {
        super(msg);
        this.msg=msg;
    }

    public NotValidException() {
        super();
    }


}
