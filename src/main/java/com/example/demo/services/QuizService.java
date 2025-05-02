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

    public int searchForQuizInCourse(Course course, Quiz Quiz) {
        for (int i = 0; i < course.getAllQuizzes().size(); i++) {
            if (course.getAllQuizzes().get(i).getTitle().equals(Quiz.getTitle())) return i;
        }
        return -1; 
    }

    public Quiz createQuiz(Long courseId, Quiz Quiz) {
        Course course = courseService.GetCourse(courseId);
        if (course != null) {
            int index = searchForQuizInCourse(course, Quiz);
            if (index != -1) return null;
            Quiz.setId(course.QuizCounter.incrementAndGet());
            course.addQuiz(Quiz);
            courseService.UpdateCourse(course);
            return Quiz;
        }
        return null;
    }

    public Quiz getQuiz(Long courseId, Long QuizId) {
        Course course = courseService.GetCourse(courseId);
        if (course != null) {
            for (Quiz Quiz : course.getAllQuizzes()) {
                if (Quiz.getId().equals(QuizId)) return Quiz;
            }
        }
        return null;
    }

    public ArrayList<Quiz> getQuizsForCourse(Long courseId) {
        Course course = courseService.GetCourse(courseId);
        if (course != null) return course.getAllQuizzes();
        return new ArrayList<>();
    }

    public boolean submitQuiz(Long QuizId, Long Courseid, Student student) {
        Course course = courseService.GetCourse(Courseid);
        if (SearchForStudentInCourse(course.getId(), student.getId()) == -1) return false;
        for (Quiz quiz : course.getAllQuizzes()) {
            if (quiz.getId().equals(QuizId)) {
                if (!quiz.isSubmitted() || !quiz.getSubmittedStudents().contains(student)) {
                    quiz.setSubmitted(true);
                    quiz.addSubmittedStudent(student);
                    return true;
                }
            }
        }
        return false;
    }
    
    public List<Student> getQuizSubmitters( Long CourseID,  Long QuizID) {
        Course course = courseService.GetCourse(CourseID);
        if (course != null) {
            for (Quiz quiz : course.getAllQuizzes()) {
                if (quiz.getId().equals(QuizID)) {
                    return quiz.getSubmittedStudents();
                }
            }
        }
        return new ArrayList<>();
    }

    public int SearchForStudentInCourse(Long CourseID , Long StudentID) {
        Course course = courseService.GetCourse(CourseID);
        if (course != null) {
            for (int i = 0 ; i < course.GetAllStudents().size() ; i ++) {
                if (course.GetAllStudents().get(i).getId().equals(StudentID)) return i ;
            }
        }
        return -1;
    }

    public boolean gradeQuiz(Long QuizId, Long CourseId) {
        Course course = courseService.GetCourse(CourseId);
        if (course != null) {
            for (Quiz quiz : course.getAllQuizzes()) {
                if (quiz.getId().equals(QuizId) && !quiz.isGraded()) {
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

    public String getQuizFeedback(Long QuizId, Long Courseid) {
        Course course = courseService.GetCourse(Courseid);
        for (Quiz Quiz : course.getAllQuizzes()) {
            if (Quiz.getId().equals(QuizId) && Quiz.isGraded()) {
                return Quiz.getFeedback();
            }
        }
        return "No feedback available.";
    }

    private void provideAutomaticFeedback(Student student, Quiz quiz) {
        String feedback = "Good job, " + student.getName() + "! Your quiz has been graded.";
        quiz.setFeedback(feedback);
        System.out.println("Feedback for " + student.getName() + ": " + feedback);
    }

    public List<String> getQuizScores(Long CourseID, Long QuizID) {
        Course course = courseService.GetCourse(CourseID);
        List<String> result = new ArrayList<>();
        if (course != null) {
            for (Quiz quiz : course.getAllQuizzes()) {
                if (quiz.getId().equals(QuizID)) {
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