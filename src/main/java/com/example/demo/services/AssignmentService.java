package com.example.demo.services;

import com.example.demo.models.Assignment;
import com.example.demo.models.Course;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    private CourseService courseService;

    public int searchForAssignmentInCourse(Course course, Assignment assignment) {
        for (int i = 0; i < course.getAllAssignments().size(); i++) {
            if (course.getAllAssignments().get(i).getTitle().equals(assignment.getTitle())) return i;
        }
        return -1;
    }

    public Assignment createAssignment(Long courseId, Assignment assignment) {
        Course course = courseService.getCourse(courseId);
        if (course != null) {
            int index = searchForAssignmentInCourse(course, assignment);
            if (index != -1) return null;
            assignment.setId(course.getAssignmentCounter().incrementAndGet());
            course.addAssignment(assignment);
            courseService.updateCourse(course);
            return assignment;
        }
        return null;
    }

    public Assignment getAssignment(Long courseId, Long assignmentId) {
        Course course = courseService.getCourse(courseId);
        if (course != null) {
            for (Assignment assignment : course.getAllAssignments()) {
                if (assignment.getId().equals(assignmentId)) return assignment;
            }
        }
        return null;
    }

    public List<Assignment> getAssignmentsForCourse(Long courseId) {
        Course course = courseService.getCourse(courseId);
        if (course != null) return course.getAllAssignments();
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

    public boolean submitAssignment(Long assignmentId, Long courseId, Student student) {
        Course course = courseService.getCourse(courseId);
        if (searchForStudentInCourse(course.getId(), student.getId()) == -1) return false;
        for (Assignment assignment : course.getAllAssignments()) {
            if (assignment.getId().equals(assignmentId)) {
                if (!assignment.getSubmittedStudents().contains(student)) {
                    assignment.addSubmittedStudent(student);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean gradeAssignment(Long assignmentId, Long courseId) {
        Course course = courseService.getCourse(courseId);
        for (Assignment assignment : course.getAllAssignments()) {
            if (assignment.getId().equals(assignmentId) && !assignment.isGraded()) {
                assignment.setGraded(true);
                return true;
            }
        }
        return false;
    }

    public String getAssignmentFeedback(Long assignmentId, Long courseId, Long studentId) {
        Course course = courseService.getCourse(courseId);
        for (Assignment assignment : course.getAllAssignments()) {
            if (assignment.getId().equals(assignmentId) && assignment.isGraded()) {
                for (Student student : assignment.getSubmittedStudents()) {
                    if (student.getId().equals(studentId)) {
                        return "Feedback for " + student.getName() + ": Your assignment has been graded";
                    }
                }
            }
        }
        return "No feedback available.";
    }

    public List<Student> getAssignmentSubmitters(Long courseId, Long assignmentId) {
        Course course = courseService.getCourse(courseId);
        if (course != null) {
            for (Assignment assignment : course.getAllAssignments()) {
                if (assignment.getId().equals(assignmentId)) {
                    return assignment.getSubmittedStudents();
                }
            }
        }
        return new ArrayList<>();
    }
}
