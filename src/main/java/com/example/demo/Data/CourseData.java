package com.example.demo.Data;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.models.Course;
import org.springframework.stereotype.Repository;

@Repository
public class CourseData {

    private List<Course> allCourses = new ArrayList<>();

    public Course getCourseByIndex(int index) {
        return allCourses.get(index);
    }

    public List<Course> getAllCourses() {
        return allCourses;
    }

    public void deleteCourse(int index) {
        allCourses.remove(index);
    }

    public void addCourse(Course course) {
        allCourses.add(course);
    }

    public boolean updateCourse(Course course, Long index) {
        int intIndex = Math.toIntExact(index);
        if (intIndex < 0 || intIndex >= allCourses.size()) {
            return false;
        }
        allCourses.set(intIndex, course);
        return true;
    }
}