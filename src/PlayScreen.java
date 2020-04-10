import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PlayScreen {
    public static Drawing drawing= new Drawing();
    private static BorderPane borderPane = new BorderPane();
    public static String towerType = null;


    public BorderPane sceneView(){
        Tower towerTest = new Tower(new Point(100,100));// test tower
        Player.getPlayer().addTower(towerTest);



        //La map
        StackPane map = new StackPane(); // permet de supperposer les différents éléments de la map (image, tours,..)

        drawing.drawSquare(towerTest.getCentre());//  dessin test tower, rectangle rouge
        map.getChildren().addAll(drawing);
        drawing.setOnMouseClicked(e->
        {if (towerType!=null)
            {new TowerMaker(drawing,towerType,new Point(e.getX(),e.getY()));
        }
        }
        );




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
        GridPane.setConstraints(fireTowerButton,0,0);

        Button iceTowerButton = new Button("ice tower");
        GridPane.setConstraints(iceTowerButton,1,0);

        shop.getChildren().addAll(fireTowerButton,iceTowerButton);
        borderPane.setRight(shop);
    }



}
