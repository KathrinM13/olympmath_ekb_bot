package main.java.models;

public class Theme {
	private String name;
	private String theory;
	private int grade;

	public Theme(String name, String theory, int grade) {
		this.name = name;
		this.theory = theory;
		this.setGrade(grade);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTheory() {
		return theory;
	}
	public void setTheory(String theory) {
		this.theory = theory;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
}
