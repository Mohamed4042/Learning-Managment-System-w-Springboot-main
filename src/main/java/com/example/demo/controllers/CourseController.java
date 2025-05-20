package com.example.demo.controllers;

import java.util.List;
import com.example.demo.models.Course;
import com.example.demo.models.Student;
import com.example.demo.models.Lesson;
import com.example.demo.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    // -------------------------- Course APIs -------------------------

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @PostMapping("/addNewCourse")
    public Course addNewCourse(@RequestBody Course course) {
        if (courseService.addCourse(course)) return course;
        return null;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @PutMapping("/updateCourse")
    public Course updateCourse(@RequestBody Course course) {
        if (courseService.updateCourse(course)) return course;
        return null;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @GetMapping("/getCourse/{id}")
    public Course getCourse(@PathVariable Long id) {
        return courseService.getCourse(id);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR') || hasRole('STUDENT')")
    @GetMapping("/getAllCourses")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @DeleteMapping("/deleteCourse/{id}")
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }

    // -------------------------- Lesson APIs -------------------------

    @GetMapping("/getCourse/{id}/getAllLessons")
    public List<Lesson> getAllLessons(@PathVariable Long id) {
        Course course = courseService.getCourse(id);
        return course.getAllLessons();
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @PostMapping("/getCourse/{id}/addNewLesson")
    public Lesson addNewLesson(@PathVariable Long id, @RequestBody Lesson lesson) {
        if (courseService.addLesson(id, lesson)) return lesson;
        return null;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @PutMapping("/getCourse/{id}/updateLesson")
    public Lesson updateLesson(@PathVariable Long id, @RequestBody Lesson lesson) {
        if (courseService.updateLesson(id, lesson)) return lesson;
        return null;
    }

    @GetMapping("/getCourse/{courseId}/getLesson/{lessonId}")
    public Lesson getLesson(@PathVariable Long courseId, @PathVariable Long lessonId) {
        return courseService.getLesson(courseId, lessonId);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @DeleteMapping("/getCourse/{courseId}/deleteLesson/{lessonId}")
    public void deleteLesson(@PathVariable Long courseId, @PathVariable Long lessonId) {
        courseService.deleteLesson(courseId, lessonId);
    }

    // -------------------------- Student APIs -------------------------

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @GetMapping("/getCourse/{courseId}/getAllStudents")
    public List<Student> getAllStudents(@PathVariable Long courseId) {
        return courseService.getAllStudentsOfCourse(courseId);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR') || hasRole('STUDENT')")
    @PostMapping("/getCourse/{courseId}/enrollStudentInCourse")
    public Student enrollStudentInCourse(@PathVariable Long courseId, @RequestBody Student student) {
        if (courseService.enrollStudentInCourse(courseId, student)) return student;
        return null;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @DeleteMapping("/getCourse/{courseId}/deleteStudentFromCourse/{studentId}")
    public void deleteStudentFromCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        courseService.deleteStudentFromCourse(courseId, studentId);
    }

    // -------------------------- Attendance APIs -------------------------

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @GetMapping("/getCourse/{courseId}/getLesson/{lessonId}/generateOtpForLesson")
    public String generateOtpForLesson(@PathVariable Long courseId, @PathVariable Long lessonId) {
        return courseService.generateOtpForLesson(courseId, lessonId).toString();
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR') || hasRole('STUDENT')")
    @PostMapping("/getCourse/{courseId}/getLesson/{lessonId}/markStudentInAttendance")
    public String markStudentInAttendance(
            @PathVariable Long courseId,
            @PathVariable Long lessonId,
            @RequestBody Student student,
            @RequestParam Long otp) {

        if (courseService.markStudentInAttendance(courseId, lessonId, student, otp)) {
            return "Attendance marked successfully for student: " + student.getName();
        }
        return "Invalid OTP or Lesson not found. Attendance not marked.";
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @GetMapping("/getCourse/{courseId}/getLesson/{lessonId}/viewAttendanceList")
    public List<Student> viewAttendanceList(@PathVariable Long courseId, @PathVariable Long lessonId) {
        return courseService.viewAttendanceList(courseId, lessonId);
    }
}
