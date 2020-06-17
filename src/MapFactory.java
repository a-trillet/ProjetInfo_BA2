
import java.util.ArrayList;

public class MapFactory  {

    private static ArrayList<ArrayList<Point>> allRoutes=new ArrayList<>() ;
    public static ArrayList<ArrayList<Point>> getAllRoutes(){return allRoutes;}

    private int[][][] easyTrack= {{
            {400,74},
            {400,150},
            {200,150},
            {200,400}},{
            {400,74},
            {400,150},
            {600,150},
            {600,400}
    }
    };

    private int[][][] normalTrack= {{
            {400,74},
            {700,74},
            {50,300},
            },{
            {400,74},
            {700,74},
            {700,300},
            {50,74}
    }
    };

    private int[][][] hardTrack= {{
            {400,74},
            {600,74},
            {600,400},
            {50,400}},{
            {400,74},
            {400,100},
            {50,100},
            {50,200},
            {400,200},
            {400,300},
            {50,300}
    }
    };

    private int[][][] insaneTrack= {{
            {400,74},
            {400,100},
            {50,100},
            {50,400}},{
            {400,74},
            {400,400},
            {50,400}
    }
    };

    public MapFactory(int difficulty){
        loadRoute(getTrack(difficulty));
    }

    public MapFactory(ArrayList<ArrayList<Point>> allRoutes){this.allRoutes=allRoutes;}

    private void loadRoute(int[][][] alltracks){
         for (int [][] track : alltracks) {
             ArrayList<Point> route = new ArrayList<>();
             for (int[] point : track) {
                 route.add(new Point(point[0], point[1]));
             }
             allRoutes.add(route);
         }

    }
    public static boolean isNotOn(Point point){       // renvoi false si point trop proche du chemin
        boolean bol = true;
        double x_C = point.getX();
        double y_C = point.getY();
        double distMinimale = 30 / Math.pow(2, 0.5);

        for ( int i = 0; i<= allRoutes.size()-1; i++){

            for ( int j = 0; j <= allRoutes.get(i).size()-2; j++) {

                if (!bol) {
                    break;
                }
                double x_A = allRoutes.get(i).get(j).getX();
                double y_A = allRoutes.get(i).get(j).getY();
                double x_B = allRoutes.get(i).get(j + 1).getX();
                double y_B = allRoutes.get(i).get(j + 1).getY();
                if (((x_C <= x_A && x_C <= x_B) || (x_C >= x_A && x_C >= x_B)) && ((y_C <= y_A && y_C <= y_B) || (y_C >= y_A && y_C >= y_B))) {       //le point C ne se trouve pas entre les points A et B
                    double distAC = Math.pow((Math.pow((x_C - x_A), 2) + Math.pow((y_C - y_A), 2)), 0.5);
                    double distBC = Math.pow((Math.pow((x_C - x_B), 2) + Math.pow((y_C - y_B), 2)), 0.5);
                    if (distAC <= distMinimale || distBC <= distMinimale) {
                        bol = false;
                        break;
                    }
                }
                else {
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
                        bol = false;
                        break;
                    }
                }
            }
        }
        return bol;
    }

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













