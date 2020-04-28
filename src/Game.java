import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.print.DocFlavor;
import java.io.*;

import static java.lang.Integer.sum;

public class Game extends Application {


    Stage window;
    Scene scene1, scene2, scene3;
    //public static Drawing drawing= new Drawing();
    public static Player player= new Player();
    public static String fileString;




    public static void main(String[] args)  {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {


        window = stage;
        System.out.println("Hello");


        //Button menu
        Button menuButton = new Button("Return to Menu");
        menuButton.setOnAction(e -> window.setScene(scene1));

        // Button setting
        Button settingButton = new Button("Settings");
        settingButton.setOnAction(e -> {
              ParameterWindow.display("Setting box","Modify preferences here...");});
        //Button play
        Button playButton = new Button("Play");
        playButton.setOnAction(e->{
            window.setScene(scene2);
        });

        //Layout 1, menu
        VBox layout1 = new VBox();
        layout1.getChildren().addAll(playButton,settingButton,menuButton);
        layout1.setAlignment(Pos.CENTER);
        scene1 = new Scene(layout1, 600, 300);


        //Layout 2 choice of save file
        HBox layout2 = new HBox(20);
        layout2.setAlignment(Pos.CENTER);
        scene2 = new Scene(layout2 , 400, 300);

        // buttons for every file
        for (int i =1 ; i<=3 ; i++ ){
            String ii = String.valueOf(i);
            Button save1 = new Button("New Game");// si le file existe, alors essayer de mettre le nom de player si possible
            if (checkFileExists("Game"+ii+".sav")){
                save1.setText("Game"+ii+".sav");
                save1.setOnMouseClicked(e-> {
                    try {
                        load("Game"+ii+".sav");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    PlayScreen.drawing.drawLifeGold();
                    fileString = "Game" + ii + ".sav";// permet de savoir le nom  du fichier dans lequel save et qu'il soit associable a un bouton
                    window.setScene(scene3);
                });
            }
            else {
                save1.setOnMouseClicked(e -> {
                    player.reset();
                    fileString = "Game" + ii + ".sav";// permet de savoir le nom  du fichier dans lequel save et qu'il soit associable a un bouton
                    window.setScene(scene3);
                });
            }
            layout2.getChildren().add(save1);
        }





        //Layout 3

        PlayScreen playscreen = new PlayScreen();
        scene3 =new Scene(playscreen.sceneView(),1000,500);


        window.setScene(scene2);
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
            try {
                save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void save() throws Exception{

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileString));
        oos.writeObject(player);
        //oos.writeObject();
        oos.flush();
        oos.close();
        System.out.println("Saving");
    }
    public void load(String filename) throws Exception{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
        System.out.println("Loading");
        try {
            player = (Player) ois.readObject();
            System.out.println("Object loaded: " + player.getName());
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ois.close();
    }

   public static boolean checkFileExists(String s){
        return new File(s).isFile();
   }


}
