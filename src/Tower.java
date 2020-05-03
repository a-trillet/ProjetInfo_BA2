import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Tower implements MapClickable, Runnable, Serializable {

    private static final long serialVersionUID = 1L;
    private static int levelMax = 3;


    private int frequency = 50;
    private int upgradeCost = 50;
    private int level;
    private Enemy targetEnemy = null;
    private Point centre;

    protected static int cost;
    protected double damage;
    protected double range;
    protected int reloadTime;
    protected int bulletRange;
    protected double freezeTime;
    protected String towerType;
    protected boolean powerActive = false;

    private boolean active = true;
    private double uprgradeBase = 1.0;      // vont servir à augmenter le range et damage
    private double upgradeMultiplier = 0.5; //
    private int numberOfKill;
    private transient Thread thread = new Thread(this);


    public Tower(Point origin){
        this.centre = origin;
        level = 1;
    }

    public void powerActivation(){
        powerActive = true;
    }
    public void powerDesactivation(){
        powerActive = false;
    }

    public void setActive(){
        thread.start();
    }

    private Enemy selectTarget(){   //Cette fonction renvoit l'ennemi, en range, le plus proche du centre de la tour
        Enemy target = null;
        Double dist = null;
        for (Enemy e : Game.player.getEnemiesOnMap()) {
            double sepa = this.centre.distance(e.getCentre());
            if ((target == null || sepa < dist) && sepa <= this.getRange()) {
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

    public String upgrade(){
        String messageUpgrade;
        if (level <= levelMax) {
            if (Game.player.getGold() >= getUpgradeCost()) {
                Game.player.addGold(-getUpgradeCost());
                level += 1;
                damage = damage*(uprgradeBase + (level-1)*upgradeMultiplier);
                range = range *(uprgradeBase + (level-1)*upgradeMultiplier);
                freezeTime = freezeTime*(uprgradeBase + (level-1)*upgradeMultiplier);
                upgradeCost = upgradeCost * (level);

                messageUpgrade = "Upgraded";
            } else {
                messageUpgrade = "You don't have enough money";
            }
        }
        else {
            messageUpgrade = "Level is maximal";
        }
        return messageUpgrade;
    }

    public void shoot(){
        Tower t = this;
        double degats = this.getDamage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                PlayScreen.drawing.draw(new Bullet(degats,t,bulletRange,targetEnemy.getCentre(),new Point(centre.getX(),centre.getY())));
            }
        });
    }

    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException
    {   if (Game.isOnGame) {
        aInputStream.defaultReadObject();
        PlayScreen.drawing.drawSquare(this.centre, TowerMaker.getColor(towerType), 30);
        thread = new Thread(this);
        this.setActive();
    }
    else{aInputStream.defaultReadObject(); }
    }

    @Override
    public void run() {
        //le premier thread.sleep est important pour que le run ne se lance pas avant que tout soit loaded
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(active) {
            if (targetEnemy == null || this.centre.distance(targetEnemy.getCentre()) > this.getRange() || !targetEnemy.isAlive() ) {
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


    @Override
    public Point getCentre(){
        return centre;
    }

    @Override
    public Info getInfo() {
        return new InfoTower(this);
    }



    public int getFrequency() {
        return frequency;
    }

    public static int getCost() {
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

    public double getFreezeTime(){
        return freezeTime;
    }

    public int getNumberOfKill() {
        return numberOfKill;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }

    public String getTowerType(){
        return this.towerType;
    }

}