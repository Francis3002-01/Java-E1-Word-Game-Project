import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameScreen {

    private Stage stage;

    private String difficulty;

    private GameLogic gameLogic;
    private String playerName;
    private WordManager wordManager;

    private Label difficultyLabel;
    private Label scoreLabel;
    private Label wordsLabel;
    private Label timerLabel;
    private Label scrambledWordLabel;
    private Label messageLabel;

    private TextField answerField;
    private Button submitButton;

    private Timeline timeline;


    // Constructor
    /*public GameScreen(Stage stage, String difficulty) {

        this.stage = stage;
        this.difficulty = difficulty;

        // Create the game classes
        gameLogic = new GameLogic(difficulty);
        wordManager = new WordManager(difficulty);
    }*/
 
    public GameScreen(Stage stage,String difficulty,String playerName) {
        this.stage = stage;
        this.difficulty = difficulty;
        this.playerName = playerName;

        gameLogic = new GameLogic(difficulty);
        wordManager = new WordManager(difficulty);
     }

    // Show the game screen
    public void show() {

        // Difficulty label
        difficultyLabel = new Label(
                difficulty.toUpperCase()
        );

        difficultyLabel.setFont(
                Font.font("Serif", 24)
        );

        difficultyLabel.setTextFill(Color.WHITE);


        // Score label
        scoreLabel = new Label(
                "Score: 0"
        );

        scoreLabel.setFont(
                Font.font("Arial", 18)
        );

        scoreLabel.setTextFill(Color.WHITE);


        // Words found label
        wordsLabel = new Label(
                "Words Found: 0 / " +
                gameLogic.getGoal()
        );

        wordsLabel.setFont(
                Font.font("Arial", 18)
        );

        wordsLabel.setTextFill(Color.WHITE);


        // Timer label
        timerLabel = new Label(
                "Time: " +
                gameLogic.getRemainingTime()
        );

        timerLabel.setFont(
                Font.font("Arial", 18)
        );

        timerLabel.setTextFill(Color.WHITE);


        // Scrambled word label
        scrambledWordLabel = new Label();

        scrambledWordLabel.setFont(
                Font.font("Serif", 42)
        );

        scrambledWordLabel.setTextFill(Color.WHITE);


        // Answer input
        answerField = new TextField();

        answerField.setPromptText(
                "Enter your answer"
        );

        answerField.setMaxWidth(300);

        answerField.setPrefHeight(45);

        answerField.setFont(
                Font.font("Arial", 18)
        );


        // Submit button
        submitButton = new Button(
                "SUBMIT"
        );

        submitButton.setPrefWidth(200);

        submitButton.setPrefHeight(45);

        submitButton.setFont(
                Font.font("Arial", 18)
        );


        // Message label
        messageLabel = new Label("Unscramble the word!");

        messageLabel.setFont(Font.font("Arial", 16));

        messageLabel.setTextFill(Color.LIGHTGRAY);


        // Submit button action
        submitButton.setOnAction(event -> {
            checkAnswer();
        });


        // Allow ENTER key to submit
        answerField.setOnAction(event -> {
            checkAnswer();
        });


        // Generate first word
        generateNewWord();


        // Layout
        VBox layout = new VBox(20);

        layout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(
                difficultyLabel,
                scoreLabel,
                wordsLabel,
                timerLabel,
                scrambledWordLabel,
                answerField,
                submitButton,
                messageLabel
        );


        // Background
        layout.setStyle("-fx-background-color: #17233C;");

        // Create scene
        Scene scene = new Scene(layout,900,600);

        // Set scene
        stage.setScene(scene);

        // Start countdown
        startTimer();

        // Automatically focus the answer field
        answerField.requestFocus();
    }


    // Generate a new scrambled word
    private void generateNewWord() {

        String scrambledWord = wordManager.generateNewWord();

        scrambledWordLabel.setText(scrambledWord);

        answerField.clear();

        answerField.requestFocus();
    }


    // Check the player's answer
    private void checkAnswer() {

        // Prevent answering after the game has ended
        if (gameLogic.isTimeUp() ||
            gameLogic.hasClearedLevel()) {

            return;
        }


        String guess =
                answerField.getText().trim();


        // Empty answer
        if (guess.isEmpty()) {
            messageLabel.setText("Please enter a word.");
            return;
        }


        // Check if the answer is valid
        boolean valid =
                gameLogic.isValidGuess(
                        guess,
                        wordManager.getScrambledWord()
                );


        if (valid) {

            // Process correct answer
            int points =
                    gameLogic.processCorrectGuess(
                            guess
                    );


            messageLabel.setText(
                    "Correct! +" +
                    points +
                    " points! +10 seconds!"
            );


            // Update screen
            updateLabels();


            // Check if level is cleared
            if (gameLogic.hasClearedLevel()) {

                endGame(true);

                return;
            }


            // Generate another word
            generateNewWord();

        } else {

            // Process wrong answer
            int penalty =
                    gameLogic.processWrongGuess();


            messageLabel.setText(
                    "Wrong answer! -" +
                    penalty +
                    " points."
            );


            // Update score
            updateLabels();


            answerField.clear();

            answerField.requestFocus();
        }
    }


    // Start the countdown timer
    private void startTimer() {

        timeline = new Timeline(

                new KeyFrame(
                        Duration.seconds(1),
                        event -> {

                            gameLogic.decreaseTime();

                            updateTimer();

                            // Check if time has run out
                            if (gameLogic.isTimeUp()) {

                                endGame(false);
                            }
                        }
                )
        );


        // Repeat every second
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    // Update score and words found
    private void updateLabels() {
        scoreLabel.setText("Score: " +gameLogic.getScore());

        wordsLabel.setText(
                "Words Found: " +
                gameLogic.getWordsFound() +
                " / " +
                gameLogic.getGoal()
        );

        updateTimer();
    }

    // Update timer display
    private void updateTimer() {
        timerLabel.setText("Time: " +gameLogic.getRemainingTime());
    }

    // End the game
    private void endGame(boolean cleared) {

        // Stop the timer
        if (timeline != null) {
            timeline.stop();
        }


        // Disable gameplay controls
        submitButton.setDisable(true);
        answerField.setDisable(true);

        if (cleared) {
            messageLabel.setText("LEVEL CLEARED!");

        } 
        
        else {
            messageLabel.setText("TIME'S UP!");
        }

        // Calculate final score
        int finalScore = gameLogic.calculateFinalScore();

        PlayerScore playerScore =
        new PlayerScore(
                playerName,
                difficulty,
                finalScore
        );

        ScoreManager scoreManager =
                new ScoreManager();

        scoreManager.saveScore(playerScore);


        // Create final score label
        Label finalScoreLabel = new Label("Final Score: " +finalScore);

        finalScoreLabel.setFont(
                Font.font("Serif", 28)
        );

        finalScoreLabel.setTextFill(
                Color.WHITE
        );


        // Create buttons
        Button playAgainButton = new Button("PLAY AGAIN");
        playAgainButton.setPrefWidth(180);
        playAgainButton.setPrefHeight(40);

        Button menuButton = new Button("MAIN MENU");

        menuButton.setPrefWidth(180);

        menuButton.setPrefHeight(40);


        // Play again
        /*playAgainButton.setOnAction(event -> {

            GameScreen gameScreen =
        new GameScreen(
                stage,
                difficulty,
                playerName
        );


        // Return to menu
        menuButton.setOnAction(event -> {
            MainMenu mainMenu = new MainMenu(stage);
            mainMenu.show();
        });*/

        // Play again
        playAgainButton.setOnAction(event -> {

        GameScreen gameScreen =
                new GameScreen(
                        stage,
                        difficulty,
                        playerName
                );

        gameScreen.show();
        });

        // Return to menu
        menuButton.setOnAction(event -> {

        MainMenu mainMenu =
                new MainMenu(stage);

        mainMenu.show();
        });

        // Add final score and buttons
        VBox layout = (VBox) stage.getScene().getRoot();

        layout.getChildren().addAll(
                finalScoreLabel,
                playAgainButton,
                menuButton
        );


        // Make sure the final result is visible
        finalScoreLabel.requestFocus();
    }
}
