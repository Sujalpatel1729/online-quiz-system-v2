package com.quiz.dao;

import java.util.List;
import com.quiz.model.Result;

public interface ResultDao {
    boolean save(Result r);                  // save a new result
    List<Result> findByUser(int userId);     // get all results for a user
    List<Result> findAll();                  // get all results (admin view)
}