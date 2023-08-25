package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Student;
import com.exception.ResourceNotFoundException;
import com.helper_classes.Reg_Courses_by_Student;
import com.payload.ApiResponse;
import com.repository.Student_Repository;
import com.service.Student_Service;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/student") // http://localhost:8585/student
public class Student_Controller {

	@Autowired
	Student_Repository stuRepo;

	@Autowired
	Student_Service stuService;

//*************************************************** : CRUD Operation : *****************************************************************	  
//=======================================================================================================================================

	// Retrieve Operation:- Op:1
	// http://localhost:8585/student/getAll

	@GetMapping("/getAll")
	public ResponseEntity<List<Student>> getAllStudent() {
		List<Student> students = new ArrayList<Student>();

		stuRepo.findAll().forEach(students::add);

		if (students.isEmpty()) {
			// return new ResponseEntity<>(HttpStatus.NO_CONTENT); OR
			throw new ResourceNotFoundException("No Data Found..");

		}

		return new ResponseEntity<>(students, HttpStatus.OK);
	}

//=======================================================================================================================================

	// Retrieve data by PK :- Op:2
	// http://localhost:8585/student/getByPK/{sEmail}

	@GetMapping("/getByPK/{sEmail}")
	public ResponseEntity<Student> getById(@PathVariable("sEmail") String sEmail) {

		Student student = stuRepo.findById(sEmail)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Student with email = " + sEmail));

		return new ResponseEntity<>(student, HttpStatus.OK);
	}

//=======================================================================================================================================

	// Insert Operation:- Op:3
	// http://localhost:8585/student/store

	@PostMapping(value = "store", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String storeStudent(@RequestBody Student stuReq) {
		System.out.println(stuReq.getsEmail());

		return stuService.storeStudent(stuReq);
	}

//=======================================================================================================================================  

	// Update Operation:- Op:4
	// http://localhost:8585/student/update/{sEmail}

	@PutMapping("/update/{sEmail}")
	public ResponseEntity<Student> updateStudent(@PathVariable("sEmail") String sEmail, @RequestBody Student stuReq) {

		Student _student = stuRepo.findById(sEmail)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Student with Email = " + sEmail));

		_student.setsName(stuReq.getsName());
		_student.setsPassword(stuReq.getsPassword());
		// _student.setCourses(stuReq.getCourses()); // Don't do this bcoz it will store
		// null at the time of Update.

		return new ResponseEntity<>(stuRepo.save(_student), HttpStatus.OK);
	}

//=======================================================================================================================================

	// Delete Operation by Id:- Op:5
	// http://localhost:8585/student/delete/{sEmail}

	@DeleteMapping("/delete/{sEmail}")
	public ResponseEntity<ApiResponse> deleteStudent(@PathVariable("sEmail") String sEmail) {

		Student stu_email = stuRepo.findById(sEmail)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Student with Email = " + sEmail));

		stuRepo.deleteById(sEmail);

		// return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Student details deleted Successfully", true),
				HttpStatus.OK);
	}

//=======================================================================================================================================
//**************************************************** : User Define : *****************************************************************		  
//======================================================================================================================================

	// Retrieve Message by Email & password | Login Operation :- Op: 6
	// http://localhost:8585/student/login

	@PostMapping(value = "login")
	public String login(@RequestBody Student student) {

		System.out.println("Controller: " + student.getsEmail());
		// Thread.sleep(3000);
		return stuService.login(student);
	}

// =================================================================================================================	  

	// Retrieve Registered Courses by Student-Email: Op: 7
	// http://localhost:8585/student/get_Reg_Courses_by_stu_Email/{sEmail}

	@GetMapping(value = "get_Reg_Courses_by_stu_Email/{sEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Reg_Courses_by_Student> get_Reg_Courses_by_stu_Email(@PathVariable("sEmail") String sEmail) {

		return stuService.getAll_RCbS(sEmail); // RCbS == Registered Courses by Student
	}

// ================================================================================================================= 
// ================================================================================================================= 

}
