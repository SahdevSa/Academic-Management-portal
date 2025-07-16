package org.stud.reg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

import org.stud.reg.bean.Admin;
import org.stud.reg.bean.Course;
import org.stud.reg.bean.Student;
import org.stud.reg.exception.AdminException;
import org.stud.reg.util.DBUtil;

public class AdminDaoImpl implements AdminDao{

	@Override
	public String addCourse(Course course) throws AdminException {
		
		String message = null;
		
		try(Connection conn = DBUtil.establishConnection()){
			
			PreparedStatement ps =  conn.prepareStatement("INSERT INTO courses(course_id,title,department,lectures,tutorials,practical,self_study,credits,semester) VALUES (?,?,?,?,?,?,?,?,?)");
			ps.setString(1, course.getCid());
			ps.setString(2, course.getCname());
			ps.setString(3, course.getDepartment());
			ps.setInt(4, course.getLectures());
			ps.setInt(5, course.getTutorials());
			ps.setInt(6, course.getPracticals());
			ps.setInt(7, course.getSelfstudy());
			ps.setInt(8, course.getCredit());
			ps.setInt(9, course.getSemester());

			PreparedStatement fps = conn.prepareStatement("INSERT INTO teaches(faculty_id, course_id) VALUES(?,?)");
			fps.setString(1, course.getFaculty());
			fps.setString(2, course.getCid());
			int res = 0;
			try{
				res = ps.executeUpdate();
				fps.executeUpdate();
			}catch(Exception e){
				// System.out.println(e);
				throw new AdminException("The Course Already Exists\n");
			}
			
			if(res > 0) {
				message = "Course Added Successfully!!!\n";
			}else {
				throw new AdminException("Error Adding Course Details\n");
			}
			
		} catch (SQLException e) {
			throw new AdminException(e.getMessage());
		}
		
		return message;
	}

	@Override
	public String deleteCourse(String cid) throws AdminException {
		
		String message = null;
		
		try(Connection conn = DBUtil.establishConnection()){
			
			PreparedStatement ps = conn.prepareStatement("DELETE FROM courses WHERE course_id = ?");
			ps.setString(1, cid);
			
			int res = ps.executeUpdate();
			
			if(res>0) message = "Course Removed Successfully!\n";
			else throw new AdminException("Course ID Error! Not Found\n");
			
			
		} catch (SQLException e) {
			throw new AdminException(e.getMessage());
		}
		
		return message;
	}

	@Override
	public String addStudentToBatch(String roll, String bid, String name, String pass, double crReg, double cr_limit) throws AdminException {
		String message = null;
		
		try(Connection conn = DBUtil.establishConnection()){
			
			PreparedStatement ps = conn.prepareStatement("INSERT INTO students(student_id, name, batch_id, password, cr_registered, cr_limit) VALUES(?,?,?,?,?,?)");
			ps.setString(1, roll);
			ps.setString(2, name);
			ps.setString(3, bid);
			ps.setString(4, pass);
			ps.setDouble(4, crReg);
			ps.setDouble(4, cr_limit);

			int res = 0;
			try{
				res = ps.executeUpdate();
			}catch(Exception e){
				throw new AdminException("The Student Already Exists\n");
			}
			
			if(res > 0) {
				message = "Student Added Successfully!!!\n";
			}else {
				throw new AdminException("Error Adding Student Details\n");
			}
			
		}
		 catch (SQLException e) {
			e.printStackTrace();
			throw new AdminException(e.getMessage());
		}
		
		return message;
	}

	@Override
	public Admin login(String username, String password) throws AdminException {
		// TODO Auto-generated method stub
		Admin admin = null;
		
		try(Connection conn = DBUtil.establishConnection()){
			
			PreparedStatement ps =  conn.prepareStatement("SELECT * FROM admin WHERE admin_id = ? AND password = ?");
			ps.setString(1, username);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				
				int id = 1; //rs.getString(1);
				String name = rs.getString(2);
				String user = rs.getString(1);
				String pass = rs.getString(3);
				
				admin = new Admin(id, name, user, pass);

			}
			else {
				throw new AdminException("Authentication Error ! ");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new AdminException(e.getMessage());
		}
		
		return admin;
	}

	@Override
	public List<Student> studentList() throws AdminException {
		// TODO Auto-generated method stub
		List<Student> students = new ArrayList<>();
		
		try(Connection conn = DBUtil.establishConnection()){
			
			PreparedStatement ps =  conn.prepareStatement("SELECT * FROM students");
		
			ResultSet rs = ps.executeQuery();
			
			boolean flag = true;
			
			while(rs.next()) {
				
				flag = false;
				String roll = rs.getString("student_id");
				String name = rs.getString("name");
				String pass= rs.getString("password");

				
				Student student = new Student(roll,name,pass);
				
				students.add(student);
			}
			
			if(flag) throw new AdminException("No Student Data Found !");
			
			
		}catch (SQLException e) {
			// TODO: handle exception
			throw new AdminException(e.getMessage());
		}
		
		return students;
	}

	@Override
	public List<Course> courseList() throws AdminException {
		List<Course> courses = new ArrayList<>();
		
		try(Connection conn = DBUtil.establishConnection()){
			
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM courses");
			ResultSet rs = ps.executeQuery();
			
			boolean flag = true;
			
			while(rs.next()) {
				String cid = rs.getString("course_id");
				String cname = rs.getString("title");
				String department = rs.getString("department");
				int lecture = rs.getInt("lectures");
				int tutorials = rs.getInt("tutorials");
				int practicals = rs.getInt("practical");
				int selfstudy = rs.getInt("self_study");
				int credits = rs.getInt("credits");
				int semester = rs.getInt("semester");

				PreparedStatement fps = conn.prepareStatement("SELECT * FROM teaches WHERE course_id = ?");
				fps.setString(1, cid);
				ResultSet frs = fps.executeQuery();
				String faculty_id = "";
				while(frs.next()){
					faculty_id = frs.getString("faculty_id");
				}

				flag = false;
				Course course = new Course(cid,cname,department,lecture,tutorials,practicals,selfstudy,credits,semester, faculty_id);
				
				courses.add(course);
			}
			
			if(flag) throw new AdminException("No Course Found !");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new AdminException(e.getMessage());
		}
		
		
		
		
		return courses;
	
	
	}

	@Override
	public String studentgrade(String roll, FileWriter writer) throws AdminException{

		String message = null;

		try (Connection con = DBUtil.establishConnection()) {

			PreparedStatement ps = con.prepareStatement("SELECT * FROM courseregister WHERE student_id = ? ORDER BY student_id, semester");
			ps.setString(1, roll);
			ResultSet rs = ps.executeQuery();
			
			boolean flg = true;
			String courseid = "";
			String grade = "";
			String sem = "";
			while (rs.next()){
				courseid = rs.getString("course_id");
				grade = rs.getString("grade");
				sem = rs.getString("semester");
				flg = false;
				try {
					writer.write("Semester : "+sem+" course id : "+courseid+"\tgrade : "+grade+"\n");
				} catch (Exception e) {
					throw new AdminException(e.getMessage());
				}
				
			}
			if (flg) throw new AdminException("No Registered Course Found !");

		} catch (SQLException e) {
			message = e.getMessage();
			throw new AdminException(message);
		}

		return message;

	}
}
