import org.w3c.dom.html.HTMLAreaElement;

import java.util.ArrayList;

public class Enemy implements Killable {
    private Point origin;
    private boolean alive = true;
    private double lifePoints = 0;
    private double maxLifePoints = 0;
    private ArrayList<Tower> targetingTowers = new ArrayList<Tower>(); // les tours qui le cible actuelement
/// idée, une case qui affiche les stats du derniere objet sur lequel on a cliqué, exemple: on clique sur un ennemi et on voit sa vie, sa vélocité,... dans cette case, pareil pour les tours


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
            this.die();
        }
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
}
