package org.stud.reg.dao;

import java.util.List;

import org.stud.reg.bean.Course;
import org.stud.reg.bean.CoursesDTO;
import org.stud.reg.bean.Student;
import org.stud.reg.exception.StudentException;

public interface StudentDao {
	
	String registerCourse(String roll, String courseid) throws StudentException;
	
	List<CoursesDTO> showAllCourseDetails() throws StudentException;
	
	Student login(String username, String pass) throws StudentException;
	
	double CalculateCGPA(String rollnum, int semester) throws StudentException;
	
	String studentrecord(String roll, int semester) throws StudentException;
}
