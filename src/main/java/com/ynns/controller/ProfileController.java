package com.ynns.controller;

import com.ynns.dto.PageDTO;
import com.ynns.mapper.UserMapper;
import com.ynns.pojo.User;
import com.ynns.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          HttpServletRequest request,
                          @RequestParam(name = "currentPage",defaultValue = "1") Integer currentPage,
                          @RequestParam(name = "size",defaultValue = "5") Integer size,
                          Model model){

        User user= (User) request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }

        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if ("replies".contains(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }

        PageDTO pageDTO = questionService.listByUserId(user.getId(), currentPage, size);
        model.addAttribute("pagination",pageDTO);
        return "profile";
    }
}
