import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game extends Application {

    Stage window;
    Scene scene1, scene2;


    public static void main(String[] args)  {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        window = stage;

        Label label1 = new Label("Let's choose between viruses goblins and meteors");
        Button startButton = new Button("Start Game");

        startButton.setOnAction(e -> window.setScene(scene2));

        //Layout 1 - children are laid ou in vertical column
        VBox layout1 =
    }





}
