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
    public static MapClickListener mapClickListener = new MapClickListener(borderPane);
    public static  TowerButtonListener towerButtonListener = new TowerButtonListener(drawing);
    public static MapPane map = new MapPane(); // permet de supperposer les différents éléments de la map (image, tours,..)


    public BorderPane sceneView(){



        //La map

        map.getChildren().addAll(drawing);
        //drawing.setOnMouseClicked(e->{if (towerType!=null){new TowerMaker(drawing,towerType,new Point(e.getX(),e.getY()));}});
        //drawing.setOnMouseReleased(new TowerButtonListener(drawing));
        map.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));
        drawing.setOnMouseClicked(e->{if (towerType!=null){new TowerMaker(drawing,towerType,new Point(e.getX(),e.getY()));}});
        drawing.drawLifeGold();





        //le shop

        mapClickListener.displayShop();
        //Info

        borderPane.setCenter(map);

        // listenners
        map.setOnMouseClicked(mapClickListener);

        return borderPane;
    }

    public void update(){

        Player p = Player.getPlayer();

    }





}
