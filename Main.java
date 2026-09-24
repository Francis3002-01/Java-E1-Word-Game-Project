import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {

    @Override
    public void start(Stage stage) {

        stage.setTitle("Wizard's Escape");

        stage.setResizable(true);

        stage.setWidth(900);
        stage.setHeight(600);


        MainMenu mainMenu = new MainMenu(stage);

        mainMenu.show();


        stage.show();


        // Opening fade animation
        stage.getScene().getRoot().setOpacity(0);


        FadeTransition fade =
                new FadeTransition(
                        Duration.seconds(2),
                        stage.getScene().getRoot()
                );


        fade.setFromValue(0);

        fade.setToValue(1);

        fade.play();

    }


    public static void main(String[] args) {
        launch(args);
    }
}