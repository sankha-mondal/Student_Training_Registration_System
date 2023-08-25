package com.helper_classes;

public class Get_Students_from_Course {

	String sEmail;
	String sName;
	String courseName;
	String courseDetails;

	public String getsEmail() {
		return sEmail;
	}

	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
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

	@Override
	public String toString() {
		return "Reg_Courses_by_Student [sEmail=" + sEmail + ", sName=" + sName + ", courseName=" + courseName
				+ ", courseDetails=" + courseDetails + "]";
	}

	public Get_Students_from_Course(String sEmail, String sName, String courseName, String courseDetails) {
		super();
		this.sEmail = sEmail;
		this.sName = sName;
		this.courseName = courseName;
		this.courseDetails = courseDetails;
	}

	public Get_Students_from_Course() {
		super();
		// TODO Auto-generated constructor stub
	}
}
