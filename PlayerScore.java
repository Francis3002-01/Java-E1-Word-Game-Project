public class PlayerScore {

    //private String playerName;
    private String difficulty;
    private int score;

    // Constructor
    public PlayerScore(String difficulty, int score) {
        this.difficulty = difficulty;
        this.score = Math.max(0, score);
    }

    // Get difficulty
    public String getDifficulty() {
        return difficulty;
    }

    // Get score
    public int getScore() {
        return score;
    }
}
