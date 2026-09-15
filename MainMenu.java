import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainMenu {

    private Stage stage;

    public MainMenu(Stage stage) {
        this.stage = stage;
    }

    public void show() {

        // Game title
        Label title = new Label("WIZARD'S ESCAPE");
        title.setFont(Font.font("Serif", 42));
        title.setTextFill(Color.WHITE);

        // Subtitle
        Label subtitle = new Label("A Word-Scramble Adventure");
        subtitle.setFont(Font.font("Serif", 18));
        subtitle.setTextFill(Color.LIGHTGRAY);

        // PLAY button
        Button playButton = new Button("PLAY");
        playButton.setPrefWidth(250);
        playButton.setPrefHeight(50);
        playButton.setFont(Font.font("Arial", 18));

        // SCORE button
        Button scoreButton = new Button("SCORE");
        scoreButton.setPrefWidth(250);
        scoreButton.setPrefHeight(50);
        scoreButton.setFont(Font.font("Arial", 18));

        // QUIT button
        Button quitButton = new Button("QUIT");
        quitButton.setPrefWidth(250);
        quitButton.setPrefHeight(50);
        quitButton.setFont(Font.font("Arial", 18));

        // PLAY action
        playButton.setOnAction(event -> {
            DifficultySelection difficultySelection = new DifficultySelection(stage);
            difficultySelection.show();
        });

        // SCORE action
        scoreButton.setOnAction(event -> {
            ScoreScreen scoreScreen = new ScoreScreen(stage);
            scoreScreen.show();
        });

        // QUIT action
        quitButton.setOnAction(event -> {
            stage.close();
        });

        // Put everything inside a VBox
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(title,subtitle,playButton,scoreButton,quitButton);

        // Background
        layout.setStyle("-fx-background-color: #17233C;");

        // Create scene
        Scene scene = new Scene(layout, 900, 600);

        // Set scene
        stage.setScene(scene);
    }
}