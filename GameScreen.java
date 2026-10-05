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
import javafx.scene.control.Button;
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
    private SpriteAnimator wizardDeathAnimator;
    private SpriteAnimator wizardRunAnimator;


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

    private StackPane battleAnimationLayer;

    // =========================
    // STATE
    // =========================

    private boolean animationPlaying = false;

    private boolean gamePaused = false;

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
        // BATTLE ANIMATION LAYER
        // -------------------------

        battleAnimationLayer =
            new StackPane();

        battleAnimationLayer.setMouseTransparent(
            true
        );

        battleAnimationLayer.setPickOnBounds(
            false
        );

        battleAnimationLayer.setAlignment(
            Pos.CENTER
        );


        // -------------------------
        // CENTER LAYOUT
        // -------------------------

        VBox centerLayout = new VBox(20);

        centerLayout.setAlignment(
            Pos.CENTER
        );

        StackPane battleContainer =
            new StackPane();

        battleContainer.setAlignment(
            Pos.CENTER
        );

        battleContainer.getChildren().addAll(
            battleArea,
            battleAnimationLayer
        );


        centerLayout.getChildren().addAll(
            difficultyLabel,
            battleContainer,
            scrambledWordLabel,
            answerInputContainer,
            submitButton,
            messageLabel
        );


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


        // -------------------------
        // ROOT
        // -------------------------

        BorderPane gameLayout = new BorderPane();

        gameLayout.setTop(hud);

        gameLayout.setCenter(centerLayout);

        gameLayout.setBottom(bottomBar);

        gameLayout.setStyle(
            "-fx-background-color: #17233C;"
        );


        StackPane root = new StackPane();

        root.getChildren().add(
            gameLayout
        );

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
    // END CARD WIZARD ANIMATIONS
    // =========================================================

    private ImageView createEndCardWizard(
        boolean cleared
    ) {

        ImageView endWizard =
            new ImageView();


        endWizard.setFitWidth(
            220
        );

        endWizard.setFitHeight(
            220
        );

        endWizard.setPreserveRatio(
            true
        );


        // =====================================================
        // SUCCESS
        // =====================================================

        if (cleared) {

            Image runSheet =
                new Image(
                    new java.io.File(
                        "assets/Wizard/Run.png"
                    ).toURI().toString()
                );


            wizardRunAnimator =
                new SpriteAnimator(
                    endWizard,
                    runSheet,
                    8,
                    150
                );


            wizardRunAnimator.playLoop();


        // =====================================================
        // FAILURE
        // =====================================================

        } else {

            Image deathSheet =
                new Image(
                    new java.io.File(
                        "assets/Wizard/Death.png"
                    ).toURI().toString()
                );


            wizardDeathAnimator =
                new SpriteAnimator(
                    endWizard,
                    deathSheet,
                    7,
                    180
                );


            wizardDeathAnimator.playOnce(
                null
            );
        }


        return endWizard;
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

        if (animationPlaying || gamePaused) {
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

            showFloatingScore(
                "+" + points + " POINTS!",
                true
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

            showFloatingScore(
                "-" + penalty + " POINTS!",
false
            );


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

    // =========================================================
    // FLOATING DAMAGE TEXT
    // =========================================================

    private void showFloatingScore(
        String text,
        boolean positive
    ) {

        Label floatingLabel =
            new Label(text);


        // -------------------------
        // TEXT STYLE
        // -------------------------

        floatingLabel.setFont(
            Font.font(
                "Georgia",
                FontWeight.BOLD,
                22
            )
        );


        floatingLabel.setTextFill(
            positive
                ? Color.LIGHTGREEN
                : Color.SALMON
        );


        floatingLabel.setEffect(
            new DropShadow(
                8,
                positive
                    ? Color.rgb(120, 255, 120, 0.65)
                    : Color.rgb(255, 80, 80, 0.65)
            )
        );


        // -------------------------
        // INITIAL STATE
        // -------------------------

        floatingLabel.setOpacity(
            0
        );

        floatingLabel.setScaleX(
            0.7
        );

        floatingLabel.setScaleY(
            0.7
        );

        floatingLabel.setTranslateY(
            10
        );


        // -------------------------
        // ADD TO BATTLE AREA
        // -------------------------

        battleAnimationLayer
            .getChildren()
            .add(
                floatingLabel
            );


        // -------------------------
        // APPEAR
        // -------------------------

        FadeTransition fadeIn =
            new FadeTransition(
                Duration.millis(180),
                floatingLabel
            );

        fadeIn.setFromValue(
            0
        );

        fadeIn.setToValue(
            1
        );


        ScaleTransition scaleUp =
            new ScaleTransition(
                Duration.millis(180),
                floatingLabel
            );

        scaleUp.setFromX(
            0.7
        );

        scaleUp.setFromY(
            0.7
        );

        scaleUp.setToX(
            1.0
        );

        scaleUp.setToY(
            1.0
        );


        // -------------------------
        // FLOAT UPWARD
        // -------------------------

        TranslateTransition rise =
            new TranslateTransition(
                Duration.millis(900),
                floatingLabel
            );

        rise.setFromY(
            10
        );

        rise.setToY(
            -70
        );


        // -------------------------
        // FADE OUT
        // -------------------------

        FadeTransition fadeOut =
            new FadeTransition(
                Duration.millis(500),
                floatingLabel
            );

        fadeOut.setFromValue(
            1
        );

        fadeOut.setToValue(
            0
        );

        fadeOut.setDelay(
            Duration.millis(400)
        );


        // -------------------------
        // APPEAR ANIMATION
        // -------------------------

        ParallelTransition appear =
            new ParallelTransition(
                fadeIn,
                scaleUp
            );


        // -------------------------
        // FLOAT + FADE
        // -------------------------

        ParallelTransition floatAndFade =
            new ParallelTransition(
                rise,
                fadeOut
            );


        // -------------------------
        // START FLOATING
        // -------------------------

        appear.setOnFinished(
            event -> {

                floatAndFade.play();

            }
        );


        // -------------------------
        // REMOVE AFTER ANIMATION
        // -------------------------

        floatAndFade.setOnFinished(
            event -> {

                battleAnimationLayer
                    .getChildren()
                    .remove(
                        floatingLabel
                    );

            }
        );


        appear.play();
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

            Button quitButton = new Button("QUIT");

            quitButton.setPrefWidth(100);
            quitButton.setPrefHeight(40);

            quitButton.setFont(
                Font.font(
                    "Georgia",
                    FontWeight.BOLD,
                    14
                )
            );

            quitButton.setTextFill(
                Color.web("#F5E6C8")
            );

            // Normal
            quitButton.setStyle(
                "-fx-background-color: #3A263F;" +
                "-fx-background-radius: 6;" +
                "-fx-border-color: #A88B5A;" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 6;" +
                "-fx-cursor: hand;"
            );

            // Hover
            quitButton.setOnMouseEntered(event -> {

                quitButton.setStyle(
                    "-fx-background-color: #503451;" +
                    "-fx-background-radius: 6;" +
                    "-fx-border-color: #D6B878;" +
                    "-fx-border-width: 1.5;" +
                    "-fx-border-radius: 6;" +
                    "-fx-cursor: hand;"
                );

            });

            // Mouse leaves
            quitButton.setOnMouseExited(event -> {

                quitButton.setStyle(
                    "-fx-background-color: #3A263F;" +
                    "-fx-background-radius: 6;" +
                    "-fx-border-color: #A88B5A;" +
                    "-fx-border-width: 1.5;" +
                    "-fx-border-radius: 6;" +
                    "-fx-cursor: hand;"
                );

            });

            quitButton.setOnAction(event -> {
                showQuitConfirmation();
            });

            return quitButton;
        }


        // =========================================================
        // QUIT CONFIRMATION
        // =========================================================

        private void showQuitConfirmation() {

                if (gamePaused) {
            return;
        }

        gamePaused = true;

        if (timeline != null) {
            timeline.pause();
        }

        if (wizardIdleAnimator != null) {
            wizardIdleAnimator.pause();
        }

        if (wizardAttackAnimator != null) {
            wizardAttackAnimator.pause();
        }

        if (wizardHitAnimator != null) {
            wizardHitAnimator.pause();
        }

        if (enemyIdleAnimator != null) {
            enemyIdleAnimator.pause();
        }

        if (enemyAttackAnimator != null) {
            enemyAttackAnimator.pause();
        }

        
            // =========================================
            // OVERLAY
            // =========================================

            StackPane overlayRoot = new StackPane();

            overlayRoot.setPickOnBounds(true);

            overlayRoot.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0.65);"
            );


            // =========================================
            // POPUP
            // =========================================

            VBox popup = new VBox(18);

            popup.setAlignment(Pos.CENTER);

            popup.setMaxWidth(400);
            popup.setMaxHeight(230);

            popup.setPadding(
                new Insets(
                    30,
                    40,
                    30,
                    40
                )
            );

            popup.setStyle(
                "-fx-background-color: #202D48;" +
                "-fx-background-radius: 14;" +
                "-fx-border-color: #A88B5A;" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 14;"
            );


            // =========================================
            // TITLE
            // =========================================

            Label titleLabel = new Label(
                "QUIT THE GAME?"
            );

            titleLabel.setFont(
                Font.font(
                    "Georgia",
                    FontWeight.BOLD,
                    28
                )
            );

            titleLabel.setTextFill(
                Color.web("#F5E6C8")
            );


            // =========================================
            // MESSAGE
            // =========================================

            Label messageLabel = new Label(
                "Are you sure you want to leave?\n" +
                "Your current progress will be lost."
            );

            messageLabel.setFont(
                Font.font(
                    "Arial",
                    15
                )
            );

            messageLabel.setTextFill(
                Color.LIGHTGRAY
            );

            messageLabel.setAlignment(
                Pos.CENTER
            );

            messageLabel.setTextAlignment(
                javafx.scene.text.TextAlignment.CENTER
            );


            // =========================================
            // CANCEL
            // =========================================

            Button cancelButton =
                new Button("CANCEL");

            cancelButton.setPrefWidth(125);
            cancelButton.setPrefHeight(42);

            cancelButton.setFont(
                Font.font(
                    "Georgia",
                    FontWeight.BOLD,
                    14
                )
            );

            cancelButton.setTextFill(
                Color.web("#F5E6C8")
            );

            cancelButton.setStyle(
                "-fx-background-color: #3A4863;" +
                "-fx-background-radius: 6;" +
                "-fx-border-color: #71809C;" +
                "-fx-border-width: 1.2;" +
                "-fx-border-radius: 6;" +
                "-fx-cursor: hand;"
            );


            // =========================================
            // QUIT
            // =========================================

            Button confirmQuitButton =
                new Button("QUIT");

            confirmQuitButton.setPrefWidth(125);
            confirmQuitButton.setPrefHeight(42);

            confirmQuitButton.setFont(
                Font.font(
                    "Georgia",
                    FontWeight.BOLD,
                    14
                )
            );

            confirmQuitButton.setTextFill(
                Color.web("#F5E6C8")
            );

            confirmQuitButton.setStyle(
                "-fx-background-color: #8B3030;" +
                "-fx-background-radius: 6;" +
                "-fx-border-color: #C66A5A;" +
                "-fx-border-width: 1.2;" +
                "-fx-border-radius: 6;" +
                "-fx-cursor: hand;"
            );


            // =========================================
            // HOVER
            // =========================================

            cancelButton.setOnMouseEntered(event -> {

                cancelButton.setStyle(
                    "-fx-background-color: #4B5B78;" +
                    "-fx-background-radius: 6;" +
                    "-fx-border-color: #A8B4C9;" +
                    "-fx-border-width: 1.2;" +
                    "-fx-border-radius: 6;" +
                    "-fx-cursor: hand;"
                );

            });

            cancelButton.setOnMouseExited(event -> {

                cancelButton.setStyle(
                    "-fx-background-color: #3A4863;" +
                    "-fx-background-radius: 6;" +
                    "-fx-border-color: #71809C;" +
                    "-fx-border-width: 1.2;" +
                    "-fx-border-radius: 6;" +
                    "-fx-cursor: hand;"
                );

            });


            confirmQuitButton.setOnMouseEntered(event -> {

                confirmQuitButton.setStyle(
                    "-fx-background-color: #A83A3A;" +
                    "-fx-background-radius: 6;" +
                    "-fx-border-color: #E08A78;" +
                    "-fx-border-width: 1.2;" +
                    "-fx-border-radius: 6;" +
                    "-fx-cursor: hand;"
                );

            });

            confirmQuitButton.setOnMouseExited(event -> {

                confirmQuitButton.setStyle(
                    "-fx-background-color: #8B3030;" +
                    "-fx-background-radius: 6;" +
                    "-fx-border-color: #C66A5A;" +
                    "-fx-border-width: 1.2;" +
                    "-fx-border-radius: 6;" +
                    "-fx-cursor: hand;"
                );

            });


            // =========================================
            // BUTTON ACTIONS
            // =========================================

            cancelButton.setOnAction(event -> {

                StackPane root =
                    (StackPane) stage.getScene().getRoot();

                root.getChildren().remove(
                    overlayRoot
                );

                 // Resume the game
                    gamePaused = false;

                    // Resume timer
                    if (timeline != null) {
                        timeline.play();
                    }

                    // Resume wizard animations
                    if (wizardIdleAnimator != null) {
                        wizardIdleAnimator.resume();
                    }

                    if (wizardAttackAnimator != null) {
                        wizardAttackAnimator.resume();
                    }

                    if (wizardHitAnimator != null) {
                        wizardHitAnimator.resume();
                    }

                    // Resume enemy animations
                    if (enemyIdleAnimator != null) {
                        enemyIdleAnimator.resume();
                    }

                    if (enemyAttackAnimator != null) {
                        enemyAttackAnimator.resume();
                    }

                    // Return focus to the answer field
                    if (answerField != null) {
                        answerField.requestFocus();
                    }

            });


            confirmQuitButton.setOnAction(event -> {

                quitGame();

            });


            // =========================================
            // BUILD POPUP
            // =========================================

            HBox buttonBox = new HBox(15);

            buttonBox.setAlignment(
                Pos.CENTER
            );

            buttonBox.getChildren().addAll(
                cancelButton,
                confirmQuitButton
            );

            popup.getChildren().addAll(
                titleLabel,
                messageLabel,
                buttonBox
            );

            overlayRoot.getChildren().add(
                popup
            );


            // =========================================
            // SHOW
            // =========================================

            StackPane root =
                (StackPane) stage.getScene().getRoot();

            root.getChildren().add(
                overlayRoot
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

        // =====================================================
        // STOP GAME TIMER
        // =====================================================

        if (timeline != null) {
            timeline.stop();
        }


        // =====================================================
        // STOP GAME ANIMATIONS
        // =====================================================

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


        // =====================================================
        // PREVENT FURTHER INPUT
        // =====================================================

        animationPlaying = true;


        // =====================================================
        // CALCULATE FINAL SCORE
        // =====================================================

        int finalScore =
            gameLogic.calculateFinalScore();


        // =====================================================
        // SAVE SCORE
        // =====================================================

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


        // =====================================================
        // END CARD ROOT
        // =====================================================

        StackPane endRoot =
            new StackPane();

        endRoot.setStyle(
            "-fx-background-color: #17233C;"
        );


        // =====================================================
        // DARK OVERLAY
        // =====================================================

        Region overlay =
            new Region();

        overlay.setStyle(
            "-fx-background-color: rgba(0, 0, 0, 0.30);"
        );


        // =====================================================
        // END CARD CONTENT
        // =====================================================

        VBox endContent =
            new VBox(20);

        endContent.setAlignment(
            Pos.CENTER
        );


        // =====================================================
        // TITLE
        // =====================================================

        Label titleLabel;

        if (cleared) {

            titleLabel =
                new Label(
                    "ESCAPED THE DUNGEON!"
                );

        } else {

            titleLabel =
                new Label(
                    "TIME HAS RUN OUT!"
                );
        }


        titleLabel.setFont(
            Font.font(
                "Georgia",
                FontWeight.BOLD,
                40
            )
        );


        titleLabel.setTextFill(
            Color.web("#F5E6C8")
        );


        titleLabel.setEffect(
            new DropShadow(
                12,
                Color.rgb(
                    0,
                    0,
                    0,
                    0.65
                )
            )
        );


        // =====================================================
        // WIZARD
        // =====================================================

        ImageView endWizard =
            createEndCardWizard(
                cleared
            );


        // =====================================================
        // FINAL SCORE
        // =====================================================

        Label finalScoreLabel =
            new Label(
                "Final Score: " +
                finalScore
            );


        finalScoreLabel.setFont(
            Font.font(
                "Serif",
                FontWeight.BOLD,
                26
            )
        );


        finalScoreLabel.setTextFill(
            Color.WHITE
        );


        // =====================================================
        // BUTTON CONTAINER
        // =====================================================

        VBox buttonBox =
            new VBox(12);

        buttonBox.setAlignment(
            Pos.CENTER
        );


        // =====================================================
        // PLAY AGAIN
        // =====================================================

        Button playAgainButton =
            new Button(
                "PLAY AGAIN"
            );


        playAgainButton.setPrefWidth(
            200
        );

        playAgainButton.setPrefHeight(
            45
        );

        playAgainButton.setFont(
            Font.font(
                "Georgia",
                FontWeight.BOLD,
                16
            )
        );

        playAgainButton.setTextFill(
            Color.web("#F5E6C8")
        );


        playAgainButton.setStyle(
            "-fx-background-color: #3A263F;" +
            "-fx-background-radius: 6;" +
            "-fx-border-color: #A88B5A;" +
            "-fx-border-width: 1.5;" +
            "-fx-border-radius: 6;" +
            "-fx-cursor: hand;"
        );


        // =====================================================
        // MAIN MENU
        // =====================================================

        Button menuButton =
            new Button(
                "MAIN MENU"
            );


        menuButton.setPrefWidth(
            200
        );

        menuButton.setPrefHeight(
            45
        );

        menuButton.setFont(
            Font.font(
                "Georgia",
                FontWeight.BOLD,
                16
            )
        );

        menuButton.setTextFill(
            Color.web("#F5E6C8")
        );


        menuButton.setStyle(
            "-fx-background-color: #3A263F;" +
            "-fx-background-radius: 6;" +
            "-fx-border-color: #A88B5A;" +
            "-fx-border-width: 1.5;" +
            "-fx-border-radius: 6;" +
            "-fx-cursor: hand;"
        );


        // =====================================================
        // BUTTON HOVER
        // =====================================================

        playAgainButton.setOnMouseEntered(
            event -> {

                playAgainButton.setStyle(
                    "-fx-background-color: #503451;" +
                    "-fx-background-radius: 6;" +
                    "-fx-border-color: #D6B878;" +
                    "-fx-border-width: 1.5;" +
                    "-fx-border-radius: 6;" +
                    "-fx-cursor: hand;"
                );

            }
        );


        playAgainButton.setOnMouseExited(
            event -> {

                playAgainButton.setStyle(
                    "-fx-background-color: #3A263F;" +
                    "-fx-background-radius: 6;" +
                    "-fx-border-color: #A88B5A;" +
                    "-fx-border-width: 1.5;" +
                    "-fx-border-radius: 6;" +
                    "-fx-cursor: hand;"
                );

            }
        );


        menuButton.setOnMouseEntered(
            event -> {

                menuButton.setStyle(
                    "-fx-background-color: #503451;" +
                    "-fx-background-radius: 6;" +
                    "-fx-border-color: #D6B878;" +
                    "-fx-border-width: 1.5;" +
                    "-fx-border-radius: 6;" +
                    "-fx-cursor: hand;"
                );

            }
        );


        menuButton.setOnMouseExited(
            event -> {

                menuButton.setStyle(
                    "-fx-background-color: #3A263F;" +
                    "-fx-background-radius: 6;" +
                    "-fx-border-color: #A88B5A;" +
                    "-fx-border-width: 1.5;" +
                    "-fx-border-radius: 6;" +
                    "-fx-cursor: hand;"
                );

            }
        );


        // =====================================================
        // PLAY AGAIN ACTION
        // =====================================================

        playAgainButton.setOnAction(
            event -> {

                if (wizardRunAnimator != null) {
                    wizardRunAnimator.stop();
                }

                if (wizardDeathAnimator != null) {
                    wizardDeathAnimator.stop();
                }


                GameScreen gameScreen =
                    new GameScreen(
                        stage,
                        difficulty
                    );


                gameScreen.show();

            }
        );


        // =====================================================
        // MAIN MENU ACTION
        // =====================================================

        menuButton.setOnAction(
            event -> {

                if (wizardRunAnimator != null) {
                    wizardRunAnimator.stop();
                }

                if (wizardDeathAnimator != null) {
                    wizardDeathAnimator.stop();
                }


                MainMenu mainMenu =
                    new MainMenu(
                        stage
                    );


                mainMenu.show();

            }
        );


        // =====================================================
        // BUILD BUTTON AREA
        // =====================================================

        buttonBox.getChildren().addAll(
            playAgainButton,
            menuButton
        );


        // =====================================================
        // BUILD END CARD
        // =====================================================

        endContent.getChildren().addAll(
            titleLabel,
            endWizard,
            finalScoreLabel,
            buttonBox
        );


        // =====================================================
        // ADD TO ROOT
        // =====================================================

        endRoot.getChildren().addAll(
            overlay,
            endContent
        );


        // =====================================================
        // REPLACE ENTIRE GAME SCREEN
        // =====================================================

        Scene endScene =
            new Scene(
                endRoot,
                900,
                600
            );


        stage.setScene(
            endScene
        );
    }
}