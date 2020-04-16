import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import static java.lang.Math.*;

public class Bullet  implements Runnable {
    private double speed=1;  // choisir si vient de la tour ou meme vitesse pour tt les balles
    private Point centre; // coordonnée
    private double damage;
    private double range;   // distance jusqu'à laquelle on est touché quand la bullet explose
    private Tower motherTower;
    private Point targetPoint;  // point d'arriver de la balle
    private Thread thread;
    private boolean alive;
    private javafx.scene.shape.Circle circle;


    public Bullet(double damage, Tower t, double range, Point targetPoint, Point originPoint) {
        this.damage = damage;
        this.motherTower = t;
        this.centre = originPoint;
        this.range = range;
        this.targetPoint = targetPoint;
        alive = true;
        thread = new Thread(this);
        thread.start();
    } // on peut ajouter speed si différent pour chaque tour

    public double getDamage() {
        double damage = this.damage;
        return damage;
    }

    public void explode() {      //hurt les ennemis dont l'origine est dans la range de la bullet
        for (Enemy e : Player.getPlayer().getEnemiesOnMap()) {
            if (this.centre.distance(e.getCentre()) <= range) { // où get origin?
                e.hurt(this);
            }
        }
    }

    public Tower getMotherTower() {
        return motherTower;
    }

    public void move() {
        double dist = targetPoint.distance(this.motherTower.getCentre());
        int deltaX = (int) (this.targetPoint.getX() - this.motherTower.getCentre().getX()); // Attention mettre des propreties pour ne pas modifier X et Y indépendament (fonction de modification),
        int deltaY = (int) (this.targetPoint.getY() - this.motherTower.getCentre().getY()); // heuu sauf que ca pose un probleme si le chemin est vertical ou horizontal, mais plus rapide en général
        double speed_x = speed * deltaX / dist;
        double speed_y = speed * deltaY / dist;
        int dx = (int) round(speed_x);
        int dy = (int) round(speed_y);
        if (abs(this.targetPoint.getY() - this.centre.getY()) > abs(dy) && abs(this.targetPoint.getX() - this.centre.getX()) > abs(dx)) {
            this.centre.setX(this.centre.getX() + dx);
            this.centre.setY(this.centre.getY() + dy);
            //System.out.println(centre);
        }
        else {
            alive = false;
            this.explode();
        }
    }

    @Override
    public void run() {
        while (alive) {
            this.move();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    }

