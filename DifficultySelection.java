import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DifficultySelection {

    private Stage stage;

    public DifficultySelection(Stage stage) {
        this.stage = stage;
    }

    public void show() {

        // Title
        Label title = new Label("SELECT DIFFICULTY");
        title.setFont(Font.font("Serif", 36));
        title.setTextFill(Color.WHITE);

        Label nameLabel = new Label("PLAYER NAME");
        nameLabel.setFont(Font.font("Arial", 18));
        nameLabel.setTextFill(Color.WHITE);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        nameField.setMaxWidth(300);
        nameField.setPrefHeight(40);
        nameField.setFont(Font.font("Arial", 16));


        // Apprentice section
        Label apprenticeInfo = new Label(
                "APPRENTICE\n" +
                "4-6 Letters\n" +
                "30 Seconds\n" +
                "Find 4 Words"
        );

        apprenticeInfo.setFont(Font.font("Arial", 18));
        apprenticeInfo.setTextFill(Color.LIGHTGRAY);
        apprenticeInfo.setAlignment(Pos.CENTER);

        Button apprenticeButton = new Button("APPRENTICE");
        apprenticeButton.setPrefWidth(250);
        apprenticeButton.setPrefHeight(50);
        apprenticeButton.setFont(Font.font("Arial", 18));
        apprenticeButton.setFocusTraversable(false);

        VBox apprenticeSection = new VBox(8);
        apprenticeSection.setAlignment(Pos.CENTER);
        apprenticeSection.setPrefWidth(320);
        apprenticeSection.setStyle(
                "-fx-background-color: #233A5E; " +
                "-fx-border-color: #8EC5FF; " +
                "-fx-border-radius: 12; " +
                "-fx-background-radius: 12; " +
                "-fx-padding: 18;"
        );
        apprenticeSection.getChildren().addAll(apprenticeInfo, apprenticeButton);

        // Sorcerer section
        Label sorcererInfo = new Label(
                "SORCERER\n" +
                "6–8 Letters\n" +
                "35 Seconds\n" +
                "Find 6 Words"
        );

        sorcererInfo.setFont(Font.font("Arial", 18));
        sorcererInfo.setTextFill(Color.LIGHTGRAY);
        sorcererInfo.setAlignment(Pos.CENTER);

        Button sorcererButton = new Button("SORCERER");
        sorcererButton.setPrefWidth(250);
        sorcererButton.setPrefHeight(50);
        sorcererButton.setFont(Font.font("Arial", 18));
        sorcererButton.setFocusTraversable(false);

        VBox sorcererSection = new VBox(8);
        sorcererSection.setAlignment(Pos.CENTER);
        sorcererSection.setPrefWidth(320);
        sorcererSection.setStyle(
                "-fx-background-color: #233A5E; " +
                "-fx-border-color: #8EC5FF; " +
                "-fx-border-radius: 12; " +
                "-fx-background-radius: 12; " +
                "-fx-padding: 18;"
        );
        sorcererSection.getChildren().addAll(sorcererInfo, sorcererButton);


        // Back button
        Button backButton = new Button("BACK");
        backButton.setPrefWidth(150);
        backButton.setPrefHeight(40);
        backButton.setFont(Font.font("Arial", 16));

        /*apprenticeButton.setOnAction(event -> {
        System.out.println("APPRENTICE CLICKED");

        GameScreen gameScreen =
                new GameScreen(stage, "Apprentice");

        gameScreen.show();
        });*/

        apprenticeButton.setOnAction(event -> {

        String playerName = nameField.getText().trim();

        if (playerName.isEmpty()) {
                return;
        }

        System.out.println("APPRENTICE CLICKED");

        GameScreen gameScreen =
                new GameScreen(
                        stage,
                        "Apprentice",
                        playerName
                );

        gameScreen.show();
        });

        /*sorcererButton.setOnAction(event -> {
        System.out.println("SORCERER CLICKED");

        GameScreen gameScreen =
                new GameScreen(stage, "Sorcerer");

        gameScreen.show();
        });*/

        sorcererButton.setOnAction(event -> {

        String playerName = nameField.getText().trim();

        if (playerName.isEmpty()) {
                return;
        }

        System.out.println("SORCERER CLICKED");

        GameScreen gameScreen =
                new GameScreen(
                        stage,
                        "Sorcerer",
                        playerName
                );

        gameScreen.show();
        });
        

        backButton.setOnAction(event -> {
            MainMenu mainMenu = new MainMenu(stage);
            mainMenu.show();
        });

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        /*layout.getChildren().addAll(
                title,
                apprenticeSection,
                sorcererSection,
                backButton
        );*/
                layout.getChildren().addAll(
                title,
                nameLabel,
                nameField,
                apprenticeSection,
                sorcererSection,
                backButton
        );


        // Background
        layout.setStyle("-fx-background-color: #17233C;");

        // Create scene
        Scene scene = new Scene(layout, 900, 600);

        // Set scene
        stage.setScene(scene);
    }
}