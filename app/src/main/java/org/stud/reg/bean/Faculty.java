package org.stud.reg.bean;

public class Faculty {
    private String fid;
	private String fname;
	private String username;
	private String password;
	
	public Faculty() {}
	
	public Faculty(String fid, String fname, String username, String password) {
		super();
		this.fid = fid;
		this.fname = fname;
		this.username = username;
		this.password = password;
	}

	public String getFid() {
		return fid;
	}

	public void setAid(String aid) {
		this.fid = aid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Faculty ID : "+this.fid+"\nFculty Name : "+this.fname+"\nFaculty Username : "+this.username+"\nFaculty Password : "+this.password;
	}
	
}
