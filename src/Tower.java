import java.util.ArrayList;

public class Tower implements MapClickable, Runnable {

    private enum type {Fire}


    private int frequency = 50;


    private int[] upgradeCost = {50, 100, 200};
    private int cost;
    private Point centre;


    private double damage = 10;
    private int level = 1;
    private double range = 150;
    private Enemy targetEnemy = null;
    private boolean active = true;
    private int reloadTime = 500;



    private int numberOfKill;  //l'idée serait de permettre  l'amélioration des tourelles
                                // que si elle a suffisament tué( + possibilité de rajouter une valeur à chaque classe de ennemi)
    private Thread thread = new Thread(this);

    public Tower(Point origin, int cost){
        this.centre = origin;
        this.cost=cost;
        level = 1;
        thread.start();

    }


    private Enemy selectTarget(){   //Cette fonction renvoit l'ennemi, en range, le plus proche du centre de la tour
        Enemy target = null;
        Double dist = null;
        for (Enemy e : Player.getPlayer().getEnemiesOnMap()) {
            double sepa = this.centre.distance(e.getCentre());
            if ((target == null || sepa < dist) && sepa <= this.range) {
                target = e;
                dist = sepa;
            }
        }
        if (target != null) {
            target.addTargetingTower(this);
        }
        return target;
    }
    public void targetIsDead(Enemy enemi){
        numberOfKill += 1;      // modifiable selon valeur du mob
        //targetEnemy = selectTarget();   // change la cible quand le mob meurt( ou sort de la range: RAJOUTER autre part)
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
        return new InfoTower(this);
    }

    public String upgrade(){
        String messageUpgrade;
        if (Player.getPlayer().getGold() >= getUpgradeCost()){

            Player.getPlayer().addGold(-getUpgradeCost());
            level += 1;
            messageUpgrade = "Upgraded";
        }
        else{ messageUpgrade = "You don't have enough money";}
        return messageUpgrade;
    }   // + retirer argent selon cout de upgrade qui appartiendrait à fire tower. + changer stats damages,...

    public int getFrequency() {
        return frequency;
    }

    public int  getCost() {
        return cost;
    }

    public double getDamage() {
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

    public int getUpgradeCost() {
        return upgradeCost[level-1];
    }

    public void shoot(){
    }

    @Override
    public void run() {
        while(active) {
            if (targetEnemy == null || this.centre.distance(targetEnemy.getCentre()) > range || !targetEnemy.isAlive() ) {
                targetEnemy = selectTarget();
            }
            if (targetEnemy != null) {
                //shoot();
                targetEnemy.decreaseLife(damage);
                System.out.println("shoot"+targetEnemy.getCentre().getY());
                try {
                    Thread.sleep(reloadTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // c'est pour pas attendre une seconde si on a pas tiré, et que le run ne fasse pas buger le programme avec un while (true) sans sleep
            else {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }







}