import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoreManager {

    private static final String FILE_NAME = "scores.txt";


    // Save a new score
    public void saveScore(PlayerScore playerScore) {

        try {

            BufferedWriter writer =
                    new BufferedWriter(
                            new FileWriter(
                                    FILE_NAME,
                                    true
                            )
                    );


            writer.write(
                    playerScore.getPlayerName()
                    + "|"
                    + playerScore.getDifficulty()
                    + "|"
                    + playerScore.getScore()
            );


            writer.newLine();

            writer.close();

        } catch (IOException e) {

            System.out.println(
                    "Error saving score: "
                    + e.getMessage()
            );
        }
    }


    // Load all saved scores
    public List<PlayerScore> loadScores() {

        List<PlayerScore> scores =
                new ArrayList<>();


        File file =
                new File(FILE_NAME);


        // If the file does not exist,
        // return an empty list
        if (!file.exists()) {

            return scores;
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


                // Make sure the line has
                // player name, difficulty, and score
                if (parts.length == 3) {

                    String playerName =
                            parts[0];

                    String difficulty =
                            parts[1];

                    int score =
                            Integer.parseInt(
                                    parts[2]
                            );


                    PlayerScore playerScore =
                            new PlayerScore(
                                    playerName,
                                    difficulty,
                                    score
                            );


                    scores.add(playerScore);
                }
            }


            reader.close();


        } catch (IOException e) {

            System.out.println(
                    "Error loading scores: "
                    + e.getMessage()
            );

        } catch (NumberFormatException e) {

            System.out.println(
                    "Invalid score found in scores.txt."
            );
        }


        // Sort highest score first
        scores.sort(
                Comparator.comparingInt(
                        PlayerScore::getScore
                ).reversed()
        );


        return scores;
    }
}
