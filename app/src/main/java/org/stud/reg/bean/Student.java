package org.stud.reg.bean;

public class Student {

	private String roll;
	private String name;
	private String password;
	private double grade;
	private int semester;
	public Student() {};
	
	
	public Student(String roll, String name, String pass) {
		super();
		this.roll = roll;
		this.name = name;
		this.password = pass;
		this.grade = 8.24;
		this.semester = 1;
	}

	public String getRoll() {
		return roll;
	}
	public void setRoll(String roll) {
		this.roll = roll;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}

	public int getSemester() {
		return semester;
	}
	public void setSemester(int sem) {
		this.semester = sem;
	}
	
	@Override
	public String toString() {
		return "Student name : "+name+"\tStudent roll : "+roll+"\tCGPA : "+grade;
	}
	
	
	
}
