import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;

public class Game extends Application {

    private static Stage window;
    private static Scene scene1, scene2;
    private static Drawing drawing= new Drawing();
    private static PlayScreen playscreen = new PlayScreen(drawing);
    private static Player player= new Player();
    private static String fileString;
    public static boolean isOnGame = false;

    public static void main(String[] args)  {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {

        window = stage;

        StackPane stackPane = new StackPane();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(250,20,20,190));
        grid.setPrefSize(641,402);

        //background
        Image image1 = new Image(Game.class.getResourceAsStream("ideaFinal.jpg"));
        BackgroundImage backgroundimage = new BackgroundImage(image1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        stackPane.setBackground(background);

        // boîte horizontal pour les boutons de lancements
        HBox hBox = new HBox(20);
        GridPane.setConstraints(hBox,0,0);
        grid.getChildren().add(hBox);
        stackPane.getChildren().addAll(grid);
        scene1 = new Scene(stackPane , 641, 402);

        // boutons pour chaque fichiers
        for (int i =1 ; i<=3 ; i++ ){
            String ii = String.valueOf(i);
            Button save1 = new Button("New Game");
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
                    window.setScene(scene2);
                });
            }
            else {
                save1.setOnMouseClicked(e -> {
                    isOnGame=true;
                    fileString = "Game" + ii + ".sav";// permet de savoir le nom  du fichier dans lequel save et qu'il soit associable a un bouton
                    ParameterScene.display(window, scene2); //ajoute a window 1 première scene avec choix de diff puis
                        // a la fin associe scene 3 a window (creer aussi un mapPAne avec la bonne difficultée

                });
            }
            hBox.getChildren().add(save1);
        }


        scene2 =new Scene(playscreen.sceneView(),1128,581);

        window.setScene(scene1);
        window.setTitle("Intellij Fighter ");
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        window.show();
    }

    private void load(String filename) throws Exception{             //lance les fichiers de sauvgarde
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

    public static void lose(){
        Pane pane = new Pane();
        pane.relocate(395,200);
        pane.setPrefSize(215,170);
        pane.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        Text text = new Text("Game Over");
        text.setFont(Font.font(40));
        text.setFill(Color.RED);
        text.relocate(5,0);
        Button endGame= new Button("EndGame");
        endGame.setOnMouseClicked(e->window.setScene(new Scene(scene4(),600,200)));
        endGame.relocate(140,140);
        Button goOn = new Button("Continue");
        goOn.relocate(10,140);
        goOn.setOnMouseClicked(e->Platform.runLater(()->drawing.getChildren().remove(pane)));
        pane.getChildren().addAll(goOn,text,endGame);
        Platform.runLater(()->drawing.getChildren().add(pane));
    }

    public static void win(){
        Pane pane = new Pane();
        pane.setPrefSize(215,170);
        pane.relocate(395,200);
        pane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        Text text = new Text("You Win !!!");
        text.setFont(Font.font(40));
        text.setFill(Color.RED);
        text.relocate(5,0);
        Button endGame= new Button("EndGame");
        endGame.setOnMouseClicked(e->window.setScene(new Scene(scene4(),600,200)));
        endGame.relocate(140,140);
        Button goOn = new Button("Continue");
        goOn.relocate(10,140);
        goOn.setOnMouseClicked(e->Platform.runLater(()->drawing.getChildren().remove(pane)));
        pane.getChildren().addAll(goOn,text,endGame);
        Platform.runLater(()->drawing.getChildren().add(pane));
    }

    private static Pane scene4(){
        Pane endScene = new Pane();
        endScene.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        Text endText = new Text("Thanks for playing!");
        endText.setFill(Color.WHITE);
        endText.setFont(Font.font(20));
        endText.relocate(50,50);
        Text credit = new Text("An original game by Dumont Jules && Thomas Vray && Trillet Antoine.");
        credit.setFill(Color.WHITE);
        credit.setFont(Font.font(15));
        credit.relocate(50,120);

        endScene.getChildren().addAll(credit,endText);

        return endScene;
    }
    public static boolean checkFileExists(String s){
        return new File(s).isFile();
    }
    public static Drawing getDrawing(){return drawing;}
    public static Player getPlayer(){return player;}

}

