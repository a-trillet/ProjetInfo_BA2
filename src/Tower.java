import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    protected int level;
    protected Enemy targetEnemy = null;
    protected Point centre;

    protected static Color color;
    protected int cost;
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
    protected Image imageBullet;
    protected Image imageTower;

    protected Enemy secondTargetEnemy; //reservé pour sycamore tower

    protected boolean active = true;
    private double uprgradeBase = 1.0;      // vont servir à augmenter le range et damage
    private double upgradeMultiplier = 0.5; //
    private transient Thread thread = new Thread(this);
    protected transient Drawing drawing;


    public Tower(Point origin,Drawing d){
        this.centre = origin;
        drawing=d;
        level = 1;
    }


    protected void powerActivation(){}

    public int getKillPower(){                  //retourne le nombre de kill à faire pour pouvoir activer le power(change en fonction du level)
        return 0;
    }


    protected Enemy selectTarget(){   //Cette fonction renvoit l'ennemi, en range, le plus proche du centre de la tour
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


    public void targetIsDead(Enemy enemi){
        numberOfKill += 1;      // modifiable selon valeur du mob
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
    public int getSellPrice(){
        int price=this.getCost()*2/3;
        for (int i=1;i<=level;i++){price+=upgradeCost*i*2/3;}
        return price;
    }
    public void sell(){active=false;}

    public void shoot(){
        Tower t = this;
        double degats = this.getDamage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                drawing.draw(new Bullet(degats,t,bulletRange,targetEnemy.getCentre(),new Point(centre.getX(),centre.getY())));
            }
        });
    }

    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException
    {   if (Game.isOnGame) {
        aInputStream.defaultReadObject();
        drawing= Game.getDrawing();

        drawing.setImage(centre,getShape(towerType),30);
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

    public void setActive(){
        thread.start();
    }

    @Override
    public Point getCentre(){
        return centre;
    }

    @Override
    public Info getInfo() {
        return new InfoTower(this);
    }

    public static Image getShape(String towerType){
        Image image = null;
        switch(towerType){
            case "Stack Overflow tower":
                image =  StackTower.getShape();
                break;
            case "Massart tower":
                image = MassartTower.getShape();
                break;
            case "Raj tower":
                image = RajTower.getShape();
                break;
            case "Sycamore":
                image = SycamoreTower.getShape();
                break;
        }
        return image;
    }



    public int getFrequency() {
        return frequency;
    }

    public int getCost() {
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

    public Color getColor(){return color;}

    public String getPowerType(){return powerType;}

    public ImageView getImageBullet(Point centre, double angle){return null;}

    public Image getImageTower(){return imageTower;}

}