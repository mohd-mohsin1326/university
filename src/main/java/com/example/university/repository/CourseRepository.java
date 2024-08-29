package com.example.university.repository;

import java.util.ArrayList;
import java.util.List;

import com.example.university.model.Professor;
import com.example.university.model.Course;

public interface CourseRepository {
    ArrayList<Course> getCourses();

    Course getCourseById(int courseId);

    Course addCourse(Course course);

    Course updateCourse(int courseId, Course course);

    void deleteCourse(int courseId);

    List<Student> getStudentCourses(int courseId);
}
