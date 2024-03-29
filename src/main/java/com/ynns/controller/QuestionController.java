package com.ynns.controller;

import com.ynns.dto.CommentDTO;
import com.ynns.dto.QuestionDTO;
import com.ynns.enums.CommentTypeEnum;
import com.ynns.service.impl.CommentService;
import com.ynns.service.impl.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id,
                           Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        //标签
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        model.addAttribute("relatedQuestions", relatedQuestions);

        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question", questionDTO);

        //回复
        List<CommentDTO> commentDTOList = commentService.listByQuestionId(id, CommentTypeEnum.QUESTION);
        model.addAttribute("comments", commentDTOList);
        return "question";
    }
}
