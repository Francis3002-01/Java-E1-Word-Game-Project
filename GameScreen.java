import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameScreen {

    private Stage stage;
    private String difficulty;

    private GameLogic gameLogic;
    private WordManager wordManager;
    private EnemyManager enemyManager;

    // =========================
    // UI ELEMENTS
    // =========================

    private Label difficultyLabel;
    private Label scoreLabel;
    private Label wordsLabel;
    private Label timerLabel;
    private Label scrambledWordLabel;
    private Label messageLabel;

    private TextField answerField;
    private Button submitButton;

    // =========================
    // GAME TIMER
    // =========================

    private Timeline timeline;

    // =========================
    // WIZARD
    // =========================

    private ImageView wizardImageView;

    private SpriteAnimator wizardIdleAnimator;
    private SpriteAnimator wizardAttackAnimator;
    private SpriteAnimator wizardHitAnimator;

    // =========================
    // ENEMY
    // =========================

    private ImageView enemyImageView;

    private SpriteAnimator enemyIdleAnimator;
    private SpriteAnimator enemyAttackAnimator;

    private Enemy currentEnemy;

    // =========================
    // BATTLE AREA
    // =========================

    private HBox battleArea;

    // =========================
    // STATE
    // =========================

    private boolean animationPlaying = false;

    public GameScreen(Stage stage, String difficulty) {

        this.stage = stage;
        this.difficulty = difficulty;

        gameLogic = new GameLogic(difficulty);
        wordManager = new WordManager(difficulty);
        enemyManager = new EnemyManager();
    }

    // =========================================================
    // SHOW GAME SCREEN
    // =========================================================

    public void show() {

        // -------------------------
        // DIFFICULTY
        // -------------------------

        difficultyLabel = new Label(
            difficulty.toUpperCase()
        );

        difficultyLabel.setFont(
            Font.font("Serif", 24)
        );

        difficultyLabel.setTextFill(Color.WHITE);


        // -------------------------
        // SCORE
        // -------------------------

        scoreLabel = new Label("Score: 0");

        scoreLabel.setFont(
            Font.font("Arial", 18)
        );

        scoreLabel.setTextFill(Color.WHITE);


        // -------------------------
        // WORDS
        // -------------------------

        wordsLabel = new Label(
            "Words Found: 0 / " +
            gameLogic.getGoal()
        );

        wordsLabel.setFont(
            Font.font("Arial", 18)
        );

        wordsLabel.setTextFill(Color.WHITE);


        // -------------------------
        // TIMER
        // -------------------------

        timerLabel = new Label(
            "Time: " +
            gameLogic.getRemainingTime()
        );

        timerLabel.setFont(
            Font.font("Arial", 18)
        );

        timerLabel.setTextFill(Color.WHITE);


        // -------------------------
        // SCRAMBLED WORD
        // -------------------------

        scrambledWordLabel = new Label();

        scrambledWordLabel.setFont(
            Font.font("Serif", 42)
        );

        scrambledWordLabel.setTextFill(Color.WHITE);


        // -------------------------
        // ANSWER FIELD
        // -------------------------

        answerField = new TextField();

        answerField.setPromptText(
            "Enter your answer"
        );

        answerField.setMaxWidth(300);

        answerField.setPrefHeight(45);

        answerField.setFont(
            Font.font("Arial", 18)
        );


        // -------------------------
        // SUBMIT BUTTON
        // -------------------------

        submitButton = new Button("SUBMIT");

        submitButton.setPrefWidth(200);

        submitButton.setPrefHeight(45);

        submitButton.setFont(
            Font.font("Arial", 18)
        );


        // -------------------------
        // MESSAGE
        // -------------------------

        messageLabel = new Label(
            "Unscramble the word!"
        );

        messageLabel.setFont(
            Font.font("Arial", 16)
        );

        messageLabel.setTextFill(
            Color.LIGHTGRAY
        );


        // -------------------------
        // BUTTON ACTIONS
        // -------------------------

        submitButton.setOnAction(event -> {
            checkAnswer();
        });

        answerField.setOnAction(event -> {
            checkAnswer();
        });


        // -------------------------
        // CREATE CHARACTER ANIMATIONS
        // -------------------------

        setupWizardAnimations();

        setupEnemy();


        // -------------------------
        // GENERATE FIRST WORD
        // -------------------------

        generateNewWord();


        // -------------------------
        // HUD
        // -------------------------

        HBox hud = createHUD();


        // -------------------------
        // BATTLE AREA
        // -------------------------

        battleArea = new HBox();

        battleArea.setAlignment(
            Pos.CENTER
        );

        battleArea.setPadding(
            new Insets(10, 50, 10, 50)
        );

        Region battleSpacer = new Region();

        HBox.setHgrow(
            battleSpacer,
            Priority.ALWAYS
        );

        battleArea.getChildren().addAll(
            wizardImageView,
            battleSpacer,
            enemyImageView
        );


        // -------------------------
        // CENTER LAYOUT
        // -------------------------

        VBox centerLayout = new VBox(20);

        centerLayout.setAlignment(
            Pos.CENTER
        );

        centerLayout.getChildren().addAll(
            difficultyLabel,
            battleArea,
            scrambledWordLabel,
            answerField,
            submitButton,
            messageLabel
        );


        // -------------------------
        // ROOT
        // -------------------------

        BorderPane root = new BorderPane();

        root.setTop(hud);

        root.setCenter(centerLayout);


        // -------------------------
        // BOTTOM BAR
        // -------------------------

        HBox bottomBar = new HBox();

        bottomBar.setAlignment(
            Pos.BOTTOM_LEFT
        );

        bottomBar.setPadding(
            new Insets(15, 20, 15, 20)
        );

        bottomBar.getChildren().add(
            createQuitButton()
        );

        root.setBottom(bottomBar);


        // -------------------------
        // BACKGROUND
        // -------------------------

        root.setStyle(
            "-fx-background-color: #17233C;"
        );


        // -------------------------
        // SCENE
        // -------------------------

        Scene scene = new Scene(
            root,
            900,
            600
        );

        stage.setScene(scene);


        // -------------------------
        // START TIMER
        // -------------------------

        startTimer();


        // -------------------------
        // FOCUS ANSWER FIELD
        // -------------------------

        answerField.requestFocus();
    }


    // =========================================================
    // HUD
    // =========================================================

    private HBox createHUD() {

        VBox scoreBox = new VBox(5);

        scoreBox.setAlignment(
            Pos.CENTER_LEFT
        );

        scoreBox.getChildren().add(
            scoreLabel
        );


        VBox wordsBox = new VBox(5);

        wordsBox.setAlignment(
            Pos.CENTER_RIGHT
        );

        wordsBox.getChildren().add(
            wordsLabel
        );


        VBox timeBox = new VBox(5);

        timeBox.setAlignment(
            Pos.CENTER_RIGHT
        );

        timeBox.getChildren().add(
            timerLabel
        );


        HBox rightHUD = new HBox(35);

        rightHUD.setAlignment(
            Pos.CENTER_RIGHT
        );

        rightHUD.getChildren().addAll(
            wordsBox,
            timeBox
        );


        Region spacer = new Region();

        HBox.setHgrow(
            spacer,
            Priority.ALWAYS
        );


        HBox hud = new HBox(20);

        hud.setAlignment(
            Pos.CENTER_LEFT
        );

        hud.setPadding(
            new Insets(20, 30, 20, 30)
        );

        hud.getChildren().addAll(
            scoreBox,
            spacer,
            rightHUD
        );

        return hud;
    }


    // =========================================================
    // WIZARD ANIMATIONS
    // =========================================================

    private void setupWizardAnimations() {

        // -------------------------
        // IDLE
        // -------------------------

        Image idleSheet = new Image(
            new java.io.File(
                "assets/Wizard/Idle.png"
            ).toURI().toString()
        );


        wizardImageView = new ImageView();

        wizardImageView.setFitWidth(192);

        wizardImageView.setFitHeight(192);

        wizardImageView.setPreserveRatio(true);

        // Wizard faces right.
        wizardImageView.setScaleX(1);


        wizardIdleAnimator =
            new SpriteAnimator(
                wizardImageView,
                idleSheet,
                6,
                150
            );


        // -------------------------
        // ATTACK
        // -------------------------

        Image attackSheet = new Image(
            new java.io.File(
                "assets/Wizard/Attack1.png"
            ).toURI().toString()
        );


        wizardAttackAnimator =
            new SpriteAnimator(
                wizardImageView,
                attackSheet,
                8,
                200
            );


        // -------------------------
        // HIT
        // -------------------------

        Image hitSheet = new Image(
            new java.io.File(
                "assets/Wizard/Hit.png"
            ).toURI().toString()
        );


        wizardHitAnimator =
            new SpriteAnimator(
                wizardImageView,
                hitSheet,
                4,
                200
            );


        // Start idle animation.

        wizardIdleAnimator.playLoop();
    }


    // =========================================================
    // ENEMY SETUP
    // =========================================================

    private void setupEnemy() {

    // Stop the previous enemy animations.

    if (enemyIdleAnimator != null) {
        enemyIdleAnimator.stop();
    }

    if (enemyAttackAnimator != null) {
        enemyAttackAnimator.stop();
    }


    // Determine which enemy should appear.

    if (
        gameLogic.getWordsFound() + 1
        >= gameLogic.getGoal()
    ) {

        // Last word = boss

        currentEnemy =
            enemyManager.getBoss();

    } else {

        // Normal word = random enemy

        currentEnemy =
            enemyManager.getRandomEnemy();
    }


    // =====================================================
    // CREATE IMAGE VIEW ONLY ONCE
    // =====================================================

    if (enemyImageView == null) {

        enemyImageView = new ImageView();

        enemyImageView.setFitWidth(192);

        enemyImageView.setFitHeight(192);

        enemyImageView.setPreserveRatio(true);

        // Enemy sprites face right by default.
        // Flip them so they face the wizard.

        enemyImageView.setScaleX(-1);
    }


    // Make the new enemy visible.

    enemyImageView.setVisible(true);


    // =====================================================
    // LOAD IDLE SPRITE
    // =====================================================

    Image idleSheet = new Image(
        new java.io.File(
            currentEnemy.getIdlePath()
        ).toURI().toString()
    );


    enemyIdleAnimator =
        new SpriteAnimator(
            enemyImageView,
            idleSheet,
            currentEnemy.getIdleFrames(),
            200
        );


    // =====================================================
    // LOAD ATTACK SPRITE
    // =====================================================

    Image attackSheet = new Image(
        new java.io.File(
            currentEnemy.getAttackPath()
        ).toURI().toString()
    );


    enemyAttackAnimator =
        new SpriteAnimator(
            enemyImageView,
            attackSheet,
            currentEnemy.getAttackFrames(),
            100
        );


    // Start the new enemy's idle animation.

    enemyIdleAnimator.playLoop();
}


    // =========================================================
    // GENERATE NEW WORD
    // =========================================================

    private void generateNewWord() {

        // Stop old enemy animations.

        if (enemyIdleAnimator != null) {
            enemyIdleAnimator.stop();
        }

        if (enemyAttackAnimator != null) {
            enemyAttackAnimator.stop();
        }


        // Create a new enemy.

        setupEnemy();


        // Generate the word.

        String scrambledWord =
            wordManager.generateNewWord();


        scrambledWordLabel.setText(
            scrambledWord
        );


        // Reset answer.

        answerField.clear();

        answerField.requestFocus();


        // Reset battle state.

        animationPlaying = false;
    }


    // =========================================================
    // CHECK ANSWER
    // =========================================================

    private void checkAnswer() {

        // Don't accept input while an animation is playing.

        if (animationPlaying) {
            return;
        }


        // Don't accept input after game ends.

        if (
            gameLogic.isTimeUp()
            || gameLogic.hasClearedLevel()
        ) {
            return;
        }


        String guess =
            answerField.getText().trim();


        // Empty answer.

        if (guess.isEmpty()) {

            messageLabel.setText(
                "Please enter a word."
            );

            return;
        }


        // Check answer.

        boolean valid =
            gameLogic.isValidGuess(
                guess,
                wordManager.getScrambledWord()
            );


        // =====================================================
        // CORRECT
        // =====================================================

        if (valid) {

            int points =
                gameLogic.processCorrectGuess(
                    guess
                );


            messageLabel.setText(
                "Correct! +" +
                points +
                " points! +10 seconds!"
            );


            updateLabels();


            // Play wizard attack.

            playWizardAttack(() -> {

                // The enemy disappears immediately
                // after the attack.

                enemyImageView.setVisible(false);


                // Check if level is complete.

                if (
                    gameLogic.hasClearedLevel()
                ) {

                    endGame(true);

                    return;
                }


                // Next word + new enemy.

                generateNewWord();
            });


        // =====================================================
        // WRONG
        // =====================================================

        } else {

            int penalty =
                gameLogic.processWrongGuess();


            messageLabel.setText(
                "Wrong answer! -" +
                penalty +
                " points."
            );


            updateLabels();


            answerField.clear();


            // Play wizard hit animation first.

            playWizardHit(() -> {

                // Enemy attacks after wizard is hit.

                playEnemyAttack(() -> {

                    // Return enemy to idle.

                    enemyIdleAnimator.playLoop();

                    animationPlaying = false;

                    answerField.requestFocus();
                });
            });
        }
    }


    // =========================================================
    // WIZARD ATTACK
    // =========================================================

    private void playWizardAttack(
        Runnable onFinished
    ) {

        animationPlaying = true;


        // Stop idle.

        if (wizardIdleAnimator != null) {
            wizardIdleAnimator.stop();
        }


        // Play attack.

        wizardAttackAnimator.playOnce(() -> {

            // Return to idle.

            wizardIdleAnimator.playLoop();


            animationPlaying = false;


            if (onFinished != null) {
                onFinished.run();
            }
        });
    }


    // =========================================================
    // WIZARD HIT
    // =========================================================

    private void playWizardHit(
        Runnable onFinished
    ) {

        animationPlaying = true;


        // Stop idle.

        if (wizardIdleAnimator != null) {
            wizardIdleAnimator.stop();
        }


        // Play hit.

        wizardHitAnimator.playOnce(() -> {

            // Return to idle.

            wizardIdleAnimator.playLoop();


            if (onFinished != null) {
                onFinished.run();
            }
        });
    }


    // =========================================================
    // ENEMY ATTACK
    // =========================================================

    private void playEnemyAttack(
        Runnable onFinished
    ) {

        // Stop idle.

        if (enemyIdleAnimator != null) {
            enemyIdleAnimator.stop();
        }


        // Play attack.

        enemyAttackAnimator.playOnce(() -> {

            // Return to idle.

            enemyIdleAnimator.playLoop();


            if (onFinished != null) {
                onFinished.run();
            }
        });
    }


    // =========================================================
    // GAME TIMER
    // =========================================================

    private void startTimer() {

        timeline = new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                event -> {

                    gameLogic.decreaseTime();

                    updateTimer();


                    if (
                        gameLogic.isTimeUp()
                    ) {

                        endGame(false);
                    }
                }
            )
        );


        timeline.setCycleCount(
            Timeline.INDEFINITE
        );

        timeline.play();
    }


    // =========================================================
    // UPDATE LABELS
    // =========================================================

    private void updateLabels() {

        scoreLabel.setText(
            "Score: " +
            gameLogic.getScore()
        );


        wordsLabel.setText(
            "Words Found: " +
            gameLogic.getWordsFound() +
            " / " +
            gameLogic.getGoal()
        );


        updateTimer();
    }


    private void updateTimer() {

        timerLabel.setText(
            "Time: " +
            gameLogic.getRemainingTime()
        );
    }


    // =========================================================
    // QUIT BUTTON
    // =========================================================

    private Button createQuitButton() {

        Button quitButton =
            new Button("QUIT");


        quitButton.setPrefWidth(100);

        quitButton.setPrefHeight(40);

        quitButton.setFont(
            Font.font("Arial", 14)
        );


        quitButton.setOnAction(event -> {

            showQuitConfirmation();
        });


        return quitButton;
    }


    // =========================================================
    // QUIT CONFIRMATION
    // =========================================================

    private void showQuitConfirmation() {

        Alert confirmation =
            new Alert(
                Alert.AlertType.CONFIRMATION
            );


        confirmation.setTitle(
            "Quit Game"
        );


        confirmation.setHeaderText(
            "Return to Main Menu?"
        );


        confirmation.setContentText(
            "Your current game progress will be lost."
        );


        confirmation.showAndWait()
            .ifPresent(response -> {

                if (
                    response == ButtonType.OK
                ) {

                    quitGame();
                }
            });
    }


    // =========================================================
    // QUIT GAME
    // =========================================================

    private void quitGame() {

        if (timeline != null) {
            timeline.stop();
        }


        if (wizardIdleAnimator != null) {
            wizardIdleAnimator.stop();
        }


        if (wizardAttackAnimator != null) {
            wizardAttackAnimator.stop();
        }


        if (wizardHitAnimator != null) {
            wizardHitAnimator.stop();
        }


        if (enemyIdleAnimator != null) {
            enemyIdleAnimator.stop();
        }


        if (enemyAttackAnimator != null) {
            enemyAttackAnimator.stop();
        }


        MainMenu mainMenu =
            new MainMenu(stage);


        mainMenu.show();
    }


    // =========================================================
    // END GAME
    // =========================================================

    private void endGame(
        boolean cleared
    ) {

        // Stop timer.

        if (timeline != null) {
            timeline.stop();
        }


        // Stop all animations.

        if (wizardIdleAnimator != null) {
            wizardIdleAnimator.stop();
        }

        if (wizardAttackAnimator != null) {
            wizardAttackAnimator.stop();
        }

        if (wizardHitAnimator != null) {
            wizardHitAnimator.stop();
        }

        if (enemyIdleAnimator != null) {
            enemyIdleAnimator.stop();
        }

        if (enemyAttackAnimator != null) {
            enemyAttackAnimator.stop();
        }


        // Disable input.

        submitButton.setDisable(true);

        answerField.setDisable(true);


        // Stop accepting animation actions.

        animationPlaying = true;


        // Game result message.

        if (cleared) {

            messageLabel.setText(
                "LEVEL CLEARED!"
            );

        } else {

            messageLabel.setText(
                "TIME'S UP!"
            );
        }


        // -------------------------
        // FINAL SCORE
        // -------------------------

        int finalScore =
            gameLogic.calculateFinalScore();


        PlayerScore playerScore =
            new PlayerScore(
                difficulty,
                finalScore
            );


        ScoreManager scoreManager =
            new ScoreManager();


        scoreManager.saveScore(
            playerScore
        );


        // -------------------------
        // RESULT UI
        // -------------------------

        Label finalScoreLabel =
            new Label(
                "Final Score: " +
                finalScore
            );


        finalScoreLabel.setFont(
            Font.font("Serif", 28)
        );


        finalScoreLabel.setTextFill(
            Color.WHITE
        );


        Button playAgainButton =
            new Button("PLAY AGAIN");


        playAgainButton.setPrefWidth(180);

        playAgainButton.setPrefHeight(40);


        Button menuButton =
            new Button("MAIN MENU");


        menuButton.setPrefWidth(180);

        menuButton.setPrefHeight(40);


        // -------------------------
        // PLAY AGAIN
        // -------------------------

        playAgainButton.setOnAction(event -> {

            GameScreen gameScreen =
                new GameScreen(
                    stage,
                    difficulty
                );


            gameScreen.show();
        });


        // -------------------------
        // MAIN MENU
        // -------------------------

        menuButton.setOnAction(event -> {

            MainMenu mainMenu =
                new MainMenu(stage);


            mainMenu.show();
        });


        // -------------------------
        // ADD RESULT CONTROLS
        // -------------------------

        BorderPane root =
            (BorderPane)
            stage.getScene().getRoot();


        VBox centerLayout =
            (VBox)
            root.getCenter();


        centerLayout.getChildren().addAll(
            finalScoreLabel,
            playAgainButton,
            menuButton
        );
    }
}