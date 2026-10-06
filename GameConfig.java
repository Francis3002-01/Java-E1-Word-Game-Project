public class GameConfig {

    // =========================
    // APPRENTICE DIFFICULTY
    // =========================

    public static final int APPRENTICE_MIN_LENGTH = 4;
    public static final int APPRENTICE_MAX_LENGTH = 6;
    public static final int APPRENTICE_START_TIME = 30;
    public static final int APPRENTICE_GOAL = 6;


    // =========================
    // SORCERER DIFFICULTY
    // =========================

    public static final int SORCERER_MIN_LENGTH = 6;
    public static final int SORCERER_MAX_LENGTH = 8;
    public static final int SORCERER_START_TIME = 35;
    public static final int SORCERER_GOAL = 8;


    // =========================
    // GAME RULES
    // =========================

    // Time added for every correct word
    public static final int CORRECT_TIME_BONUS = 10;

    // Points removed for every wrong answer
    public static final int WRONG_WORD_PENALTY = 10;

    // Points gained for remaining seconds
    //public static final int TIME_BONUS_PER_SECOND = 5;


    // =========================
    // WORD SCORING
    // =========================

    public static final int THREE_LETTER_POINTS = 30;
    public static final int FOUR_LETTER_POINTS = 50;
    public static final int FIVE_LETTER_POINTS = 80;
    public static final int SIX_LETTER_POINTS = 120;
    public static final int SEVEN_LETTER_POINTS = 170;
    public static final int EIGHT_LETTER_POINTS = 230;

    // =========================
    // HINT SYSTEM
    // =========================

    public static final int APPRENTICE_HINTS = 3;
    public static final int SORCERER_HINTS = 2;
    public static final int HINT_PENALTY = 10;

    // =========================
    // STREAK BONUS
    // =========================

    public static final int STREAK_BONUS_2 = 10;
    public static final int STREAK_BONUS_3 = 20;
    public static final int STREAK_BONUS_4 = 30;
    public static final int STREAK_BONUS_MAX = 40;

    // =========================
    // SPEED BONUS
    // =========================

    public static final int SPEED_BONUS_2_SECONDS = 40;
    public static final int SPEED_BONUS_4_SECONDS = 25;
    public static final int SPEED_BONUS_6_SECONDS = 15;
    public static final int SPEED_BONUS_8_SECONDS = 5;

    // =========================
    // DIFFICULTY MULTIPLIER
    // =========================

    public static final double APPRENTICE_MULTIPLIER = 1.0;
    public static final double SORCERER_MULTIPLIER = 1.5;


    // =========================
    // PRIVATE CONSTRUCTOR
    // =========================

    private GameConfig() {
        // Prevent creating objects from this class.
    }
}

