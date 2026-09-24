public class PlayerScore {

    //private String playerName;
    private String difficulty;
    private int score;

    // Constructor
    public PlayerScore(String difficulty,int score) {
        //this.playerName = playerName;
        this.difficulty = difficulty;
        this.score = score;
    }

    // Get player name
    /*public String getPlayerName() {
        return playerName;
    }*/

    // Get difficulty
    public String getDifficulty() {
        return difficulty;
    }

    // Get score
    public int getScore() {
        return score;
    }
}
