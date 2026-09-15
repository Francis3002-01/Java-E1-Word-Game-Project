import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class WordManager {

    private final List<String> availableWords;

    private String currentWord;
    private String scrambledWord;

    public WordManager(String difficulty) {

        availableWords = new ArrayList<>();

        int minLength;
        int maxLength;

        // Set the word length based on difficulty
        if (difficulty.equalsIgnoreCase("Apprentice")) {

            minLength = GameConfig.APPRENTICE_MIN_LENGTH;
            maxLength = GameConfig.APPRENTICE_MAX_LENGTH;

        } else if (difficulty.equalsIgnoreCase("Sorcerer")) {

            minLength = GameConfig.SORCERER_MIN_LENGTH;
            maxLength = GameConfig.SORCERER_MAX_LENGTH;

        } else {
            throw new IllegalArgumentException("Invalid difficulty.");
        }

        // Get words from the hardcoded dictionary
        Set<String> dictionary = WordDictionary.getWords();

        // Keep only words that fit the difficulty
        for (String word : dictionary) {

            if (word.length() >= minLength &&
                word.length() <= maxLength) {

                availableWords.add(word);
            }
        }

        if (availableWords.isEmpty()) {
            throw new IllegalStateException(
                    "No words available for this difficulty."
            );
        }
    }


    // Select and scramble a new word
    public String generateNewWord() {

        // Randomly select a word
        currentWord = availableWords.get(
                (int) (Math.random() * availableWords.size())
        );

        scrambledWord = scrambleWord(currentWord);

        return scrambledWord;
    }


    // Scramble the letters of a word
    private String scrambleWord(String word) {

        List<Character> letters = new ArrayList<>();

        for (char letter : word.toCharArray()) {
            letters.add(letter);
        }

        Collections.shuffle(letters);

        StringBuilder scrambled = new StringBuilder();

        for (char letter : letters) {
            scrambled.append(letter);
        }

        // Try again if the scramble is exactly the original word
        if (scrambled.toString().equals(word) && word.length() > 1) {

            Collections.shuffle(letters);

            scrambled.setLength(0);

            for (char letter : letters) {
                scrambled.append(letter);
            }
        }

        return scrambled.toString().toUpperCase();
    }


    // Get the original word
    public String getCurrentWord() {
        return currentWord;
    }


    // Get the current scrambled word
    public String getScrambledWord() {
        return scrambledWord;
    }


    // Get the available words for this difficulty
    public List<String> getAvailableWords() {
        return availableWords;
    }
}
