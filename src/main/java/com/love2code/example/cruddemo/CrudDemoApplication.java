package com.love2code.example.cruddemo;

import com.love2code.example.cruddemo.dao.AppDAO;
import com.love2code.example.cruddemo.entity.Course;
import com.love2code.example.cruddemo.entity.Instructor;
import com.love2code.example.cruddemo.entity.InstructorDetail;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CrudDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudDemoApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(AppDAO appDAO) {
        return runner -> {
            //createInstructor(appDAO);
            //findInstructorById(appDAO);
            //deleteInstructor(appDAO);
            //findInstructorDetail(appDAO);
            //deleteInstructorDetail(appDAO);
            //createInstructorWithCourses(appDAO);
            //findInstructorWithCourses(appDAO);
            //findCoursesForInstructor(appDAO);
            //findInstructorWithCoursesJoinFetch(appDAO);
            //updateInstructor(appDAO);
			updateCourse(appDAO);
        };
    }


	private void updateCourse(AppDAO appDAO){
		int theId = 10;

		System.out.println("Finding course id: " + theId);
		Course course = appDAO.findCourseById(theId);
		System.out.println("Update Course with id  " + theId);
		course.setTitle("Tennis table class");
		appDAO.updateCourse(course);
		System.out.println("Done!");
	}
    private void updateInstructor(AppDAO appDAO) {

        // finding instructor

        int theId = 1;
        System.out.println("Finding instructor id: " + theId);
        Instructor tempInstructor = appDAO.findInstructorByID(theId);
        // updateInstructor

        System.out.println("Update instructor id " + theId);
        tempInstructor.setFirstName("Test");
        appDAO.updateInstructor(tempInstructor);
        System.out.println("Done!");


    }

    private void findInstructorWithCoursesJoinFetch(AppDAO appDAO) {

        int theId = 1;
        // find Instructor
        System.out.println("Finding instructor id : " + theId);
        Instructor tempInstructor = appDAO.findInstructorByIdJoinFetch(theId);
        System.out.println("Temp instructor: " + tempInstructor);
        System.out.println("The associate courses : " + tempInstructor.getCourses());
        System.out.println("Done!");
    }

    private void findCoursesForInstructor(AppDAO appDAO) {
        int theId = 1;
        Instructor tempInstructor = appDAO.findInstructorByID(theId);
        System.out.println("tempInstructor: " + tempInstructor);
        System.out.println("Finding courses for Instructor: " + theId);
        List<Course> courseList = appDAO.findCoursesByInstructorId(theId);
        //associate the objects
        tempInstructor.setCourses(courseList);
        System.out.println("the associate courses:" + tempInstructor.getCourses());
        System.out.println("Done!");


    }

    private void findInstructorWithCourses(AppDAO appDAO) {

        int theId = 1;
        System.out.println("Finding Instructor ID: " + theId);
        Instructor tempInstructor = appDAO.findInstructorByID(theId);
        System.out.println("tempInstructor: " + tempInstructor);
        System.out.println("associated courses " + tempInstructor.getCourses());
        System.out.println("Done!");
    }

    private void createInstructorWithCourses(AppDAO appDAO) {
        Instructor tempInstructor = new Instructor("Radoslav", "Dimitrov", "radoslav@gmail.com");
        InstructorDetail tempInstructorDetail = new InstructorDetail("https//SSLazioChannel", "Football");
        tempInstructor.setInstructorDetail(tempInstructorDetail);
        Course tempCourse1 = new Course("Java-MasterClass");
        Course tempCourse2 = new Course("JPA/Hibernate");
        tempInstructor.add(tempCourse1);
        tempInstructor.add(tempCourse2);
        System.out.println("Saving instructor " + tempInstructor);
        System.out.println("Courses: " + tempInstructor.getCourses());
        //This will ALSO save the courses, because of Cascade.Type.PERSIST
        appDAO.save(tempInstructor);
        System.out.println("Done!");
    }

    private void deleteInstructorDetail(AppDAO appDAO) {

        int theId = 6;
        System.out.println("Deleting Instructor Detail with ID: " + theId);
        appDAO.deleteInstructorDetailById(theId);
        System.out.println("Done!");
    }

    private void findInstructorDetail(AppDAO appDAO) {
        int theId = 2;

        InstructorDetail tempInstructorDetail = appDAO.findInstructorDetailById(theId);
        System.out.println("Find Instructor Detail by ID: " + theId);
        System.out.println("Instructor Detail : " + tempInstructorDetail);
        System.out.println("Associate Instructor : " + tempInstructorDetail.getInstructor());
        System.out.println("Done!");

    }

    private void deleteInstructor(AppDAO appDAO) {

        int theId = 1;
        System.out.println("Deleting Instructor by ID: " + theId);
        appDAO.deleteInstructorById(theId);
        System.out.println("Done!");
    }

    private void findInstructorById(AppDAO appDAO) {
        int theId = 2;
        System.out.println("Finding Instructor with ID: " + theId);
        Instructor tempInstructor = appDAO.findInstructorByID(theId);

        System.out.println("Temp Instructor " + tempInstructor);
        System.out.println("The associate Instructor Details " + tempInstructor.getInstructorDetail());

    }

    private void createInstructor(AppDAO appDAO) {

        Instructor tempInstructor = new Instructor("Radoslav", "Dimitrov", "radoslav1@gmail.com");
        //Instructor tempInstructor = new Instructor("Nikoleta","Hristova","nikoleta@gmail.com");
        InstructorDetail tempDetail = new InstructorDetail("https://LazioChannel", "Football");
        //InstructorDetail tempDetail = new InstructorDetail("https://MetropolGrifid","Managing");
        //associate the objects
        tempInstructor.setInstructorDetail(tempDetail);
        //saving instructor

        System.out.println("Saving Instructor " + tempInstructor);
        appDAO.save(tempInstructor);
        //this will also save the detail object becaus of Cascade.Type.ALL
        System.out.println("Saving Instructor Detail: " + tempInstructor.getInstructorDetail());
        System.out.println("Done!");
    }
}
