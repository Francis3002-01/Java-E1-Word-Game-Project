import java.util.HashSet;
import java.util.Set;

public class GameLogic {

    private final String difficulty;
    private int score;
    private int wordsFound;
    private int remainingTime;
    private int currentStreak;
    private int hintsRemaining;
    private final int goal;
    private final Set<String> usedWords = new HashSet<>();

    // Constructor
    public GameLogic(String difficulty) {

        this.difficulty = difficulty;

        score = 0;
        wordsFound = 0;
        currentStreak = 0;

        if (difficulty.equalsIgnoreCase("Apprentice")) {
            remainingTime = GameConfig.APPRENTICE_START_TIME;
            goal = GameConfig.APPRENTICE_GOAL;
            hintsRemaining = GameConfig.APPRENTICE_HINTS;
        }
        
        else if (difficulty.equalsIgnoreCase("Sorcerer")) {
            remainingTime = GameConfig.SORCERER_START_TIME;
            goal = GameConfig.SORCERER_GOAL;
            hintsRemaining = GameConfig.SORCERER_HINTS;
        }
        
        else {
            throw new IllegalArgumentException("Invalid difficulty.");
        }
    }

    // Validate the player's guess
    /*public boolean isValidGuess(String guess, String currentWord) {

        if (guess == null || currentWord == null) {
            return false;
        }

        guess = guess.trim().toLowerCase();
        currentWord = currentWord.trim().toLowerCase();

        
        if (guess.isEmpty()) {
            return false;
        }

        
        if (!WordDictionary.contains(guess)) {
            return false;
        }

        
        return guess.equals(currentWord);
    }*/

    public boolean isValidGuess(String guess, String currentWord) {

        if (guess == null || currentWord == null) {
            return false;
        }

        guess = guess.trim().toLowerCase();
        currentWord = currentWord.trim().toLowerCase();

        if (guess.isEmpty()) {
            return false;
        }

        if (!WordDictionary.contains(guess)) {
            return false;
        }

        if (usedWords.contains(guess)) {
            return false;
        }

        return guess.equals(currentWord);
    }

    // Check whether the guess can be created
    // using only the scramble letters
    /*private boolean canCreateWord(String guess, String scramble) {

        int[] letterCount = new int[26];

        // Count letters in the scramble
        for (char letter : scramble.toCharArray()) {

            if (letter >= 'a' && letter <= 'z') {
                letterCount[letter - 'a']++;
            }
        }

        // Use the scramble letters for the guess
        for (char letter : guess.toCharArray()) {

            if (letter < 'a' || letter > 'z') {
                return false;
            }

            int index = letter - 'a';

            if (letterCount[index] <= 0) {
                return false;
            }

            letterCount[index]--;
        }

        return true;
    }*/


    // Process a correct answer
    public int processCorrectGuess(String guess, double secondsTaken) {

        currentStreak++;

        usedWords.add(guess.trim().toLowerCase());

        int basePoints = calculateWordPoints(guess);
        
        int streakBonus = calculateStreakBonus();
        int speedBonus = calculateSpeedBonus(secondsTaken);

        int subtotal =
            basePoints +
            streakBonus +
            speedBonus;

        double multiplier = getDifficultyMultiplier();

        int totalPoints =
            (int) Math.round(subtotal * multiplier);

        score += totalPoints;

        // Add 10 seconds
        remainingTime += GameConfig.CORRECT_TIME_BONUS;

        // Increase words found
        wordsFound++;

        return totalPoints;
    }


    // Process a wrong answer
    public int processWrongGuess() {

        currentStreak = 0;

        score -= GameConfig.WRONG_WORD_PENALTY;

        return GameConfig.WRONG_WORD_PENALTY;
    }


    // Calculate points based on word length
    public int calculateWordPoints(String word) {

        int length = word.length();

        switch (length) {

            case 3:
                return GameConfig.THREE_LETTER_POINTS;

            case 4:
                return GameConfig.FOUR_LETTER_POINTS;

            case 5:
                return GameConfig.FIVE_LETTER_POINTS;

            case 6:
                return GameConfig.SIX_LETTER_POINTS;

            case 7:
                return GameConfig.SEVEN_LETTER_POINTS;

            case 8:
                return GameConfig.EIGHT_LETTER_POINTS;

            default:
                return 0;
        }
    }

    private int calculateStreakBonus() {

        switch (currentStreak) {

            case 2:
                return GameConfig.STREAK_BONUS_2;

            case 3:
                return GameConfig.STREAK_BONUS_3;

            case 4:
                return GameConfig.STREAK_BONUS_4;

            default:
                if (currentStreak >= 5) {
                    return GameConfig.STREAK_BONUS_MAX;
                }

                return 0;
        }
    }

    private int calculateSpeedBonus(double secondsTaken) {

        if (secondsTaken <= 2) {
            return GameConfig.SPEED_BONUS_2_SECONDS;
        }

        else if (secondsTaken <= 4) {
            return GameConfig.SPEED_BONUS_4_SECONDS;
        }

        else if (secondsTaken <= 6) {
            return GameConfig.SPEED_BONUS_6_SECONDS;
        }

        else if (secondsTaken <= 8) {
            return GameConfig.SPEED_BONUS_8_SECONDS;
        }

        return 0;
    }

    private double getDifficultyMultiplier() {

        if (difficulty.equalsIgnoreCase("Sorcerer")) {
            return GameConfig.SORCERER_MULTIPLIER;
        }

        return GameConfig.APPRENTICE_MULTIPLIER;
    }


    // Remove one second from the timer
    public void decreaseTime() {

        if (remainingTime > 0) {
            remainingTime--;
        }
    }


    // Add time
    public void addTime(int seconds) {

        if (seconds > 0) {
            remainingTime += seconds;
        }
    }


    // Check if the player has completed the level
    public boolean hasClearedLevel() {

        return wordsFound >= goal;
    }


    // Check if time has run out
    public boolean isTimeUp() {

        return remainingTime <= 0;
    }


    // Calculate the time bonus
    // Get final score
    public int calculateFinalScore() {

        return score;
    }


    // Getters
    public String getDifficulty() {
        return difficulty;
    }


    public int getScore() {
        return score;
    }


    public int getWordsFound() {
        return wordsFound;
    }


    public int getRemainingTime() {
        return remainingTime;
    }


    public int getGoal() {
        return goal;
    }

    public boolean useHint() {

        if (hintsRemaining <= 0) {
            return false;
        }

        hintsRemaining--;
        score -= GameConfig.HINT_PENALTY;

        return true;
    }

    public int getHintsRemaining() {
        return hintsRemaining;
    }
}
