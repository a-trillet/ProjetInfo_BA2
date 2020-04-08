import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game extends Application {


    Stage window;
    Scene scene1, scene2, scene3;
    public static Drawing map= new Drawing();
    public int difficulty = 2; //// A CHANGER ABSOLUMENT, JUSTE UN TEST




    public static void main(String[] args)  {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        window = stage;

        Label label1 = new Label("Let's choose between viruses goblins and meteors");

        //button Start
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> window.setScene(scene2));



        //Layout 1 - children are laid ou in vertical column
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1,startButton);
        layout1.setAlignment(Pos.CENTER);
        scene1 = new Scene(layout1 , 400, 300);

        //Button menu
        Button menuButton = new Button("Return to Menu");
        menuButton.setOnAction(e -> window.setScene(scene1));

        // Button setting
        Button settingButton = new Button("Settings");
        settingButton.setOnAction(e -> {
              difficulty =  ParameterWindow.display("Setting box","Modify preferences here...", difficulty);});
        //Button play
        Button playButton = new Button("Play");
        playButton.setOnAction(e->{
            window.setScene(scene3);
        });

        //Layout 2
        VBox layout2 = new VBox();
        layout2.getChildren().addAll(menuButton, settingButton, playButton);
        layout2.setAlignment(Pos.CENTER);
        scene2 = new Scene(layout2, 600, 300);

        // Layout 3 jeu
        BorderPane borderPane = new BorderPane();
        map.draw(new Point(0,0));// à enlever
        VBox menu = new VBox(); //
        menu.getChildren().add(menuButton);
        borderPane.setCenter(map);
        borderPane.setRight(menu);
        scene3 =new Scene(borderPane,600,400);

        window.setScene(scene1);
        window.setTitle("Cool Name To Be Inserted Here!!!");
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        window.show();

    }

    private void closeProgram(){
        boolean answer = ConfirmBox.display("Warning", "Are you sure you want to exit?");
        if (answer){
            window.close();
        }
    }








}
