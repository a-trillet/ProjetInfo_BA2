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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayScreen{
    public static Drawing drawing= new Drawing();
    private static BorderPane borderPane = new BorderPane();
    public static String towerType = null;
    public static MapClickListener mapClickListener = new MapClickListener(borderPane);
    public static  TowerButtonListener towerButtonListener = new TowerButtonListener(drawing);
    public static Pane map = new Pane(); // permet de supperposer les différents éléments de la map (image, tours,..)


    public BorderPane sceneView(){



        //La map


        //drawing.setOnMouseClicked(e->{if (towerType!=null){new TowerMaker(drawing,towerType,new Point(e.getX(),e.getY()));}});
        //drawing.setOnMouseReleased(new TowerButtonListener(drawing));
        map.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));
        drawing.setOnMouseClicked(e->{
            if ( towerType!=null ) {
                if (MapPane.isOn(new Point(e.getX(), e.getY()))) {
                    new TowerMaker(drawing, towerType, new Point(e.getX(), e.getY()));
                } else {
                    System.out.println("trop proche du chemin");
                }
            }


        });
        drawing.drawLifeGold();





        //le shop

        mapClickListener.displayShop();
        //Info


        borderPane.setCenter(map);

        // listenners
        map.setOnMouseClicked(mapClickListener);


        //intégration d'images test (je savais pas ou mettre)
        //final ImageView selectedImage = new ImageView();
        //Image image1 = new Image(PlayScreen.class.getResourceAsStream("AntoineBg.jpg"));
        //selectedImage.setImage(image1);

        map.getChildren().addAll(drawing);

        return borderPane;
    }







}
