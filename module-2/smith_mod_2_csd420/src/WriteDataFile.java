
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Johnathan Smith
 * CSD420
 * Module 2 Assignment
 *
 * This program creates two arrays:
 * 1. An array of five random integers
 * 2. An array of five random double values
 *
 * The data is written to a file named "Smith datafile.dat".
 * If the file already exists, the new data is appended.
 */
public class WriteDataFile {
    private static final String FILE_NAME = "Smith datafile.dat";

    public static void main(String[] args) {
        int[] integers = new int[5];
        double[] doubles = new double[5];
        Random random = new Random();

        for (int i = 0; i < integers.length; i++) {
            integers[i] = random.nextInt(100);
            doubles[i] = random.nextDouble() * 100;
        }

        File file = new File(FILE_NAME);

        try (PrintWriter output = new PrintWriter(new FileOutputStream(file, true))) {
            output.println("--- New Data Set ---");
            output.print("Integers: ");
            for (int value : integers) {
                output.print(value + " ");
            }
            output.println();

            output.print("Doubles: ");
            for (double value : doubles) {
                output.printf("%.2f ", value);
            }
            output.println();
            output.println();

            // Display to console for testing
            System.out.println("Data was written to " + FILE_NAME);
            System.out.print("Random integers: ");
            for (int value : integers) {
                System.out.print(value + " ");
            }
            System.out.println();

            System.out.print("Random doubles: ");
            for (double value : doubles) {
                System.out.printf("%.2f ", value);
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
