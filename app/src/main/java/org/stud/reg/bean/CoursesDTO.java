package org.stud.reg.bean;

public class CoursesDTO {
	
	private String courseid;
	private String title;
	private String department;
	
	public CoursesDTO() {}

	public CoursesDTO(String courseid,String title, String department) {
		super();
		this.courseid = courseid;
		this.title = title;
		this.department = department;
	}

	public String getCid() {
		return courseid;
	}

	public void setCid(String cid) {
		this.courseid = cid;
	}

	public String getCname() {
		return title;
	}

	public void setCname(String cname) {
		this.title = cname;
	}

	public String getSeats() {
		return department;
	}

	public void setSeats(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Course ID : "+this.courseid+"\nCourse Name : "+this.title+"\nDepartment : "+this.department;
	};
	
	
	
	

}
