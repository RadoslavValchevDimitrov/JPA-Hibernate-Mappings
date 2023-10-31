package com.love2code.example.cruddemo.dao;

import com.love2code.example.cruddemo.entity.Course;
import com.love2code.example.cruddemo.entity.Instructor;
import com.love2code.example.cruddemo.entity.InstructorDetail;
import com.love2code.example.cruddemo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO {
    // define entityManager
    private EntityManager entityManager;

    //inject entityManager with constructor injection
    @Autowired
    public AppDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Instructor theInstructor) {

        // this will also save  the detail object because of Cascade.Type.ALL
        entityManager.persist(theInstructor);

    }

    @Override
    public Instructor findInstructorByID(int theId) {

        //this will also retrieve the instructor detail object because of default behavior of @OneToOne
        //fetch type is eager
        return entityManager.find(Instructor.class, theId);

    }

    @Override
    @Transactional
    public void deleteInstructorById(int theId) {
        //retrieve the instructor
        Instructor tempInstructor = entityManager.find(Instructor.class, theId);
        //get the courses
        List<Course> courseList = tempInstructor.getCourses();

        //break association for all courses for the instructor

        for (var c : courseList) {

            //remove the instructor for courses
            c.setInstructor(null);
        }
        //delete the instructor

        entityManager.remove(tempInstructor);
    }

    @Override
    public InstructorDetail findInstructorDetailById(int theId) {

        return entityManager.find(InstructorDetail.class, theId);

    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int theId) {

        //retrieve the instructor
        InstructorDetail tempinstructorDetail = entityManager.find(InstructorDetail.class, theId);

        //remove the associate object reference
        //break bi-directional link
        tempinstructorDetail.getInstructor().setInstructorDetail(null);
        // this will also delete the associate instructor
        entityManager.remove(tempinstructorDetail);

    }

    @Override
    public List<Course> findCoursesByInstructorId(int theId) {
        // create query
        TypedQuery<Course> theQuery = entityManager.createQuery("from Course where instructor.id= :data", Course.class);
        theQuery.setParameter("data", theId);
        //execute query
        List<Course> courseList = theQuery.getResultList();
        return courseList;
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int theId) {
        TypedQuery<Instructor> theQuery = entityManager.createQuery("select i from Instructor i "
                + "JOIN FETCH i.courses "
                + "JOIN FETCH i.instructorDetail "
                + "where i.id = :data", Instructor.class);
        theQuery.setParameter("data", theId);
        Instructor tempInstructor = theQuery.getSingleResult();
        return tempInstructor;
    }

    @Transactional
    @Override
    public void updateInstructor(Instructor instructor) {

        entityManager.merge(instructor);

    }

    @Transactional
    @Override
    public void updateCourse(Course course) {

        entityManager.merge(course);
    }

    @Override
    public Course findCourseById(int theId) {

        return entityManager.find(Course.class, theId);

    }

    @Override
    @Transactional
    public void deleteCourse(int theId) {

        //retrieve the course
        Course course = entityManager.find(Course.class,theId);

        //delete the course

        entityManager.remove(course);


    }

    @Override
    @Transactional
    public void save(Course theCourse) {
        // this will also save the associated review because of Cascade.Type.ALL
        entityManager.persist(theCourse);

    }

    @Override
    public Course findCourseAndReviewsByCourseId(int theId) {

        // create query

        TypedQuery<Course> theQuery = entityManager.createQuery(" select r from Course r "
                                                             + "JOIN FETCH r.reviews "+
                                                               "where r.id = :data",Course.class );
        theQuery.setParameter("data",theId);

        // execute query
        Course course = theQuery.getSingleResult();
        return course;
    }

    @Override
    public Course findCourseAndStudentsByCourseId(int theId) {

        TypedQuery<Course> theQuery = entityManager.createQuery( " select c from Course c " +
                " JOIN FETCH c.students "+
                " where c.id = :data",Course.class);
        theQuery.setParameter("data",theId);

        return theQuery.getSingleResult();
    }

    @Override
    public Student findStudentAndCourseByStudentId(int theId) {

        TypedQuery<Student>theQuery = entityManager.createQuery("select s from Student s " +
                "JOIN FETCH s.courses " +
                "where s.id=:data",Student.class);
        theQuery.setParameter("data",theId);

        return theQuery.getSingleResult();
    }

    @Override
    @Transactional
    public void updateStudent(Student student) {

        entityManager.merge(student);

    }

    @Override
    @Transactional
    public void deleteStudentById(int theId) {
        // retrieve the student
        Student tempStudent = entityManager.find(Student.class,theId);
        // delete the student
        entityManager.remove(tempStudent);
    }


}
