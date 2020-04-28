import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Math.*;
import static java.lang.Math.atan2;

public class Enemy implements Killable, MapClickable, Moveable, Runnable, Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Point> trackPoints;
    private int nextPoint=1;
    private Point origin;
    private double angle;

    private boolean alive = false;
    private double lifePoints;
    private ArrayList<Tower> targetingTowers = new ArrayList<>(); // les tours qui le cible actuelement
    private Thread t;
    private transient javafx.scene.shape.Circle c;
    private boolean frozen = false;
    private double freezeStartTime;
    private double freezeDuration;

    //attributs venant des s-classe
    protected String enemyType;
    protected double enemySpeed;
    protected double maxLifePoints;
    protected int reward;
    protected int enemyPower;     //cbdDeVieRetireraPlayerSiArriveaLaFin


    public Enemy(ArrayList<Point> trackPoints, double life, int reward){
        this.trackPoints=trackPoints;   //liste de points par lesquels l'ennemi passe (point de changement de direction)
        this.origin = new Point(trackPoints.get(0).getX(),trackPoints.get(0).getY());
        this.lifePoints = life;
        this.maxLifePoints = life;
        this.reward = reward;
        t = new Thread(this);
        c= new javafx.scene.shape.Circle(0,40,10,new Color(0,0,1,0.4));


    }

    public String getEnemyType(){
        return enemyType;
    }

    public int getEnemyPower(){
        return enemyPower;
    }

    public Point getCentre(){ return this.origin; }

    @Override
    public Info getInfo() {
        return new InfoEnemy(this);
    }

    @Override
    public void hurt(Bullet bullet) {
        decreaseLife(bullet.getDamage());

    }

    public void freeze(Tower t){    //freeze(tower t)?
        this.frozen = true;
        freezeStartTime = System.currentTimeMillis(); //peut voir ça comme heure de démarrage
        freezeDuration = t.getFreezeTime();
    }
    public void unFreeze(){
        this.frozen = false;
    }

    public void setAlive(){
        this.alive = true;
        this.t.start();
        Platform.runLater(() -> PlayScreen.drawing.draw(c));

    }

    //prévient toutes les tourelles qui le vise qu'il est mort + die()
    private void killed(){
        die();
        Game.player.addGold(reward);
        for(Tower killer: targetingTowers){
            killer.targetIsDead(this);
        }
    }

    //retire le cercle et retire de ennemies on map dans Player
    private void die() {
        this.alive = false;
        //on peut pas toucher à des element javafx depuis un autre thread
        Platform.runLater(() -> PlayScreen.drawing.getChildren().remove(c));
        Game.player.getEnemiesOnMap().remove(this);

    }

    public void addTargetingTower(Tower tower){
        this.targetingTowers.add(tower);
    }

    @Override
    public boolean isOn(Point p){
        boolean res= false;
        if (p.distance(this.origin)<30){res=true;}  //On peut modifier pour pouvoir cliquer sur tt la carré
        return res;
    }

    public double getSpeed() {
        if (frozen){
            return 0;
        }
        else{
            return enemySpeed;
        }
    }


    public double getLifePoints() {
        return lifePoints;
    }

    public double getMaxLifePoints() {
        return maxLifePoints;
    }


    public void move(){
        if (origin.distance(trackPoints.get(nextPoint))>enemySpeed/15){
            double dist = trackPoints.get(nextPoint).distance(trackPoints.get(nextPoint-1));
            int deltaX = (int) (this.trackPoints.get(nextPoint).getX() - this.trackPoints.get(nextPoint-1).getX());
            int deltaY = (int) (this.trackPoints.get(nextPoint).getY() - this.trackPoints.get(nextPoint-1).getY());

            double dx = enemySpeed / 15 * deltaX / dist;
            double dy = enemySpeed / 15 * deltaY / dist;
            origin.setX(origin.getX() + dx);
            origin.setY(origin.getY() + dy);



        }
        else{
            origin.setX(trackPoints.get(nextPoint).getX());
            origin.setY(trackPoints.get(nextPoint).getY());
            if (trackPoints.size()-1>nextPoint){
                nextPoint++;


            }
            else {reachEndPoint();}
            }
        }


    private void reachEndPoint(){
        this.die();
        Game.player.decreaseLife(this.getEnemyPower());

    }


    public void update(){
        c.setCenterX(this.origin.getX());
        c.setCenterY(this.origin.getY());
    }
    public boolean isAlive(){
        return alive;
    }
    public void decreaseLife(double dmg){
        lifePoints -= dmg;
        if(this.lifePoints <= 0){
            this.killed();
        }
        //met à jour display info display info
        if (PlayScreen.mapClickListener.getCurrentSelection() == this) {
            Platform.runLater(() -> PlayScreen.mapClickListener.displayInfo(""));
        }

        }


    @Override
    public void run() {
        System.out.println("X: "+ this.getCentre().getX()+"enemy object run");
        while (alive) {
            if (frozen && freezeDuration < System.currentTimeMillis()-freezeStartTime){  //freezetime et freeze duration assocé à tt les eemis frozen
                unFreeze();
            }
            if (!frozen){this.move();}  //bouge uniquement si pas freeze
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public int getReward() {
        return reward;
    }
}


