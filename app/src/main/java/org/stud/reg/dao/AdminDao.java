package org.stud.reg.dao;

import java.util.List;
import java.io.FileWriter;

import org.stud.reg.bean.Admin;
import org.stud.reg.bean.Course;
import org.stud.reg.bean.Student;
import org.stud.reg.exception.AdminException;


public interface AdminDao {
	
	String addCourse(Course course) throws AdminException;
	
	String deleteCourse(String cid) throws AdminException;
	
	String addStudentToBatch(String roll, String bid, String name, String pass, double crReg, double cr_limit) throws AdminException;

	Admin login(String username, String password) throws AdminException;
	
	List<Student> studentList() throws AdminException;
	
	List<Course> courseList() throws AdminException;

	String studentgrade(String roll, FileWriter writer ) throws AdminException;
	
}
