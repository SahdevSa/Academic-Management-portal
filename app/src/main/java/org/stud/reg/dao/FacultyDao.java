package org.stud.reg.dao;

import org.stud.reg.bean.Faculty;
import org.stud.reg.bean.Course;
import org.stud.reg.exception.FacultyException;
// import java.util.ArrayList;
import org.stud.reg.bean.Student;
import java.util.List;


public interface FacultyDao {
    
    String updateGrade(String path) throws FacultyException;

    Faculty login(String username, String password) throws FacultyException;

	String addCourse(Course course) throws FacultyException;

    String deleteCourse(String cid) throws FacultyException;

    List<Student> studentList() throws FacultyException ;

    List<Course> courseList() throws FacultyException;

    String studentgrade(String roll) throws FacultyException;

    String enrolReq() throws FacultyException;
}
