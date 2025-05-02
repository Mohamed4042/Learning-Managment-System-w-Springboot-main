package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

public class Assignment {

    private Long id;
    private String title;
    private String description;
    private boolean submitted;
    private boolean graded;
    private String feedback;
    private List<Student> submittedStudents = new ArrayList<>();
    private List<String> feedbackList = new ArrayList<>();

    public Assignment(String title, String description) {

        this.title = title;
        this.description = description;
        this.submitted = false;
        this.graded = false;
    }

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

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public boolean isGraded() {
        return graded;
    }

    public void setGraded(boolean graded) {
        this.graded = graded;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    public List<Student> getSubmittedStudents() {
        return submittedStudents;
    }

    public List<String> getFeedbackList() {
        return feedbackList;
    }

    public void addSubmittedStudent(Student student) {
        submittedStudents.add(student);
    }

    public void addFeedback(String feedback) {
        feedbackList.add(feedback);
    }
}

