import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class ScoreScreen {

    private Stage stage;

    public ScoreScreen(Stage stage) {
        this.stage = stage;
    }


    // Show the score screen
    public void show() {

        // Title
        Label title = new Label("HIGH SCORES");

        title.setFont(Font.font("Serif", 36));

        title.setTextFill(Color.WHITE);

        // Score list
        ListView<String> scoreList = new ListView<>();
        scoreList.setPrefWidth(600);
        scoreList.setPrefHeight(350);


        // Load saved scores
        ScoreManager scoreManager = new ScoreManager();

        List<PlayerScore> scores = scoreManager.loadScores();


        // Check if there are saved scores
        if (scores.isEmpty()) {
            scoreList.getItems().add("No scores yet.");

        } 
        
        else {
            // Display each score
            int rank = 1;

            for (PlayerScore playerScore : scores) {

                String scoreText =
                        rank +
                        ". " +
                        playerScore.getPlayerName() +
                        " | " +
                        playerScore.getDifficulty() +
                        " | Score: " +
                        playerScore.getScore();

                scoreList.getItems().add(
                        scoreText
                );

                rank++;
            }
        }


        // Back button
        Button backButton = new Button("BACK");
        backButton.setPrefWidth(180);
        backButton.setPrefHeight(45);
        backButton.setFont(Font.font("Arial", 16));

        // Back button action
        backButton.setOnAction(event -> {
            MainMenu mainMenu = new MainMenu(stage);
            mainMenu.show();
        });


        // Layout
        VBox layout =
                new VBox(20);

        layout.setAlignment(
                Pos.CENTER
        );


        layout.getChildren().addAll(
                title,
                scoreList,
                backButton
        );


        // Background
        layout.setStyle(
                "-fx-background-color: #17233C;"
        );


        // Create scene
        Scene scene =
                new Scene(
                        layout,
                        900,
                        600
                );


        // Set scene
        stage.setScene(scene);
    }
}
