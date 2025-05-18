package com.example.demo.models;
import jakarta.persistence.*;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="student_lesson",
            joinColumns=@JoinColumn(name="student_id" , nullable = false),
            inverseJoinColumns=@JoinColumn(name="lesson_id" , nullable = false))
    private ArrayList<Student> students = new ArrayList<>() ;

    @JsonIgnore
    private Long CurrentOTP ;


    public Lesson() {
    }

    public Lesson(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Lesson id(Long id) {
        setId(id);
        return this;
    }

    public Lesson title(String title) {
        setTitle(title);
        return this;
    }

    public Lesson description(String description) {
        setDescription(description);
        return this;
    }

    public Long generateOTP() {
        CurrentOTP = (long) (Math.random() * 999999);
        students.clear();
        return CurrentOTP;
    }

    public Long GetCurrentOTP() {
        return CurrentOTP ;
    }

    public ArrayList<Student> getAttendanceList() {
        return students ;
    }

    public Long getCurrentOTP() {
        return CurrentOTP;
    }

    public void setCurrentOTP(Long currentOTP) {
        CurrentOTP = currentOTP;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }
}