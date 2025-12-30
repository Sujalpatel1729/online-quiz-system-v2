package com.quiz.dao;

import java.util.List;
import com.quiz.model.Question;

public interface QuestionDao {
    List<Question> findRandomBySubject(String subject, int limit); // get random questions
    boolean save(Question q);                                      // add new question
    boolean update(Question q);                                    // update existing question
    boolean delete(int qId);                                       // delete question
    List<Question> findAll(String subject);                        // get all questions by subject
}