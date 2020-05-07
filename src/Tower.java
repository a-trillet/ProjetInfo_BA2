import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Tower implements MapClickable, Runnable, Serializable {

    private static final long serialVersionUID = 1L;
    private static int levelMax = 3;

    private int frequency = 50;
    protected int level;
    protected Enemy targetEnemy = null;
    protected Point centre;

    protected int[] upgradeCosts = {0,0,0};
    protected double[] ranges = {0,0,0};              //change en fct du level
    protected double[] damages = {0,0,0};
    protected double[] powerDurations = {0,0,0};
    protected transient Color color;
    protected int reloadTime;
    protected int bulletRange;
    protected String towerType;
    protected boolean powerActive = false;
    protected String powerType = null;
    protected double powerStartTime;
    protected int numberOfKill=0;
    protected transient Node shape;

    protected Enemy secondTargetEnemy; // à enlever

    protected boolean active=false;
    private transient Thread thread = new Thread(this);


    public Tower(Point origin){
        this.centre = origin;
        level = 1;
        this.setTowerShape();
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
        return target;
    }


    public void targetIsDead(Enemy enemi){
        numberOfKill += 1;      // modifiable selon valeur du mob
        if(numberOfKill == this.getKillPower()){Platform.runLater(()->Game.getDrawing().getChildren().add(new Tips(4,new Point(20,250),Game.getDrawing())));}
    }

    public boolean isOn(Point p){
        return (centre.getY() -30< p.getY() && centre.getY() + 30 > p.getY() && centre.getX() -30< p.getX() && centre.getX() + 30 > p.getX());
        }

    public String upgrade(){
        String messageUpgrade;
        if (level < levelMax) {
            if (Game.getPlayer().getGold() >= upgradeCosts[level]) {
                Game.getPlayer().addGold(-upgradeCosts[level]);
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
    }

    public int getSellPrice(){
        int price =0;
        for (int i=0;i<=level;i++){price+=upgradeCosts[level]*i*2/3;}
        return price;
    }

    public void sell(){active=false;}

    public void shoot(){
        Tower t = this;
        double degats = this.getDamage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Game.getDrawing().draw(new Bullet(degats,t,bulletRange,targetEnemy.getCentre(),new Point(centre.getX(),centre.getY())));
            }
        });
    }

    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException
    {   if (Game.isOnGame) {
        aInputStream.defaultReadObject();
        this.setTowerShape();
        Game.getDrawing().drawTower(this);
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
        active=true;
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

    public Node getTowerShape(){
        return shape;
    }
    public void setTowerShape(){shape=null;}


    public int getReloadTime(){return reloadTime;}

    public int getCost() {
        return upgradeCosts[0];
    }

    public double getDamage() {
        return damages[level-1];
    }

    public int getLevel() {
        return level;
    }

    public double getRange() {
        return ranges[level-1];
    }

    public int getNumberOfKill() {
        return numberOfKill;
    }

    public int getUpgradeCost() {
        return upgradeCosts[level];
    }

    public String getTowerType(){
        return this.towerType;
    }

    public Color getColor(){return color;}

    public String getPowerType(){return powerType;}

    public ImageView getImageBullet(Point centre, double angle){return null;}


}