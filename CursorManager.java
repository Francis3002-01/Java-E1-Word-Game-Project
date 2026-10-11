import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class CursorManager {

    public static void setCustomCursor(Scene scene) {
        var resource = CursorManager.class.getResource(
            "/assets/staff_cursor.png"
        );

        if (resource == null) {
            System.out.println("Custom cursor image not found!");
            scene.setCursor(Cursor.DEFAULT);
            return;
        }

        Image cursorImage = new Image(resource.toExternalForm());

        scene.setCursor(new ImageCursor(cursorImage, 0, 0));
    }
}