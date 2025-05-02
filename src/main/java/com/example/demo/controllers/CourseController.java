package com.example.demo.controllers;
import java.util.List;
import com.example.demo.models.Course;
import com.example.demo.models.Student;
import com.example.demo.models.Lesson;
import com.example.demo.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




@RestController
public class CourseController {
    
    @Autowired
    CourseService courseService ;
    
    //! -------------------------- Course APIs -------------------------

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @PostMapping("/AddNewCourse")
    public Course AddNewCourse(@RequestBody Course course) {
        if (courseService.AddCourse(course)) return course ;
        return null ;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @PutMapping("/UpdateCourse")
    public Course UpdateCourse(@RequestBody Course course) {
        if (courseService.UpdateCourse(course)) return course ;
        return null ;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @GetMapping("/GetCourse/{id}")
    public Course GetCourse(@PathVariable Long id) {
        return courseService.GetCourse(id) ;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR') || hasRole('STUDENT')")
    @GetMapping("/GetAllCourses")
    public List<Course> GetAllCourses() {
        return courseService.GetAllCourses() ;
    }
    
    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @DeleteMapping("/DeleteCourse/{id}")
    public void DeleteCourse(@PathVariable Long id) {
        courseService.DeleteCourse(id) ;
    }

    //! -------------------------- Lesson APIs -------------------------

    @GetMapping("/GetCourse/{id}/GetAllLessons")
    public List<Lesson> GetAllLessons(@PathVariable Long id) {
        return courseService.GetCourse(id).GetAllLessons() ;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @PostMapping("/GetCourse/{id}/AddNewLesson")
    public Lesson AddNewLesson(@PathVariable Long id , @RequestBody Lesson lesson) {
        if (courseService.AddLesson(id , lesson)) return lesson ;
        return null ;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @PutMapping("/GetCourse/{id}/UpdateLesson")
    public Lesson UpdateLesson(@PathVariable Long id, @RequestBody Lesson lesson) {
        if (courseService.UpdateLesson(id , lesson)) return lesson ;
        return null ;
    }

    @GetMapping("/GetCourse/{CourseID}/GetLesson/{LessonID}")
    public Lesson GetLesson(@PathVariable Long CourseID , @PathVariable Long LessonID) {
        return courseService.GetLesson(CourseID, LessonID) ;
    }
    
    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @DeleteMapping("/GetCourse/{CourseID}/DeleteLesson/{LessonID}")
    public void DeleteLesson(@PathVariable Long CourseID , @PathVariable Long LessonID) {
        courseService.DeleteLesson(CourseID, LessonID) ;
    }

    //! -------------------------- Student APIs -------------------------

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @GetMapping("/GetCourse/{CourseID}/GetAllStudents")
    public List<Student> GetAllStudents(@PathVariable Long CourseID) {
        return courseService.GetAllStudentsOfCourse(CourseID) ;
    }
    
    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR') || hasRole('STUDENT')")
    @PostMapping("/GetCourse/{CourseID}/EnrollStudentInCourse")
    public Student EnrollStudentInCourse(@PathVariable Long CourseID , @RequestBody Student student) {
        if (courseService.EnrollStudentInCourse(CourseID, student)) return student ;
        return null ;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @DeleteMapping("/GetCourse/{CourseID}/DeleteStudentFromCourse/{StudentID}")
    public void DeleteStudentFromCourse(@PathVariable Long CourseID , @PathVariable Long StudentID) {
        courseService.DeleteStudentFromCourse(CourseID , StudentID) ;
    }

    //! -------------------------- Attendance APIs -------------------------

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @GetMapping("/GetCourse/{CourseID}/GetLesson/{LessonID}/GenerateOTPForLesson")
    public String GenerateOTPForLesson(@PathVariable Long CourseID , @PathVariable Long LessonID) {
        return courseService.GenerateOTPForLesson(CourseID, LessonID).toString() ;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR') || hasRole('STUDENT')")
    @PostMapping("/GetCourse/{CourseID}/GetLesson/{LessonID}/MarkStudentInAttendance")
    public String MarkStudentInAttendance(@PathVariable Long CourseID , @PathVariable Long LessonID , @RequestBody Student student , @RequestParam Long OTP) {
        if (courseService.MarkStudentInAttendance(CourseID, LessonID , student , OTP)) {
            return "Attendance marked successfully for student: " + student.getName();
        }
        return "Invalid OTP or Lesson not found. Attendance not marked." ;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @GetMapping("/GetCourse/{CourseID}/GetLesson/{LessonID}/ViewAttendanceList")
    public List<Student> ViewAttendanceList(@PathVariable Long CourseID , @PathVariable Long LessonID) {
        return courseService.ViewAttendanceList(CourseID, LessonID);
    }
    
}