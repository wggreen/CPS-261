package spell_checker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class SpellCheck {

	// We could hava also used TreeSet for the dictionary
	private HashSet<String> dictionary = new HashSet<String>();
	private TreeSet<String> miss_spelled_words = new TreeSet<String>();

	public SpellCheck() throws FileNotFoundException {

		Scanner input = null;

		String word;

		// Add all of the words from "dictionary.txt"
		// to the dictionary HashSet
		try {
			input = new Scanner(new File("dictionary.txt"));

			while (input.hasNextLine()) {
				word = input.nextLine();
				dictionary.add(word);
			}

		} catch (IOException e) {
			e.printStackTrace();
			;
		} finally {
			if (input != null) {
				input.close();
			}
		}

	}

	public void checkSpelling(String fileName) throws FileNotFoundException {
		System.out.println("======== Spell checking " + fileName + " =========");

		// Clear miss_spelled_words
		miss_spelled_words.clear();

		Scanner input = null;

		String line;
		String word;

		// Read in each line from "fileName"
		try {
			input = new Scanner(new File(fileName));
			
			Scanner kbInput = new Scanner(System.in);

			int lineNumber = 1;

			while (input.hasNextLine()) {
				line = input.nextLine();

				// For each line, break the line into words
				// using the following StringTokenizer
				StringTokenizer st = new StringTokenizer(line, " \t,.;:-%'\"");

				boolean linePrinted = false;

				while (st.hasMoreTokens()) {
					word = st.nextToken();

					// lower case each word obtained from the StringTokenizer
					word = word.toLowerCase();

					// skip word if the first character is not a letter
					// Skip over word if it can be found in either dictionary,
					// or miss_spelled_words
					if (Character.isLetter(word.charAt(0)) && !dictionary.contains(word)
							&& !miss_spelled_words.contains(word)) {
						if (word.endsWith("s")) {
							String singular = word.substring(0, word.length() - 1);

							// If word ends with 's',
							// then try the singular version of the word
							// in the dictionary and miss_spelled_words
							// skip if found
							if (!dictionary.contains(singular) && !miss_spelled_words.contains(singular)) {

								// Print line containing miss-spelled word
								// make sure you only print it once if multiple miss-spelled words
								// are found on this line
								if (!linePrinted) {
									System.out.println(lineNumber + ": " + line);
								}
								System.out.println(word + " not in dictionary.  Add to dictionary? (y/n) ");
								linePrinted = true;

								// Ask the user if he wants the word added to the dictionary
								// or the miss-spelled words
								// and add word as specified by the user
								String response = kbInput.nextLine();

								if (response.equalsIgnoreCase("y")) {
									dictionary.add(word);
								} else if (response.equalsIgnoreCase("n")) {
									miss_spelled_words.add(word);
								}
							}
						} else {

							// Print line containing miss-spelled word
							// make sure you only print it once if multiple miss-spelled words
							// are found on this line
							if (!linePrinted) {
								System.out.println(lineNumber + ": " + line);
							}
							System.out.println(word + " not in dictionary.  Add to dictionary? (y/n) ");
							linePrinted = true;

							// Ask the user if he wants the word added to the dictionary
							// or the miss-spelled words
							// and add word as specified by the user
							String response = kbInput.nextLine();

							if (response.equalsIgnoreCase("y")) {
								dictionary.add(word);
							} else if (response.equalsIgnoreCase("n")) {
								miss_spelled_words.add(word);
							}
						}

					}
				}
				lineNumber++;
			}
			
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
			;
		}

	}

	public void dump_miss_spelled_words() {
		// Print out the miss-spelled words
		System.out.println(" ****** Miss spelled words *******");
		for (String word : miss_spelled_words) {
		    System.out.println(word);
		}
	}

	public static void main(String[] args) {

		try {
			SpellCheck spellCheck = new SpellCheck();

			for (int i = 0; i < args.length; i++) {
				spellCheck.checkSpelling(args[i]);
				spellCheck.dump_miss_spelled_words();
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}

	}
}