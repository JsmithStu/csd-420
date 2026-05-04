/**
 * Johnathan Smith
 * Module 7 Programming Assignment
 * CSD420
 *
 * This JavaFX program displays four circles and uses an external CSS file
 * to apply a style class and CSS IDs.
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class CircleStyleDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        HBox pane = buildPane();

        Scene scene = new Scene(pane, 450, 140);

        // Load the external CSS stylesheet.
        String css = getClass().getResource("mystyle.css").toExternalForm();
        scene.getStylesheets().add(css);

        // Test code to make sure the program is set up correctly.
        runTests(pane, scene);

        primaryStage.setTitle("Module 7 CSS Circle Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Builds the main pane with four circles.
     *
     * @return HBox containing the circles
     */
    public static HBox buildPane() {
        HBox pane = new HBox(15);
        pane.setPadding(new Insets(20));

        Circle circle1 = new Circle(50);
        Circle circle2 = new Circle(50);
        Circle circle3 = new Circle(50);
        Circle circle4 = new Circle(50);

        // Style class from mystyle.css: white fill and black stroke.
        circle1.getStyleClass().add("plaincircle");
        circle4.getStyleClass().add("plaincircle");

        // IDs from mystyle.css: red and green circles.
        circle2.setId("redcircle");
        circle3.setId("greencircle");

        pane.getChildren().addAll(circle1, circle2, circle3, circle4);

        return pane;
    }

    /**
     * Simple test code that checks whether the program meets the assignment requirements.
     *
     * @param pane  the main HBox pane
     * @param scene the scene containing the CSS stylesheet
     */
    public static void runTests(HBox pane, Scene scene) {
        check(pane.getChildren().size() == 4, "There should be exactly four circles.");
        check(pane.getChildren().get(0).getStyleClass().contains("plaincircle"),
                "Circle 1 should use the plaincircle style class.");
        check("redcircle".equals(pane.getChildren().get(1).getId()),
                "Circle 2 should use the redcircle ID.");
        check("greencircle".equals(pane.getChildren().get(2).getId()),
                "Circle 3 should use the greencircle ID.");
        check(pane.getChildren().get(3).getStyleClass().contains("plaincircle"),
                "Circle 4 should use the plaincircle style class.");
        check(!scene.getStylesheets().isEmpty(),
                "The external CSS stylesheet should be loaded.");

        System.out.println("All tests passed. The JavaFX CSS circle program is working correctly.");
    }

    /**
     * Helper method for test checks.
     *
     * @param condition condition being tested
     * @param message   message shown if the test fails
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
