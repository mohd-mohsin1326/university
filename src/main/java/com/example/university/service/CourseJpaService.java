package com.example.university.service;

import com.example.university.model.Course;
import com.example.university.model.Student;
import com.example.university.repository.CourseJpaRepository;
import com.example.university.repository.StudentJpaRepository;
import com.example.university.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseJpaService implements CourseRepository {

    @Autowired
    private StudentJpaRepository studentJpaRepository;

    @Autowired
    private CourseJpaRepository courseJpaRepository;

    @Override
    public ArrayList<Course> getCourses() {
        List<Course> courseList = courseJpaRepository.findAll();
        return new ArrayList<>(courseList);
    }

    @Override
    public Course getCourseById(int courseId) {
        return courseJpaRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Course addCourse(Course course) {
        List<Integer> studentIds = new ArrayList<>();
        for (Student student : course.getStudents()) {
            studentIds.add(student.getStudentId());
        }

        List<Student> students = studentJpaRepository.findAllById(studentIds);
        course.setStudents(students);

        for (Student student : students) {
            student.getCourses().add(course);
        }

        return courseJpaRepository.save(course);
    }

    @Override
    public Course updateCourse(int courseId, Course course) {
        Course existingCourse = courseJpaRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (course.getCourseName() != null) {
            existingCourse.setCourseName(course.getCourseName());
        }
        if (course.getCredits() != null) {
            existingCourse.setCredits(course.getCredits());
        }
        if (course.getStudents() != null) {
            List<Student> students = studentJpaRepository.findAllById(
                    course.getStudents().stream().map(Student::getStudentId).toList());
            existingCourse.setStudents(students);
        }

        return courseJpaRepository.save(existingCourse);
    }

    @Override
    public void deleteCourse(int courseId) {
        Course course = courseJpaRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        for (Student student : course.getStudents()) {
            student.getCourses().remove(course);
        }

        courseJpaRepository.deleteById(courseId);
    }

    @Override
    public List<Student> getStudentCourses(int courseId) {
        Course course = courseJpaRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return course.getStudents();
    }
}
