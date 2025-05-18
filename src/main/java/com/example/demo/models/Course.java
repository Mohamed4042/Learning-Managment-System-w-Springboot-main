package com.example.demo.models;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Integer duration;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "course_id")
    private ArrayList<Lesson> Lessons = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="student_course",
            joinColumns=@JoinColumn(name="student_id" , nullable = false),
            inverseJoinColumns=@JoinColumn(name="course_id" , nullable = false))
    private ArrayList<Student> students = new  ArrayList<>() ;

    @JsonIgnore
    public AtomicLong lessonCounter = new AtomicLong(0) ;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "course_id")
    private ArrayList<Assignment> Assignments = new ArrayList<>();

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "course_id")
    private ArrayList<Quiz> Quizzes = new ArrayList<>();

    @JsonIgnore
    public AtomicLong assignmentCounter = new AtomicLong(0) ;

    @JsonIgnore
    public AtomicLong quizCounter = new AtomicLong(0) ;

    public ArrayList<Lesson> getAllLessons() {
        return this.Lessons;
    }

    public void addLesson(Lesson lesson) {
        Lessons.add(lesson) ;
    }

    public void updateLesson(Lesson lesson , int index) {
        Lessons.set(index, lesson) ;
    }

    public void deleteLesson(int index) {
        Lessons.remove(index) ;
    }

    public Lesson getLesson(int index) {
        return Lessons.get(index) ;
    }

    public void enrollStudent(Student student) {
        students.add(student);
    }

    public void deleteStudent(Student student) {
        students.remove(student);
    }

    public ArrayList<Student> getAllStudents() {
        return students ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public ArrayList<Assignment> getAllAssignments() {
        return Assignments;
    }
    public void addAssignment(Assignment assignment) {
        Assignments.add(assignment);
    }

    public void updateAssignment(Assignment assignment, int index) {
        Assignments.set(index, assignment);
    }

    public void deleteAssignment(int index) {
        Assignments.remove(index);
    }

    public Assignment getAssignment(int index) {
        return Assignments.get(index);
    }
    public ArrayList<Quiz> getAllQuizzes() {
        return Quizzes;
    }

    public void addQuiz(Quiz quiz) {
        Quizzes.add(quiz);
    }

    public void updateQuiz(Quiz quiz, int index) {
        Quizzes.set(index, quiz);
    }

    public void deleteQuiz(int index) {
        Quizzes.remove(index);
    }

    public Quiz getQuiz(int index) {
        return Quizzes.get(index);
    }

    public ArrayList<Lesson> getLessons() {
        return Lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        Lessons = lessons;
    }


    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public AtomicLong getLessonCounter() {
        return lessonCounter;
    }



    public void setAssignments(ArrayList<Assignment> assignments) {
        Assignments = assignments;
    }


    public void setQuizzes(ArrayList<Quiz> quizzes) {
        Quizzes = quizzes;
    }

    public AtomicLong getAssignmentCounter() {
        return assignmentCounter;
    }


    public AtomicLong getQuizCounter() {
        return quizCounter;
    }


}