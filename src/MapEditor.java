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
    private Drawing drawing= new Drawing();

    public MapEditor(Button endMapEditor) {
        super();
        editMap=endMapEditor;
        TilePane buttonPane = new TilePane();
        buttonPane.getChildren().add(addTrack);
        buttonPane.getChildren().add(endTrack);
        buttonPane.getChildren().add(editMap);
        this.setBottom(buttonPane);
        drawing.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));
        this.setCenter(drawing);


        addTrack.setOnMouseClicked(e->{
            route=new ArrayList<>();
        });
        endTrack.setOnMouseClicked(e->{
            if(route.size()>1) {  //ajouter un message d'erreur si route pas route pas assez longue
                drawing.drawRoute(route);
                PlayScreen.drawing.drawRoute(route);
                allRoutes.add(route);
                route = new ArrayList<>();
                System.out.println(allRoutes);
            }});
        this.setOnMouseClicked(e->route.add(new Point(e.getX(),e.getY())));
    }
    public ArrayList<ArrayList<Point>> getAllRoutes(){return allRoutes;}

}



