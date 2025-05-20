package com.example.demo.services;

import com.example.demo.models.Quiz;
import com.example.demo.models.Course;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    private CourseService courseService;

    public int searchForQuizInCourse(Course course, Quiz quiz) {
        for (int i = 0; i < course.getAllQuizzes().size(); i++) {
            if (course.getAllQuizzes().get(i).getTitle().equals(quiz.getTitle())) return i;
        }
        return -1;
    }

    public Quiz createQuiz(Long courseId, Quiz quiz) {
        Course course = courseService.getCourse(courseId);
        if (course != null) {
            int index = searchForQuizInCourse(course, quiz);
            if (index != -1) return null;
            quiz.setId(course.getQuizCounter().incrementAndGet());
            course.addQuiz(quiz);
            courseService.updateCourse(course);
            return quiz;
        }
        return null;
    }

    public Quiz getQuiz(Long courseId, Long quizId) {
        Course course = courseService.getCourse(courseId);
        if (course != null) {
            for (Quiz quiz : course.getAllQuizzes()) {
                if (quiz.getId().equals(quizId)) return quiz;
            }
        }
        return null;
    }

    public List<Quiz> getQuizzesForCourse(Long courseId) {
        Course course = courseService.getCourse(courseId);
        if (course != null) return course.getAllQuizzes();
        return new ArrayList<>();
    }

    public boolean submitQuiz(Long quizId, Long courseId, Student student) {
        Course course = courseService.getCourse(courseId);
        if (searchForStudentInCourse(course.getId(), student.getId()) == -1) return false;
        for (Quiz quiz : course.getAllQuizzes()) {
            if (quiz.getId().equals(quizId)) {
                if (!quiz.isSubmitted() || !quiz.getSubmittedStudents().contains(student)) {
                    quiz.setSubmitted(true);
                    quiz.addSubmittedStudent(student);
                    return true;
                }
            }
        }
        return false;
    }

    public List<Student> getQuizSubmitters(Long courseId, Long quizId) {
        Course course = courseService.getCourse(courseId);
        if (course != null) {
            for (Quiz quiz : course.getAllQuizzes()) {
                if (quiz.getId().equals(quizId)) {
                    return quiz.getSubmittedStudents();
                }
            }
        }
        return new ArrayList<>();
    }

    public int searchForStudentInCourse(Long courseId, Long studentId) {
        Course course = courseService.getCourse(courseId);
        if (course != null) {
            for (int i = 0; i < course.getAllStudents().size(); i++) {
                if (course.getAllStudents().get(i).getId().equals(studentId)) return i;
            }
        }
        return -1;
    }

    public boolean gradeQuiz(Long quizId, Long courseId) {
        Course course = courseService.getCourse(courseId);
        if (course != null) {
            for (Quiz quiz : course.getAllQuizzes()) {
                if (quiz.getId().equals(quizId) && !quiz.isGraded()) {
                    for (Student student : quiz.getSubmittedStudents()) {
                        int score = calculateScoreForStudent(student, quiz);
                        quiz.addStudentScore(student.getId(), score);
                        provideAutomaticFeedback(student, quiz);
                    }
                    quiz.setGraded(true);
                    return true;
                }
            }
        }
        return false;
    }

    private int calculateScoreForStudent(Student student, Quiz quiz) {
        return (int) (Math.random() * 10);
    }

    public String getQuizFeedback(Long quizId, Long courseId) {
        Course course = courseService.getCourse(courseId);
        for (Quiz quiz : course.getAllQuizzes()) {
            if (quiz.getId().equals(quizId) && quiz.isGraded()) {
                return quiz.getFeedback();
            }
        }
        return "No feedback available.";
    }

    private void provideAutomaticFeedback(Student student, Quiz quiz) {
        String feedback = "Good job, " + student.getName() + "! Your quiz has been graded.";
        quiz.setFeedback(feedback);
        System.out.println("Feedback for " + student.getName() + ": " + feedback);
    }

    public List<String> getQuizScores(Long courseId, Long quizId) {
        Course course = courseService.getCourse(courseId);
        List<String> result = new ArrayList<>();
        if (course != null) {
            for (Quiz quiz : course.getAllQuizzes()) {
                if (quiz.getId().equals(quizId)) {
                    for (Student student : quiz.getSubmittedStudents()) {
                        if (quiz.getStudentScores().containsKey(student.getId())) {
                            int score = quiz.getStudentScores().get(student.getId());
                            result.add("Student: " + student.getName() + ", Score: " + score);
                        } else {
                            result.add("Student: " + student.getName() + ", Score: Not graded");
                        }
                    }
                    return result;
                }
            }
        }
        return result;
    }
}
