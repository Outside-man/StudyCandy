package com.studycandy.mapper;

import com.studycandy.model.Question;

import java.util.List;

public interface QuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Question record);

    Question selectByPrimaryKey(Integer id);

    List<Question> selectAll();

    int updateByPrimaryKey(Question record);

    List<Question> selectByUserId(Integer userId);
}