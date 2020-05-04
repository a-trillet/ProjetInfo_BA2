import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;


public class MapEditor extends BorderPane {
    private Button addTrack = new Button("Begin"+" "+"Track");
    private Button endTrack=new Button("End"+" "+"Track");
    private Button editMap;
    private ArrayList<ArrayList<Point>> allRoutes=new ArrayList<>();
    private ArrayList<Point>route =new ArrayList<>();
    private Drawing dr=new Drawing();
    private Drawing drawing;

    public MapEditor(Drawing d, Stage window, Scene paramScene) {
        super();
        Image image1 = new Image(PlayScreen.class.getResourceAsStream("intellij.jpg"));
        BackgroundImage backgroundimage = new BackgroundImage(image1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        this.setBackground(background);
        this.getChildren().add(new Tips("On conseil entre .. et .. de distance de chemin\nDistance: "+0,new Point(20,250),drawing));


        drawing=d;
        TilePane buttonPane = new TilePane();
        buttonPane.getChildren().add(addTrack);
        buttonPane.getChildren().add(endTrack);
        this.setBottom(buttonPane);
        this.setCenter(dr);


        //boutons pour retourner aux parametre
        Button endMapEditor=new Button("Confirm Map");

        endMapEditor.setOnMouseClicked(e->{
            MapFactory mapPane=new MapFactory(1,drawing);
            mapPane.addRoutes(allRoutes);
            ParameterScene.setMapFactory(mapPane);
            window.setScene(paramScene);
        });

        addTrack.setOnMouseClicked(e->{
            route=new ArrayList<>();
        });
        endTrack.setOnMouseClicked(e->{
            if(route.size()>1) {  //ajouter un message d'erreur si route pas route pas assez longue
                drawing.drawRoute(route);
                dr.drawRoute(route);
                allRoutes.add(route);
                route = new ArrayList<>();
                if(allRoutes.size()==1){buttonPane.getChildren().add(endMapEditor);}
            }});
        dr.setOnMouseClicked(e->{
            route.add(new Point(e.getX(),e.getY()));
        });
    }
    public ArrayList<ArrayList<Point>> getAllRoutes(){return allRoutes;}

}



