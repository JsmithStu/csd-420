
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDisplay extends Application {
    private final ImageView[] cardViews = new ImageView[4];

    @Override
    public void start(Stage primaryStage) {
        HBox cardBox = new HBox(10);
        cardBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < 4; i++) {
            cardViews[i] = new ImageView();
            cardViews[i].setFitWidth(120);
            cardViews[i].setFitHeight(170);
            cardViews[i].setPreserveRatio(true);
            cardBox.getChildren().add(cardViews[i]);
        }
        Button refreshButton = new Button("Refresh Cards");
        refreshButton.setOnAction(e -> displayRandomCards()); // Lambda expression
        BorderPane root = new BorderPane();
        root.setCenter(cardBox);
        root.setBottom(refreshButton);
        BorderPane.setAlignment(refreshButton, Pos.CENTER);
        displayRandomCards();
        Scene scene = new Scene(root, 600, 300);
        primaryStage.setTitle("Random Card Display");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void displayRandomCards() {
        // Create a list of card indices
        List<Integer> deck = new ArrayList<>();
        for (int i = 1; i <= 52; i++) {
            deck.add(i);
        }
        // Shuffle the deck
        Collections.shuffle(deck);
        // Display the first four cards
        for (int i = 0; i < 4; i++) {
            int cardNumber = deck.get(i);
            try {
                Image image = new Image(new FileInputStream("cards/" + cardNumber + ".png"));
                cardViews[i].setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
