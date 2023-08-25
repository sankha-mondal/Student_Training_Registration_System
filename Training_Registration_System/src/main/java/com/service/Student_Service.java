package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.entity.Student;
import com.helper_classes.Reg_Courses_by_Student;
import com.repository.Student_Repository;

@Service
public class Student_Service {

	Connection con = null;
	String url = "jdbc:mysql://localhost:3306/";
	String dbName = "reg_db";
	String driver = "com.mysql.jdbc.Driver";
	String userName = "root";
	String password = "Sankha@12";

	@Autowired
	Student_Repository stuRepo;

// =============================================================================================================

	// Insert Operation by Id-Unique PK:- Op:3

	public String storeStudent(Student student) {
		boolean res = stuRepo.existsById(student.getsEmail());
		System.out.println(res);
		if (res) {
			return "Student details didn't store...\nYou have already Registered...";
		} else {
			stuRepo.save(student);
			return "Passenger(" + student.getsName() + ") Registered successfully...";
		}
	}

// =============================================================================================================

	// Retrieve Message by Email & password | Login Operation :- Op: 6

	public String login(Student student) {
		String email = student.getsEmail();
		String password = student.getsPassword();

		Optional<Student> op = stuRepo.findById(student.getsEmail());
		System.out.println("**************************" + op);

		if (op.isPresent()) {
			Student s = op.get();

			if (s.getsPassword().equals(student.getsPassword())) {
				return "WELCOME";
			} else {
				return "Password may be worng";
			}
		} else {
			return "Email or Password may be worng";
		}
	}

// =============================================================================================================

	// Retrieve Registered Courses by Student-Email: Op: 7

	public List<Reg_Courses_by_Student> getAll_RCbS(String sEmail) { // RCbS == Registered Courses by Student

		System.out.println(sEmail);

		List<Reg_Courses_by_Student> rcbs_list = new ArrayList<>();

		try {

			Class.forName(driver).newInstance();
			con = DriverManager.getConnection(url + dbName, userName, password);

			String qu = "select stu.s_email, stu.s_name, co.course_name, co.course_details\r\n"
						+ "from student stu\r\n"
						+ "inner JOIN student_courses sc\r\n"
						+ "on stu.s_email = sc.s_email\r\n"
						+ "inner JOIN courses co\r\n"
						+ "on sc.c_id = co.c_id\r\n"
						+ "and stu.s_email = ?;";

			PreparedStatement pstmt = con.prepareStatement(qu);

			pstmt.setString(1, sEmail); // dynamic Student

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Reg_Courses_by_Student rcbs = new Reg_Courses_by_Student();
				
				rcbs.setsEmail(rs.getString(1));
				rcbs.setsName(rs.getString(2));
				rcbs.setCourseName(rs.getString(3));
				rcbs.setCourseDetails(rs.getString(4));
				rcbs_list.add(rcbs);
			}

			System.out.println("Results");
			// st.close();
			con.close();
			rs.close();
			pstmt.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return rcbs_list;

	}

// =====================================================================================================================
// =====================================================================================================================

	

}
