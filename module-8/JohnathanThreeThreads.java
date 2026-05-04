/**
 * Johnathan Smith
 * Module 8 Programming Assignment
 * CSD420
 *
 * This JavaFX program uses three threads to generate random letters,
 * random digits, and random special characters. Each thread generates
 * at least 10,000 characters, and the characters are displayed in a
 * TextArea as they are generated.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class JohnathanThreeThreads extends Application {

    private static final int REQUIRED_COUNT = 10000;
    private static final char[] SYMBOLS = {'!', '@', '#', '$', '%', '&', '*', '^'};

    private final AtomicInteger letterCount = new AtomicInteger(0);
    private final AtomicInteger digitCount = new AtomicInteger(0);
    private final AtomicInteger symbolCount = new AtomicInteger(0);

    @Override
    public void start(Stage primaryStage) {
        runTests();

        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setEditable(false);

        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10));
        pane.setCenter(textArea);

        Scene scene = new Scene(pane, 800, 500);

        primaryStage.setTitle("Johnathan Three Threads");
        primaryStage.setScene(scene);
        primaryStage.show();

        startThreads(textArea);
    }

    /**
     * Starts three separate threads.
     * One thread creates letters, one creates digits, and one creates symbols.
     *
     * @param textArea the TextArea where generated characters are displayed
     */
    public void startThreads(TextArea textArea) {
        Thread letterThread = new Thread(() -> generateLetters(textArea), "Letter Thread");
        Thread digitThread = new Thread(() -> generateDigits(textArea), "Digit Thread");
        Thread symbolThread = new Thread(() -> generateSymbols(textArea), "Symbol Thread");

        letterThread.setDaemon(true);
        digitThread.setDaemon(true);
        symbolThread.setDaemon(true);

        letterThread.start();
        digitThread.start();
        symbolThread.start();

        Thread testThread = new Thread(() -> {
            try {
                letterThread.join();
                digitThread.join();
                symbolThread.join();

                Platform.runLater(() -> {
                    textArea.appendText("\n\nAll threads finished successfully.");
                    textArea.appendText("\nLetters generated: " + letterCount.get());
                    textArea.appendText("\nDigits generated: " + digitCount.get());
                    textArea.appendText("\nSymbols generated: " + symbolCount.get());
                    textArea.appendText("\nTotal characters generated: "
                            + (letterCount.get() + digitCount.get() + symbolCount.get()));
                });

                check(letterCount.get() == REQUIRED_COUNT,
                        "Letter thread should generate exactly 10,000 letters.");
                check(digitCount.get() == REQUIRED_COUNT,
                        "Digit thread should generate exactly 10,000 digits.");
                check(symbolCount.get() == REQUIRED_COUNT,
                        "Symbol thread should generate exactly 10,000 symbols.");

                System.out.println("Thread test passed.");
                System.out.println("Letters generated: " + letterCount.get());
                System.out.println("Digits generated: " + digitCount.get());
                System.out.println("Symbols generated: " + symbolCount.get());
                System.out.println("Total characters generated: "
                        + (letterCount.get() + digitCount.get() + symbolCount.get()));

            } catch (InterruptedException ex) {
                System.out.println("Thread test was interrupted.");
            }
        }, "Test Thread");

        testThread.setDaemon(true);
        testThread.start();
    }

    /**
     * Generates random lowercase letters.
     *
     * @param textArea the TextArea where characters are displayed
     */
    public void generateLetters(TextArea textArea) {
        Random random = new Random();

        for (int i = 0; i < REQUIRED_COUNT; i++) {
            char letter = getRandomLetter(random);
            appendCharacter(textArea, letter);
            letterCount.incrementAndGet();
        }
    }

    /**
     * Generates random digits.
     *
     * @param textArea the TextArea where characters are displayed
     */
    public void generateDigits(TextArea textArea) {
        Random random = new Random();

        for (int i = 0; i < REQUIRED_COUNT; i++) {
            char digit = getRandomDigit(random);
            appendCharacter(textArea, digit);
            digitCount.incrementAndGet();
        }
    }

    /**
     * Generates random special characters.
     *
     * @param textArea the TextArea where characters are displayed
     */
    public void generateSymbols(TextArea textArea) {
        Random random = new Random();

        for (int i = 0; i < REQUIRED_COUNT; i++) {
            char symbol = getRandomSymbol(random);
            appendCharacter(textArea, symbol);
            symbolCount.incrementAndGet();
        }
    }

    /**
     * Appends one generated character to the TextArea.
     * Platform.runLater is used because JavaFX UI updates must happen
     * on the JavaFX Application Thread.
     *
     * @param textArea  the TextArea being updated
     * @param character the character to display
     */
    public void appendCharacter(TextArea textArea, char character) {
        Platform.runLater(() -> textArea.appendText(String.valueOf(character)));
    }

    /**
     * Returns a random lowercase letter from a-z.
     *
     * @param random Random object used to generate the letter
     * @return random lowercase letter
     */
    public static char getRandomLetter(Random random) {
        return (char) ('a' + random.nextInt(26));
    }

    /**
     * Returns a random digit from 0-9.
     *
     * @param random Random object used to generate the digit
     * @return random digit character
     */
    public static char getRandomDigit(Random random) {
        return (char) ('0' + random.nextInt(10));
    }

    /**
     * Returns a random special character from the SYMBOLS array.
     *
     * @param random Random object used to generate the symbol
     * @return random special character
     */
    public static char getRandomSymbol(Random random) {
        return SYMBOLS[random.nextInt(SYMBOLS.length)];
    }

    /**
     * Test code that checks the random character methods.
     * The thread count checks are completed after the threads finish.
     */
    public static void runTests() {
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            char letter = getRandomLetter(random);
            check(letter >= 'a' && letter <= 'z',
                    "Random letter must be between a and z.");

            char digit = getRandomDigit(random);
            check(digit >= '0' && digit <= '9',
                    "Random digit must be between 0 and 9.");

            char symbol = getRandomSymbol(random);
            check(isValidSymbol(symbol),
                    "Random symbol must be one of the listed symbols.");
        }

        check(REQUIRED_COUNT >= 10000,
                "Each thread must generate at least 10,000 characters.");

        System.out.println("Character generation tests passed.");
    }

    /**
     * Checks if a symbol is included in the allowed symbol list.
     *
     * @param symbol the symbol being checked
     * @return true if the symbol is valid
     */
    public static boolean isValidSymbol(char symbol) {
        for (char validSymbol : SYMBOLS) {
            if (symbol == validSymbol) {
                return true;
            }
        }

        return false;
    }

    /**
     * Helper method for test checks.
     *
     * @param condition condition being tested
     * @param message   message shown if the condition fails
     */
    public static void check(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException("Test failed: " + message);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
