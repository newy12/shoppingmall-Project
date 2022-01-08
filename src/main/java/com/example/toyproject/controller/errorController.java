package com.example.toyproject.controller;

import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@Controller
public class errorController implements ErrorController{

/*    public String getErrorPath(){
        return "/error";
        }*/
    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model){
        String errorPage = "error";
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null){
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()){
                errorPage = "error/404";

            }
        }
        return errorPage;
    }
}
