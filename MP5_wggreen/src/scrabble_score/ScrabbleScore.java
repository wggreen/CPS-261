package scrabble_score;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScrabbleScore {

	public static void main(String[] args) {

		List<String> words = Arrays.asList("Java", "program", "list", "string", "unix", "hours", "syntax", "error");

		Map<String, Integer> wordScores = words.stream()
				.collect(Collectors.toMap(word -> word, ScrabbleScore::calculateScore, (a, b) -> a));

		List<String> topThreeWords = wordScores.entrySet().stream()
				.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())).limit(3).map(Map.Entry::getKey)
				.collect(Collectors.toList());
		
        double averageScore = wordScores.values().stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
		
        System.out.println("Top three words are:");
        topThreeWords.forEach(word -> System.out.println(word + ":" + wordScores.get(word)));
        
        System.out.println("Average scrabble value is: " + averageScore);
        
        List<String> wordsBelowAverage = wordScores.entrySet().stream()
                .filter(entry -> entry.getValue() < averageScore)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        
        List<String> wordsAboveAverage = wordScores.entrySet().stream()
                .filter(entry -> entry.getValue() > averageScore)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        
        System.out.println("Words below average: " + wordsBelowAverage);
        System.out.println("Words above average: " + wordsAboveAverage);


	}

	public static int calculateScore(String word) {
		Map<Character, Integer> letterValues = new HashMap();

		letterValues.put('a', 1);
		letterValues.put('b', 3);
		letterValues.put('c', 3);
		letterValues.put('d', 2);
		letterValues.put('e', 1);
		letterValues.put('f', 4);
		letterValues.put('g', 2);
		letterValues.put('h', 4);
		letterValues.put('i', 1);
		letterValues.put('j', 8);
		letterValues.put('k', 5);
		letterValues.put('l', 1);
		letterValues.put('m', 3);
		letterValues.put('n', 1);
		letterValues.put('o', 1);
		letterValues.put('p', 3);
		letterValues.put('q', 10);
		letterValues.put('r', 1);	
		letterValues.put('s', 1);
		letterValues.put('t', 1);
		letterValues.put('u', 1);
		letterValues.put('v', 8);
		letterValues.put('w', 4);
		letterValues.put('x', 8);
		letterValues.put('y', 4);
		letterValues.put('z', 10);

		int score = 0;
		for (char c : word.toLowerCase().toCharArray()) {
			score += letterValues.getOrDefault(c, 0);
		}
		return score;
	}

}
