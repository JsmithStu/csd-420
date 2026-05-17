import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FanApp is a simple JavaFX application that allows a user to view and
 * update records in the {@code fans} table.  The UI consists of input
 * fields for the ID, first name, last name and favorite team of a fan and
 * two buttons to display and update records.  The display button loads
 * an existing record by ID and populates the fields; the update button
 * writes any changes back to the database.
 */
public class FanApp extends Application {

    private TextField idField;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField favoriteTeamField;
    private Label statusLabel;
    private final FanDAO fanDAO = new FanDAO();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Fan Database Viewer/Updater");

        // Create UI controls
        idField = new TextField();
        firstNameField = new TextField();
        lastNameField = new TextField();
        favoriteTeamField = new TextField();
        statusLabel = new Label();

        Button displayButton = new Button("Display");
        Button updateButton = new Button("Update");

        // Hook up event handlers
        displayButton.setOnAction(e -> onDisplay());
        updateButton.setOnAction(e -> onUpdate());

        // Layout using a GridPane for simplicity
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Fan ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("First Name:"), 0, 1);
        grid.add(firstNameField, 1, 1);
        grid.add(new Label("Last Name:"), 0, 2);
        grid.add(lastNameField, 1, 2);
        grid.add(new Label("Favorite Team:"), 0, 3);
        grid.add(favoriteTeamField, 1, 3);
        grid.add(displayButton, 0, 4);
        grid.add(updateButton, 1, 4);
        grid.add(statusLabel, 0, 5, 2, 1);

        Scene scene = new Scene(grid, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Handles the display button click.  Attempts to parse the ID and load
     * a record from the database.  Populates the text fields if a record
     * is found or updates the status label otherwise.
     */
    private void onDisplay() {
        statusLabel.setText("");
        String idText = idField.getText();
        if (idText == null || idText.trim().isEmpty()) {
            statusLabel.setText("Please enter a numeric ID.");
            return;
        }
        try {
            int id = Integer.parseInt(idText.trim());
            Fan fan = fanDAO.getFanById(id);
            if (fan == null) {
                firstNameField.clear();
                lastNameField.clear();
                favoriteTeamField.clear();
                statusLabel.setText("No record found for ID " + id);
            } else {
                firstNameField.setText(fan.getFirstname());
                lastNameField.setText(fan.getLastname());
                favoriteTeamField.setText(fan.getFavoriteTeam());
                statusLabel.setText("Record loaded.");
            }
        } catch (NumberFormatException ex) {
            statusLabel.setText("Invalid ID format");
        } catch (Exception ex) {
            statusLabel.setText("Error: " + ex.getMessage());
        }
    }

    /**
     * Handles the update button click.  Validates the input fields and
     * attempts to update the corresponding record in the database.
     */
    private void onUpdate() {
        statusLabel.setText("");
        String idText = idField.getText();
        if (idText == null || idText.trim().isEmpty()) {
            statusLabel.setText("Please enter the fan ID to update.");
            return;
        }
        try {
            int id = Integer.parseInt(idText.trim());
            Fan fan = new Fan();
            fan.setId(id);
            fan.setFirstname(firstNameField.getText());
            fan.setLastname(lastNameField.getText());
            fan.setFavoriteTeam(favoriteTeamField.getText());
            boolean updated = fanDAO.updateFan(fan);
            if (updated) {
                statusLabel.setText("Record updated successfully.");
            } else {
                statusLabel.setText("No record updated. Check that the ID exists.");
            }
        } catch (NumberFormatException ex) {
            statusLabel.setText("Invalid ID format");
        } catch (Exception ex) {
            statusLabel.setText("Error: " + ex.getMessage());
        }
    }

    /**
     * Launches the JavaFX application.  This call delegates to
     * {@link Application#launch} which will in turn invoke {@link #start}.
     *
     * @param args program arguments (unused)
     */
    public static void main(String[] args) {
        launch(args);
    }
}