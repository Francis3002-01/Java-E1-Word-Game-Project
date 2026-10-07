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
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DifficultySelection {

    private Stage stage;

    // Design resolution
    private static final double DESIGN_WIDTH = 900;
    private static final double DESIGN_HEIGHT = 600;

    // =========================================================
    // PARTICLE SETTINGS
    // =========================================================

        private static final int PARTICLE_COUNT = 35;

        private final Random random = new Random();

        private final List<Circle> particles = new ArrayList<>();

    public DifficultySelection(Stage stage) {
        this.stage = stage;
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

        private void updateScale(StackPane scaleContainer, StackPane designRoot) {

        double scaleX =
                scaleContainer.getWidth() / DESIGN_WIDTH;

        double scaleY =
                scaleContainer.getHeight() / DESIGN_HEIGHT;

        double scale =
                Math.min(scaleX, scaleY);

        designRoot.setScaleX(scale);
        designRoot.setScaleY(scale);
        }

        private void createParticles(StackPane particleLayer) {

                for (int i = 0; i < PARTICLE_COUNT; i++) {

                        double radius = 1.0 + random.nextDouble() * 2.0;

                        Circle particle = new Circle(radius);

                        particle.setFill(Color.web(
                                random.nextBoolean()
                                        ? "#C9A7F2"
                                        : "#8F6BB8"
                        ));

                        particle.setOpacity(
                                0.15 + random.nextDouble() * 0.45
                        );

                        resetParticle(particle);

                        particleLayer.getChildren().add(particle);
                        particles.add(particle);

                        animateParticle(particle);
                }
        }

        private void animateParticle(Circle particle) {

                double startX = particle.getTranslateX();
                double startY = particle.getTranslateY();

                double movementX =
                        (random.nextDouble() - 0.5) * 80;

                double movementY =
                        -40 - random.nextDouble() * 100;

                double duration =
                        4 + random.nextDouble() * 5;

                TranslateTransition movement =
                        new TranslateTransition(
                                Duration.seconds(duration),
                                particle
                        );

                movement.setFromX(startX);
                movement.setFromY(startY);
                movement.setToX(startX + movementX);
                movement.setToY(startY + movementY);

                FadeTransition fade =
                        new FadeTransition(
                                Duration.seconds(duration),
                                particle
                        );

                fade.setFromValue(
                        0.15 + random.nextDouble() * 0.45
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

                double startX =
                        random.nextDouble() * DESIGN_WIDTH
                        - DESIGN_WIDTH / 2;

                double startY =
                        DESIGN_HEIGHT / 2
                        + random.nextDouble() * 50;

                particle.setTranslateX(startX);
                particle.setTranslateY(startY);

                particle.setOpacity(
                        0.15 + random.nextDouble() * 0.45
                );
        }

    public void show() {

        // =========================================================
        // TITLE
        // =========================================================

        Label title = new Label("CHOOSE YOUR PATH");
        title.setFont(Font.font(
                "Palatino Linotype",
                FontWeight.BOLD,
                42
        ));
        title.setTextFill(Color.web("#E8D8FF"));

        title.setStyle(
                "-fx-effect: dropshadow(gaussian, #9B6DCE, 14, 0.55, 0, 0);"
        );

        Label subtitle = new Label("How will you enter the dungeon?");
        subtitle.setFont(Font.font(
                "Garamond",
                FontPosture.ITALIC,
                18
        ));
        subtitle.setTextFill(Color.web("#CFC0E8"));

        // =========================================================
        // APPRENTICE CARD
        // =========================================================

        Label apprenticeTitle = new Label("APPRENTICE");
        apprenticeTitle.setFont(Font.font(
                "Palatino Linotype",
                FontWeight.BOLD,
                27
        ));
        apprenticeTitle.setTextFill(Color.web("#E9D9FF"));

        Label apprenticeInfo = new Label(
                "4–6 Letters\n" +
                "30 Seconds\n" +
                "Find 6 Words"
        );

        apprenticeInfo.setFont(Font.font("Palatino Linotype", 17));
        apprenticeInfo.setTextFill(Color.web("#D8CCE8"));
        apprenticeInfo.setAlignment(Pos.CENTER);
        apprenticeInfo.setLineSpacing(4);

        Button apprenticeButton = new Button("ENTER");
        apprenticeButton.setPrefWidth(190);
        apprenticeButton.setPrefHeight(48);
        apprenticeButton.setFocusTraversable(false);

        String normalButtonStyle =
                "-fx-background-color: rgba(35, 24, 60, 0.95);" +
                "-fx-text-fill: #F3E8FF;" +
                "-fx-font-family: 'Serif';" +
                "-fx-font-size: 17px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: #9C7AC7;" +
                "-fx-border-radius: 10;" +
                "-fx-border-width: 1.5;" +
                "-fx-cursor: hand;";

        String hoverButtonStyle =
                "-fx-background-color: rgba(72, 45, 110, 0.98);" +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-font-family: 'Serif';" +
                "-fx-font-size: 17px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: #C9A7F2;" +
                "-fx-border-radius: 10;" +
                "-fx-border-width: 2;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, #9B6DCE, 16, 0.65, 0, 0);";

        apprenticeButton.setStyle(normalButtonStyle);

        VBox apprenticeCard = new VBox(14);
        apprenticeCard.setAlignment(Pos.CENTER);
        apprenticeCard.setPrefSize(320, 255);
        apprenticeCard.setMinSize(320, 255);
        apprenticeCard.setMaxSize(320, 255);

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
                apprenticeInfo,
                apprenticeButton
        );

        // =========================================================
        // SORCERER CARD
        // =========================================================

        Label sorcererTitle = new Label("SORCERER");
        sorcererTitle.setFont(Font.font(
                "Palatino Linotype",
                FontWeight.BOLD,
                27
        ));
        sorcererTitle.setTextFill(Color.web("#E9D9FF"));

        Label sorcererInfo = new Label(
                "6–8 Letters\n" +
                "35 Seconds\n" +
                "Find 8 Words"
        );

        sorcererInfo.setFont(Font.font("Palatino Linotype", 17));
        sorcererInfo.setTextFill(Color.web("#D8CCE8"));
        sorcererInfo.setAlignment(Pos.CENTER);
        sorcererInfo.setLineSpacing(4);

        Button sorcererButton = new Button("ENTER");
        sorcererButton.setPrefWidth(190);
        sorcererButton.setPrefHeight(48);
        sorcererButton.setFocusTraversable(false);
        sorcererButton.setStyle(normalButtonStyle);

        VBox sorcererCard = new VBox(14);
        sorcererCard.setAlignment(Pos.CENTER);
        sorcererCard.setPrefSize(320, 255);
        sorcererCard.setMinSize(320, 255);
        sorcererCard.setMaxSize(320, 255);

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
                sorcererInfo,
                sorcererButton
        );

        // =========================================================
        // BUTTON HOVER EFFECTS
        // =========================================================

        Button[] difficultyButtons = {
                apprenticeButton,
                sorcererButton
        };

        for (Button button : difficultyButtons) {

        button.setOnMouseEntered(event -> {

                button.setStyle(hoverButtonStyle);

                ScaleTransition grow =
                        new ScaleTransition(
                                Duration.millis(150),
                                button
                        );

                grow.setToX(1.06);
                grow.setToY(1.06);
                grow.playFromStart();
        });

        button.setOnMouseExited(event -> {

                button.setStyle(normalButtonStyle);

                ScaleTransition shrink =
                        new ScaleTransition(
                                Duration.millis(150),
                                button
                        );

                shrink.setToX(1.0);
                shrink.setToY(1.0);
                shrink.playFromStart();
        });
        }

        // =========================================================
        // BUTTON ACTIONS
        // =========================================================

        apprenticeButton.setOnAction(event -> {

                transitionTo(()-> {
                GameScreen gameScreen =
                        new GameScreen(stage, "Apprentice");

                gameScreen.show();
                });
        });

        sorcererButton.setOnAction(event -> {

                transitionTo(()-> {
                GameScreen gameScreen =
                        new GameScreen(stage, "Sorcerer");

                gameScreen.show();
                });
        });

        // =========================================================
        // BACK BUTTON
        // =========================================================

        Button backButton = new Button("BACK");
        backButton.setPrefWidth(140);
        backButton.setPrefHeight(42);
        backButton.setFocusTraversable(false);

        String backStyle =
                "-fx-background-color: rgba(35, 24, 60, 0.95);" +
                "-fx-text-fill: #F3E8FF;" +
                "-fx-font-family: 'Serif';" +
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
                "-fx-font-family: 'Serif';" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-border-color: #C9A7F2;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-border-width: 2;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, #9B6DCE, 12, 0.55, 0, 0);";

        backButton.setStyle(backStyle);

        backButton.setOnMouseEntered(event -> {

        backButton.setStyle(backHoverStyle);

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

        backButton.setStyle(backStyle);

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

                transitionTo(() -> {
                        MainMenu mainMenu = new MainMenu(stage);
                        mainMenu.show();
                });
        });

        // =========================================================
        // DIFFICULTY CARD CONTAINER
        // =========================================================

        HBox difficultyCards = new HBox(35);
        difficultyCards.setAlignment(Pos.CENTER);

        difficultyCards.getChildren().addAll(
                apprenticeCard,
                sorcererCard
        );

        // =========================================================
        // MAIN CONTENT
        // =========================================================

        VBox content = new VBox(12);
        content.setAlignment(Pos.CENTER);

        content.getChildren().addAll(
                title,
                subtitle,
                difficultyCards,
                backButton
        );

        // =========================================================
        // SCALING CONTAINER
        // =========================================================

        StackPane scaleContainer = new StackPane();

        scaleContainer.setStyle(
                "-fx-background-color: #120D20;"
        );

        // =========================================================
        // BACKGROUND
        // =========================================================

        ImageView backgroundImage = new ImageView(
                new Image(
                        getClass()
                                .getResource("/assets/difficultybg.png")
                                .toExternalForm()
                )
        );

        backgroundImage.setPreserveRatio(false);
        backgroundImage.setMouseTransparent(true);

        // Make the background always fill the entire window
        backgroundImage.fitWidthProperty()
                .bind(scaleContainer.widthProperty());

        backgroundImage.fitHeightProperty()
                .bind(scaleContainer.heightProperty());


        // =========================================================
        // DESIGN ROOT
        // =========================================================

        StackPane designRoot = new StackPane();

        designRoot.setPrefSize(
                DESIGN_WIDTH,
                DESIGN_HEIGHT
        );


        // =========================================================
        // PARTICLE LAYER
        // =========================================================

        StackPane particleLayer = new StackPane();

        particleLayer.setPrefSize(
                DESIGN_WIDTH,
                DESIGN_HEIGHT
        );

        particleLayer.setMouseTransparent(true);

        createParticles(particleLayer);


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
                updateScale(scaleContainer, designRoot);
                }
        );

        scaleContainer.heightProperty().addListener(
                (obs, oldValue, newValue) -> {
                updateScale(scaleContainer, designRoot);
                }
        );

        // =========================================================
        // SCENE
        // =========================================================

        Scene scene = new Scene(
                scaleContainer,
                DESIGN_WIDTH,
                DESIGN_HEIGHT
        );

        scene.setFill(Color.web("#120D20"));

        stage.setScene(scene);
        stage.setTitle("Wizard's Escape");
        stage.show();

        updateScale(scaleContainer, designRoot);
    }

}
