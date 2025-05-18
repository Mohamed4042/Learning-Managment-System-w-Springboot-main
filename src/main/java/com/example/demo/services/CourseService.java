package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.example.demo.Data.CourseData;
import com.example.demo.models.Constance;
import com.example.demo.models.Course;
import com.example.demo.models.Lesson;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class CourseService {

    @JsonIgnore
    private AtomicLong idCounter = new AtomicLong(0);

    @Autowired
    private CourseData courseData;

    // ---------------------------- Course Services --------------------------------

    public ArrayList<Course> getAllCourses() {
        return (ArrayList<Course>) courseData.getAllCourses();
    }

    public boolean addCourse(Course course) {
        int index = searchForCourseByName(course);
        if (index != Constance.No_Match) return false;
        course.setId(idCounter.incrementAndGet());
        courseData.addCourse(course);
        return true;
    }

    public void deleteCourse(Long id) {
        Course course = getCourse(id);
        if (course != null) courseData.deleteCourse(searchForCourseByName(course));
    }

    public boolean updateCourse(Course course) {
        int index = (int) searchForCourseById(course);
        if (index == Constance.No_Match) return false;
        courseData.updateCourse(course, (long) index);
        return true;
    }

    public long searchForCourseById(Course course) {
        for (int i = 0; i < getAllCourses().size(); i++) {
            if (courseData.getCourseByIndex(i).getId().equals(course.getId())) return i;
        }
        return Constance.No_Match;
    }

    public int searchForCourseByName(Course course) {
        for (int i = 0; i < getAllCourses().size(); i++) {
            if (courseData.getCourseByIndex(i).getName().equals(course.getName())) return i;
        }
        return Constance.No_Match;
    }

    public Course getCourse(Long id) {
        for (int i = 0; i < getAllCourses().size(); i++) {
            if (courseData.getCourseByIndex(i).getId().equals(id)) return courseData.getCourseByIndex(i);
        }
        return null;
    }

    // ---------------------------- Lesson Services --------------------------------

    public int searchForLessonInCourse(Course course, Lesson lesson) {
        if (course.getAllLessons() != null) {
            for (int i = 0; i < course.getAllLessons().size(); i++) {
                Lesson currentLesson = course.getAllLessons().get(i);
                if (lesson.getId() != null && lesson.getId().equals(currentLesson.getId())) {
                    return i;
                }
            }
        }
        return Constance.No_Match;
    }

    public boolean addLesson(Long courseId, Lesson lesson) {
        Course course = getCourse(courseId);
        if (course != null) {
            int index = searchForLessonInCourse(course, lesson);
            if (index == Constance.No_Match) {
                lesson.setId(course.getLessonCounter().incrementAndGet());
                course.addLesson(lesson);
                courseData.updateCourse(course, searchForCourseById(course));
                return true;
            }
        }
        return false;
    }

    public boolean updateLesson(Long courseId, Lesson lesson) {
        Course course = getCourse(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course with ID " + courseId + " not found.");
        }

        int lessonIndex = searchForLessonInCourse(course, lesson);
        if (lessonIndex == Constance.No_Match) {
            throw new IllegalArgumentException("Lesson with ID " + lesson.getId() + " not found in course with ID " + courseId);
        }

        course.updateLesson(lesson, lessonIndex);

        long courseIndex = searchForCourseById(course);
        if (courseIndex == Constance.No_Match) {
            throw new IllegalArgumentException("Course with ID " + courseId + " could not be located for updating lessons.");
        }

        boolean updateSuccessful = courseData.updateCourse(course, courseIndex);
        if (!updateSuccessful) {
            System.err.println("courseData.updateCourse failed for Course ID: " + courseId + " at index: " + courseIndex);
            throw new IllegalStateException("Failed to update course data. Ensure CourseData is synced and valid.");
        }

        return true;
    }

    public Lesson getLesson(Long courseId, Long lessonId) {
        Course course = getCourse(courseId);
        if (course != null) {
            for (Lesson lesson : course.getAllLessons()) {
                if (lesson.getId().equals(lessonId)) return lesson;
            }
        }
        return null;
    }

    public boolean deleteLesson(Long courseId, Long lessonId) {
        Course course = getCourse(courseId);
        if (course != null) {
            return course.getAllLessons().removeIf(lesson -> lesson.getId().equals(lessonId));
        }
        return false;
    }

    // ---------------------------- Student Services --------------------------------

    public int searchForStudentInCourse(Long courseId, Long studentId) {
        Course course = getCourse(courseId);
        if (course != null) {
            for (int i = 0; i < course.getAllStudents().size(); i++) {
                if (course.getAllStudents().get(i).getId().equals(studentId)) return i;
            }
        }
        return Constance.No_Match;
    }

    public boolean enrollStudentInCourse(Long courseId, Student student) {
        Course course = getCourse(courseId);
        if (course != null) {
            int index = searchForStudentInCourse(courseId, student.getId());
            if (index == Constance.No_Match) {
                course.enrollStudent(student);
                courseData.updateCourse(course, searchForCourseById(course));
                return true;
            }
        }
        return false;
    }

    public void deleteStudentFromCourse(Long courseId, Long studentId) {
        Course course = getCourse(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course with ID: " + courseId + " not found.");
        }
        boolean removed = course.getAllStudents().removeIf(student -> student.getId().equals(studentId));

        if (!removed) {
            throw new IllegalArgumentException(studentId + " is not enrolled in the course with ID " + courseId);
        }
    }

    public List<Student> getAllStudentsOfCourse(Long courseId) {
        return getCourse(courseId).getAllStudents();
    }

    // ---------------------------- Attendance Services --------------------------------

    public Long generateOtpForLesson(Long courseId, Long lessonId) {
        Lesson lesson = getLesson(courseId, lessonId);
        if (lesson != null) {
            return lesson.generateOTP();
        }
        return (long) Constance.No_Match;
    }

    public boolean markStudentInAttendance(Long courseId, Long lessonId, Student student, Long otp) {
        Lesson lesson = getLesson(courseId, lessonId);
        if (lesson != null) {
            Course course = getCourse(courseId);
            boolean found = course.getAllStudents().stream()
                    .anyMatch(s -> s.getId().equals(student.getId()));

            if (!found) return false;

            if (otp.equals(lesson.getCurrentOTP())) {
                lesson.getAttendanceList().add(student);
                return true;
            }
        }
        return false;
    }

    public List<Student> viewAttendanceList(Long courseId, Long lessonId) {
        return getLesson(courseId, lessonId).getAttendanceList();
    }
}
