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

    private int currentFrame;


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

        this.frameWidth =
            spriteSheet.getWidth() / frameCount;

        this.frameHeight =
            spriteSheet.getHeight();

        this.currentFrame = 0;
    }


    private void setFrame(int frame) {

        currentFrame = frame;

        imageView.setViewport(
            new Rectangle2D(
                frame * frameWidth,
                0,
                frameWidth,
                frameHeight
            )
        );
    }


    private void prepare() {

        imageView.setImage(spriteSheet);

        currentFrame = 0;

        setFrame(0);
    }


    // =========================================================
    // LOOP
    // =========================================================

    public void playLoop() {

        stop();

        prepare();

        timeline = new Timeline(
            new KeyFrame(
                Duration.millis(frameDuration),
                event -> {

                    currentFrame++;

                    if (currentFrame >= frameCount) {
                        currentFrame = 0;
                    }

                    setFrame(currentFrame);
                }
            )
        );

        timeline.setCycleCount(
            Timeline.INDEFINITE
        );

        timeline.play();
    }


    // =========================================================
    // PLAY ONCE
    // =========================================================

    public void playOnce(Runnable onFinished) {

        stop();

        prepare();

        timeline = new Timeline(
            new KeyFrame(
                Duration.millis(frameDuration),
                event -> {

                    currentFrame++;

                    /*
                     * Do NOT loop back to frame 0.
                     *
                     * Stop when the final frame
                     * has been reached.
                     */
                    if (currentFrame >= frameCount) {

                        timeline.stop();

                        currentFrame =
                            frameCount - 1;

                        setFrame(currentFrame);

                        if (onFinished != null) {
                            onFinished.run();
                        }

                        return;
                    }

                    setFrame(currentFrame);
                }
            )
        );

        /*
         * We don't use cycleCount to determine
         * when the animation ends.
         *
         * The frame counter above handles it.
         */
        timeline.setCycleCount(
            Timeline.INDEFINITE
        );

        timeline.play();
    }


    // =========================================================
    // STOP
    // =========================================================

    public void stop() {

        if (timeline != null) {

            timeline.stop();

            timeline.setOnFinished(null);

            timeline = null;
        }
    }


    // =========================================================
    // RESET
    // =========================================================

    public void reset() {

        stop();

        prepare();
    }
}