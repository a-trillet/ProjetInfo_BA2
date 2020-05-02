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
import java.util.ArrayList;

import static java.lang.Integer.sum;

public class Game extends Application {

    PlayScreen playscreen = new PlayScreen();
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

                    fileString = "Game" + ii + ".sav";// permet de savoir le nom  du fichier dans lequel save et qu'il soit associable a un bouton
                    ParameterScene.display("Set your name and choose difficulty", window,scene3); //ajoute a window 1 première scene avec choix de diff puis
                        // a la fin associe scene 3 a window (creer aussi un mapPAne avec la bonne difficultée

                });
            }
            layout2.getChildren().add(save1);
        }
        Button editMapButton = new Button("Edit"+" "+"Map");
        Button endMapEditor=new Button("EditMap");
        MapEditor mapEditor = new MapEditor(endMapEditor);
        endMapEditor.setOnMouseClicked(e->{
            window.setScene(scene3);
            MapPane.addRoutes(mapEditor.getAllRoutes());
            fileString = "Game3.sav";
            player.reset();
            PlayScreen.drawing.drawLifeGold();
        });

        editMapButton.setOnMouseClicked(e->{
            window.setScene(new Scene(mapEditor,1000,500));
        });
        layout2.getChildren().add(editMapButton);




        // Layout 2.5 choix des options avant de lancer sa game, plus sauvergarder les choix


        //Layout 3


        scene3 =new Scene(playscreen.sceneView(),1000,500);


        window.setScene(scene2);
        window.setTitle("Debug Fighter 2");
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

    public static void save() throws Exception{

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileString));
        oos.writeObject(MapPane.getAllRoutes());
        oos.writeObject(player);
        oos.close();
        System.out.println("Saving"+player.getLives());
    }
    public void load(String filename) throws Exception{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
        System.out.println("Loading");
        try {
            //new MapPane(0);
            ArrayList<ArrayList<Point>>allRoutes = (ArrayList<ArrayList<Point>>)ois.readObject();
            MapPane.addRoutes(allRoutes);
            MapPane.draw();
            player = (Player) ois.readObject();
            System.out.println("Object loaded: " + player.getName());
        } catch (ClassNotFoundException | IOException e1) {
            e1.printStackTrace();
        }
        ois.close();
    }

    public static boolean checkFileExists(String s){
        return new File(s).isFile();
    }


}
