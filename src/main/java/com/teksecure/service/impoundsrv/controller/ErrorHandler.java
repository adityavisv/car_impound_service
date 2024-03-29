package com.teksecure.service.impoundsrv.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public void handleIllegalArgument(Exception ex) {
        System.out.println("HTTP REQUEST 400");
        System.out.println(ex.getMessage());
        ex.printStackTrace();
    }
}
