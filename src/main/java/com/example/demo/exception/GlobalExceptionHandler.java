package com.example.demo.exception;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(ResourceNotFoundException.class)
@ResponseStatus(HttpStatus.NOT_FOUND)
public Map<String,String> handleNotFound(ResourceNotFoundException ex) {
return Map.of("error", ex.getMessage());
}

@ExceptionHandler(Exception.class)
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public Map<String,String> handleOther() {
return Map.of("error","Internal Server Error");
}
}
