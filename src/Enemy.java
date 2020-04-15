import javafx.scene.paint.Color;


import java.awt.*;
import java.util.ArrayList;

public class Enemy implements Killable, MapClickable, Runnable {
    static int LEFT = 1;
    static int RIGHT = 2;
    static int UP = 3;
    static int DOWN = 4;
    private int direction=2;
    private Point origin;
    private int speed = 10;
    private boolean alive = false;
    private double lifePoints = 0;
    private double maxLifePoints = 0;
    private ArrayList<Tower> targetingTowers = new ArrayList<Tower>(); // les tours qui le cible actuelement
    private Thread t;
    private javafx.scene.shape.Circle c;
    private int cbdevieretireeaplayersiarrivealafin;


    public Enemy( Point origin, double life){
        this.origin = origin;
        this.lifePoints = life;
        this.maxLifePoints = life;
        t = new Thread(this);
        c= new javafx.scene.shape.Circle(0,40,10,new Color(0,0,1,0.4));
        PlayScreen.drawing.draw(this,c);

    }

    public Point getCentre(){ return this.origin; }

    @Override
    public Info getInfo() {
        return new InfoEnemy(this);
    }

    @Override
    public void hurt(Bullet bullet) {
        this.lifePoints -= bullet.getDamage();
        if(this.lifePoints <= 0){
            this.die();
        }
    }
    public void setAlive(){

        this.alive = true;
        this.t.start();
    }

    private void die() {     //prévient toutes les tourelles qui le vise qu'il est mort
        this.alive = false;
        for(Tower killer: targetingTowers){
        killer.targetIsDead(this);
        }
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
    public int getSpeed() {
        return speed;
    }

    public double getLifePoints() {
        return lifePoints;
    }

    public double getMaxLifePoints() {
        return maxLifePoints;
    }


    public void move(){
        if(direction == UP)
        {
            origin.setY(origin.getY() - 1);
        }
        else if(direction == DOWN)
        {
            origin.setY(origin.getY() + 1);
        }
        else if(direction == LEFT)
        {
            origin.setX(origin.getY() - 1);
        }
        else if(direction==RIGHT) {
            this.origin.setX(origin.getX() + 1);
        }
    }
    private void reachEndPoint(){
        alive = false;
        Player.getPlayer().decreaseLife(1);
    }


    public void update(){
        c.setCenterX(this.origin.getX());
        c.setCenterY(this.origin.getY());
    }

    @Override
    public void run() {
        System.out.println("X: "+ this.getCentre().getX()+"enemy object run");
        while (alive) {
            this.move();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (origin.getX() > 600) {             // temporaire
                direction = 4;
            }
            if (isOn(MapPane.getEndPoint())){
                reachEndPoint();         //met alive false, Comment retirer le carré?

            }
        }
    }

}


