import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tower {
    private int frequency = 50;
    private Point centre;
    private ArrayList<Enemy>enemies = new ArrayList<Enemy>();

    public Tower(Point origin){
        this.centre = origin;
    }

    private Enemy selectTarget(){   //Cette fonction renvoit l'ennemi le plus proche du centre de la tour
        Enemy target = null;
        Double dist = null;
        for(Enemy e : enemies){
            if (target == null | this.centre.distance(e.getOrigin()) < dist){
                target = e;
                dist = this.centre.distance(e.getOrigin());
            }
        }
        return target;
    }
}