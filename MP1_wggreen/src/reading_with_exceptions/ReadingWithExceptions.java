package reading_with_exceptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ReadingWithExceptions {
	public void process(String inputFilename) {
		try {
			File inputFile = new File(inputFilename);
			Scanner fileScanner = new Scanner(inputFile);

			String outputFilename = "";
			int number_to_read = 0;

			String firstLine = fileScanner.nextLine();
			Scanner lineScanner = new Scanner(firstLine);

			outputFilename = lineScanner.next();
			if (lineScanner.hasNextInt()) {
				number_to_read = lineScanner.nextInt();
			}

			lineScanner.close();

			File outputFile = new File(outputFilename);
			PrintWriter writer = new PrintWriter(outputFile);

			int count = 0;
			int numbersPerLine = 10;
			int number = 0;

			if (number_to_read > 0) {
				while (count < number_to_read && fileScanner.hasNext()) {
					if (fileScanner.hasNextInt()) {
						number = fileScanner.nextInt();

						writer.print(number + " ");

						count++;
						if (count % numbersPerLine == 0) {
							writer.println();
						}
					} else {
						String badInput = fileScanner.next();
						System.out.println(badInput + " is not a number. Moving to next number.\n");
						count++;
					}
				}
			} else {
				while (fileScanner.hasNext()) {
					if (fileScanner.hasNextInt()) {
						number = fileScanner.nextInt();

						writer.print(number + " ");

						count++;
						if (count % numbersPerLine == 0) {
							writer.println();
						}
					} else {
						String badInput = fileScanner.next();
						System.out.println(badInput + " is not a number. Moving to next number.\n");
						count++;
					}
				}
			}

			fileScanner.close();
			writer.close();

			System.out.println(outputFilename + " created with the following output:");

			Scanner outputFileScanner = new Scanner(outputFile);
			while (outputFileScanner.hasNextLine()) {
				System.out.println(outputFileScanner.nextLine());
			}

			outputFileScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("\nFile not found: " + inputFilename + "\n");
		}
	}

	public static void main(String[] args) {
		ReadingWithExceptions reader = new ReadingWithExceptions();
		for (String string : args) {
			reader.process(string);
		}
	}
}
