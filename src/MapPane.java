import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.math.*;

import java.util.ArrayList;

public class MapPane {
    private static Point entryPoint;
    public static Point getEntryPoint(){return entryPoint;}


    private static Point endPoint;
    public static Point getEndPoint() {
        return endPoint;
    }

    private static ArrayList<ArrayList<Point>> allRoutes = new ArrayList<>();                //Path et Track existe en javafx, considérer path pour afficher le chemin
    private static ArrayList<Point> mainRoute;       //utile tant qu'on a pas de classe chemin qui envoie les points a enemi

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
        int nombreRoutes = allRoutes.size();
        System.out.println("le isOn s'active");
        System.out.println(nombreRoutes);
        double distMinimale = 30 / Math.pow(2, 0.5);      //demi-hypothénuse des carrés des tower

        for ( int i = 0; i<= allRoutes.size()-1; i++){            //applique ce qui suit à chaque route (i =0,1)
            boolean testBreak = false;

            System.out.println("le 1er for s'active ");


            for ( int j = 0; j <= allRoutes.get(i).size()-2; j++) {     // -2 car comme ça fait le calcul autant de fois qu'il y a de points-1 (càd autant qu'il y a de segment) (j =0,1,2 qd i = 0)

                System.out.println("le 2er for s'active ");
                double x_A = allRoutes.get(i).get(j).getX();
                double y_A = allRoutes.get(i).get(j).getY();
                double x_B = allRoutes.get(i).get(j + 1).getX();
                double y_B = allRoutes.get(i).get(j + 1).getY();
                if (((x_C < x_A && x_C < x_B) || (x_C > x_A && x_C > x_B)) && ((y_C < y_A && y_C < y_B) || (y_C > y_A && y_C > y_B))) {       //le point C ne se trouve pas entre les points A et B
                    System.out.println("cas ou le point la plus proche ne se trouve pas sur le chemin ");
                    double distAC = Math.pow((Math.pow((x_C - x_A), 2) + Math.pow((y_C - y_A), 2)), 0.5);
                    double distBC = Math.pow((Math.pow((x_C - x_B), 2) + Math.pow((y_C - y_B), 2)), 0.5);
                    if (distAC <= distMinimale || distBC <= distMinimale) {
                        System.out.println("trop proche d'un des points");
                        testBreak = true;
                        break;
                    }
                }
                else {
                    System.out.println(" cas ou le point est entre les points ");
                    //calcul du point d'intersection                    remarque : le point d'intersection est le point du chemin le plus proche de tower
                    double a = (y_A - y_B) / (x_A - x_B);
                    double x_I = (a * x_A + y_C - y_A + x_C / a) / (a + 1 / a);
                    double y_I = a * (x_I - x_A) + y_A;
                    //calcul de la distance entre I et centre de la tower
                    double dist = Math.pow((Math.pow((x_C - x_I), 2) + Math.pow((y_C - y_I), 2)), 0.5);

                    if (dist <= distMinimale) {
                        System.out.println("trop proche de la route");
                        bol = false;
                        testBreak = true;
                        System.out.println("le 2 eme for va s'arreter");
                        break;
                    }
                }
                if (testBreak) {
                    System.out.println("le 1 er for va s'arreter");
                    break;
                }
            }
        }
        return bol;
    }


}
