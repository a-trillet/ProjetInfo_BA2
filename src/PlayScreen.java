import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class PlayScreen{
    public static Drawing drawing= new Drawing();
    private static BorderPane borderPane = new BorderPane();
    public static String towerType = null;




    public BorderPane sceneView(){



        //La map
        MapPane map = new MapPane(); // permet de supperposer les différents éléments de la map (image, tours,..)

        map.getChildren().addAll(drawing);
        drawing.setOnMouseClicked(e->{if (towerType!=null){new TowerMaker(drawing,towerType,new Point(e.getX(),e.getY()));}});
        //drawing.setOnMouseReleased(new TowerButtonListener(drawing));
        map.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));
        drawing.setOnMouseClicked(e->{if (towerType!=null){new TowerMaker(drawing,towerType,new Point(e.getX(),e.getY()));}});
        drawing.drawLifeGold();





        //le shop

        //Info

        displayShop();
        borderPane.setCenter(map);

        // listenners
        map.setOnMouseClicked(new MapClickListener(borderPane));


        return borderPane;
    }
    public static void displayShop(){
        GridPane shop = new GridPane();
        shop.setPadding(new Insets(10,10,10,10));
        shop.setVgap(8);
        shop.setHgap(10);

        Button fireTowerButton = new Button("fire tower");
        fireTowerButton.setOnMouseClicked(e->towerType="FIRE");
        //TowerButtonListener firelistener = new TowerButtonListener(drawing);
        //firelistener.setS("FIRE");
        //fireTowerButton.setOnMousePressed(firelistener);

        GridPane.setConstraints(fireTowerButton,0,0);

        Button iceTowerButton = new Button("ice tower");
        GridPane.setConstraints(iceTowerButton,1,0);

        Button nextWave = new Button("Next Wave");
        GridPane.setConstraints(nextWave, 0,7);
        nextWave.setOnAction(e -> {
            Player.getEnemyFactory().nextWave();
        });

        shop.getChildren().addAll(fireTowerButton,iceTowerButton, nextWave);
        borderPane.setRight(shop);
    }
    public void update(){
        Player p = Player.getPlayer();

    }





}
