package com.example.demo.Data;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.models.Course;
import org.springframework.stereotype.Repository;

@Repository
public class CourseData {

    private List<Course> AllCourses = new ArrayList<>();

    public Course GetCourseByIndex(int index) {
        return AllCourses.get(index);
    }

    public List<Course> GetAllCourses() {
        return AllCourses;
    }

    public void DeleteCourse(int index) {
        AllCourses.remove(index);
    }

    public void AddCourse(Course course) {
        AllCourses.add(course);
    }

    public boolean UpdateCourse(Course course, Long index) {
        int intIndex = Math.toIntExact(index);
        if (intIndex < 0 || intIndex >= AllCourses.size()) {
            return false;}
        AllCourses.set(intIndex, course);
        return true;
    }
}