package com.example.toyproject.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class errorController implements ErrorController{
    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model){
        String errorPage = "error";
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null){
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()){
                return "error/404";
            }
            if(statusCode == HttpStatus.BAD_REQUEST.value()){
                return "error/404";
            }
        }
        return "error/404";
    }
}
