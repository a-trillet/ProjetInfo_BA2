import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class MapPane {


    private static ArrayList<ArrayList<Point>> allRoutes = new ArrayList<>();                //Path et Track existe en javafx, considérer path pour afficher le chemin

    public static ArrayList<ArrayList<Point>> getAllRoutes(){return allRoutes;}

    private int[][][] easyTrack= {{
            {50,50},
            {300,50},
            {300,400},
            {550,400}},{
            {50,400},
            {50,200},
            {550,200},
            {550,50}
    }
    };

    public MapPane(){
        loadRoute(easyTrack);  /// changer par un fichier
        for (ArrayList<Point> track : allRoutes) {
            PlayScreen.drawing.drawRoute(track);
            PlayScreen.drawing.drawSquare(track.get(0),Color.web("483576"));
            PlayScreen.drawing.drawSquare(track.get(track.size()-1),Color.web("483576"));
        }


    }
    private void loadRoute(int[][][] alltracks){       //lira un fichier, à changer, ou bien créer fonction fichier to int [][] "read track" et garder les tracks par défaut dans le code comme fait là
         for (int [][] track : alltracks) {
             ArrayList<Point> route = new ArrayList<>();
             for (int[] point : track) {
                 route.add(new Point(point[0], point[1]));
             }
             allRoutes.add(route);
         }

    }

    public static boolean isOn(Point point){       // renvoi true si point est trop proche du chemin ! fait en fct des dimensions des carrés des tower
        return true;
    }


}
