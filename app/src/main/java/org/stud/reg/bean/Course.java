package org.stud.reg.bean;

public class Course {
	
	private String cid;
	private String cname;
	private String department;
	private int lectures;
	private int tutorials;
	private int practicals;
	private int selfStudy;
	private int credit;
	private int semester;
	private String faculty_id;
	
	public Course() {}
	
	

	public Course(String cid, String cname, String department, int l, int t, int p, int s, int c,int sem, String faculty) {
		super();
		this.cid = cid;
		this.cname = cname;
		this.lectures = l;
		this.tutorials = t;
		this.practicals = p;
		this.selfStudy = s;
		this.credit = c;
		this.semester = sem;
		this.faculty_id = faculty;
	}



	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getFaculty() {
		return faculty_id;
	}

	public void setFaculty(String cname) {
		this.faculty_id = cname;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String dep) {
		this.department = dep;
	}

	public int getLectures() {
		return lectures;
	}

	public void setLectures(int l) {
		this.lectures = l;
	}

	public int getTutorials() {
		return tutorials;
	}

	public void setTutorials(int t) {
		this.tutorials = t;
	}

	public int getPracticals() {
		return practicals;
	}

	public void setPracticals(int p) {
		this.practicals = p;
	}

	public int getSelfstudy() {
		return selfStudy;
	}

	public void setSelfstudy(int s) {
		this.selfStudy = s;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int c) {
		this.credit = c;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int c) {
		this.semester = c;
	}

	@Override
	public String toString() {
		return "Course id : "+cid+"\nCourse name : "+cname+"\nDepartment : "+this.department+"\nlectures : "+this.lectures+"\ntutorials : "+this.tutorials+"\npracticals : "+this.practicals+"\nselfStudy : "+this.selfStudy+"\ncredit : "+this.credit+"\nSemester : "+this.semester;
	}
	
	

}
