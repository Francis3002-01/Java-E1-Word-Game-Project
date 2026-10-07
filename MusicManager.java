import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MusicManager {

    private static MediaPlayer menuMusic;
    private static MediaPlayer gameMusic;
    private static MediaPlayer endMusic;

    // =====================================================
    // MENU MUSIC
    // =====================================================

    public static void playMenuMusic() {

        // Stop any game/end music
        stopGameMusic();
        stopEndMusic();

        // Already playing
        if (menuMusic != null) {
            MediaPlayer.Status status = menuMusic.getStatus();

            if (status == MediaPlayer.Status.PLAYING ||
                status == MediaPlayer.Status.PAUSED) {
                return;
            }
        }

        File musicFile = new File("assets/mainmenu.mp3");

        if (!musicFile.exists()) {
            System.out.println(
                "Menu music not found: " +
                musicFile.getAbsolutePath()
            );
            return;
        }

        Media media = new Media(
            musicFile.toURI().toString()
        );

        menuMusic = new MediaPlayer(media);

        // Loop forever
        menuMusic.setCycleCount(MediaPlayer.INDEFINITE);

        menuMusic.setVolume(0.5);

        menuMusic.play();
    }

    // =====================================================
    // STOP MENU MUSIC
    // =====================================================

    public static void stopMenuMusic() {

        if (menuMusic != null) {
            menuMusic.stop();
            menuMusic.dispose();
            menuMusic = null;
        }
    }

    // =====================================================
    // GAME MUSIC
    // =====================================================

    public static void playGameMusic() {

        // Stop menu/end music
        stopMenuMusic();
        stopEndMusic();

        // Already playing
        if (gameMusic != null) {
            MediaPlayer.Status status = gameMusic.getStatus();

            if (status == MediaPlayer.Status.PLAYING ||
                status == MediaPlayer.Status.PAUSED) {
                return;
            }
        }

        File musicFile = new File("assets/ingame.mp3");

        if (!musicFile.exists()) {
            System.out.println(
                "Game music not found: " +
                musicFile.getAbsolutePath()
            );
            return;
        }

        Media media = new Media(
            musicFile.toURI().toString()
        );

        gameMusic = new MediaPlayer(media);

        // Loop forever
        gameMusic.setCycleCount(MediaPlayer.INDEFINITE);

        gameMusic.setVolume(0.5);

        gameMusic.play();
    }

    // =====================================================
    // STOP GAME MUSIC
    // =====================================================

    public static void stopGameMusic() {

        if (gameMusic != null) {
            gameMusic.stop();
            gameMusic.dispose();
            gameMusic = null;
        }
    }

    // =====================================================
    // END SCREEN MUSIC
    // =====================================================

    public static void playEndMusic(boolean victory) {

        // Stop the gameplay music
        stopGameMusic();

        // Stop menu music just in case
        stopMenuMusic();

        // Stop previous end music
        stopEndMusic();

        String fileName;

        if (victory) {
            fileName = "assets/victory.mp3";
        } else {
            fileName = "assets/lose.mp3";
        }

        File musicFile = new File(fileName);

        if (!musicFile.exists()) {
            System.out.println(
                "End music not found: " +
                musicFile.getAbsolutePath()
            );
            return;
        }

        Media media = new Media(
            musicFile.toURI().toString()
        );

        endMusic = new MediaPlayer(media);

        // Play ONLY ONCE
        endMusic.setCycleCount(1);

        endMusic.setVolume(0.6);

        endMusic.play();
    }

    // =====================================================
    // STOP END MUSIC
    // =====================================================

    public static void stopEndMusic() {

        if (endMusic != null) {
            endMusic.stop();
            endMusic.dispose();
            endMusic = null;
        }
    }

    // =====================================================
    // STOP EVERYTHING
    // =====================================================

    public static void stopAllMusic() {
        stopMenuMusic();
        stopGameMusic();
        stopEndMusic();
    }
}