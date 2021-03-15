package com.ynns.controller;

import com.ynns.dto.PageDTO;
import com.ynns.mapper.UserMapper;
import com.ynns.pojo.User;
import com.ynns.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "currentPage",defaultValue = "1") Integer currentPage,
                        @RequestParam(name = "size",defaultValue = "5") Integer size
    ){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null && cookies.length!=0) {
            for (Cookie cookie : cookies) {
                //通过token的key找到value值
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        //从数据库中查找到Session信息
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        //获取user与question所有数据
        PageDTO pagination=questionService.list(currentPage,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }

}
