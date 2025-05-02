package com.example.demo.controllers;

import com.example.demo.models.Quiz;
import com.example.demo.models.Student;
import com.example.demo.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController

public class QuizController {

    @Autowired
    private QuizService quizService;

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @PostMapping("/GetCourse/{id}/CreateQuiz")
    public Quiz CreateQuiz(@PathVariable Long id, @RequestBody Quiz quiz) {
        return  quizService.createQuiz(id, quiz);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @GetMapping("/GetCourse/{id}/getQuizsForCourse")
    public ArrayList<Quiz> getQuizsForCourse(@PathVariable Long id) {
        return quizService.getQuizsForCourse(id);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @GetMapping("/GetCourse/{id}/getQuiz/{QuizId}")
    public Quiz getQuiz(@PathVariable Long id, @PathVariable Long QuizId) {
        return quizService.getQuiz(id,QuizId);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/GetCourse/{id}/getQuiz/{QuizId}/submitQuiz")
    public String submitQuiz(@PathVariable Long QuizId, @PathVariable Long id,@RequestBody Student student ) {
        if (quizService.submitQuiz(QuizId,id,student)) return "Quiz Submitted";
        return "Quiz Not Submitted";
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @GetMapping("/GetCourse/{CourseID}/GetQuiz/{QuizID}/getQuizSubmitters")
    public List<Student> getQuizSubmitters(@PathVariable Long CourseID, @PathVariable Long QuizID) {
        return  quizService.getQuizSubmitters(CourseID,QuizID);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @PostMapping("/GetCourse/{id}/GetQuiz/{QuizId}/gradeQuiz")
    public String gradeQuiz(@PathVariable Long QuizId, @PathVariable Long id) {
        if(quizService.gradeQuiz(QuizId, id)) return "Quiz Graded";
        return "Quiz Not Graded";
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/GetCourse/{id}/getQuiz/{QuizId}/getQuizFeedback")
    public String getQuizFeedback(@PathVariable Long QuizId, @PathVariable Long id) {
        return quizService.getQuizFeedback(QuizId, id);
    }
    
    @GetMapping("/GetCourse/{id}/getQuiz/{QuizId}/getQuizScores")
    public List<String> getQuizScores(@PathVariable Long id, @PathVariable Long QuizId) {
        return quizService.getQuizScores(id,QuizId);
    }
}