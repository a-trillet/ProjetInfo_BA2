import java.util.ArrayList;
import java.util.Arrays;

public class Bullet {
    private double speed;  // choisir si vient de la tour ou meme vitesse pour tt les balles
    private Point centre; // coordonnée
    private double damage;
    private double range;   // distance jusqu'à laquelle on est touché quand la bullet explose
    private ArrayList<Enemy> enemies;
    private Tower motherTower;
    private Point targetPoint;  // point d'arriver de la balle

    public Bullet(double damage, Tower t, double range, Point targetPoint){
        this.damage=damage;
        this.motherTower=t;
        this.centre=t.getCentre();
        this.range=range;
        this.targetPoint=targetPoint; //ajouter fonction dans tower pour donner la position de la cible
    } // on peut ajouter speed si différent pour chaque tour

    public double getDamage(){return damage;}

    public void explode(){      //hurt les ennemis dont l'origine est dans la range de la bullet
        for(Enemy e : enemies){
            if( this.centre.distance(e.getOrigin()) <= range){
                e.hurt(this);
            }
        }
    }
    public Tower getMotherTower(){return motherTower;}

}
