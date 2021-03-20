package com.ynns.handle.advice;

import com.alibaba.fastjson.JSON;
import com.ynns.dto.ResultDTO;
import com.ynns.handle.exception.CustomizeErrorCode;
import com.ynns.handle.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request,
                  HttpServletResponse response,
                  Throwable throwable,
                  Model model) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            ResultDTO resultDTO;
            System.out.println("111111111"+new ResultDTO<>());
            //返回JSON
            if (throwable instanceof CustomizeException){
                resultDTO=ResultDTO.errorOf((CustomizeException)throwable);
            }else {
                resultDTO=ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            System.out.println("222222"+resultDTO);
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }else{
            //错误页面跳转
            //        HttpStatus httpStatus=getStatus(request);
            if (throwable instanceof CustomizeException){
                model.addAttribute("message", throwable.getMessage());
            }else {
                model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }




//    private HttpStatus getStatus(HttpServletRequest request) {
//        Integer attribute = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        if (attribute==null){
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return HttpStatus.valueOf(attribute);
//    }
}
