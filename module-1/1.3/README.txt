
Random Card Display Application
===================================

This project contains a simple JavaFX program that displays four random playing cards from a standard deck of 52 cards. A "Refresh Cards" button allows the user to select a new set of four random cards each time it is pressed.

How to Run:

1. Ensure you have Java and JavaFX installed and configured on your system. 
   - Set the module path to your JavaFX SDK's lib directory.
   - Add the JavaFX modules (e.g., javafx.controls) to the VM options.
2. Compile the Java source:
   javac --module-path "<path_to_javafx_lib>" --add-modules javafx.controls src/CardDisplay.java
3. Run the program:
   java --module-path "<path_to_javafx_lib>" --add-modules javafx.controls -cp src CardDisplay

Directory Structure:
- cards/ : Contains the 52 card image files used by the program.
- src/ : Contains the Java source file CardDisplay.java.
- README.txt : This file with instructions and notes.
- Johnathan_Smith_Module_3_ : Word document containing required screenshots.



