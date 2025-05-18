package com.example.demo.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="quizes")
@NoArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private boolean isGraded;
    private String feedback;
    private boolean isSubmitted;

    @ElementCollection
    @CollectionTable(name = "quiz_student_scores", joinColumns = @JoinColumn(name = "quiz_id"))
    @MapKeyColumn(name = "student_id")
    @Column(name = "score")
    private Map<Long, Integer> studentScores = new HashMap<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="user_quiz",
            joinColumns=@JoinColumn(name="student_id" , nullable = false),
            inverseJoinColumns=@JoinColumn(name="quiz_id" , nullable = false))
    private List<Student> students=new ArrayList<>();

    // Constructor
    public Quiz(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isGraded = false;
        this.feedback = "";
    }

    // Getters and Setters

    public void setStudentScores(Map<Long, Integer> studentScores) {
        this.studentScores = studentScores;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
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
        return students;
    }

    public Map<Long, Integer> getStudentScores() {
        return studentScores;
    }

    public void addStudentScore(Long studentId, int score) {
        studentScores.put(studentId, score);
    }

    public void addSubmittedStudent(Student student) {
        this.students.add(student);
    }

}