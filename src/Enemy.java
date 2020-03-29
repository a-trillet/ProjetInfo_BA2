import org.w3c.dom.html.HTMLAreaElement;

import java.util.ArrayList;

public class Enemy implements Killable {
    protected Point origin;
    protected boolean alive = true;
    protected double lifePoints = 0;
    protected double maxLifePoints = 0;
    protected ArrayList<Tower> targetingTowers = new ArrayList<Tower>(); // les tours qui le cible actuelement
/// idée, une case qui affiche les stat du derniere objet sur lequel on a cliqué, exemple: on clique sur un ennemi et on voit sa vie, sa vélocité,... dans cette case, pareil pour les tours


    public Enemy( Point origin, double life){
        this.origin = origin;
        this.lifePoints = life;
        this.maxLifePoints = life;
    }

    public Point getOrigin(){ return this.origin; }

    @Override
    public void hurt(Bullet bullet) {
        this.lifePoints -= bullet.getDamage();
        if(this.lifePoints <= 0){
            this.die();///
        }
    }

    @Override
    public void die() {     //prévient toutes les tourelles qui le vise qu'il est mort
        this.alive = false;
        for(Tower killer: targetingTowers){
        killer.targetIsDead(this);
        }
    }

    public void addTargetingTower(Tower tower){
        this.targetingTowers.add(tower);
    }
}
