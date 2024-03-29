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
    private Button endMapEditor=new Button("Confirm Map");
    private ArrayList<ArrayList<Point>> allRoutes=new ArrayList<>();
    private ArrayList<Point>route =new ArrayList<>();
    private Drawing drawing=new Drawing();
    private double pathSize = 0;

    public MapEditor( Stage window, Scene paramScene) {
        super();
        Image image1 = new Image(PlayScreen.class.getResourceAsStream("Images/intellij.jpg"));
        BackgroundImage backgroundimage = new BackgroundImage(image1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        this.setBackground(background);

        // tuto dans mapEditor
        Tips tip = new Tips("On conseil entre 400 et 500 de distance de chemin\nDistance: "+round(pathSize),new Point(600,80),drawing);
        this.getChildren().add(tip);


        TilePane buttonPane = new TilePane();
        buttonPane.getChildren().addAll(addTrack,endTrack);
        this.setBottom(buttonPane);
        this.setCenter(drawing);
        drawing.drawSquare(new Point(550,74), Color.WHITE, 40);
        route.add(new Point(550, 74));


        // listenners
        endMapEditor.setOnMouseClicked(e->{
            new MapFactory(allRoutes);
            window.setScene(paramScene);
        });

        addTrack.setOnMouseClicked(e->{
            route=new ArrayList<>();
            pathSize = 0;
            route.add(new Point(550, 74));
        });

        endTrack.setOnMouseClicked(e->{
            if(route.size()>1) {
                allRoutes.add(route);
                drawing.drawRoute(route);
                route = new ArrayList<>();
                if(allRoutes.size()==1){buttonPane.getChildren().add(endMapEditor);}
            }});

        drawing.setOnMouseClicked(e->{
            if (e.getX()<920 && e.getY()>0 &&e.getY()<500){
            Point  p = new Point(e.getX(),e.getY());
            pathSize += p.distance(route.get(route.size()-1));
            route.add(p);
            tip.setText("On conseil entre 400 et 500 de distance de chemin\nDistance: "+round(pathSize));
            System.out.println(p.getY());}
        });
    }
}



