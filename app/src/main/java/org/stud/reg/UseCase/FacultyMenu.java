package org.stud.reg.UseCase;

import java.util.Scanner;
import java.util.List;
import org.stud.reg.bean.Faculty;
import org.stud.reg.dao.FacultyDao;
import org.stud.reg.dao.FacultyDaoImpl;
import org.stud.reg.exception.FacultyException;
import org.stud.reg.bean.Course;
import org.stud.reg.bean.CourseDTO;
import org.stud.reg.bean.Student;


public class FacultyMenu {

    public int login() {
		
		int check = 0;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter Username : ");
		String userName = sc.next();
		
		System.out.println("Enter Password : ");
		String password = sc.next();

        FacultyDao ad = new FacultyDaoImpl();
		
		try {
			
			Faculty faculty = ad.login(userName, password);
			
			// System.out.println(Faculty);
			
			check = 1;
			
		}catch(FacultyException e){
			
			System.out.println(e.getMessage());
		}
		
		return check;
		
	}

	public void studentList() {
		
		FacultyDao ad = new FacultyDaoImpl();
		
		try {
			List<Student> students = ad.studentList();
			
			for(Student s: students) {
				try {
					System.out.println(s);
					ad.studentgrade(s.getRoll());
				} catch (FacultyException e) {
					System.out.println(e.getMessage());
				}
				System.out.println("------------------------");
			}
			
			if(students.size() == 0) System.out.println("No Student to Show");
		}catch(FacultyException e) {
			System.out.println(e.getMessage());
		}
		
		
		
	}

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
		
		// System.out.println("---->Kaam Ka hai: "+course_id+ " "+name+ " "+department+ " "+lectures+ " "+tutorials+ " "+practicals+ " "+selfStudy+ " "+credit);

		Course course = new Course();
		course.setCid(course_id);
		course.setCname(name);
		course.setDepartment(department);
		course.setLectures(lectures);
		course.setTutorials(tutorials);
		course.setPracticals(practicals);
		course.setSelfstudy(selfStudy);
		course.setCredit(credit);

		
		FacultyDao ad = new FacultyDaoImpl();
		
		try {
			System.out.println(ad.addCourse(course));
		} catch (FacultyException e) {
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
		
		FacultyDao ad = new FacultyDaoImpl();

		System.out.println("Kaam ka hai : "+cid);
		try {
			
			System.out.println(ad.deleteCourse(cid));
			
		}catch(FacultyException ae) {
			System.out.println(ae.getMessage());
		}
	}

	public void showCourse() {
		
		FacultyDao ad = new FacultyDaoImpl();
		
		try {
			
			List<Course> courses =  ad.courseList();
			for(Course c: courses) {
				System.out.println(c);
				System.out.println("-----------------------");
			}
		}catch (FacultyException e) {
			// TODO: handle exception
			
			System.out.println(e.getMessage());
		}
		
	}

	public void UpdateGrades() {
		
		System.out.println("Enter the CSV file path: ");
		Scanner sc = new Scanner(System.in);

		String Path = sc.nextLine();

        FacultyDao ad = new FacultyDaoImpl();
		
		try {
			
			String message = ad.updateGrade(Path);
			throw new FacultyException(message);
			
		}catch(FacultyException e){
			
			System.out.println(e.getMessage());
		}

	}

	public void enrollmentReq() {
		FacultyDao ad = new FacultyDaoImpl();
		System.out.println("Pending Courses : \n");
		try {
			ad.enrolReq();
			System.out.println("------------------------");
		}catch(FacultyException e) {
			System.out.println(e.getMessage());
		}
	}

}
