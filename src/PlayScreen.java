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

import javax.swing.*;

public class PlayScreen{
    public static Drawing drawing= new Drawing();
    private static BorderPane borderPane = new BorderPane();
    public static String towerType = null;
    public static MapClickListener mapClickListener = new MapClickListener(borderPane);
    public static Pane map = new Pane(); // permet de supperposer les différents éléments de la map (image, tours,..)



    public BorderPane sceneView(){
        //La map

        map.getChildren().addAll(drawing);
        //drawing.setOnMouseClicked(e->{if (towerType!=null){new TowerMaker(drawing,towerType,new Point(e.getX(),e.getY()));}});
        //drawing.setOnMouseReleased(new TowerButtonListener(drawing));
        map.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));
        drawing.setOnMouseClicked(e->{
            if ( towerType!=null ) {
                new TowerMaker(drawing, towerType, new Point(e.getX(), e.getY()));
                towerType=null;
            }


        });
        drawing.drawLifeGold();





        //le shop

        mapClickListener.displayShop();
        //Info

        borderPane.setCenter(map);

        // listenners
        map.setOnMouseClicked(mapClickListener);

        // bouton test
        Button testButton = new Button("Save");
        testButton.setOnMouseClicked(e-> {
            try {
                Game.save();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
       /* Button testButton2 = new Button("test222");
        testButton2.relocate(15,200);
        testButton2.setOnMouseClicked(e-> {
            Game.player.newTower.SetActive();
            drawing.drawSquare(Game.player.newTower.getCentre(),new Color(0, 1,0,1));
        });*/
        map.getChildren().addAll(testButton);

        //intégration d'images test (je savais pas ou mettre)
        //final ImageView selectedImage = new ImageView();
        //Image image1 = new Image(PlayScreen.class.getResourceAsStream("AntoineBg.jpg"));
        //selectedImage.setImage(image1);



        return borderPane;
    }







}
