import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tower {
    protected int frequency = 50;
    protected Point centre;
    protected ArrayList<Enemy>enemies = new ArrayList<Enemy>();
    protected double range;
    protected Enemy targetEnemy;
    protected int numberOfKill;  //l'idée serait de permettre  l'amélioration des tourelles
                            //que si elle a suffisament tué( + possibilité de rajouter une valeur à chaque classe de ennemi)
    public Tower(Point origin){
        this.centre = origin;
    }

    private Enemy selectTarget(){   //Cette fonction renvoit l'ennemi en range le plus proche du centre de la tour
        Enemy target = null;
        Double dist = null;
        for(Enemy e : enemies){
            double sepa = this.centre.distance(e.getOrigin());
            if ((target == null || sepa < dist) && sepa <= this.range ) {
                target = e;
                dist = sepa;
            }
        }
        return target;
    }
    public void targetIsDead(Enemy e){
        numberOfKill += 1;      // modifiable selon valeur du mob
        targetEnemy = selectTarget();   // change la cible quand le mob meurt( ou sort de la range: RAJOUTER autre part)
    }
}