package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Courses;
import com.entity.Student;
import com.entity.Student_Courses;
import com.exception.ResourceNotFoundException;
import com.repository.Courses_Repository;
import com.repository.Student_Courses_Repository;
import com.repository.Student_Repository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/student_course")       //  http://localhost:8585/student_course
public class Student_Courses_Controller {
	
	@Autowired
	Student_Repository studentRepo;
	
	@Autowired
	Courses_Repository coursesRepo;
	
	@Autowired
	Student_Courses_Repository studentCourses_Repo;
	
//======================================================================================================================================
	
	  //  Retrieve by Student-Courses Id:-  Op:1A
	  //  http://localhost:8585/student_course/getAll

	  @GetMapping("/getAll")
	  public ResponseEntity<List<Student_Courses>> getAll() {
		  
	    List<Student_Courses> student_courses = new ArrayList<Student_Courses>();
	    	studentCourses_Repo.findAll().forEach(student_courses::add);
		
		    if (student_courses.isEmpty()) {
		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    }
		 return new ResponseEntity<>(student_courses, HttpStatus.OK);
	  }
	  
//======================================================================================================================================
		
	  //  Retrieve Student_Courses by Student Email PK:-  Op:1B
	  //  http://localhost:8585/student_course/getByStuEmail/{s_email}

	  @GetMapping("/getByStuEmail/{s_email}")
	  public ResponseEntity<List<Student_Courses>> getByStuEmail(@PathVariable(value = "s_email") String s_email) {
		  
	    Student student = studentRepo.findById(s_email)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found Student Details with Id = " + s_email));
	
		    List<Student_Courses> student_course = new ArrayList<Student_Courses>();
		    student_course.addAll(student.getStudent_courses());   // calling getStudent_courses()
	    
	    return new ResponseEntity<>(student_course, HttpStatus.OK);
	  }
	  
//======================================================================================================================================
		
	  //  Retrieve Student_Courses by Course Id PK:-  Op:1B
	  //  http://localhost:8585/student_course/getByCourseId/{c_id}

	  @GetMapping("/getByCourseId/{c_id}")
	  public ResponseEntity<List<Student_Courses>> getByCourseId(@PathVariable(value = "c_id") int c_id) {
		  
	    Courses courses = coursesRepo.findById(c_id)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found Course Details with Id = " + c_id));
	
		    List<Student_Courses> student_course = new ArrayList<Student_Courses>();
		    student_course.addAll(courses.getStudent_courses());   // calling getStudent_courses()
	    
	    return new ResponseEntity<>(student_course, HttpStatus.OK);
	  }
	
//======================================================================================================================================
	  
	  //  Insert Operation with r.to Student-Enail & Course-Id:-  Op:2  
	  //  http://localhost:8585/student_course/store/{s_email}/{c_id}         
	  
	  @PostMapping("/store/{s_email}/{c_id}")
	  public ResponseEntity<Student_Courses> create_studentcourses( @PathVariable(value = "s_email") String s_email,
													@PathVariable(value = "c_id") int c_id,
													@RequestBody Student_Courses studentCourses_Req) {
		  
		  Student_Courses student_courses = studentRepo.findById(s_email).map(student -> {
			  student.getStudent_courses().add(studentCourses_Req);
			  
			  
			  Student_Courses _student_courses = coursesRepo.findById(c_id).map(course -> {
					  course.getStudent_courses().add(studentCourses_Req);
					  
					  return studentCourses_Repo.save(studentCourses_Req);
				  }).orElseThrow(() -> new ResourceNotFoundException("Not found Course with id = " + c_id));
		  		
		      return studentCourses_Repo.save(studentCourses_Req);
		  }).orElseThrow(() -> new ResourceNotFoundException("Not found Student with Email = " + s_email));
		 
	
	    return new ResponseEntity<>(student_courses, HttpStatus.CREATED);
	  }

//======================================================================================================================================
	  


}
