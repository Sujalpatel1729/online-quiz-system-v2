package com.quiz.dao;

import com.quiz.model.User;

public interface UserDao {
    User findByEmail(String email);   // find user by email
    boolean save(User user);          // save new user
}