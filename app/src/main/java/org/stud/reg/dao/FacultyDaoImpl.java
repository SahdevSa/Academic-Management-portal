package org.stud.reg.dao;

import java.sql.Connection;
// import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

import org.stud.reg.bean.Faculty;
import org.stud.reg.exception.FacultyException;
import org.stud.reg.bean.Course;
import org.stud.reg.util.DBUtil;
import org.stud.reg.bean.Student;
// import org.stud.reg.bean.StudentDTO;


public class FacultyDaoImpl implements FacultyDao {

    @Override
	public Faculty login(String username, String password) throws FacultyException {
		Faculty faculty = null;
		
		try(Connection conn = DBUtil.establishConnection()){
			
			PreparedStatement ps =  conn.prepareStatement("SELECT * FROM faculty WHERE faculty_id = ? AND password = ?");
			ps.setString(1, username);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				
				String id = rs.getString(1);
				String name = rs.getString(2);
				String user = rs.getString(1);
				String pass = rs.getString(4);
				
				faculty = new Faculty(id, name, user, pass);

			}
			else {
				throw new FacultyException("Authentication Error ! ");
			}
			
		} catch (SQLException e) {
			throw new FacultyException(e.getMessage());
		}
		
		return faculty;
	}

	@Override
	public String addCourse(Course course) throws FacultyException{
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
			


			
			int res = ps.executeUpdate();
			
			if(res > 0) {
				message = "Course Added Successfully";
			}else {
				throw new FacultyException("Error Adding Course Details");
			}
			
		} catch (SQLException e) {
			throw new FacultyException(e.getMessage());
		}
		
		return message;
	}

	@Override
	public String deleteCourse(String cid) throws FacultyException {
		
		String message = null;
		
		try(Connection conn = DBUtil.establishConnection()){
			
			PreparedStatement ps = conn.prepareStatement("DELETE FROM courses WHERE course_id = ?");
			ps.setString(1, cid);
			
			int res = ps.executeUpdate();
			
			if(res>0) message = "Course Removed Successfully";
			else throw new FacultyException("Course ID Error! Not Found");
			
			
		} catch (SQLException e) {
			throw new FacultyException(e.getMessage());
		}
		
		return message;
	}

	@Override
	public List<Student> studentList() throws FacultyException {
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
			
			if(flag) throw new FacultyException("No Student Data Found !");
			
			
		}catch (SQLException e) {
			// TODO: handle exception
			throw new FacultyException(e.getMessage());
		}
		
		return students;
	}

	@Override
	public List<Course> courseList() throws FacultyException {
		// TODO Auto-generated method stub
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
			
			if(flag) throw new FacultyException("No Course Found !");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new FacultyException(e.getMessage());
		}
		
		
		
		
		return courses;
	
	
	}

	@Override
	public String studentgrade(String roll) throws FacultyException{

		String message = null;

		try (Connection con = DBUtil.establishConnection()) {

			PreparedStatement ps = con.prepareStatement("SELECT * FROM courseregister WHERE student_id = ? ");
			ps.setString(1, roll);
			ResultSet rs = ps.executeQuery();
			
			boolean flg = true;
			String courseid = "";
			String grade = "";
			
			while (rs.next()){
				courseid = rs.getString("course_id");
				grade = rs.getString("grade");
				flg = false;

				System.out.println("course id : "+courseid+"\tgrade : "+grade);
			}
			if (flg) throw new FacultyException("No Registered Course Found !");

		} catch (SQLException e) {
			message = e.getMessage();
			throw new FacultyException(message);
		}

		return message;

	}

	@Override
	public String enrolReq() throws FacultyException {
		String message = null;
		Scanner sc = new Scanner(System.in);
		System.out.println("You Must Verify, Enter your id : ");
		String faculty_id = sc.nextLine();

		try (Connection con = DBUtil.establishConnection()) {

			PreparedStatement ps = con.prepareStatement("SELECT * FROM courseregister WHERE status = ? AND course_id IN (SELECT course_id FROM teaches WHERE faculty_id = ?)");
			ps.setString(1, "Pending");
			ps.setString(2, faculty_id);
			ResultSet rs = ps.executeQuery();
			
			boolean flg = true;
			String courseid = "";
			String sid = "";
			
			while (rs.next()){
				courseid = rs.getString("course_id");
				sid = rs.getString("student_id");
				flg = false;

				System.out.println("Student Id : "+sid+"\tcourse id : "+courseid+"\n");
			}
			if (flg) {
				throw new FacultyException("No Pending Course Found !");

			}else{
				System.out.println("Want to approve or not --> y/n");
				String check = sc.nextLine();
				if (check.equals("y") || check.equals("Y")){
					System.out.println("Enter Student Id");
					String studentid = sc.nextLine();
					System.out.println("Enter Approved or Rejected");
					String status = sc.nextLine();

					PreparedStatement ps1 = con.prepareStatement("UPDATE courseregister SET status = ? WHERE student_id = ? AND course_id = ?");
					ps1.setString(1, status);
					ps1.setString(2, studentid);
					ps1.setString(3, courseid);
					
					int rs1 = ps1.executeUpdate();
					String m = "Request "+status+" Successfully!";
					if (rs1>0) throw new FacultyException(m);

				}
			}

		} catch (SQLException e) {
			message = e.getMessage();
			throw new FacultyException(message);
		}

		return message;
	}

	@Override
	public String updateGrade(String path) throws FacultyException {

		String message = null;
		BufferedReader reader = null;
		
		try(Connection conn = DBUtil.establishConnection()){
			
			PreparedStatement ps =  conn.prepareStatement("UPDATE courseregister SET grade = ? WHERE student_id = ? AND course_id = ?");
			

			try {
				reader = new BufferedReader(new FileReader(path));

				String line;

				while ((line = reader.readLine()) != null) {

					String[] data = line.split(",");
					String column1 = data[0];
					String column2 = data[1];
					String column3 = data[2];

					ps.setString(1, column1);
					ps.setString(2, column2);
					ps.setString(3, column3);

					int res = ps.executeUpdate();

					if(res > 0) {
						message = "\nGrades Updated Successfully!\n";
					}else {
						throw new FacultyException("Error Error Updating Grades! ");
					}
				}
			
			
			} catch (Exception e) {
				throw new FacultyException(e.getMessage());
			}finally{
				try {
					if (reader != null) {
						reader.close();
					}
				} catch (Exception e) {
					throw new FacultyException(e.getMessage());
				}

			}
			
		} catch (SQLException e) {
			throw new FacultyException(e.getMessage());
		}
		
		return message;
	}

}
