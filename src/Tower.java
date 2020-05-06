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
    protected String towerType;
    protected boolean powerActive = false;
    protected String powerType = null;
    protected double powerDuration;
    protected double powerStartTime;
    protected int numberOfKill;
    private Enemy secondTargetEnemy; //reservé pour sycamore tower

    private boolean active = true;
    private double uprgradeBase = 1.0;      // vont servir à augmenter le range et damage
    private double upgradeMultiplier = 0.5; //
    private transient Thread thread = new Thread(this);
    private transient Drawing drawing;

    public Tower(Point origin,Drawing d){
        this.centre = origin;
        drawing=d;
        level = 1;
    }

    public void setPowerActivation(boolean bol){
        powerActive = bol;
    }

    protected void powerActivation(){}

    public String getPowerType(){return powerType;}


    public int getKillPower(){                  //retourne le nombre de kill à faire pour pouvoir activer le power(change en fonction du level
        int killPower = 0;
        killPower = killPower*level;
        return killPower;
    }

    public void setActive(){
        thread.start();
    }

    private Enemy selectTarget(){   //Cette fonction renvoit l'ennemi, en range, le plus proche du centre de la tour
        Enemy target = null;
        Double dist = null;
        for (Enemy e : Game.getPlayer().getEnemiesOnMap()) {
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
    private Enemy selectSecondTarget(){   //Cette fonction renvoit l'ennemi, en range, le plus proche du centre de la tour, !! et qui n'est pas targetEnnemy
        Enemy target = null;
        Double dist = null;
        for (Enemy e : Game.getPlayer().getEnemiesOnMap()) {
            double sepa = this.centre.distance(e.getCentre());
            if ((target == null || sepa < dist) && sepa <= this.getRange() && e!= targetEnemy) {
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
            if (Game.getPlayer().getGold() >= getUpgradeCost()) {
                Game.getPlayer().addGold(-getUpgradeCost());
                level += 1;
                damage = damage*(uprgradeBase + (level-1)*upgradeMultiplier);
                range = range *(uprgradeBase + (level-1)*upgradeMultiplier);
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

                drawing.draw(new Bullet(degats,t,bulletRange,targetEnemy.getCentre(),new Point(centre.getX(),centre.getY())));
                if (powerActive && t.getTowerType() == "Sycamore tower"){
                    if (secondTargetEnemy != null){
                        drawing.draw(new Bullet(degats,t,bulletRange,secondTargetEnemy.getCentre(),new Point(centre.getX(),centre.getY())));
                    }
                }
            }
        });
    }

    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException
    {   if (Game.isOnGame) {
        aInputStream.defaultReadObject();
        drawing= Game.getDrawing();
        drawing.setImage(centre,TowerMaker.getImage(towerType),30);
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
            if (powerActive && towerType == "Sycamore tower"){
                if (System.currentTimeMillis()< powerDuration+ powerStartTime){
                    if (secondTargetEnemy == null || this.centre.distance(secondTargetEnemy.getCentre()) > this.getRange() || !secondTargetEnemy.isAlive() ) {
                        secondTargetEnemy = selectSecondTarget();
                    }
                }
                else{powerActive = false;}
            }

            if (targetEnemy != null) {
                shoot();
                System.out.println("shoot"+targetEnemy.getCentre().getY());
                try {
                    if (powerActive && towerType == "Stack Overflow tower" ){
                        if(System.currentTimeMillis()< powerDuration+ powerStartTime){
                            Thread.sleep((int)(reloadTime/(2*level)));
                        }
                        else{
                            powerActive = false;
                            Thread.sleep(reloadTime);
                        }
                    }
                    else{
                        Thread.sleep(reloadTime);
                    }

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