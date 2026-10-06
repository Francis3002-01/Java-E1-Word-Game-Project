import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ScoreScreen {

    private Stage stage;

    // Design resolution
    private static final double DESIGN_WIDTH = 900;
    private static final double DESIGN_HEIGHT = 600;

    public ScoreScreen(Stage stage) {
        this.stage = stage;
    }

    // =========================================================
    // SHOW SCORE SCREEN
    // =========================================================

    public void show() {

        // =========================================================
        // TITLE
        // =========================================================

        Label title = new Label("HIGH SCORES");

        title.setFont(Font.font(
                "Palatino Linotype",
                FontWeight.BOLD,
                42
        ));

        title.setTextFill(Color.web("#E8D8FF"));

        title.setStyle(
                "-fx-effect: dropshadow(gaussian, #9B6DCE, 14, 0.55, 0, 0);"
        );

        Label subtitle = new Label(
                "The greatest word wizards of the dungeon"
        );

        subtitle.setFont(Font.font(
                "Serif",
                FontPosture.ITALIC,
                18
        ));

        subtitle.setTextFill(Color.web("#CFC0E8"));

        // =========================================================
        // SCORE MANAGER
        // =========================================================

        ScoreManager scoreManager = new ScoreManager();

        PlayerScore apprenticeScore =
                scoreManager.getHighScore("Apprentice");

        PlayerScore sorcererScore =
                scoreManager.getHighScore("Sorcerer");

        // =========================================================
        // APPRENTICE SCORE
        // =========================================================

        Label apprenticeTitle =
                new Label("APPRENTICE");

        apprenticeTitle.setFont(Font.font(
                "Serif",
                FontWeight.BOLD,
                27
        ));

        apprenticeTitle.setTextFill(
                Color.web("#E9D9FF")
        );

        Label apprenticeDescription =
                new Label("The path of the beginning wizard");

        apprenticeDescription.setFont(
                Font.font("Serif", 15)
        );

        apprenticeDescription.setTextFill(
                Color.web("#BFB1D0")
        );

        Label apprenticeScoreLabel =
                new Label();

        apprenticeScoreLabel.setFont(
                Font.font(
                        "Serif",
                        FontWeight.BOLD,
                        36
                )
        );

        apprenticeScoreLabel.setTextFill(
                Color.web("#F3E8FF")
        );

        if (apprenticeScore != null) {

            apprenticeScoreLabel.setText(
                    String.valueOf(
                            apprenticeScore.getScore()
                    )
            );

        } else {

            apprenticeScoreLabel.setText("--");
        }

        Label apprenticeScoreText =
                new Label("BEST SCORE");

        apprenticeScoreText.setFont(
                Font.font(
                        "Serif",
                        FontWeight.BOLD,
                        13
                )
        );

        apprenticeScoreText.setTextFill(
                Color.web("#9C7AC7")
        );

        VBox apprenticeCard =
                new VBox(8);

        apprenticeCard.setAlignment(
                Pos.CENTER
        );

        apprenticeCard.setPrefSize(
                320,
                250
        );

        apprenticeCard.setMinSize(
                320,
                250
        );

        apprenticeCard.setMaxSize(
                320,
                250
        );

        apprenticeCard.setStyle(
                "-fx-background-color: rgba(25, 18, 45, 0.94);" +
                "-fx-border-color: #9C7AC7;" +
                "-fx-border-radius: 14;" +
                "-fx-background-radius: 14;" +
                "-fx-border-width: 1.5;" +
                "-fx-padding: 22;"
        );

        apprenticeCard.getChildren().addAll(
                apprenticeTitle,
                apprenticeDescription,
                apprenticeScoreLabel,
                apprenticeScoreText
        );

        // =========================================================
        // SORCERER SCORE
        // =========================================================

        Label sorcererTitle =
                new Label("SORCERER");

        sorcererTitle.setFont(Font.font(
                "Serif",
                FontWeight.BOLD,
                27
        ));

        sorcererTitle.setTextFill(
                Color.web("#E9D9FF")
        );

        Label sorcererDescription =
                new Label("The path of the master wizard");

        sorcererDescription.setFont(
                Font.font("Serif", 15)
        );

        sorcererDescription.setTextFill(
                Color.web("#BFB1D0")
        );

        Label sorcererScoreLabel =
                new Label();

        sorcererScoreLabel.setFont(
                Font.font(
                        "Serif",
                        FontWeight.BOLD,
                        36
                )
        );

        sorcererScoreLabel.setTextFill(
                Color.web("#F3E8FF")
        );

        if (sorcererScore != null) {

            sorcererScoreLabel.setText(
                    String.valueOf(
                            sorcererScore.getScore()
                    )
            );

        } else {

            sorcererScoreLabel.setText("--");
        }

        Label sorcererScoreText =
                new Label("BEST SCORE");

        sorcererScoreText.setFont(
                Font.font(
                        "Serif",
                        FontWeight.BOLD,
                        13
                )
        );

        sorcererScoreText.setTextFill(
                Color.web("#9C7AC7")
        );

        VBox sorcererCard =
                new VBox(8);

        sorcererCard.setAlignment(
                Pos.CENTER
        );

        sorcererCard.setPrefSize(
                320,
                250
        );

        sorcererCard.setMinSize(
                320,
                250
        );

        sorcererCard.setMaxSize(
                320,
                250
        );

        sorcererCard.setStyle(
                "-fx-background-color: rgba(25, 18, 45, 0.94);" +
                "-fx-border-color: #9C7AC7;" +
                "-fx-border-radius: 14;" +
                "-fx-background-radius: 14;" +
                "-fx-border-width: 1.5;" +
                "-fx-padding: 22;"
        );

        sorcererCard.getChildren().addAll(
                sorcererTitle,
                sorcererDescription,
                sorcererScoreLabel,
                sorcererScoreText
        );

        // =========================================================
        // SCORE CARDS
        // =========================================================

        HBox scoreCards =
                new HBox(35);

        scoreCards.setAlignment(
                Pos.CENTER
        );

        scoreCards.getChildren().addAll(
                apprenticeCard,
                sorcererCard
        );

        // =========================================================
        // BACK BUTTON
        // =========================================================

        Button backButton =
                new Button("BACK");

        backButton.setPrefWidth(
                140
        );

        backButton.setPrefHeight(
                42
        );

        backButton.setFocusTraversable(
                false
        );

        String backStyle =
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #BFAED6;" +
                "-fx-font-family: 'Serif';" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-border-color: transparent;" +
                "-fx-cursor: hand;";

        String backHoverStyle =
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-font-family: 'Serif';" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-border-color: #9C7AC7;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, #9B6DCE, 10, 0.45, 0, 0);";

        backButton.setStyle(
                backStyle
        );

        backButton.setOnMouseEntered(event -> {
            backButton.setStyle(
                    backHoverStyle
            );
        });

        backButton.setOnMouseExited(event -> {
            backButton.setStyle(
                    backStyle
            );
        });

        backButton.setOnAction(event -> {

            MainMenu mainMenu =
                    new MainMenu(stage);

            mainMenu.show();
        });

        // =========================================================
        // MAIN CONTENT
        // =========================================================

        VBox content =
                new VBox(12);

        content.setAlignment(
                Pos.CENTER
        );

        content.getChildren().addAll(
                title,
                subtitle,
                scoreCards,
                backButton
        );

        // =========================================================
        // DESIGN ROOT
        // =========================================================

        StackPane designRoot =
                new StackPane();

        designRoot.setPrefSize(
                DESIGN_WIDTH,
                DESIGN_HEIGHT
        );

        designRoot.setStyle(
                "-fx-background-color: #120D20;"
        );

        designRoot.getChildren().add(
                content
        );

        // =========================================================
        // SCALING CONTAINER
        // =========================================================

        StackPane scaleContainer =
                new StackPane();

        scaleContainer.setStyle(
                "-fx-background-color: #120D20;"
        );

        scaleContainer.getChildren().add(
                designRoot
        );

        scaleContainer.widthProperty().addListener(
                (obs, oldValue, newValue) -> {
                    updateScale(
                            scaleContainer,
                            designRoot
                    );
                }
        );

        scaleContainer.heightProperty().addListener(
                (obs, oldValue, newValue) -> {
                    updateScale(
                            scaleContainer,
                            designRoot
                    );
                }
        );

        // =========================================================
        // SCENE
        // =========================================================

        Scene scene =
                new Scene(
                        scaleContainer,
                        DESIGN_WIDTH,
                        DESIGN_HEIGHT
                );

        scene.setFill(
                Color.web("#120D20")
        );

        stage.setScene(scene);
        stage.setTitle("Wizard's Escape");
        stage.show();

        // Apply initial scale
        updateScale(
                scaleContainer,
                designRoot
        );
    }

    // =========================================================
    // RESPONSIVE SCALING
    // =========================================================

    private void updateScale(
            StackPane scaleContainer,
            StackPane designRoot
    ) {

        double scaleX =
                scaleContainer.getWidth()
                        / DESIGN_WIDTH;

        double scaleY =
                scaleContainer.getHeight()
                        / DESIGN_HEIGHT;

        double scale =
                Math.min(scaleX, scaleY);

        designRoot.setScaleX(
                scale
        );

        designRoot.setScaleY(
                scale
        );
    }
}

