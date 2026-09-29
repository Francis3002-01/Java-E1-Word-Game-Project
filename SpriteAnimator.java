import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimator {

    private ImageView imageView;
    private Image spriteSheet;

    private Timeline timeline;

    private int frameCount;
    private double frameWidth;
    private double frameHeight;
    private double frameDuration;


    public SpriteAnimator(
        ImageView imageView,
        Image spriteSheet,
        int frameCount,
        double frameDuration
    ) {

        this.imageView = imageView;
        this.spriteSheet = spriteSheet;
        this.frameCount = frameCount;
        this.frameDuration = frameDuration;

        frameWidth =
            spriteSheet.getWidth() / frameCount;

        frameHeight =
            spriteSheet.getHeight();
    }


    private void setFrame(int frame) {

        imageView.setViewport(
            new Rectangle2D(
                frame * frameWidth,
                0,
                frameWidth,
                frameHeight
            )
        );
    }


    private void showFirstFrame() {

        imageView.setImage(spriteSheet);

        setFrame(0);
    }


    // =========================================================
    // LOOPING ANIMATION
    // =========================================================

    public void playLoop() {

        stop();

        showFirstFrame();

        timeline = new Timeline();

        for (int i = 0; i < frameCount; i++) {

            final int frame = i;

            timeline.getKeyFrames().add(
                new KeyFrame(
                    Duration.millis(
                        frameDuration * i
                    ),
                    event -> setFrame(frame)
                )
            );
        }

        timeline.setCycleCount(
            Timeline.INDEFINITE
        );

        timeline.play();
    }


    // =========================================================
    // ONE-TIME ANIMATION
    // =========================================================

    public void playOnce(Runnable onFinished) {

        stop();

        showFirstFrame();

        timeline = new Timeline();

        for (int i = 0; i < frameCount; i++) {

            final int frame = i;

            timeline.getKeyFrames().add(
                new KeyFrame(
                    Duration.millis(
                        frameDuration * i
                    ),
                    event -> setFrame(frame)
                )
            );
        }

        timeline.setOnFinished(event -> {

            // Keep the final frame visible.
            setFrame(frameCount - 1);

            if (onFinished != null) {
                onFinished.run();
            }
        });

        timeline.play();
    }


    // =========================================================
    // STOP
    // =========================================================

    public void stop() {

        if (timeline != null) {

            timeline.stop();

            timeline.setOnFinished(null);
        }
    }


    // =========================================================
    // RESET
    // =========================================================

    public void reset() {

        stop();

        showFirstFrame();
    }
}