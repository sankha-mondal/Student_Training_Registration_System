package com.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student {
	
	@Id
	private String sEmail;
	
	private String sPassword;

	private String sName;
	
	/*
	 	{
		    "sEmail": "sankha@gmail.com",
		    "sName": "Sankha Subhra Mondal",
		    "sPassword": "s123"
		}
	 */
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "s_email")  //  "s_email" column will create in "Student_Courses" table
	private Set<Student_Courses> student_courses = new HashSet<>();    // One Student can register many Courses

	public String getsEmail() {
		return sEmail;
	}

	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}
	
	public String getsPassword() {
		return sPassword;
	}

	public void setsPassword(String sPassword) {
		this.sPassword = sPassword;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}
	
	public Set<Student_Courses> getStudent_courses() {
		return student_courses;
	}

	public void setStudent_courses(Set<Student_Courses> student_courses) {
		this.student_courses = student_courses;
	}
	

	public Student(String sEmail, String sPassword, String sName, Set<Student_Courses> student_courses) {
		super();
		this.sEmail = sEmail;
		this.sPassword = sPassword;
		this.sName = sName;
		this.student_courses = student_courses;
	}

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	

}