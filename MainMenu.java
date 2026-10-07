import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import java.util.Random;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import javafx.animation.ScaleTransition;
import javafx.animation.FadeTransition;


public class MainMenu {

    private Stage stage;

    public MainMenu(Stage stage) {
        this.stage = stage;
    }

    public void show() {


        // TITLE
        Label title = new Label("WIZARD'S ESCAPE");

        title.setFont(Font.font("Palatino Linotype", FontWeight.BOLD, 50));

        title.setTextFill(Color.web("#E8D8FF"));
        

        // Add glow effect
        title.setStyle(
            "-fx-effect: dropshadow(gaussian, #7FDBFF, 15, 0.5, 0, 0);"
        );


        // SUBTITLE
        Label subtitle = new Label("A Word-Scramble Adventure");
        subtitle.setFont(Font.font("Garamond", FontPosture.ITALIC, 19));

        subtitle.setTextFill(
            Color.web("#D8C7FF")
        );

        subtitle.setStyle(
            "-fx-effect: dropshadow(gaussian, #4B0082, 10, 0.5, 0, 0);"
        );



        // BUTTON STYLE
        String buttonStyle =
            "-fx-background-color: rgba(20, 15, 45, 0.88);" +
            "-fx-text-fill: #F3E8FF;" +
            "-fx-font-family: 'Georgia';" +
            "-fx-font-size: 21px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: #9C7AC7;" +
            "-fx-border-radius: 12;" +
            "-fx-border-width: 1.5;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 8 25 8 25;";

        Button playButton = new Button("PLAY");
        Button scoreButton = new Button("SCORES");
        Button quitButton = new Button("QUIT");

        Button[] buttons = {
            playButton,
            scoreButton,
            quitButton
        };

        for (Button button : buttons) {

            button.setPrefWidth(250);
            button.setPrefHeight(54);
            button.setFont(Font.font("Georgia", 21));
            button.setStyle(buttonStyle);

            button.setOnMouseEntered(e -> {

                button.setStyle(
                    "-fx-background-color: rgba(63, 39, 105, 0.95);" +
                    "-fx-text-fill: #FFFFFF;" +
                    "-fx-font-family: 'Serif';" +
                    "-fx-font-size: 21px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 12;" +
                    "-fx-border-color: #C9A7F2;" +
                    "-fx-border-radius: 12;" +
                    "-fx-border-width: 2;" +
                    "-fx-cursor: hand;" +
                    "-fx-effect: dropshadow(gaussian, #9B6DCE, 16, 0.65, 0, 0);"
                );

                ScaleTransition grow =
                new ScaleTransition(
                        Duration.millis(150),
                        button
                );

                grow.setToX(1.06);
                grow.setToY(1.06);
                grow.playFromStart();
            });

            button.setOnMouseExited(e -> {

                button.setStyle(buttonStyle);

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



        // BUTTON ACTIONS

        playButton.setOnAction(event -> {

            transitionTo(()-> {
            DifficultySelection difficultySelection =
                    new DifficultySelection(stage);

            difficultySelection.show();
            });

        });


        scoreButton.setOnAction(event -> {

            transitionTo(()-> {
            ScoreScreen scoreScreen =
                    new ScoreScreen(stage);

            scoreScreen.show();
            });

        });


        quitButton.setOnAction(event -> {
            stage.close();
        });

        Image logoImage = new Image(
            "file:assets/wizard_logo.png",
            200,
            200,
            true,
            true
        );


        ImageView logo = new ImageView(logoImage);

        logo.setStyle(
            "-fx-effect: dropshadow(gaussian,#7FDBFF,25,0.8,0,0);"
        );


        logo.setFitWidth(95);
        logo.setFitHeight(95);

        logo.setPreserveRatio(true);

        // MENU CONTENT
        VBox menuBox = new VBox(16);

        menuBox.setAlignment(Pos.CENTER);

        menuBox.getChildren().addAll(
                logo,
                title,
                subtitle,
                playButton,
                scoreButton,
                quitButton
        );

        // BACKGROUND IMAGE

        Image bgImage = new Image(
                "file:assets/wizard_background.png",
                900,
                600,
                false,
                true
        );


        ImageView backgroundView = new ImageView(bgImage);


        backgroundView.fitWidthProperty()
            .bind(stage.widthProperty());

        backgroundView.fitHeightProperty()
            .bind(stage.heightProperty());
        backgroundView.setPreserveRatio(false);

        Pane darkOverlay = new Pane();

        darkOverlay.setStyle(
                "-fx-background-color: rgba(0,0,0,0.25);"
        );

        darkOverlay.prefWidthProperty()
                .bind(stage.widthProperty());

        darkOverlay.prefHeightProperty()
                .bind(stage.heightProperty());

        StackPane root = new StackPane();

        StackPane menuScaler = new StackPane();

        menuScaler.setAlignment(Pos.CENTER);

        root.setStyle("-fx-background-color: black;");

        root.getChildren().addAll(
                backgroundView,
                darkOverlay
        );

        Random random = new Random();


        for(int i = 0; i < 25; i++) {

            Circle particle = createParticle();


            particle.setTranslateX(
                random.nextInt(900) - 450
            );

            particle.setTranslateY(
                random.nextInt(600) - 300
            );


            root.getChildren().add(particle);


            TranslateTransition animation =
                    new TranslateTransition(
                            Duration.seconds(3 + random.nextInt(5)),
                            particle
                    );


            animation.setByY(-120);

            animation.setByX(
                random.nextInt(80) - 40
            );

            animation.setAutoReverse(true);

            animation.setCycleCount(
                    TranslateTransition.INDEFINITE
            );


            animation.play();
        }


        menuScaler.getChildren().add(menuBox);
        double designWidth = 900;
        double designHeight = 600;

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {

            double scaleX =
                    newVal.doubleValue() / designWidth;

            double scaleY =
                    stage.getHeight() / designHeight;


            double scale =
                    Math.min(scaleX, scaleY);


            menuScaler.setScaleX(scale);
            menuScaler.setScaleY(scale);

        });


        stage.heightProperty().addListener((obs, oldVal, newVal) -> {

            double scaleX =
                    stage.getWidth() / designWidth;

            double scaleY =
                    newVal.doubleValue() / designHeight;


            double scale =
                    Math.min(scaleX, scaleY);


            menuScaler.setScaleX(scale);
            menuScaler.setScaleY(scale);

        });

        root.getChildren().add(menuScaler);


        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);

        stage.setScene(scene);

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

    private Circle createParticle() {

        Circle particle = new Circle();

        particle.setRadius(2);
        particle.setOpacity(0.7);

        String[] colors = {
            "#B98CFF",
            "#6FE7FF",
            "#FFFFFF"
        };

        particle.setStyle(
            "-fx-fill: " +
            colors[new Random().nextInt(colors.length)]
        );

        return particle;
    }
    
}