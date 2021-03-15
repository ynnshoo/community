package com.ynns.service;

import com.ynns.dto.QuestionDTO;
import com.ynns.dto.PageDTO;
import com.ynns.mapper.QuestionMapper;
import com.ynns.mapper.UserMapper;
import com.ynns.pojo.Question;
import com.ynns.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;

    public PageDTO list(Integer currentPage, Integer size) {
        PageDTO pageDTO = new PageDTO();
        Integer totalCount = questionMapper.count();
        pageDTO.setPagination(totalCount,currentPage,size);

        if (currentPage<1){
            currentPage=1;
        }
        if (currentPage>pageDTO.getTotalPage()){
            currentPage=pageDTO.getTotalPage();
        }

        Integer offset=size*(currentPage-1);
        List<Question> questionList = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questionList) {
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        pageDTO.setQuestions(questionDTOList);
        return pageDTO;
    }
}
