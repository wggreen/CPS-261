package twod_array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TwoDArray {

	public static void main(String[] args) {
		int[][] number_array = { { 34, 89 }, { 56, 3 }, { 27, 61 }, { 45, 8 }, { 45, 89 } };

		List<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 2; j++) {
				list.add(number_array[i][j]);
			}
		}

		Collections.sort(list);

		list.stream().distinct().forEach(i -> System.out.print(i + " "));
		System.out.println();

	}

}
