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
        Integer totalPage;

        Integer totalCount = questionMapper.count();
        if (totalCount%size==0){
            totalPage=totalCount/size;
        }else{
            totalPage=totalCount/size+1;
        }
        if (currentPage<1){
            currentPage=1;
        }
        if (currentPage>totalPage){
            currentPage=totalPage;
        }

        pageDTO.setPagination(totalPage,currentPage);

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
    //个人资料发布问题列表实现
    public PageDTO listByUserId(int userId, Integer currentPage, Integer size) {
        PageDTO pageDTO = new PageDTO();
        Integer totalPage;

        Integer totalCount = questionMapper.countByUserId(userId);

        if (totalCount%size==0){
            totalPage=totalCount/size;
        }else{
            totalPage=totalCount/size+1;
        }


        if (currentPage<1){
            currentPage=1;
        }
        if (currentPage>totalPage){
            currentPage=totalPage;
        }

        pageDTO.setPagination(totalPage,currentPage);

        Integer offset=size*(currentPage-1);
        List<Question> questionList = questionMapper.listByUserId(userId,offset,size);
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

    public QuestionDTO getById(Integer id) {
        Question question=questionMapper.getById(id);
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user=userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpate(Question question) {
        if (question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else {
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        }
    }
}
