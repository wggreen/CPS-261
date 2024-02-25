package comparable;

import java.util.Random;

public class Student implements Comparable<Student> {
	private int studentID;
	private String name;
	private double gpa;

	private static int maxStudentID = 0;

	public Student(String name, double gpa) {
		this.name = name;
		this.gpa = gpa;
		Random random = new Random();
		this.studentID = random.nextInt(100) + 1;

		if (studentID > maxStudentID) {
			maxStudentID = studentID;
		}
	}

	public int getStudentID() {
		return studentID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getGpa() {
		return gpa;
	}

	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	@Override
	public String toString() {
		return "Student [studentID=" + studentID + ", name=" + name + ", gpa=" + gpa + "]";
	}

//	public int compareTo(Student student) {
//		if (this.studentID < student.studentID)
//			return -1;
//		if (this.studentID > student.studentID)
//			return 1;
//		return 0;
//	}

	public int compareTo(Student otherStudent) {
		return this.name.compareTo(otherStudent.name); // using Stirng's compareTo method
	}

}
