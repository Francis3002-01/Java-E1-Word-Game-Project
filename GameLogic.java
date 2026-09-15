public class GameLogic {

    private final String difficulty;
    private int score;
    private int wordsFound;
    private int remainingTime;

    private final int goal;

    // Constructor
    public GameLogic(String difficulty) {

        this.difficulty = difficulty;

        score = 0;
        wordsFound = 0;

        if (difficulty.equalsIgnoreCase("Apprentice")) {

            remainingTime = GameConfig.APPRENTICE_START_TIME;
            goal = GameConfig.APPRENTICE_GOAL;

        } 
        
        else if (difficulty.equalsIgnoreCase("Sorcerer")) {

            remainingTime = GameConfig.SORCERER_START_TIME;
            goal = GameConfig.SORCERER_GOAL;

        } 
        
        else {
            throw new IllegalArgumentException(
                    "Invalid difficulty."
            );
        }
    }


    // Validate the player's guess
    public boolean isValidGuess(String guess, String scramble) {

        if (guess == null || scramble == null) {
            return false;
        }

        // Remove spaces and convert to lowercase
        guess = guess.trim().toLowerCase();
        scramble = scramble.trim().toLowerCase();

        // Empty answer is invalid
        if (guess.isEmpty()) {
            return false;
        }

        // Check dictionary
        if (!WordDictionary.contains(guess)) {
            return false;
        }

        // Check if the word can be created
        // using the available scramble letters
        return canCreateWord(guess, scramble);
    }


    // Check whether the guess can be created
    // using only the scramble letters
    private boolean canCreateWord(String guess, String scramble) {

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
    }


    // Process a correct answer
    public int processCorrectGuess(String guess) {

        int points = calculateWordPoints(guess);

        score += points;

        // Add 10 seconds
        remainingTime += GameConfig.CORRECT_TIME_BONUS;

        // Increase words found
        wordsFound++;

        return points;
    }


    // Process a wrong answer
    public int processWrongGuess() {

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

            default:
                return 0;
        }
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
    public int calculateTimeBonus() {

        return remainingTime * GameConfig.TIME_BONUS_PER_SECOND;
    }


    // Get final score after applying time bonus
    public int calculateFinalScore() {

        return score + calculateTimeBonus();
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
}
