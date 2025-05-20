package com.example.demo.controllers;

import com.example.demo.models.Assignment;
import com.example.demo.models.Student;
import com.example.demo.services.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @PostMapping("/GetCourse/{id}/createAssignment")
    public Assignment createAssignment(@PathVariable Long id, @RequestBody Assignment assignment) {
        return  assignmentService.createAssignment(id, assignment);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @GetMapping("/GetCourse/{id}/getAssignmentsForCourse")
    public List<Assignment> getAssignmentsForCourse(@PathVariable Long id) {
        return assignmentService.getAssignmentsForCourse(id);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @GetMapping("/GetCourse/{id}/getAssignment/{assignmentId}")
    public Assignment getAssignment(@PathVariable Long id, @PathVariable Long assignmentId) {
        return assignmentService.getAssignment(id, assignmentId);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/GetCourse/{id}/getAssignment/{assignmentId}/submitAssignment")
    public String submitAssignment(@PathVariable Long assignmentId, @PathVariable Long id, @RequestBody Student student ) {
        if (assignmentService.submitAssignment(assignmentId,id,student)) return "Assignment Submitted";
        return "Assignment Not Submitted";
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @PostMapping("/GetCourse/{id}/getAssignment/{assignmentId}/gradeAssignment")
    public String gradeAssignment(@PathVariable Long assignmentId, @PathVariable Long id) {
        if(assignmentService.gradeAssignment(assignmentId, id)) return "Assignment Graded";
        return "Assignment Not Graded";
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/GetCourse/{id}/getAssignment/{assignmentId}/getAssignmentFeedback/{studentId}")
    public String getAssignmentFeedback(@PathVariable Long assignmentId, @PathVariable Long id, @PathVariable Long studentId) {
        return assignmentService.getAssignmentFeedback(assignmentId,id, studentId);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @GetMapping("/GetCourse/{id}/getAssignment/{assignmentId}/getAssignmentSubmitters")
    public List<Student> getAssignmentSubmitters(@PathVariable Long id, @PathVariable Long assignmentId) {
        return  assignmentService.getAssignmentSubmitters(id, assignmentId);
    }
}
