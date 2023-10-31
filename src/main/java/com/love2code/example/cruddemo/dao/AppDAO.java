package com.love2code.example.cruddemo.dao;

import com.love2code.example.cruddemo.entity.Course;
import com.love2code.example.cruddemo.entity.Instructor;
import com.love2code.example.cruddemo.entity.InstructorDetail;
import com.love2code.example.cruddemo.entity.Student;

import java.util.List;

public interface AppDAO {

    void save(Instructor theInstructor);

    Instructor findInstructorByID(int theId);

    void deleteInstructorById(int theId);

    InstructorDetail findInstructorDetailById(int theId);

    void deleteInstructorDetailById(int theId);

    List<Course> findCoursesByInstructorId(int theId);

    Instructor findInstructorByIdJoinFetch(int theId);

    void updateInstructor(Instructor instructor);

    void updateCourse(Course course);

    Course findCourseById(int theId);

    void deleteCourse(int theId);

    void save (Course theCourse);

    Course findCourseAndReviewsByCourseId(int theId);

    Course findCourseAndStudentsByCourseId(int theId);

    Student findStudentAndCourseByStudentId(int theId);

    void updateStudent(Student student);

    void deleteStudentById(int theId);



}
