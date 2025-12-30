package com.quiz.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.quiz.model.Question;
import com.quiz.model.Result;
import com.quiz.dao.QuestionDao;
import com.quiz.dao.ResultDao;
import com.quiz.dao.impl.QuestionDaoImpl;
import com.quiz.dao.impl.ResultDaoImpl;

public class AdminFrame extends JFrame {
    private QuestionDao qDao = new QuestionDaoImpl();
    private ResultDao rDao = new ResultDaoImpl();

    private JTextArea output = new JTextArea();

    public AdminFrame() {
        setTitle("Admin Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton addQBtn = new JButton("Add Question");
        JButton viewQBtn = new JButton("View Questions");
        JButton viewRBtn = new JButton("View Results");

        JPanel top = new JPanel();
        top.add(addQBtn);
        top.add(viewQBtn);
        top.add(viewRBtn);

        output.setEditable(false);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(output), BorderLayout.CENTER);

        // Add Question (simple demo)
        addQBtn.addActionListener(e -> {
            Question q = new Question();
            q.setSubject("Java");
            q.setQuestion("What is JVM?");
            q.setOptionA("Java Virtual Machine");
            q.setOptionB("Java Variable Method");
            q.setOptionC("Joint Virtual Memory");
            q.setOptionD("None");
            q.setCorrectOption('A');
            if (qDao.save(q)) {
                output.setText("Question added successfully!");
            } else {
                output.setText("Failed to add question.");
            }
        });

        // View Questions
        viewQBtn.addActionListener(e -> {
            List<Question> list = qDao.findAll("Java");
            StringBuilder sb = new StringBuilder();
            for (Question q : list) {
                sb.append(q.getQId()).append(". ").append(q.getQuestion()).append("\n");
            }
            output.setText(sb.toString());
        });

        // View Results
        viewRBtn.addActionListener(e -> {
            List<Result> list = rDao.findAll();
            StringBuilder sb = new StringBuilder();
            for (Result r : list) {
                sb.append("User ").append(r.getUserId())
                  .append(" scored ").append(r.getScore())
                  .append("/").append(r.getTotal())
                  .append(" in ").append(r.getSubject())
                  .append("\n");
            }
            output.setText(sb.toString());
        });
    }
}