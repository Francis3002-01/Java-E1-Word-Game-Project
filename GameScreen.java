import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.DropShadow;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
    // ANIMATED INPUT
    // =========================

    private StackPane answerInputContainer;
    private HBox animatedLetters;


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


    // =========================
    // ANIMATION SETTINGS
    // =========================

    private static final int WIZARD_ATTACK_FRAME_DURATION = 200;

    private static final int ENEMY_ATTACK_FRAME_DURATION = 100;

    private static final int HIT_TRIGGER_FRAMES = 2;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public GameScreen(
        Stage stage,
        String difficulty
    ) {

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

        difficultyLabel.setTextFill(
            Color.WHITE
        );


        // -------------------------
        // SCORE
        // -------------------------

        scoreLabel = new Label(
            "Score: 0"
        );

        scoreLabel.setFont(
            Font.font("Arial", 18)
        );

        scoreLabel.setTextFill(
            Color.WHITE
        );


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

        wordsLabel.setTextFill(
            Color.WHITE
        );


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

        timerLabel.setTextFill(
            Color.WHITE
        );


        // -------------------------
        // SCRAMBLED WORD
        // -------------------------

        scrambledWordLabel = new Label();

        scrambledWordLabel.setFont(
            Font.font("Serif", 42)
        );

        scrambledWordLabel.setTextFill(
            Color.WHITE
        );


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
        // ANIMATED LETTER DISPLAY
        // -------------------------

        animatedLetters = new HBox(3);

        animatedLetters.setAlignment(
            Pos.CENTER
        );

        animatedLetters.setMouseTransparent(
            true
        );


        // -------------------------
        // INPUT CONTAINER
        // -------------------------

        answerInputContainer = new StackPane();

        answerInputContainer.setMaxWidth(300);

        answerInputContainer.setPrefHeight(45);


        // Dark transparent TextField.

        answerField.setStyle(
            "-fx-background-color: rgba(0, 0, 0, 0.35);" +
            "-fx-background-insets: 0;" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: rgba(255, 255, 255, 0.25);" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 8;" +
            "-fx-text-fill: transparent;" +
            "-fx-prompt-text-fill: rgba(255, 255, 255, 0.45);" +
            "-fx-highlight-fill: transparent;" +
            "-fx-highlight-text-fill: transparent;" +
            "-fx-cursor: text;"
        );


        // Put animated letters above TextField.

        answerInputContainer.getChildren().addAll(
            answerField,
            animatedLetters
        );


        // -------------------------
        // SUBMIT BUTTON
        // -------------------------

        submitButton = new Button(
            "CAST SPELL"
        );

        submitButton.setPrefWidth(200);

        submitButton.setPrefHeight(45);

        submitButton.setFont(
            Font.font(
                "Georgia",
                FontWeight.BOLD,
                16
            )
        );

        submitButton.setTextFill(
            Color.web("#F5E6C8")
        );

        setSubmitButtonNormalStyle();


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


        submitButton.setOnMouseEntered(event -> {

            setSubmitButtonHoverStyle();

        });


        submitButton.setOnMouseExited(event -> {

            setSubmitButtonNormalStyle();

        });


        // -------------------------
        // INPUT LISTENER
        // -------------------------

        answerField.textProperty().addListener(
            (observable, oldValue, newValue) -> {

                updateAnimatedInput(newValue);

            }
        );


        // -------------------------
        // CHARACTER ANIMATIONS
        // -------------------------

        setupWizardAnimations();

        setupEnemy();


        // -------------------------
        // FIRST WORD
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
            new Insets(
                10,
                50,
                10,
                50
            )
        );

        Region battleSpacer = new Region();

        // Dynamically control the distance between wizard and enemy
        battleArea.widthProperty().addListener(
            (observable, oldWidth, newWidth) -> {

                double gap = newWidth.doubleValue() * 0.12;

                // Minimum gap
                gap = Math.max(gap, 70);

                // Maximum gap
                gap = Math.min(gap, 160);

                battleSpacer.setPrefWidth(gap);
                battleSpacer.setMinWidth(gap);
                battleSpacer.setMaxWidth(gap);
            }
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
            answerInputContainer,
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
            new Insets(
                15,
                20,
                15,
                20
            )
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
    // SUBMIT BUTTON NORMAL STYLE
    // =========================================================

    private void setSubmitButtonNormalStyle() {

        submitButton.setStyle(
            "-fx-background-color: #3A263F;" +
            "-fx-background-radius: 6;" +
            "-fx-border-color: #A88B5A;" +
            "-fx-border-width: 1.5;" +
            "-fx-border-radius: 6;" +
            "-fx-padding: 10 28 10 28;" +
            "-fx-cursor: hand;"
        );
    }


    // =========================================================
    // SUBMIT BUTTON HOVER STYLE
    // =========================================================

    private void setSubmitButtonHoverStyle() {

        submitButton.setStyle(
            "-fx-background-color: #503451;" +
            "-fx-background-radius: 6;" +
            "-fx-border-color: #D6B878;" +
            "-fx-border-width: 1.5;" +
            "-fx-border-radius: 6;" +
            "-fx-padding: 10 28 10 28;" +
            "-fx-cursor: hand;"
        );
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
            new Insets(
                20,
                30,
                20,
                30
            )
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
                WIZARD_ATTACK_FRAME_DURATION
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


        // Start idle.

        wizardIdleAnimator.playLoop();
    }


    // =========================================================
    // ENEMY SETUP
    // =========================================================

    private void setupEnemy() {

        // Stop previous animations.

        if (enemyIdleAnimator != null) {
            enemyIdleAnimator.stop();
        }

        if (enemyAttackAnimator != null) {
            enemyAttackAnimator.stop();
        }


        // Determine enemy.

        if (
            gameLogic.getWordsFound() + 1
            >= gameLogic.getGoal()
        ) {

            currentEnemy =
                enemyManager.getBoss();

        } else {

            currentEnemy =
                enemyManager.getRandomEnemy();
        }


        // -------------------------
        // CREATE IMAGE VIEW
        // -------------------------

        if (enemyImageView == null) {

            enemyImageView =
                new ImageView();

            enemyImageView.setFitWidth(192);

            enemyImageView.setFitHeight(192);

            enemyImageView.setPreserveRatio(true);

            // Face wizard.

            enemyImageView.setScaleX(-1);
        }


        enemyImageView.setVisible(true);


        // Remove any previous effect.

        enemyImageView.setEffect(null);


        // -------------------------
        // IDLE
        // -------------------------

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


        // -------------------------
        // ATTACK
        // -------------------------

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
                ENEMY_ATTACK_FRAME_DURATION
            );


        // Start idle.

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


        // Create new enemy.

        setupEnemy();


        // Generate word.

        String scrambledWord =
            wordManager.generateNewWord();


        scrambledWordLabel.setText(
            scrambledWord
        );


        // Reset answer.

        answerField.clear();

        answerField.requestFocus();


        // Reset state.

        animationPlaying = false;
    }


    // =========================================================
    // CHECK ANSWER
    // =========================================================

    private void checkAnswer() {

        // Don't accept input during animation.

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


        // -------------------------
        // EMPTY ANSWER
        // -------------------------

        if (guess.isEmpty()) {

            messageLabel.setText(
                "Please enter a word."
            );

            return;
        }


        // -------------------------
        // CHECK ANSWER
        // -------------------------

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


            // Wizard attacks.

            playWizardAttack(() -> {

                // Enemy disappears after attack.

                enemyImageView.setVisible(false);


                // Level complete?

                if (
                    gameLogic.hasClearedLevel()
                ) {

                    endGame(true);

                    return;
                }


                // Next word.

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


            // Wizard gets hit.

            // Enemy attacks.

            playEnemyAttack(() -> {

                enemyIdleAnimator.playLoop();

                animationPlaying = false;

                answerField.requestFocus();

            });
        }
    }


    // =========================================================
    // ANIMATED INPUT
    // =========================================================

    private void updateAnimatedInput(
        String text
    ) {

        animatedLetters.getChildren().clear();


        // Create a label for each character.

        for (
            int i = 0;
            i < text.length();
            i++
        ) {

            char character =
                text.charAt(i);


            Label letter =
                new Label(
                    String.valueOf(character)
                );


            letter.setFont(
                Font.font(
                    "Georgia",
                    FontWeight.BOLD,
                    22
                )
            );


            letter.setTextFill(
                Color.WHITE
            );


            // White glow.

            letter.setEffect(
                new DropShadow(
                    8,
                    Color.rgb(
                        255,
                        255,
                        255,
                        0.65
                    )
                )
            );


            letter.setMinWidth(18);

            letter.setAlignment(
                Pos.CENTER
            );


            // Start lower/smaller.

            letter.setTranslateY(8);

            letter.setScaleX(0.8);

            letter.setScaleY(0.8);

            letter.setOpacity(0);


            animatedLetters.getChildren().add(
                letter
            );


            // -------------------------
            // APPEAR
            // -------------------------

            FadeTransition fade =
                new FadeTransition(
                    Duration.millis(180),
                    letter
                );


            fade.setFromValue(0);

            fade.setToValue(1);


            ScaleTransition scale =
                new ScaleTransition(
                    Duration.millis(180),
                    letter
                );


            scale.setFromX(0.8);

            scale.setFromY(0.8);

            scale.setToX(1);

            scale.setToY(1);


            TranslateTransition rise =
                new TranslateTransition(
                    Duration.millis(180),
                    letter
                );


            rise.setFromY(8);

            rise.setToY(0);


            ParallelTransition appear =
                new ParallelTransition(
                    fade,
                    scale,
                    rise
                );


            appear.setDelay(
                Duration.millis(
                    i * 25
                )
            );


            appear.play();


            // -------------------------
            // FLOAT
            // -------------------------

            TranslateTransition floatAnimation =
                new TranslateTransition(
                    Duration.millis(
                        900 + (i * 80)
                    ),
                    letter
                );


            floatAnimation.setFromY(-2);

            floatAnimation.setToY(2);

            floatAnimation.setAutoReverse(true);

            floatAnimation.setCycleCount(
                TranslateTransition.INDEFINITE
            );


            appear.setOnFinished(
                event -> {

                    floatAnimation.play();

                }
            );
        }
    }


    // =========================================================
    // RED HIT FLASH
    // =========================================================

    private void flashRed(
        ImageView imageView
    ) {

        if (imageView == null) {
            return;
        }


        // Create red overlay.

        ColorInput redColor =
            new ColorInput(
                0,
                0,
                imageView.getFitWidth(),
                imageView.getFitHeight(),
                Color.RED
            );


        Blend redBlend =
            new Blend(
                BlendMode.SRC_ATOP,
                null,
                redColor
            );


        // First flash.

        imageView.setEffect(
            redBlend
        );


        PauseTransition firstFlash =
            new PauseTransition(
                Duration.millis(80)
            );


        firstFlash.setOnFinished(
            event -> {

                imageView.setEffect(
                    null
                );


                // Small gap before second flash.

                PauseTransition gap =
                    new PauseTransition(
                        Duration.millis(50)
                    );


                gap.setOnFinished(
                    event2 -> {

                        // Second flash.

                        imageView.setEffect(
                            redBlend
                        );


                        PauseTransition secondFlash =
                            new PauseTransition(
                                Duration.millis(80)
                            );


                        secondFlash.setOnFinished(
                            event3 -> {

                                imageView.setEffect(
                                    null
                                );

                            }
                        );


                        secondFlash.play();

                    }
                );


                gap.play();

            }
        );


        firstFlash.play();
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

        wizardAttackAnimator.playOnce(
            () -> {

                wizardIdleAnimator.playLoop();

                animationPlaying = false;


                if (onFinished != null) {

                    onFinished.run();

                }

            }
        );


        // -----------------------------------------------------
        // ENEMY HIT
        // -----------------------------------------------------

        int attackDuration =
            8 *
            WIZARD_ATTACK_FRAME_DURATION;


        int hitDelay =
            attackDuration -
            (
                HIT_TRIGGER_FRAMES *
                WIZARD_ATTACK_FRAME_DURATION
            );


        hitDelay =
            Math.max(
                0,
                hitDelay
            );


        PauseTransition hit =
            new PauseTransition(
                Duration.millis(
                    hitDelay
                )
            );


        hit.setOnFinished(
            event -> {

                // Enemy flashes red.

                flashRed(
                    enemyImageView
                );

            }
        );


        hit.play();
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


        // Play hit animation.

        wizardHitAnimator.playOnce(
            () -> {

                wizardIdleAnimator.playLoop();


                if (onFinished != null) {

                    onFinished.run();

                }

            }
        );
    }


    // =========================================================
    // ENEMY ATTACK
    // =========================================================

    private void playEnemyAttack(
        Runnable onFinished
    ) {

        // Stop enemy idle.

        if (enemyIdleAnimator != null) {
            enemyIdleAnimator.stop();
        }


        // Track whether each animation
        // has completed.

        final boolean[] attackFinished = {
            false
        };

        final boolean[] hitFinished = {
            false
        };


        // Finish only when both animations
        // have completed.

        Runnable finishAttack =
            () -> {

                if (
                    attackFinished[0]
                    && hitFinished[0]
                ) {

                    if (
                        enemyIdleAnimator != null
                    ) {

                        enemyIdleAnimator.playLoop();

                    }


                    if (
                        onFinished != null
                    ) {

                        onFinished.run();

                    }
                }
            };


        // -----------------------------------------------------
        // ENEMY ATTACK
        // -----------------------------------------------------

        enemyAttackAnimator.playOnce(
            () -> {

                attackFinished[0] = true;

                finishAttack.run();

            }
        );


        // -----------------------------------------------------
        // HIT TIMING
        // -----------------------------------------------------

        int attackFrames =
            currentEnemy.getAttackFrames();


        int attackDuration =
            attackFrames *
            ENEMY_ATTACK_FRAME_DURATION;


        int hitDelay =
            attackDuration -
            (
                HIT_TRIGGER_FRAMES *
                ENEMY_ATTACK_FRAME_DURATION
            );


        hitDelay =
            Math.max(
                0,
                hitDelay
            );


        PauseTransition hit =
            new PauseTransition(
                Duration.millis(
                    hitDelay
                )
            );


        hit.setOnFinished(
            event -> {

                // Flash wizard red.

                flashRed(
                    wizardImageView
                );


                // Stop wizard idle.

                if (
                    wizardIdleAnimator != null
                ) {

                    wizardIdleAnimator.stop();

                }


                // Play wizard hit.

                wizardHitAnimator.playOnce(
                    () -> {

                        wizardIdleAnimator.playLoop();


                        hitFinished[0] = true;


                        finishAttack.run();

                    }
                );

            }
        );


        hit.play();
    }


    // =========================================================
    // GAME TIMER
    // =========================================================

    private void startTimer() {

        timeline =
            new Timeline(
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
            new Button(
                "QUIT"
            );


        quitButton.setPrefWidth(100);

        quitButton.setPrefHeight(40);

        quitButton.setFont(
            Font.font(
                "Arial",
                14
            )
        );


        quitButton.setOnAction(
            event -> {

                showQuitConfirmation();

            }
        );


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
            .ifPresent(
                response -> {

                    if (
                        response ==
                        ButtonType.OK
                    ) {

                        quitGame();

                    }

                }
            );
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
            new MainMenu(
                stage
            );


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


        // Stop animations.

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

        submitButton.setDisable(
            true
        );

        answerField.setDisable(
            true
        );


        animationPlaying = true;


        // -------------------------
        // RESULT MESSAGE
        // -------------------------

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
        // FINAL SCORE LABEL
        // -------------------------

        Label finalScoreLabel =
            new Label(
                "Final Score: " +
                finalScore
            );


        finalScoreLabel.setFont(
            Font.font(
                "Serif",
                28
            )
        );


        finalScoreLabel.setTextFill(
            Color.WHITE
        );


        // -------------------------
        // PLAY AGAIN
        // -------------------------

        Button playAgainButton =
            new Button(
                "PLAY AGAIN"
            );


        playAgainButton.setPrefWidth(
            180
        );

        playAgainButton.setPrefHeight(
            40
        );


        // -------------------------
        // MAIN MENU
        // -------------------------

        Button menuButton =
            new Button(
                "MAIN MENU"
            );


        menuButton.setPrefWidth(
            180
        );

        menuButton.setPrefHeight(
            40
        );


        // -------------------------
        // PLAY AGAIN ACTION
        // -------------------------

        playAgainButton.setOnAction(
            event -> {

                GameScreen gameScreen =
                    new GameScreen(
                        stage,
                        difficulty
                    );


                gameScreen.show();

            }
        );


        // -------------------------
        // MAIN MENU ACTION
        // -------------------------

        menuButton.setOnAction(
            event -> {

                MainMenu mainMenu =
                    new MainMenu(
                        stage
                    );


                mainMenu.show();

            }
        );


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