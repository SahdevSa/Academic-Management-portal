package org.stud.reg.UseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.stud.reg.bean.CoursesDTO;
import org.stud.reg.bean.Student;
import org.stud.reg.dao.StudentDao;
import org.stud.reg.dao.StudentDaoImpl;
import org.stud.reg.exception.StudentException;

public class StudentMenu {

	public String login() {
		
		String check = "";
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter Your roll number");
		String username = sc.next();
		
		System.out.println("Enter Your Password (case sensitive)");
		String password = sc.next();
		
		StudentDao sd = new StudentDaoImpl();
		
		try {
			
			Student student = sd.login(username, password);
			
			System.out.println("Welcome ! Your Credentials are : ");
			System.out.println("----------------------------");
			System.out.println(student);
			
			check = student.getRoll();
			
		} catch (StudentException e) {
			// TODO: handle exception
			
			System.out.println(e.getMessage());
		}
		return check;
		
	}
	
	public void showAllCourse() {
		StudentDao sd = new StudentDaoImpl();
		
		List<CoursesDTO> courses = new ArrayList<>();
		
		try {
			courses = sd.showAllCourseDetails();
			
			if(courses.size() == 0) {
				System.out.println("No course to Show.");
			}else {
				for(CoursesDTO c: courses) {
					System.out.println(c);
					System.out.println("------------------------");
				}
			}
		} catch (StudentException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
	public void registerCourse(String rollNumber) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Course Detail \n");
		
		// System.out.println("Enter your roll number : ");
		System.out.println("Enter the Course id: ");
		String course_id = sc.nextLine();
		
		course_id = course_id.toLowerCase();
			
		StudentDao sd = new StudentDaoImpl();
		try {
			System.out.println( sd.registerCourse(rollNumber, course_id));
		} catch (StudentException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public void StudentRecord(String roll) {
		Scanner sc = new Scanner(System.in);
		
		StudentDao sd = new StudentDaoImpl();

		System.out.println("Enter Semester number : ");
		String op = sc.nextLine();
		int semester = Integer.parseInt(op);

		try {
			sd.studentrecord(roll, semester);
		} catch (StudentException e) {
			System.out.println(e.getMessage());
		}

	}

	private double getCGPA(String rollnum, int semester){

		double cgpa = 7.24;
		return cgpa;
	}
}
