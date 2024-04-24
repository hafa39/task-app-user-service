package com.example.userservice.web.exc;

public class UnsupportedMediaTypeExc extends RuntimeException{
    public UnsupportedMediaTypeExc(String message) {
        super(message);
    }

    public UnsupportedMediaTypeExc() {
    }
}
