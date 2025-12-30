package com.quiz.dao.impl;

import com.quiz.dao.ResultDao;
import com.quiz.model.Result;
import com.quiz.config.DB;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ResultDaoImpl implements ResultDao {

    @Override
    public boolean save(Result r) {
        String sql = "INSERT INTO results(userId,score,total,subject,date) VALUES(?,?,?,?,?)";
        try (Connection con = DB.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getUserId());
            ps.setInt(2, r.getScore());
            ps.setInt(3, r.getTotal());
            ps.setString(4, r.getSubject());
            ps.setTimestamp(5, Timestamp.valueOf(r.getDate()));
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Result> findByUser(int userId) {
        List<Result> list = new ArrayList<>();
        String sql = "SELECT * FROM results WHERE userId=?";
        try (Connection con = DB.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Result> findAll() {
        List<Result> list = new ArrayList<>();
        String sql = "SELECT * FROM results";
        try (Connection con = DB.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Result mapRow(ResultSet rs) throws SQLException {
        Result r = new Result();
        r.setResultId(rs.getInt("id"));
        r.setUserId(rs.getInt("userId"));
        r.setScore(rs.getInt("score"));
        r.setTotal(rs.getInt("total"));
        r.setSubject(rs.getString("subject"));
        r.setDate(rs.getTimestamp("date").toLocalDateTime());
        return r;
    }
}