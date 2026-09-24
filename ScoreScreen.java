import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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

        // Score manager
        ScoreManager scoreManager = new ScoreManager();

        // Get high scores
        PlayerScore apprenticeScore = scoreManager.getHighScore("Apprentice");

        PlayerScore sorcererScore = scoreManager.getHighScore("Sorcerer");

        // Apprentice
        Label apprenticeLabel = new Label();

        apprenticeLabel.setFont(Font.font("Arial", 22));

        apprenticeLabel.setTextFill(Color.WHITE);

        if (apprenticeScore != null) {
            apprenticeLabel.setText("APPRENTICE\n"+ apprenticeScore.getScore());
        } 
        
        else {
            apprenticeLabel.setText("APPRENTICE\n"+ "No score yet");
        }

        // Sorcerer
        Label sorcererLabel = new Label();

        sorcererLabel.setFont(Font.font("Arial", 22));

        sorcererLabel.setTextFill(Color.WHITE);

        if (sorcererScore != null) {
            sorcererLabel.setText("SORCERER\n"+ sorcererScore.getScore());

        } 
        
        else {
            sorcererLabel.setText("SORCERER\n"+ "No score yet");
        }

        // Back button
        Button backButton =new Button("BACK");

        backButton.setPrefWidth(180);
        backButton.setPrefHeight(45);

        backButton.setFont(Font.font("Arial", 16));

        backButton.setOnAction(event -> {
            MainMenu mainMenu =new MainMenu(stage);
            mainMenu.show();
        });

        // Layout
        VBox layout =new VBox(30);

        layout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(
                title,
                apprenticeLabel,
                sorcererLabel,
                backButton
        );

        // Background
        layout.setStyle("-fx-background-color: #17233C;");

        // Scene
        Scene scene = new Scene(layout,900,600);

        stage.setScene(scene);
    }
}