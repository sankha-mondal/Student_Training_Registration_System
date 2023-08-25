package com.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Courses;
import com.helper_classes.Get_Students_from_Course;
import com.repository.Courses_Repository;

@Service
public class Course_Service {

	Connection con = null;
	String url = "jdbc:mysql://localhost:3306/";
	String dbName = "reg_db";
	String driver = "com.mysql.jdbc.Driver";
	String userName = "root";
	String password = "Sankha@12";

	@Autowired
	Courses_Repository coursesRepo;

// =============================================================================================================

	// Insert Operation by Id-Unique PK:- Op:3

	public String storeCourse(Courses course) {
		boolean res = coursesRepo.existsById(course.getcId());
		System.out.println(res);
		if (res) {
			return "Course details didn't store...\nIt's already added...";
		} else {
			coursesRepo.save(course);
			return "Course(" + course.getCourseName() + ") Added successfully...";
		}
	}

// =============================================================================================================

	// Get Students by Course-Name : Op: 5

	public List<Get_Students_from_Course> getAll_GSbC(String cName) { // GSbC == Get Students by Course-Id

		System.out.println(cName);

		List<Get_Students_from_Course> gsbc_list = new ArrayList<>();

		try {

			Class.forName(driver).newInstance();
			con = DriverManager.getConnection(url + dbName, userName, password);

			String qu = "select stu.s_email, stu.s_name, co.course_name, co.course_details\r\n"
						+ "from student stu\r\n"
						+ "inner JOIN student_courses sc\r\n"
						+ "on stu.s_email = sc.s_email\r\n"
						+ "inner JOIN courses co\r\n"
						+ "on sc.c_id = co.c_id\r\n"
						+ "and co.course_name = ?;";

			PreparedStatement pstmt = con.prepareStatement(qu);

			pstmt.setString(1, cName); // dynamic Course

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				Get_Students_from_Course gsbc = new Get_Students_from_Course();

				gsbc.setsEmail(rs.getString(1));
				gsbc.setsName(rs.getString(2));
				gsbc.setCourseName(rs.getString(3));
				gsbc.setCourseDetails(rs.getString(4));

				gsbc_list.add(gsbc);

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
		return gsbc_list;
	}

// =============================================================================================================
// =============================================================================================================

}
