package com.ynns.handle.advice;

import com.ynns.handle.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request,Throwable throwable,
                        Model model){
//        HttpStatus httpStatus=getStatus(request);
        if (throwable instanceof CustomizeException){
            model.addAttribute("message", throwable.getMessage());
        }else {
            model.addAttribute("message", "出现错误！！！");
        }
        return new ModelAndView("error");
    }

//    private HttpStatus getStatus(HttpServletRequest request) {
//        Integer attribute = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        if (attribute==null){
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return HttpStatus.valueOf(attribute);
//    }
}
