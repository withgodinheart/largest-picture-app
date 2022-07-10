package com.bobocode.picture.controller;

import com.bobocode.picture.exception.PictureNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PictureControllerAdvice {

    @ExceptionHandler(PictureNotFoundException.class)
    public ResponseEntity<String> handlePictureNotFound(PictureNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
