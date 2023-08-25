package com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Student_Courses {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int scId;
	
	private String body;

	public int getScId() {
		return scId;
	}

	public void setScId(int scId) {
		this.scId = scId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Student_Courses [scId=" + scId + ", body=" + body + "]";
	}

	public Student_Courses(int scId, String body) {
		super();
		this.scId = scId;
		this.body = body;
	}

	public Student_Courses() {
		super();
		// TODO Auto-generated constructor stub
	}

		
	

}
