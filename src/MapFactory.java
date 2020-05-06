import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.*;

import java.util.ArrayList;

public class MapFactory  {


    private static ArrayList<ArrayList<Point>> allRoutes=new ArrayList<>() ;                //Path et Track existe en javafx, considérer path pour afficher le chemin
    public static ArrayList<ArrayList<Point>> getAllRoutes(){return allRoutes;}


    private int[][][] easyTrack= {{
            {400,50},
            {300,50},
            {300,400},
            {550,400}},{
            {400,50},
            {50,200},
            {550,200},
            {550,50}
    }
    };
    private int[][][] normalTrack= {{
            {400,50},
            {700,50},
            {50,300},
            },{
            {400,50},
            {700,50},
            {700,300},
            {50,50}
    }
    };
    private int[][][] hardTrack= {{
            {400,50},
            {600,50},
            {600,400},
            {50,400}},{
            {400,50},
            {400,100},
            {50,100},
            {50,200},
            {400,200},
            {400,300},
            {50,300}
    }
    };
    private int[][][] insaneTrack= {{
            {400,50},
            {400,100},
            {50,100},
            {50,400}},{
            {400,50},
            {400,400},
            {50,400}
    }
    };

    public MapFactory(int difficulty){
        loadRoute(getTrack(difficulty));  /// changer par un fichier
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
    public static boolean isOn(Point point){       // renvoi false si point est trop proche du chemin ! fait en fct des dimensions des carrés des tower
        boolean bol = true;
        double x_C = point.getX();
        double y_C = point.getY();
        double distMinimale = 30 / Math.pow(2, 0.5);      //demi-hypothénuse des carrés des tower

        for ( int i = 0; i<= allRoutes.size()-1; i++){            //applique ce qui suit à chaque route (i =0,1)

            for ( int j = 0; j <= allRoutes.get(i).size()-2; j++) {     // -2 car comme ça fait le calcul autant de fois qu'il y a de points-1 (càd autant qu'il y a de segment) (j =0,1,2 qd i = 0)

                if (!bol) {
                    break;
                }
                double x_A = allRoutes.get(i).get(j).getX();
                double y_A = allRoutes.get(i).get(j).getY();
                double x_B = allRoutes.get(i).get(j + 1).getX();
                double y_B = allRoutes.get(i).get(j + 1).getY();
                if (((x_C < x_A && x_C < x_B) || (x_C > x_A && x_C > x_B)) && ((y_C < y_A && y_C < y_B) || (y_C > y_A && y_C > y_B))) {       //le point C ne se trouve pas entre les points A et B
                    double distAC = Math.pow((Math.pow((x_C - x_A), 2) + Math.pow((y_C - y_A), 2)), 0.5);
                    double distBC = Math.pow((Math.pow((x_C - x_B), 2) + Math.pow((y_C - y_B), 2)), 0.5);
                    if (distAC <= distMinimale || distBC <= distMinimale) {
                        bol = false;
                        break;
                    }
                }
                else {
                    //calcul du point d'intersection                    remarque : le point d'intersection est le point du chemin le plus proche de tower
                    double x_I =0;
                    double y_I=0;
                    if (y_A != y_B && x_A != x_B){
                        double a = (y_A - y_B) / (x_A - x_B);
                        x_I = (a * x_A + y_C - y_A + x_C / a) / (a + 1 / a);
                        y_I = a * (x_I - x_A) + y_A;
                    }
                    else if ( y_A == y_B){
                        x_I = x_C;
                        y_I = y_A;
                    }
                    else if ( x_A == x_B){
                        x_I = x_A;
                        y_I = y_C;
                    }
                    //calcul de la distance entre I et centre de la tower
                    double dist = Math.pow((Math.pow((x_C - x_I), 2) + Math.pow((y_C - y_I), 2)), 0.5);

                    if (dist <= distMinimale) {
                        System.out.println("trop proche de la route");
                        bol = false;
                        break;
                    }
                }
            }
        }
        return bol;
    }


    public static void createNewRoutes(ArrayList<ArrayList<Point>> newallroutes){allRoutes=newallroutes;}

    private  int[][][] getTrack(int difficulty){
        int[][][] track = null;
        switch (difficulty){
            case 1 :
                track = easyTrack;
                break;
            case 2 :
                track = normalTrack;
                break;
            case 3 :
                track = hardTrack;
                break;
            case 4 :
                track = insaneTrack;
                break;
        }
        return track;
    }
}













