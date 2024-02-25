package IO;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

class Person implements Serializable {
	String name;
	int age;

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}

}

public class MP1PersonIO {
	String fileName;
	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	static Scanner kbInput = new Scanner(System.in);

	public MP1PersonIO(String fileName) {
		this.fileName = fileName;

		try {
			this.oos = new ObjectOutputStream(new FileOutputStream(fileName));
			this.ois = new ObjectInputStream(new FileInputStream(fileName));
		} catch (EOFException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void add() {
		System.out.println("Please enter the person's name:");
		String name = kbInput.nextLine();

		System.out.println("Please enter the person's age:");
		int age = Integer.parseInt(kbInput.nextLine());

		Person addedPerson = new Person(name, age);

		try {
			oos.writeObject(addedPerson);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void display() {
		try {
			while (true) {
				Person person = (Person) ois.readObject();
				System.out.println(person);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (EOFException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		MP1PersonIO mp1 = new MP1PersonIO("person.ser");
		try {
			int option = -1;
			while (option != 0) {
				System.out.println("Please choose an option:");
				System.out.println("0: quit");
				System.out.println("1: add");
				System.out.println("2: display");
				option = kbInput.nextInt();
				kbInput.nextLine();
				switch (option) {
				case 0:
					System.out.println("Bye");
					break;
				case 1:
					mp1.add();
					break;
				case 2:
					mp1.display();
					break;
				}

			}
		} finally {
			try {
				mp1.ois.close();
				mp1.oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}