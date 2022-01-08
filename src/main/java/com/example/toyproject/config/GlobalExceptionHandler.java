package com.example.toyproject.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

/*    @ExceptionHandler(value = Exception.class)
    public String handleArgumentException(Exception e){
        return "<h1>"+e.getMessage()+"</h1>"+"<button>"+"<a href='/'>"+"메인으로 돌아가기"+"</a>"+"</button>";
    }*/
}
