import javafx.application.Platform;
import javafx.scene.paint.Color;
import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.round;

public class Enemy implements Killable, MapClickable, Runnable {
    static int LEFT = 1;
    static int RIGHT = 2;
    static int UP = 3;
    static int DOWN = 4;
    private int direction = 2;
    private Point origin;
    private ArrayList<Point> route = PlayScreen.map.getMainRoute();
    private Point targetPoint = route.get(0);
    private Point lastTurnPoint;

    private boolean alive = false;
    private double lifePoints = 0;
    private ArrayList<Tower> targetingTowers = new ArrayList<Tower>(); // les tours qui le cible actuelement
    private Thread t;
    private javafx.scene.shape.Circle c;
    private boolean frozen = false;
    private double freezeStartTime;
    private double freezeDuration;

    //attributs venant des s-classe
    protected String enemyType;
    protected double enemySpeed;
    protected double maxLifePoints;
    protected int reward;
    protected int enemyPower;     //cbdDeVieRetireraPlayerSiArriveaLaFin


    public Enemy( Point origin, double life, int reward){
        this.origin = origin;
        this.lifePoints = life;
        this.maxLifePoints = life;
        t = new Thread(this);
        c= new javafx.scene.shape.Circle(0,40,10,new Color(0,0,1,0.4));
        this.reward = reward;


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
        Platform.runLater(new Runnable() {
            @Override public void run() {
                PlayScreen.drawing.draw(c);
            }
        });

    }

    //prévient toutes les tourelles qui le vise qu'il est mort + die()
    private void killed(){
        die();
        Player.getPlayer().addGold(reward);
        for(Tower killer: targetingTowers){
            killer.targetIsDead(this);
        }
    }

    //retire le cercle et retire de ennemies on map dans Player
    private void die() {
        this.alive = false;
        //on peut pas toucher à des element javafx depuis un autre thread
        Platform.runLater(new Runnable() {
            @Override public void run() {
                PlayScreen.drawing.getChildren().remove(c);
            }
        });

        Player.getPlayer().getEnemiesOnMap().remove(this);

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
    public boolean isOnTurnPoint(Point p){
        boolean res= false;
        if (p.distance(this.origin)<1){res=true;}  //On peut modifier pour pouvoir cliquer sur tt la carré
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

        if (isOnTurnPoint(targetPoint)){
            lastTurnPoint = targetPoint;
            targetPoint = route.get(route.indexOf(targetPoint)+1);
        }

        double dist = targetPoint.distance(this.lastTurnPoint);
        int deltaX = (int) (this.targetPoint.getX() - this.lastTurnPoint.getX());
        int deltaY = (int) (this.targetPoint.getY() - this.lastTurnPoint.getY());

            double dx = enemySpeed / 15 * deltaX / dist;
            double dy = enemySpeed / 15 * deltaY / dist;
            origin.setX(origin.getX() + dx);
            origin.setY(origin.getY() + dy);

        if (frozen && freezeDuration < System.currentTimeMillis()-freezeStartTime){  //freezetime et freeze duration assocé à tt les eemis frozen
            unFreeze();
        }
    }

    private void reachEndPoint(Enemy enemy){
        die();
        Player.getPlayer().decreaseLife(enemy.getEnemyPower());

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
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    PlayScreen.mapClickListener.displayInfo("");
                }
            });
        }

        }


    @Override
    public void run() {
        System.out.println("X: "+ this.getCentre().getX()+"enemy object run");
        while (alive) {
            this.move();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isOn(MapPane.getEndPoint())){
                reachEndPoint(this);

            }
        }
    }

    public int getReward() {
        return reward;
    }
}


