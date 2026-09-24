import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ScoreManager {

    private static final String FILE_NAME = "scores.txt";

    // Save score only if it is a new high score
    public void saveScore(PlayerScore playerScore) {

        PlayerScore currentHighScore =
                getHighScore(playerScore.getDifficulty());

        // Only save if there is no existing score
        // or the new score is higher
        if (currentHighScore == null ||
                playerScore.getScore() >
                currentHighScore.getScore()) {

            try {

                // Load existing high scores
                PlayerScore apprenticeScore =
                        getHighScore("Apprentice");

                PlayerScore sorcererScore =
                        getHighScore("Sorcerer");

                // Replace the appropriate high score
                if (playerScore.getDifficulty()
                        .equals("Apprentice")) {

                    apprenticeScore = playerScore;

                } else if (playerScore.getDifficulty()
                        .equals("Sorcerer")) {

                    sorcererScore = playerScore;
                }

                BufferedWriter writer =
                        new BufferedWriter(
                                new FileWriter(FILE_NAME)
                        );

                if (apprenticeScore != null) {

                    writer.write(
                            "Apprentice|"
                            + apprenticeScore.getScore()
                    );

                    writer.newLine();
                }

                if (sorcererScore != null) {

                    writer.write(
                            "Sorcerer|"
                            + sorcererScore.getScore()
                    );

                    writer.newLine();
                }

                writer.close();

            } catch (IOException e) {

                System.out.println(
                        "Error saving score: "
                        + e.getMessage()
                );
            }
        }
    }

    // Get the high score for a specific difficulty
    public PlayerScore getHighScore(String difficulty) {

        File file =
                new File(FILE_NAME);

        if (!file.exists()) {
            return null;
        }

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(file)
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts =
                        line.split("\\|");

                if (parts.length == 2 &&
                        parts[0].equals(difficulty)) {

                    int score =
                            Integer.parseInt(parts[1]);

                    reader.close();

                    return new PlayerScore(
                            difficulty,
                            score
                    );
                }
            }

            reader.close();

        } catch (IOException e) {

            System.out.println(
                    "Error loading score: "
                    + e.getMessage()
            );

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid score found in scores.txt."
            );
        }

        return null;
    }
}