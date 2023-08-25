package com.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "courses")
public class Courses {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cId;
	
	private String courseName;
	
	private String courseDetails;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "c_Id")  //  "c_Id" column will create in "Student_Courses" table
	private Set<Student_Courses> student_courses = new HashSet<>();    // One Course can be registered many Students

	/*
		{
    		"cId" : 1,
    		"courseName": "Spring",
    		"courseDetails": "Complete Spring Certification Course"
		}
	 */
	
	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(String courseDetails) {
		this.courseDetails = courseDetails;
	}

	public Set<Student_Courses> getStudent_courses() {
		return student_courses;
	}

	public void setStudent_courses(Set<Student_Courses> student_courses) {
		this.student_courses = student_courses;
	}

	@Override
	public String toString() {
		return "Courses [cId=" + cId + ", courseName=" + courseName + ", courseDetails=" + courseDetails
				+ ", student_courses=" + student_courses + "]";
	}

	public Courses(int cId, String courseName, String courseDetails, Set<Student_Courses> student_courses) {
		super();
		this.cId = cId;
		this.courseName = courseName;
		this.courseDetails = courseDetails;
		this.student_courses = student_courses;
	}

	public Courses() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	


	
	

}
