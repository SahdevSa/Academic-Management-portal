package org.stud.reg.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.stud.reg.bean.CoursesDTO;
import org.stud.reg.bean.Student;
import org.stud.reg.bean.CGPA;
import org.stud.reg.exception.StudentException;
import org.stud.reg.util.DBUtil;

public class StudentDaoImpl implements StudentDao {

	@Override
	public String registerCourse(String roll, String courseid) throws StudentException {

		String message = null;
		Statement stmt = null;

		try (Connection con = DBUtil.establishConnection()) {

			// Create table to resgister
			stmt = con.createStatement();
			String create = "CREATE TABLE IF NOT EXISTS courseregister(student_id VARCHAR(50), name VARCHAR(50),course_id VARCHAR(25),semester INT,year INT,batch_id VARCHAR(20),grade VARCHAR(5),status VARCHAR(80))";
			stmt.executeUpdate(create);

			// The student info from Student table
			PreparedStatement ps1 = con.prepareStatement("SELECT * FROM students WHERE student_id=?");
			ps1.setString(1, roll);
			ResultSet rs1 = ps1.executeQuery();
			boolean flag = true;
			String name = "";
			String bid = "";
			int year = 0;
			double preCredit = 0;
			while (rs1.next()) {
				name = rs1.getString("name");
				bid = rs1.getString("batch_id");
				preCredit = rs1.getDouble("cr_registered");
				flag = false;
			}
			if (flag)
				throw new StudentException("No Student id Found !");
			// Check if the course exist or not?
			PreparedStatement ps2 = con.prepareStatement("SELECT * FROM courses WHERE course_id=?");
			ps2.setString(1, courseid);
			ResultSet rs2 = ps2.executeQuery();
			flag = true;
			int semester = 0;
			double crdit = 0;


			while (rs2.next()) {
				flag = false;
				semester = rs2.getInt("semester");
				crdit = rs2.getDouble("credits");
			}
			if (flag)
				throw new StudentException("No Course Found !");

			// Get the year

			PreparedStatement ps5 = con.prepareStatement("SELECT * FROM terms WHERE semester=?");
			ps5.setInt(1, semester);
			ResultSet rs5 = ps5.executeQuery();
			flag = true;
			while (rs5.next()) {
				flag = false;
				year = rs5.getInt("year");
			}
			if (flag)
				throw new StudentException("No Course Found !");

			// Check if the pre Exist or not
			PreparedStatement ps3 = con.prepareStatement("SELECT * FROM prerequisites WHERE course_id=?");
			ps3.setString(1, courseid);
			ResultSet rs3 = ps3.executeQuery();
			flag = true;
			String prereq_id = "";

			while (rs3.next()) {
				prereq_id = rs3.getString("prerequisite_id");
				flag = false;
			}

			// Check for prerequisit is clear or not?
			if (!flag) {
				PreparedStatement ps4 = con
						.prepareStatement("SELECT * FROM courseregister WHERE student_id=? AND course_id=?");
				ps4.setString(1, roll);
				ps4.setString(2, prereq_id);
				ResultSet rs4 = ps4.executeQuery();
				flag = true;
				while (rs4.next()) {
					flag = false;
				}
				if (flag)
					throw new StudentException("You Can not Resgister, Prerequisit "+prereq_id+" is not cleared !");
			}

			// Check whether the course is already registered
			PreparedStatement ps6 = con
					.prepareStatement("SELECT * FROM courseregister WHERE student_id=? AND course_id=?");
			ps6.setString(1, roll);
			ps6.setString(2, courseid);
			ResultSet rs6 = ps6.executeQuery();
			flag = true;
			if (rs6.next()) {
				flag = false;
			}
			if (!flag)
				throw new StudentException("Already, registered !");
			// Now regster
			PreparedStatement ps = con.prepareStatement("INSERT INTO courseregister(student_id,name,course_id,semester,year,batch_id,grade,status) VALUES (?,?,?,?,?,?,?,?)");
			ps.setString(1, roll);
			ps.setString(2, name);
			ps.setString(3, courseid);
			ps.setInt(4, semester);
			ps.setInt(5, 2019);
			ps.setString(6, bid);
			ps.setString(7, "NA");
			ps.setString(8, "Pending");

			int rs = ps.executeUpdate();
			if (rs > 0) {
				try {
					PreparedStatement ps7 = con.prepareStatement("UPDATE students SET cr_registered = ? WHERE student_id = ?");
					ps7.setDouble(1, crdit+preCredit);
					ps7.setString(2, roll);
					int rs7 = ps7.executeUpdate();
					if (!(rs7>0)) throw new StudentException("Error Adding Course Details");

				} catch (Exception e) {
					throw new StudentException(e.getMessage());
				}
				message = "Course Added Successfully";
			} else {
				throw new StudentException("Error Adding Course Details");
			}

		} catch (SQLException e) {
			message = e.getMessage();
			throw new StudentException(message);
		}

		return message;
	}

	@Override
	public List<CoursesDTO> showAllCourseDetails() throws StudentException {
		// TODO Auto-generated method stub
		List<CoursesDTO> courses = new ArrayList<>();

		try (Connection con = DBUtil.establishConnection()) {

			// PreparedStatement ps = con.prepareStatement("SELECT
			// c.c_id,c.c_name,sum(b.seats) FROM batch b " + "INNER JOIN course c ON c.c_id
			// = b.cid GROUP BY c.c_id");
			PreparedStatement ps = con.prepareStatement("SELECT c.course_id, c.title, c.department FROM courses c");

			ResultSet rs = ps.executeQuery();

			boolean flag = true;

			while (rs.next()) {

				String courseid = rs.getString(1);
				String title = rs.getString(2);
				String department = rs.getString(3);

				flag = false;

				CoursesDTO course = new CoursesDTO(courseid, title, department);

				courses.add(course);
			}

			if (flag)
				throw new StudentException("No course Found");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return courses;
	}

	@Override
	public Student login(String rollno, String pass) throws StudentException {
		Student student = null;

		try (Connection conn = DBUtil.establishConnection()) {

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM students WHERE student_id = ? AND password = ?");
			ps.setString(1, rollno);
			ps.setString(2, pass);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String roll = rs.getString("student_id");
				String name = rs.getString("name");

				student = new Student(roll, name, pass);
			} else {
				throw new StudentException("Wrong Username/Password!");
			}

		} catch (SQLException e) {
			throw new StudentException(e.getMessage());
		}

		return student;
	}

	@Override
	public String studentrecord(String roll, int semester) throws StudentException{
		String message = null;

		try (Connection con = DBUtil.establishConnection()) {
			System.out.println("chal raha hai");
			PreparedStatement ps = con.prepareStatement("SELECT * FROM courseregister WHERE student_id = ? AND semester = ?");
			ps.setString(1, roll);
			ps.setInt(2, semester);
			ResultSet rs = ps.executeQuery();
			System.out.println("Nahi chal raha hai");
			boolean flg = true;
			String courseid = "";
			String grade = "";
			
			while (rs.next()){
				if (flg) System.out.println("Semester : "+semester+" CGPA : "+7.24);
				courseid = rs.getString("course_id");
				grade = rs.getString("grade");
				flg = false;

				System.out.println("course id : "+courseid+"\tgrade : "+grade);
			}
			if (flg) throw new StudentException("No Registered Course Found !");

		} catch (SQLException e) {
			message = e.getMessage();
			throw new StudentException(message);
		}

		return message;

	}

	@Override
	public double CalculateCGPA(String rollnum, int semester) throws StudentException {
		double ans = 0.0;
		CGPA[] myList = new CGPA[8];
		try (Connection con = DBUtil.establishConnection()) {

			PreparedStatement ps = con.prepareStatement("SELECT * FROM courseregister WHERE student_id = ? ORDER BY semester");
			ps.setString(1, rollnum);
			
			ResultSet rs = ps.executeQuery();
			
			boolean flg = true;
			String courseid = "";
			String grade = "";
			int sem = 0;
			double ec=0, ep=0, cg= 0;
			while (rs.next()){
				courseid = rs.getString("course_id");
				grade = rs.getString("grade");
				sem = rs.getInt("semester");
				// get the course's credit
				PreparedStatement ps1 = con.prepareStatement("SELECT * FROM courses WHERE course_id = ?");
				ps1.setString(1, courseid);
				ResultSet rs1 = ps1.executeQuery();
				double credit = 0;
				while(rs1.next()){
					credit = rs1.getDouble("credits");
				}

				switch (sem) {
					case 1:
						CGPA cgpa = new CGPA(ec, ep, cg);
						myList[0] = cgpa;
						break;
				
					default:
						break;
				}

				
				flg = false;
			}
			if (flg) throw new StudentException("No Registered Course Found !");

		} catch (SQLException e) {
			String message = e.getMessage();
			throw new StudentException(message);
		}
		return ans;
	}

}
