import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Circle;
import java.util.Random;


public class ScoreScreen {

    private Stage stage;

    // Design resolution
    private static final double DESIGN_WIDTH = 900;
    private static final double DESIGN_HEIGHT = 600;

    // =========================================================
    // PARTICLE SETTINGS
    // =========================================================

    private static final int PARTICLE_COUNT = 30;

    private final Random random = new Random();

    public ScoreScreen(Stage stage) {
        this.stage = stage;
    }

        // =========================================================
        // PARTICLE SYSTEM
        // =========================================================

        private void createParticles(StackPane particleLayer) {

        for (int i = 0; i < PARTICLE_COUNT; i++) {

                double radius =
                        1.0 + random.nextDouble() * 2.0;

                Circle particle =
                        new Circle(radius);

                particle.setFill(
                        Color.web(
                                random.nextBoolean()
                                        ? "#C9A7F2"
                                        : "#8F6BB8"
                        )
                );

                resetParticle(particle);

                particleLayer.getChildren().add(
                        particle
                );

                animateParticle(particle);
        }
        }

        private void animateParticle(Circle particle) {

        double startX =
                particle.getTranslateX();

        double startY =
                particle.getTranslateY();

        double movementX =
                (random.nextDouble() - 0.5) * 60;

        double movementY =
                -40 - random.nextDouble() * 90;

        double duration =
                4 + random.nextDouble() * 4;

        TranslateTransition movement =
                new TranslateTransition(
                        Duration.seconds(duration),
                        particle
                );

        movement.setFromX(startX);
        movement.setFromY(startY);

        movement.setToX(
                startX + movementX
        );

        movement.setToY(
                startY + movementY
        );

        FadeTransition fade =
                new FadeTransition(
                        Duration.seconds(duration),
                        particle
                );

        fade.setFromValue(
                0.2 + random.nextDouble() * 0.4
        );

        fade.setToValue(0.0);

        ParallelTransition animation =
                new ParallelTransition(
                        movement,
                        fade
                );

        animation.setOnFinished(event -> {

                resetParticle(particle);

                animateParticle(particle);
        });

        animation.play();
        }

        private void resetParticle(Circle particle) {

        /*
        * Score cards occupy roughly the center
        * of the 900x600 design.
        *
        * The particles are therefore spawned
        * around the outside of the cards.
        */

        double x;
        double y;

        int area =
                random.nextInt(4);

        switch (area) {

                // Left side of the cards
                case 0:

                x =
                        80 + random.nextDouble() * 120;

                y =
                        240 + random.nextDouble() * 230;

                break;

                // Right side of the cards
                case 1:

                x =
                        700 + random.nextDouble() * 120;

                y =
                        240 + random.nextDouble() * 230;

                break;

                // Above the cards
                case 2:

                x =
                        180 + random.nextDouble() * 540;

                y =
                        190 + random.nextDouble() * 50;

                break;

                // Below the cards
                default:

                x =
                        180 + random.nextDouble() * 540;

                y =
                        475 + random.nextDouble() * 45;

                break;
        }

        /*
        * StackPane uses its center as the origin
        * for translateX / translateY.
        */

        particle.setTranslateX(
                x - DESIGN_WIDTH / 2
        );

        particle.setTranslateY(
                y - DESIGN_HEIGHT / 2
        );

        particle.setOpacity(
                0.2 + random.nextDouble() * 0.4
        );
        }
    
        private void transitionTo(Runnable nextScreen) {

                Scene currentScene = stage.getScene();

                if (currentScene == null) {
                        nextScreen.run();
                        return;
                }

                FadeTransition fadeOut =
                        new FadeTransition(
                                Duration.millis(250),
                                currentScene.getRoot()
                        );

                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);

                fadeOut.setOnFinished(event -> {

                        nextScreen.run();

                        Scene newScene = stage.getScene();

                        newScene.getRoot().setOpacity(0.0);

                        FadeTransition fadeIn =
                                new FadeTransition(
                                        Duration.millis(300),
                                        newScene.getRoot()
                                );

                        fadeIn.setFromValue(0.0);
                        fadeIn.setToValue(1.0);

                        fadeIn.play();
                });

                fadeOut.play();
        }

    // =========================================================
    // SHOW SCORE SCREEN
    // =========================================================

    public void show() {

        MusicManager.playMenuMusic();

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
                "Georgia",
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
                "Georgia",
                FontWeight.BOLD,
                27
        ));

        apprenticeTitle.setTextFill(
                Color.web("#E9D9FF")
        );

        Label apprenticeDescription =
                new Label("The path of the beginning wizard");

        apprenticeDescription.setFont(
                Font.font("Georgia", 15)
        );

        apprenticeDescription.setTextFill(
                Color.web("#BFB1D0")
        );

        Label apprenticeScoreLabel =
                new Label();

        apprenticeScoreLabel.setFont(
                Font.font(
                        "Georgia",
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
                        "Georgia",
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
                "Georgia",
                FontWeight.BOLD,
                27
        ));

        sorcererTitle.setTextFill(
                Color.web("#E9D9FF")
        );

        Label sorcererDescription =
                new Label("The path of the master wizard");

        sorcererDescription.setFont(
                Font.font("Georgia", 15)
        );

        sorcererDescription.setTextFill(
                Color.web("#BFB1D0")
        );

        Label sorcererScoreLabel =
                new Label();

        sorcererScoreLabel.setFont(
                Font.font(
                        "Georgia",
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
                        "Georgia",
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
                "-fx-background-color: rgba(35, 24, 60, 0.95);" +
                "-fx-text-fill: #F3E8FF;" +
                "-fx-font-family: 'Georgia';" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-border-color: #9C7AC7;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-border-width: 1.5;" +
                "-fx-cursor: hand;";

        String backHoverStyle =
                "-fx-background-color: rgba(72, 45, 110, 0.98);" +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-font-family: 'Georgia';" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-border-color: #C9A7F2;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-border-width: 2;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, #9B6DCE, 12, 0.55, 0, 0);";

        backButton.setStyle(
                backStyle
        );

        backButton.setOnMouseEntered(event -> {

        backButton.setStyle(
                backHoverStyle
        );

        ScaleTransition grow =
                new ScaleTransition(
                        Duration.millis(150),
                        backButton
                );

        grow.setToX(1.06);
        grow.setToY(1.06);
        grow.playFromStart();
        });

        backButton.setOnMouseExited(event -> {

        backButton.setStyle(
                backStyle
        );

        ScaleTransition shrink =
                new ScaleTransition(
                        Duration.millis(150),
                        backButton
                );

        shrink.setToX(1.0);
        shrink.setToY(1.0);
        shrink.playFromStart();
        });

        backButton.setOnAction(event -> {

                transitionTo(()-> {
                        
                MainMenu mainMenu =
                        new MainMenu(stage);

                mainMenu.show();
                });
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
        // SCALING CONTAINER
        // =========================================================

        StackPane scaleContainer =
                new StackPane();

        scaleContainer.setStyle(
                "-fx-background-color: #120D20;"
        );

        // =========================================================
        // BACKGROUND
        // =========================================================

        ImageView backgroundImage =
                new ImageView(
                        new Image(
                                getClass()
                                        .getResource("/assets/scoresbg.png")
                                        .toExternalForm()
                        )
                );

        backgroundImage.setPreserveRatio(false);
        backgroundImage.setMouseTransparent(true);

        // Make the background fill the entire window
        backgroundImage.fitWidthProperty()
                .bind(scaleContainer.widthProperty());

        backgroundImage.fitHeightProperty()
                .bind(scaleContainer.heightProperty());

        // =========================================================
        // DESIGN ROOT
        // =========================================================

        StackPane designRoot =
                new StackPane();

        designRoot.setPrefSize(
                DESIGN_WIDTH,
                DESIGN_HEIGHT
        );

        // =========================================================
        // PARTICLE LAYER
        // =========================================================

        StackPane particleLayer =
                new StackPane();

        particleLayer.setPrefSize(
                DESIGN_WIDTH,
                DESIGN_HEIGHT
        );

        particleLayer.setMouseTransparent(
                true
        );

        createParticles(
                particleLayer
        );

        // =========================================================
        // LAYER ORDER
        // =========================================================

        designRoot.getChildren().addAll(
                particleLayer,
                content
        );

        scaleContainer.getChildren().addAll(
                backgroundImage,
                designRoot
        );

        // =========================================================
        // RESPONSIVE SCALING
        // =========================================================

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

