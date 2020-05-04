import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

import static java.lang.StrictMath.round;


public class MapEditor extends BorderPane {
    private Button addTrack = new Button("Begin"+" "+"Track");
    private Button endTrack=new Button("End"+" "+"Track");
    private Button editMap;
    private ArrayList<ArrayList<Point>> allRoutes=new ArrayList<>();
    private ArrayList<Point>route =new ArrayList<>();
    private Drawing dr=new Drawing();
    private Drawing drawing;
    private double pathSize = 0;

    public MapEditor(Drawing d, Stage window, Scene paramScene) {
        super();
        Image image1 = new Image(PlayScreen.class.getResourceAsStream("intellij.jpg"));
        BackgroundImage backgroundimage = new BackgroundImage(image1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        this.setBackground(background);
        Tips tip = new Tips("On conseil entre .. et .. de distance de chemin\nDistance: "+round(pathSize),new Point(600,80),drawing);
        this.getChildren().add(tip);


        drawing=d;
        TilePane buttonPane = new TilePane();
        buttonPane.getChildren().add(addTrack);
        buttonPane.getChildren().add(endTrack);
        this.setBottom(buttonPane);
        this.setCenter(dr);
        dr.drawSquare(new Point(550,74), Color.WHITE, 40);
        route.add(new Point(550, 74));

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
            pathSize = 0;
            route.add(new Point(550, 74));
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
            Point  p = new Point(e.getX(),e.getY());
            pathSize += p.distance(route.get(route.size()-1));
            route.add(p);
            tip.setText("On conseil entre .. et .. de distance de chemin\nDistance: "+round(pathSize));
        });
    }
    public ArrayList<ArrayList<Point>> getAllRoutes(){return allRoutes;}

}



