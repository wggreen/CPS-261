package single_digits;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class SingleDigits {

	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<>();
		Random r = new Random();
		for (int i = 0; i < 100; i++) {
			list.add(r.nextInt(10));
		}

		Map<Integer, Long> countMap = list.stream().collect(Collectors.groupingBy(i -> i, Collectors.counting()));
		
		for (int i = 0; i <= 9; i++) {
			Long count = countMap.getOrDefault(i, 0L);
			System.out.println(i + ": " + count);
		}

	}

}
