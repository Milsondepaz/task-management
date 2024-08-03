package com.milsondev.taskmanagement.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {


    private final static String NOT_FOUND = "Error 404: It seems that the page you are looking for does not exist. The link might be incorrect, or the page may have been removed.";
    private final static String BAD_REQUEST = "Error 400: It seems there was a problem with the information you submitted. Some data might be incorrect or missing.";
    private final static String INTERNAL_SERVER_ERROR = "Error 500: There was an internal problem on our server. We are working to resolve this as quickly as possible.";

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model){
        Object httpStatus = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(httpStatus != null){
            int code = Integer.parseInt(httpStatus.toString());
            if(code == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorNotFound", NOT_FOUND);
            } else if(code == HttpStatus.BAD_REQUEST.value()){
                model.addAttribute("errorBadRequest", BAD_REQUEST);
            } else if(code == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                model.addAttribute("errorInternalServerError", INTERNAL_SERVER_ERROR);
            }
        }

        return "error";
    }
}
