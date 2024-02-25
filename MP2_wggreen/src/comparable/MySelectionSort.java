package comparable;

public class MySelectionSort {

	private static <T> void swap(T[] a, int i, int j) {
		if (i != j) {
			T temp = a[i];
			a[i] = a[j];
			a[j] = temp;
		}
	}

	public static <T extends Comparable<T>> void sort(T[] a) {
		for (int i = 0; i < a.length - 1; i++) {
			int smallest = i;
			for (int j = i + 1; j < a.length; j++) {
				if (a[j].compareTo(a[smallest]) <= 0) {
					smallest = j;
				}
			}

			swap(a, i, smallest);
		}
	}

	public static void main(String[] args) {
		Student[] students = { new Student("Jing", 4.0), new Student("Bob", 3.0), new Student("Mike", 1.0),
				new Student("Sue", 2.0), new Student("Jane", 3.5), };
		System.out.println("The students unsorted are:");
		
		for(Student student : students) {
			System.out.println(student);
		}
		
		sort(students);
		
		System.out.println("\nThe students sorted are:");
		
		for(Student student : students) {
			System.out.println(student);
		}
				
	}

}
