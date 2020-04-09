import java.util.ArrayList;

public class Tower implements MapClickable {
    private enum type {Fire}


    private int frequency = 50;
    private int cost;
    private Point centre;


    private int damage = 37; // changer pour le mettre dans bullet
    private int level = 1;
    private ArrayList<Enemy>enemies = new ArrayList<Enemy>(); //à supprimer
    private double range = 250;
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
            double sepa = this.centre.distance(e.getCentre());
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

    @Override
    public Point getCentre(){
        return centre;
    }


    @Override
    public Info getInfo() {
        return new Info(this);
    }

    public void upgrade(){ level += 1; }   // + retirer argent selon cout de upgrade qui appartiendrait à fire tower. + changer stats damages,...

    public int getFrequency() {
        return frequency;
    }
    public int getCost() {
        return cost;
    }

    public int getDamage() {
        return damage;
    }

    public int getLevel() {
        return level;
    }

    public double getRange() {
        return range;
    }

    public int getNumberOfKill() {
        return numberOfKill;
    }


}