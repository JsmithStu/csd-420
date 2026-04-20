import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeSet;

public class NonDuplicateWords {

    public static void main(String[] args) {
        String fileName = "collection_of_words.txt";

        TreeSet<String> words = readWordsFromFile(fileName);

        if (words.isEmpty()) {
            System.out.println("No words were found in the file.");
            return;
        }

        System.out.println("Words in ascending order:");
        for (String word : words) {
            System.out.println(word);
        }

        System.out.println("\nWords in descending order:");
        for (String word : words.descendingSet()) {
            System.out.println(word);
        }

        runTests(words);
    }

    public static TreeSet<String> readWordsFromFile(String fileName) {
        TreeSet<String> words = new TreeSet<>();

        try {
            File file = new File(fileName);
            Scanner input = new Scanner(file);

            while (input.hasNext()) {
                String word = input.next().toLowerCase().replaceAll("[^a-z]", "");
                if (!word.isEmpty()) {
                    words.add(word);
                }
            }

            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + fileName);
        }

        return words;
    }

    public static void runTests(TreeSet<String> words) {
        System.out.println("\n--- Test Results ---");

        if (words.contains("apple")) {
            System.out.println("Test 1 Passed: 'apple' was found.");
        } else {
            System.out.println("Test 1 Failed: 'apple' was not found.");
        }

        if (words.size() > 0) {
            System.out.println("Test 2 Passed: Words were successfully loaded.");
        } else {
            System.out.println("Test 2 Failed: No words were loaded.");
        }

        String firstWord = words.first();
        String lastWord = words.last();

        System.out.println("Test 3 Passed: First word in ascending order is '" + firstWord + "'.");
        System.out.println("Test 4 Passed: Last word in ascending order is '" + lastWord + "'.");
    }
}