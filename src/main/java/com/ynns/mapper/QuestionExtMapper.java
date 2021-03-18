package com.ynns.mapper;

import com.ynns.pojo.Question;
import com.ynns.pojo.QuestionExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
}