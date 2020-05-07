import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.print.DocFlavor;
import java.io.*;
import java.util.ArrayList;

import static java.lang.Integer.sum;

public class Game extends Application {

    Stage window;
    Scene scene1, scene2, scene3;
    private static Drawing drawing= new Drawing();
    private static PlayScreen playscreen = new PlayScreen(drawing);
    private static Player player= new Player();
    private static String fileString;
    public static boolean isOnGame = false;

    public static Drawing getDrawing(){return drawing;}
    public static Player getPlayer() {
        return player;
    }

    public static void main(String[] args)  {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {


        window = stage;

        //background
        Image image1 = new Image(Game.class.getResourceAsStream("ideaFinal.jpg"));
        BackgroundImage backgroundimage = new BackgroundImage(image1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);


        //Layout 2 choice of save file
        StackPane stackPane = new StackPane();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(250,20,20,190));
        grid.setPrefSize(641,402);

        HBox hBox = new HBox(20);
        GridPane.setConstraints(hBox,0,0);
        grid.getChildren().add(hBox);
        stackPane.setBackground(background);
        stackPane.getChildren().addAll(grid);
        scene2 = new Scene(stackPane , 641, 402);

        // buttons for every file
        for (int i =1 ; i<=3 ; i++ ){
            String ii = String.valueOf(i);
            Button save1 = new Button("New Game");// si le file existe, alors essayer de mettre le nom de player si possible
            if (checkFileExists("Game"+ii+".sav")){
                save1.setText(loadName("Game"+ii+".sav"));
                save1.setOnMouseClicked(e-> {
                    try {
                        loadAndDraw("Game"+ii+".sav");
                        isOnGame=true;
                        load("Game"+ii+".sav");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    drawing.drawLifeGold();
                    PlayScreen.drawRun();
                    fileString = "Game" + ii + ".sav";// permet de savoir le nom  du fichier dans lequel save et qu'il soit associable a un bouton
                    Game.getDrawing().getChildren().add(new Tips(0,new Point(20,250),Game.getDrawing()));
                    window.setScene(scene3);
                });
            }
            else {
                save1.setOnMouseClicked(e -> {
                    isOnGame=true;
                    fileString = "Game" + ii + ".sav";// permet de savoir le nom  du fichier dans lequel save et qu'il soit associable a un bouton
                    ParameterScene.display(window,scene3); //ajoute a window 1 première scene avec choix de diff puis
                        // a la fin associe scene 3 a window (creer aussi un mapPAne avec la bonne difficultée

                });
            }
            hBox.getChildren().add(save1);
        }
        scene3 =new Scene(playscreen.sceneView(),1128,581);

        window.setScene(scene2);
        window.setTitle("Intellij Fighter ");
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
        oos.writeObject(player);
        oos.close();
        System.out.println("Saving"+player.getLives());
    }
    public void load(String filename) throws Exception{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
        System.out.println("Loading");
        try {
            player = (Player) ois.readObject();

            System.out.println("Object loaded: " + player.getName());
            for(Updatable u: player.getEnemiesOnMap()){System.out.println(u.getShape());}

        } catch (ClassNotFoundException | IOException e1) {
            e1.printStackTrace();
        }
        ois.close();
    }
    private String loadName(String fileName) throws Exception{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
        Player player1 = (Player) ois.readObject();
        return player1.getName();
    }
    private void loadAndDraw(String file) throws Exception{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Player player1 = (Player) ois.readObject();
        for (ArrayList<Point> route : player1.getAllRoutes()){
            drawing.drawRoute(route);
        }
    }

    public static boolean checkFileExists(String s){
        return new File(s).isFile();
    }

    public static void lose(){}

    public static void win(){}

}
