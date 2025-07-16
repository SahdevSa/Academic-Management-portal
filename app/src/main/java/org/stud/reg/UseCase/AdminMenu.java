package org.stud.reg.UseCase;

import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

import org.stud.reg.bean.Admin;
import org.stud.reg.bean.Course;
import org.stud.reg.bean.Student;
import org.stud.reg.dao.AdminDao;
import org.stud.reg.dao.AdminDaoImpl;
import org.stud.reg.exception.AdminException;


public class AdminMenu {
	
	public void addCourse() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter Course Details ");
		System.out.println("---------------------");
		System.out.println("Course id : ");
		String course_id = sc.nextLine();
		
		System.out.println("Course Name : ");
		String name = sc.nextLine();
		System.out.println("Department : ");
		String department = sc.nextLine();
		System.out.println("Number of Lecture : ");
		String op = sc.nextLine();
		int lectures = Integer.parseInt(op);	
		System.out.println("Number of Tutorials : ");
		op = sc.nextLine();
		int tutorials = Integer.parseInt(op);
		
		System.out.println("Number of Practicals : ");
		op = sc.nextLine();
		int practicals = Integer.parseInt(op);

		System.out.println("Self Study Hours : ");
		op = sc.nextLine();
		int selfStudy = Integer.parseInt(op);

		System.out.println("Credits : ");
		op = sc.nextLine();
		int credit = Integer.parseInt(op);

		System.out.println("Semester : ");
		op = sc.nextLine();
		int semester = Integer.parseInt(op);
		System.out.println("Faculty id : ");
		String faculty_id = sc.nextLine();
		

		Course course = new Course();
		course.setCid(course_id);
		course.setCname(name);
		course.setDepartment(department);
		course.setLectures(lectures);
		course.setTutorials(tutorials);
		course.setPracticals(practicals);
		course.setSelfstudy(selfStudy);
		course.setCredit(credit);
		course.setCredit(semester);
		course.setFaculty(faculty_id);

		
		AdminDao ad = new AdminDaoImpl();
		
		try {
			System.out.println(ad.addCourse(course));
		} catch (AdminException e) {
			System.out.println(e.getMessage());
		}
	}

	public void deleteCourse() {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter Course ID : ");
		String cid="";
		
		try {
			cid = sc.nextLine();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Try Again !");
			deleteCourse();
		}
		
		AdminDao ad = new AdminDaoImpl();

		System.out.println("Kaam ka hai : "+cid);
		try {
			
			System.out.println(ad.deleteCourse(cid));
			
		}catch(AdminException ae) {
			System.out.println(ae.getMessage());
		}
	}

	public void addStudentToBatch() {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Add Student To Batch ");
		System.out.println("----------------------");
		
		System.out.println("Enter the Student Roll Number : ");
		String roll = sc.nextLine();

		System.out.println("Enter the Student Name : ");
		String name = sc.nextLine();

		System.out.println("Enter the Student password : ");
		String pass = sc.nextLine();

		System.out.println("Enter the Student batch id : ");
		String bid = sc.nextLine();

		double crReg = 0;

		System.out.println("Enter the Student Credit Limit : ");
		double crlimit = sc.nextDouble();

		
		AdminDao ad = new AdminDaoImpl();
		
		try {
			
			System.out.println(ad.addStudentToBatch(roll, bid, name, pass, crReg, crlimit));
			
		} catch (AdminException e) {
			System.out.println(e.getMessage());
		} 
	}

	public void studentList() {
		
		AdminDao ad = new AdminDaoImpl();
		
		try {
			List<Student> students = ad.studentList();
			System.out.println("size="+students.size());
			for(Student s: students) {
				FileWriter writer = null;
				System.out.println(s);
				// try {
				// 	// ad.studentgrade(s.getRoll(), writer);
				// } catch (AdminException e) {
				// 	System.out.println(e.getMessage());
				// }
				System.out.println("------------------------");
			}
			
			if(students.size() == 0) System.out.println("No Student to Show");
		}catch(AdminException e) {
			System.out.println(e.getMessage());
		}
		
		
		
	}
	
	public int login() {
		
		int check = 0;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter Username : ");
		String userName = sc.next();
		
		System.out.println("Enter Password : ");
		String password = sc.next();
		
		AdminDao ad = new AdminDaoImpl();
		
		try {
			
			Admin admin = ad.login(userName, password);
			
			System.out.println(admin);
			
			check = 1;
			
		}catch(AdminException e){
			
			System.out.println(e.getMessage());
		}
		
		return check;
		
	}
	
	public void showCourse() {
		
		AdminDao ad = new AdminDaoImpl();
		
		try {
			
			List<Course> courses =  ad.courseList();
			for(Course c: courses) {
				System.out.println(c);
				System.out.println("-----------------------");
			}
		}catch (AdminException e) {
			// TODO: handle exception
			
			System.out.println(e.getMessage());
		}
		
	}
	
	public void GenerateTranscript() {

		AdminDao ad = new AdminDaoImpl();
		FileWriter writer = null;

		try {
			
			List<Student> students = ad.studentList();

			for(Student s: students) {
				try {
					try {
						writer = new FileWriter(s.getRoll()+"Transcript.txt", true);
						ad.studentgrade(s.getRoll(), writer);
					} catch (Exception e) {
						throw new AdminException(e.getMessage());
					}finally {
						try {
						  if (writer != null) {
							writer.close();
						  }
						} catch (IOException e) {
						  System.out.println("Error: " + e.getMessage());
						}
					  }
					
				} catch (AdminException e) {
					System.out.println(e.getMessage());
				}
				System.out.println("------------------------");
			}
			
			if(students.size() == 0) System.out.println("No Student to Show");
		}catch(AdminException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
