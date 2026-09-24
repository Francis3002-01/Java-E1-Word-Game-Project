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


public class MainMenu {

    private Stage stage;

    public MainMenu(Stage stage) {
        this.stage = stage;
    }


    public void show() {


        // TITLE
        Label title = new Label("WIZARD'S ESCAPE");

        title.setFont(Font.font("Serif", 50));

        title.setTextFill(Color.web("#E8D8FF"));
        

        // Add glow effect
        title.setStyle(
            "-fx-effect: dropshadow(gaussian, #7FDBFF, 15, 0.5, 0, 0);"
        );


        // SUBTITLE
        Label subtitle = new Label("A Word-Scramble Adventure");
        subtitle.setFont(Font.font("Serif", 20));

        subtitle.setTextFill(
            Color.web("#D8C7FF")
        );

        subtitle.setStyle(
            "-fx-effect: dropshadow(gaussian, #4B0082, 10, 0.5, 0, 0);"
        );



        // BUTTON STYLE
        String buttonStyle =
            "-fx-background-color: rgba(35,20,75,0.85);" +
            "-fx-text-fill: #F5EFFF;" +
            "-fx-font-size: 18px;" +
            "-fx-background-radius: 18;" +
            "-fx-border-color: #B98CFF;" +
            "-fx-border-radius: 18;" +
            "-fx-border-width: 2;" +
            "-fx-cursor: hand;";


        Button playButton = new Button("✨ PLAY ✨");
        Button scoreButton = new Button("📜 SCORE");
        Button quitButton = new Button("🚪 QUIT");


        Button[] buttons = {
            playButton,
            scoreButton,
            quitButton
        };


        for(Button button : buttons){

            button.setPrefWidth(250);
            button.setPrefHeight(55);
            button.setFont(Font.font("Arial",18));
            button.setStyle(buttonStyle);


            button.setOnMouseEntered(e -> {

                button.setStyle(
                    buttonStyle +
                    "-fx-background-color: rgba(120,80,200,0.95);" +
                    "-fx-effect: dropshadow(gaussian,#B98CFF,20,0.7,0,0);"
                );

                button.setScaleX(1.05);
                button.setScaleY(1.05);

            });


            button.setOnMouseExited(e -> {

                button.setStyle(buttonStyle);

                button.setScaleX(1);
                button.setScaleY(1);

            });
        }



        // BUTTON ACTIONS

        playButton.setOnAction(event -> {
            DifficultySelection difficultySelection =
                    new DifficultySelection(stage);

            difficultySelection.show();
        });


        scoreButton.setOnAction(event -> {
            ScoreScreen scoreScreen =
                    new ScoreScreen(stage);

            scoreScreen.show();
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
        VBox menuBox = new VBox(25);

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