import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.*;

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
        for(Enemy e : enemies){                                //pt un truc à optimiser ici plus tard
            if( this.centre.distance(e.getOrigin()) <= range){
                e.hurt(this);
            }
        }
    }
    public Tower getMotherTower(){return motherTower;}

    public void move(double time){
        double dist = targetPoint.distance(this.motherTower.getCentre());
        int deltaX = (int) (this.targetPoint.getX()- this.motherTower.getCentre().getX()); // Attention mettre des propreties pour ne pas modifier X et Y indépendament (fonction de modification),
        int deltaY = (int) (this.targetPoint.getY()- this.motherTower.getCentre().getY()); // heuu sauf que ca pose un probleme si le chemin est vertical ou horizontal, mais plus rapide en général
        double speed_x = speed * deltaX / dist;
        double speed_y = speed * deltaY / dist;
        int dx = (int)round(time*speed_x);
        int dy = (int)round(time*speed_y);
        if (abs(this.targetPoint.getY()-this.centre.getY()) > abs(dy) && abs(this.targetPoint.getX()-this.centre.getX()) > abs(dx) ) {
            this.centre.setX(this.centre.getX() + dx);
            this.centre.setY(this.centre.getY() + dy);
        }
        else{
            this.explode();
        }
    }


}
