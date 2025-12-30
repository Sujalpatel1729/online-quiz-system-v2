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

import java.time.LocalDateTime;

public class QuizFrame extends JFrame {
    private List<Question> questions;
    private int currentIndex = 0;
    private int score = 0;
    private int userId;
    private String subject;

    private JLabel qLabel = new JLabel();
    private JRadioButton optA = new JRadioButton();
    private JRadioButton optB = new JRadioButton();
    private JRadioButton optC = new JRadioButton();
    private JRadioButton optD = new JRadioButton();
    private ButtonGroup group = new ButtonGroup();
    private JButton nextBtn = new JButton("Next");

    private QuestionDao qDao = new QuestionDaoImpl();
    private ResultDao rDao = new ResultDaoImpl();

    public QuizFrame(int userId, String subject) {
        this.userId = userId;
        this.subject = subject;
        this.questions = qDao.findRandomBySubject(subject, 5);
if (questions.isEmpty()) {
    JOptionPane.showMessageDialog(this, "No questions found for subject: " + subject);
    dispose();
    return;
}

        setTitle("Quiz - " + subject);
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel opts = new JPanel(new GridLayout(4,1));

        group.add(optA); group.add(optB); group.add(optC); group.add(optD);
        opts.add(optA); opts.add(optB); opts.add(optC); opts.add(optD);

        panel.add(qLabel, BorderLayout.NORTH);
        panel.add(opts, BorderLayout.CENTER);
        panel.add(nextBtn, BorderLayout.SOUTH);

        add(panel);

        loadQuestion();

        nextBtn.addActionListener(e -> {
            checkAnswer();
            currentIndex++;
            if (currentIndex < questions.size()) {
                loadQuestion();
            } else {
                saveResult();
                JOptionPane.showMessageDialog(this, "Quiz finished! Score: " + score + "/" + questions.size());
                dispose();
            }
        });
    }

    private void loadQuestion() {
        Question q = questions.get(currentIndex);
        qLabel.setText("Q" + (currentIndex+1) + ": " + q.getQuestion());
        optA.setText("A. " + q.getOptionA());
        optB.setText("B. " + q.getOptionB());
        optC.setText("C. " + q.getOptionC());
        optD.setText("D. " + q.getOptionD());
        group.clearSelection();
    }

    private void checkAnswer() {
        Question q = questions.get(currentIndex);
        char correct = q.getCorrectOption();
        if ((optA.isSelected() && correct=='A') ||
            (optB.isSelected() && correct=='B') ||
            (optC.isSelected() && correct=='C') ||
            (optD.isSelected() && correct=='D')) {
            score++;
        }
    }

    private void saveResult() {
        Result r = new Result();
        r.setUserId(userId);
        r.setScore(score);
        r.setTotal(questions.size());
        r.setSubject(subject);
        r.setDate(LocalDateTime.now());
        rDao.save(r);
    }
}