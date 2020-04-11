import org.w3c.dom.html.HTMLAreaElement;

import java.util.ArrayList;

public class Enemy implements Killable, MapClickable {
    public enum direction {LEFT , RIGHT, UP, DOWN}
    private Point origin;
    private int speed = 10;
    private boolean alive = false;
    private double lifePoints = 0;
    private double maxLifePoints = 0;
    private ArrayList<Tower> targetingTowers = new ArrayList<Tower>(); // les tours qui le cible actuelement


    public Enemy( Point origin, double life){
        this.origin = origin;
        this.lifePoints = life;
        this.maxLifePoints = life;
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
    public void setAlive(){alive = true;}

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
}