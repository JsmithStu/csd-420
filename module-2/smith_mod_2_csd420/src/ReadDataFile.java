
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Johnathan Smith
 * CSD420
 * Module 2 Assignment
 *
 * This program reads the contents of "Smith datafile.dat"
 * and displays all stored data to the console.
 */
public class ReadDataFile {
    private static final String FILE_NAME = "Smith datafile.dat";

    public static void main(String[] args) {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println(FILE_NAME + " was not found.");
            System.out.println("Run WriteDataFile first so the file can be created.");
            return;
        }

        try (Scanner input = new Scanner(file)) {
            System.out.println("Contents of " + FILE_NAME + ":
");
            while (input.hasNextLine()) {
                System.out.println(input.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
