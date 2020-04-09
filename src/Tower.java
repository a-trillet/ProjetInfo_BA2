import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tower {
    private enum type {Fire}

    private int frequency = 50;
    private int cost;
    private Point centre;
    private int level;
    private ArrayList<Enemy>enemies = new ArrayList<Enemy>(); //à supprimer
    private double range;
    private Enemy targetEnemy;
    private int numberOfKill;  //l'idée serait de permettre  l'amélioration des tourelles
                                // que si elle a suffisament tué( + possibilité de rajouter une valeur à chaque classe de ennemi)


    public Tower(Point origin){
        this.centre = origin;
        level=1;
    }

    private Enemy selectTarget(){   //Cette fonction renvoit l'ennemi, en range, le plus proche du centre de la tour
        Enemy target = null;
        Double dist = null;
        for(Enemy e : Player.getPlayer().getEnemiesOnMap()){
            double sepa = this.centre.distance(e.getOrigin());
            if ((target == null || sepa < dist) && sepa <= this.range ) {
                target = e;
                dist = sepa;
            }
        }
        if (target != null ){
            target.addTargetingTower(this);
        }
        return target;
    }
    public void targetIsDead(Enemy enemi){
        numberOfKill += 1;      // modifiable selon valeur du mob
        targetEnemy = selectTarget();   // change la cible quand le mob meurt( ou sort de la range: RAJOUTER autre part)
    }


    public boolean isOn(Point p){
        boolean res= false;
        if (p.distance(this.centre)<30){res=true;}  //On peut modifier pour pouvoir cliquer sur tt la carré
        return res;
    }
    public Point getCentre(){
        return centre;
    }

    public void upgrade(){ level += 1; }   // + retirer argent selon cout de upgrade qui appartiendrait à fire tower. + changer stats damages,...


}