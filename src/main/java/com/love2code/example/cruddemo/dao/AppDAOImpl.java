package com.love2code.example.cruddemo.dao;

import com.love2code.example.cruddemo.entity.Course;
import com.love2code.example.cruddemo.entity.Instructor;
import com.love2code.example.cruddemo.entity.InstructorDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO{
    // define entityManager
    //inject entityManager with constructor injection
    private EntityManager entityManager;

    @Autowired
    public AppDAOImpl(EntityManager entityManager){
        this.entityManager =entityManager;
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
      return  entityManager.find(Instructor.class,theId);

    }

    @Override
    @Transactional
    public void deleteInstructorById(int theId) {

        Instructor tempInstructor = entityManager.find(Instructor.class,theId);
        entityManager.remove(tempInstructor);
    }

    @Override
    public InstructorDetail findInstructorDetailById(int theId) {

        return entityManager.find(InstructorDetail.class,theId);

    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int theId) {
        InstructorDetail tempinstructorDetail = entityManager.find(InstructorDetail.class,theId);

        //remove the associate object reference
        //break bi-directional link
       tempinstructorDetail.getInstructor().setInstructorDetail(null);
        // this will also delete the associate instructor
        entityManager.remove(tempinstructorDetail);

    }

    @Override
    public List<Course> findCoursesByInstructorId(int theId) {
        TypedQuery<Course> theQuery = entityManager.createQuery("from Course where instructor.id= :data",Course.class);
        theQuery.setParameter("data",theId);
        //execute query
        List<Course> courseList = theQuery.getResultList();
        return courseList;
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int theId) {
        TypedQuery<Instructor> theQuery = entityManager.createQuery("select i from Instructor i "
                + "JOIN FETCH i.courses "
                + "JOIN FETCH i.instructorDetail "
                + "where i.id = :data",Instructor.class);
        theQuery.setParameter("data",theId);
        Instructor tempInstructor = theQuery.getSingleResult();
        return tempInstructor;
    }

    @Transactional
    @Override
    public void updateInstructor(Instructor instructor) {

        int theId = 1;

        entityManager.merge(instructor);

    }

    @Transactional
    @Override
    public void updateCourse(Course course) {


        entityManager.merge(course);
    }

    @Override
    public Course findCourseById(int theId) {

      return  entityManager.find(Course.class,theId);

    }


}
