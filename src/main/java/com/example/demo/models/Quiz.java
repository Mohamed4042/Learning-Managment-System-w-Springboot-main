package com.example.demo.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;


@Data
public class Quiz {

    private Long id;
    private String title;
    private String description;
    private boolean isGraded;
    private String feedback;
    private boolean isSubmitted;
    private List<Student> submittedStudents=new ArrayList<>(); // New field
    // Constructor
    public Quiz(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isGraded = false;
        this.feedback = "";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isGraded() {
        return isGraded;
    }

    public void setGraded(boolean graded) {
        isGraded = graded;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    public boolean isSubmitted() {
        return isSubmitted;
    }
    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }

    public List<Student> getSubmittedStudents() {
        return submittedStudents;
    }
    
    public Map<Long, Integer> getStudentScores() {
        return studentScores;
    }

    public void addStudentScore(Long studentId, int score) {
        studentScores.put(studentId, score);
    }
private Map<Long, Integer> studentScores = new HashMap<>();

    public void addSubmittedStudent(Student student) {
        this.submittedStudents.add(student);
    }

}
