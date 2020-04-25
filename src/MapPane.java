import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class MapPane extends StackPane {
    private static Point entryPoint;
    public static Point getEntryPoint(){return entryPoint;}


    private static Point endPoint;
    public static Point getEndPoint() {
        return endPoint;
    }

    private ArrayList<ArrayList<Point>> allRoutes = new ArrayList<>();                                   //Path et Track existe en javafx, considérer path pour afficher le chemin
    private ArrayList<Point> mainRoute;       //utile tant qu'on a pas de classe chemin qui envoie les points a enemi
    public ArrayList<Point> getMainRoute(){return mainRoute;}

    private int[][] easyTrack= {
            {60,50},
            {380,50},
            {300,420},
            {670,420},

    };

    public MapPane(){
        loadRoute(easyTrack);  /// changer par un fichier
        for (ArrayList<Point> track : allRoutes) {
            PlayScreen.drawing.drawRoute(track);
        }
        mainRoute = allRoutes.get(0);
        endPoint = mainRoute.get(mainRoute.size()-1);
        Rectangle exitSquare = new Rectangle(36, 36, Color.web("483576"));
        exitSquare.setX(endPoint.getX()-18);
        exitSquare.setY(endPoint.getY()-18);

        entryPoint = mainRoute.get(0);
        Rectangle entrySquare = new Rectangle(36, 36, Color.web("483576"));
        entrySquare.setX(entryPoint.getX()-18);
        entrySquare.setY(entryPoint.getY()-18);

        PlayScreen.drawing.getChildren().addAll(entrySquare, exitSquare);

    }
    private void loadRoute(int[][] track){       //lira un fichier, à changer, ou bien créer fonction fichier to int [][] "read track" et garder les tracks par défaut dans le code comme fait là
         ArrayList<Point> route = new ArrayList<>();
         for ( int[] point : track ){
             route.add(new Point(point[0],point[1]));
         }
         allRoutes.add(route);

    }


}
