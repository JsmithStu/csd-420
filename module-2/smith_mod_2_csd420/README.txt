
CSD420 Data File Assignment
Johnathan Smith

Files included:
- WriteDataFile.java
- ReadDataFile.java

How to compile and run:
1. Open a terminal and navigate to this project directory.
2. Compile both Java source files:
   javac src/WriteDataFile.java src/ReadDataFile.java
3. Run the writer program to create or append data to the file:
   java -cp src WriteDataFile
4. Run the reader program to display the contents of the data file:
   java -cp src ReadDataFile

What the programs do:
- WriteDataFile creates an array of five random integers and an array of five random double values.
  It writes the data to "Smith datafile.dat" in the project directory. If the file already exists,
  the new data is appended.
- ReadDataFile reads the contents of "Smith datafile.dat" and displays all stored data to the console.

Before you submit:
- Add your Java and JavaFX installation screenshots to the Word document provided.
- Rename the zip file with your correct module number if needed (e.g., smith_mod_2_csd420.zip).
- Include a link to your GitHub repository in the submission comments.

