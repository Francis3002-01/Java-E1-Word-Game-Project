import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        stage.setTitle("Wizard's Escape");
        stage.setWidth(900);
        stage.setHeight(600);
        stage.setResizable(false);

        MainMenu mainMenu = new MainMenu(stage);
        mainMenu.show();

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}