package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Courses;
import com.exception.ResourceNotFoundException;
import com.helper_classes.Get_Students_from_Course;
import com.payload.ApiResponse;
import com.repository.Courses_Repository;
import com.service.Course_Service;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/course") // http://localhost:8585/course
public class Courses_Controller {

	@Autowired
	Courses_Repository courseRepo;

	@Autowired
	Course_Service courseService;

//====================================================== : CRUD : =============================================================

	// Retrieve Operation:- Op:1A
	// http://localhost:8585/course/getAll

	@GetMapping("/getAll")
	public ResponseEntity<List<Courses>> getAllBooking() {

		List<Courses> courses = new ArrayList<Courses>();
		courseRepo.findAll().forEach(courses::add);

		if (courses.isEmpty()) {
			// return new ResponseEntity<>(HttpStatus.NO_CONTENT); OR
			throw new ResourceNotFoundException("No Data Found..");
		}
		return new ResponseEntity<>(courses, HttpStatus.OK);
	}

//======================================================================================================================================

	// Retrieve by Student-Email:- Op:1B
	// http://localhost:8585/course/getByStudentEmail/{sEmail}

//	  @GetMapping("/getByStudentEmail/{sEmail}")
//	  public ResponseEntity<List<Courses>> getAllBookingByStudentEmail(@PathVariable(value = "sEmail") String sEmail) {
//		  
//	    Student student = stuRepo.findById(sEmail)
//	        .orElseThrow(() -> new ResourceNotFoundException("Not found Student with Email = " + sEmail));
//	
//		    List<Courses> courses = new ArrayList<Courses>();
//		    courses.addAll(student.getCourses());   // calling getBooking()
//	    
//	    return new ResponseEntity<>(courses, HttpStatus.OK);
//	  }

//======================================================================================================================================

	// Retrieve by Booking-Id:- Op:1C
	// http://localhost:8585/course/single_course/{cId}

	@GetMapping("/single_course/{cId}")
	public ResponseEntity<Courses> getSingleCourseBycId(@PathVariable(value = "cId") int cId) {

		Courses courses = courseRepo.findById(cId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Courses with Id = " + cId));

		return new ResponseEntity<>(courses, HttpStatus.OK);
	}

//=======================================================================================================================================

	// Insert Operation:- Op:3
	// http://localhost:8585/course/store

	@PostMapping(value = "store", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String storeCourse(@RequestBody Courses courseReq) {
		System.out.println("In Controller..");

		return courseService.storeCourse(courseReq);
	}

//=======================================================================================================================================  

	// Update Operation:- Op:4
	// http://localhost:8585/course/update/cId

	@PutMapping("/update/{cId}")
	public ResponseEntity<Courses> updateStudent(@PathVariable("cId") int cId, @RequestBody Courses coursesReq) {

		Courses _courses = courseRepo.findById(cId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Course with id = " + cId));

		_courses.setCourseDetails(coursesReq.getCourseName());
		_courses.setCourseDetails(coursesReq.getCourseDetails());

		return new ResponseEntity<>(courseRepo.save(_courses), HttpStatus.OK);
	}

//=======================================================================================================================================

	// Delete Operation by Id:- Op:5
	// http://localhost:8585/course/delete/{cId}

	@DeleteMapping("/delete/{cId}")
	public ResponseEntity<ApiResponse> deleteCourse(@PathVariable("cId") int cId) {

		Courses _cId = courseRepo.findById(cId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Course with Id = " + cId));

		courseRepo.deleteById(cId);

		// return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Course details deleted Successfully", true),
				HttpStatus.OK);
	}

//=======================================================================================================================================

	// Get Students by Course-Name : Op: 5
	// http://localhost:8585/course/get_Students_from_Course/{cName}

	@GetMapping(value = "get_Students_from_Course/{cId}", produces = MediaType.APPLICATION_JSON_VALUE)

	public List<Get_Students_from_Course> get_Students_from_Course(@PathVariable("cId") String cName) {

		return courseService.getAll_GSbC(cName); // RCbS == Registered Courses by Student
	}
	
//=======================================================================================================================================
//=======================================================================================================================================


}
