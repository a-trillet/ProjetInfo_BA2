import javafx.application.Platform;

import java.io.Serializable;
import java.util.ArrayList;

public class Tower implements MapClickable, Runnable, Serializable {

    private static final long serialVersionUID = 1L;


    private int frequency = 50;


    private int[] upgradeCost = {50, 100, 200};
    private Point centre;
    private int level;
    private static int levelMax = 3;
    private Enemy targetEnemy = null;

    protected String towerType;
    protected static int cost;
    protected double damage;
    protected double range;
    protected int reloadTime;
    protected int bulletRange;
    protected int freezeTime;

    private boolean active = true;
    private double uprgradeBase = 1.0;      // vont servir à augmenter le range et damage
    private double upgradeMultiplier = 0.5; //



    private int numberOfKill;  //l'idée serait de permettre  l'amélioration des tourelles
                                // que si elle a suffisament tué( + possibilité de rajouter une valeur à chaque classe de ennemi)
    private Thread thread = new Thread(this);

    public Tower(Point origin){
        this.centre = origin;
        level = 1;
    }

    public void SetActive(){
        thread.start();
    }


    private Enemy selectTarget(){   //Cette fonction renvoit l'ennemi, en range, le plus proche du centre de la tour
        Enemy target = null;
        Double dist = null;
        for (Enemy e : Game.player.getEnemiesOnMap()) {
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
        return (centre.getY() -15< p.getY() && centre.getY() + 15 > p.getY() && centre.getX() -15< p.getX() && centre.getX() + 15 > p.getX());
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
        if (level <= levelMax) {
            if (Game.player.getGold() >= getUpgradeCost()) {

                Game.player.addGold(-getUpgradeCost());
                level += 1;
                messageUpgrade = "Upgraded";
            } else {
                messageUpgrade = "You don't have enough money";
            }
        }
        else {
            messageUpgrade = "Level is maximal";
        }
        return messageUpgrade;
    }   // + retirer argent selon cout de upgrade qui appartiendrait à fire tower. + changer stats damages,...

    public int getFrequency() {
        return frequency;
    }

    public static int  getCost() {
        return cost;
    }

    public double getDamage() {
        return (damage*(uprgradeBase + (level-1)*upgradeMultiplier  ));
    }

    public int getLevel() {
        return level;
    }

    public double getRange() {
        return (range *(uprgradeBase + (level-1)*upgradeMultiplier)  ); //à chaque level le range est mult par 1.5 puis 2
    }

    public double getFreezeTime(){
        return (freezeTime*(uprgradeBase + (level-1)*upgradeMultiplier) );
    }

    public int getNumberOfKill() {
        return numberOfKill;
    }

    public int getUpgradeCost() {
        return upgradeCost[level-1];
    }

    public String getTowerType(){
        return this.towerType;
    }

    public void shoot(){
        Tower t = this;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                PlayScreen.drawing.draw(new Bullet(damage,t,bulletRange,targetEnemy.getCentre(),new Point(centre.getX(),centre.getY())));
            }
        });
    }

    @Override
    public void run() {
        while(active) {
            if (targetEnemy == null || this.centre.distance(targetEnemy.getCentre()) > range || !targetEnemy.isAlive() ) {
                targetEnemy = selectTarget();
            }
            if (targetEnemy != null) {
                shoot();
               // targetEnemy.decreaseLife(damage);
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