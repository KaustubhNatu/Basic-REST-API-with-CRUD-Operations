package com.example.userapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> handleBadRequest(IllegalArgumentException ex) {
        String msg = ex.getMessage();
        if ("User not found".equals(msg)) {
            return ResponseEntity.status(404).body(Map.of("error", msg));
        }
        return ResponseEntity.badRequest().body(Map.of("error", msg));
    }
}
