public class GameConfig {

    // =========================
    // APPRENTICE DIFFICULTY
    // =========================

    public static final int APPRENTICE_MIN_LENGTH = 4;
    public static final int APPRENTICE_MAX_LENGTH = 6;
    public static final int APPRENTICE_START_TIME = 30;
    public static final int APPRENTICE_GOAL = 4;


    // =========================
    // SORCERER DIFFICULTY
    // =========================

    public static final int SORCERER_MIN_LENGTH = 6;
    public static final int SORCERER_MAX_LENGTH = 8;
    public static final int SORCERER_START_TIME = 35;
    public static final int SORCERER_GOAL = 6;


    // =========================
    // GAME RULES
    // =========================

    // Time added for every correct word
    public static final int CORRECT_TIME_BONUS = 10;

    // Points removed for every wrong answer
    public static final int WRONG_WORD_PENALTY = 10;

    // Points gained for remaining seconds
    public static final int TIME_BONUS_PER_SECOND = 5;


    // =========================
    // WORD SCORING
    // =========================

    public static final int THREE_LETTER_POINTS = 30;
    public static final int FOUR_LETTER_POINTS = 50;
    public static final int FIVE_LETTER_POINTS = 80;
    public static final int SIX_LETTER_POINTS = 120;
    public static final int SEVEN_LETTER_POINTS = 170;


    // =========================
    // PRIVATE CONSTRUCTOR
    // =========================

    private GameConfig() {
        // Prevent creating objects from this class.
    }
}

