package com.quiz.dao.impl;

import com.quiz.dao.QuestionDao;
import com.quiz.model.Question;
import com.quiz.config.DB;

import java.sql.*;
import java.util.*;

public class QuestionDaoImpl implements QuestionDao {

    @Override
    public List<Question> findRandomBySubject(String subject, int limit) {
        List<Question> list = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE subject=? ORDER BY RAND() LIMIT ?";
        try (Connection con = DB.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, subject);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Question q = mapRow(rs);
                list.add(q);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean save(Question q) {
        String sql = "INSERT INTO questions(subject,question,optionA,optionB,optionC,optionD,correctOption) VALUES(?,?,?,?,?,?,?)";
        try (Connection con = DB.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, q.getSubject());
            ps.setString(2, q.getQuestion());
            ps.setString(3, q.getOptionA());
            ps.setString(4, q.getOptionB());
            ps.setString(5, q.getOptionC());
            ps.setString(6, q.getOptionD());
            ps.setString(7, String.valueOf(q.getCorrectOption()));
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Question q) {
        String sql = "UPDATE questions SET subject=?, question=?, optionA=?, optionB=?, optionC=?, optionD=?, correctOption=? WHERE id=?";
        try (Connection con = DB.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, q.getSubject());
            ps.setString(2, q.getQuestion());
            ps.setString(3, q.getOptionA());
            ps.setString(4, q.getOptionB());
            ps.setString(5, q.getOptionC());
            ps.setString(6, q.getOptionD());
            ps.setString(7, String.valueOf(q.getCorrectOption()));
            ps.setInt(8, q.getQId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int qId) {
        String sql = "DELETE FROM questions WHERE id=?";
        try (Connection con = DB.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, qId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Question> findAll(String subject) {
        List<Question> list = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE subject=?";
        try (Connection con = DB.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, subject);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Question mapRow(ResultSet rs) throws SQLException {
        Question q = new Question();
        q.setQId(rs.getInt("id"));
        q.setSubject(rs.getString("subject"));
        q.setQuestion(rs.getString("question"));
        q.setOptionA(rs.getString("optionA"));
        q.setOptionB(rs.getString("optionB"));
        q.setOptionC(rs.getString("optionC"));
        q.setOptionD(rs.getString("optionD"));
        q.setCorrectOption(rs.getString("correctOption").charAt(0));
        return q;
    }
}