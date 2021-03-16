package com.ynns.controller;

import com.ynns.dto.PageDTO;
import com.ynns.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "currentPage",defaultValue = "1") Integer currentPage,
                        @RequestParam(name = "size",defaultValue = "5") Integer size
    ){

        //获取user与question所有数据
        PageDTO pagination=questionService.list(currentPage,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }

}
