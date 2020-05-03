import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;


public class MapEditor extends BorderPane {
    private Button addTrack = new Button("Begin"+" "+"Track");
    private Button endTrack=new Button("End"+" "+"Track");
    private Button editMap;
    private ArrayList<ArrayList<Point>> allRoutes=new ArrayList<>();
    private ArrayList<Point>route =new ArrayList<>();
    private Drawing dr=new Drawing();
    private Drawing drawing;

    public MapEditor(Button endMapEditor,Drawing d) {
        super();
        editMap=endMapEditor;
        drawing=d;
        TilePane buttonPane = new TilePane();
        buttonPane.getChildren().add(addTrack);
        buttonPane.getChildren().add(endTrack);
        this.setBottom(buttonPane);
        this.setCenter(dr);


        addTrack.setOnMouseClicked(e->{
            route=new ArrayList<>();
        });
        endTrack.setOnMouseClicked(e->{
            if(route.size()>1) {  //ajouter un message d'erreur si route pas route pas assez longue
                drawing.drawRoute(route);
                dr.drawRoute(route);
                allRoutes.add(route);
                route = new ArrayList<>();
                if(allRoutes.size()==1){buttonPane.getChildren().add(editMap);}
            }});
        dr.setOnMouseClicked(e->{
            route.add(new Point(e.getX(),e.getY()));
        });
    }
    public ArrayList<ArrayList<Point>> getAllRoutes(){return allRoutes;}

}



