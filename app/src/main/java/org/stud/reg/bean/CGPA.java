package org.stud.reg.bean;

public class CGPA {
    private double earnedCredit;
    private double earnedPoints;
    private double cgpa;

    public CGPA(double ec, double ep, double cg){
        this.earnedCredit = ec;
        this.earnedPoints = ep;
        this.cgpa = cg;
    }

    public double getCgpa() {
        return cgpa;
    }
    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public double getEarnedCredit() {
        return earnedCredit;
    }
    public void setEarnedCredit(double earnedCredit) {
        this.earnedCredit = earnedCredit;
    }

    public double getEarnedPoints() {
        return earnedPoints;
    }
    public void setEarnedPoints(double earnedPoints) {
        this.earnedPoints = earnedPoints;
    }

    @Override
	public String toString() {
		return "earnedCredit : "+earnedCredit+"\tearnedPoints : "+earnedPoints+"\tCGPA : "+cgpa;
	}
    
}
