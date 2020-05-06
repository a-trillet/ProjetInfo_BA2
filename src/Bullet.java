import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;


import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Math.*;

public class Bullet implements Runnable, Serializable ,Updatable{
    private double speed=5;  // choisir si vient de la tour ou meme vitesse pour tt les balles
    private Point centre; // coordonnée
    private double damage;
    private double range;   // distance jusqu'à laquelle on est touché quand la bullet explose
    private Tower motherTower;
    private Point targetPoint;  // point d'arriver de la balle
    private boolean alive;
    private javafx.scene.shape.Circle circle;
    private Thread thread;
    private ImageView imageView;
    private double size =  0;


    public Bullet (double damage, Tower t, double range, Point targetPoint, Point originPoint) {
        alive=true;
        this.damage = damage;
        this.motherTower = t;
        this.centre = originPoint;
        this.range = range;
        this.targetPoint = targetPoint;

        circle = new javafx.scene.shape.Circle();
        circle.setCenterX(centre.getX());
        circle.setCenterY(centre.getY());
        circle.setRadius(4);
        circle.setFill(new Color(1, 1, 0, 1));

        imageView = motherTower.getImageBullet(centre, angle());



        thread = new Thread(this);
        thread.start();

    }

    private double angle(){ //calcul angle(en degre) entre horitonal droit et l'ennemi
        double y = targetPoint.getY() - motherTower.getCentre().getY();
        double x = targetPoint.getX() - motherTower.getCentre().getX();
        double alpha = Math.atan2( y, x ) ;     //arctg de (y/x)
        alpha = (alpha / PI) *180;      //passage e degré
        return alpha;
    }


    @Override
    public Node getShape() {
        if (imageView != null){
            return imageView;
        }
        else{       //par défault si images non dispo
            return circle;
        }
    }



    @Override
    public boolean isAlive() {
        return alive;
    }


    public double getDamage() {
        double damage = this.damage;
        return damage;
    }

    public void explode() {      //hurt les ennemis dont l'origine est dans la range de la bullet
        ArrayList<Enemy> enemiestoremove=new ArrayList<>();
        for( Enemy e: Game.getPlayer().getEnemiesOnMap()) {
            if (e.getCentre().distance(this.centre) < range) {
                e.hurt(this);
            }
            if (!e.isAlive()) {
                enemiestoremove.add(e);
            }
        }
        for (Enemy enemytoremove : enemiestoremove){Game.getPlayer().removeEnemy(enemytoremove);}
        enemiestoremove.clear();
    }


    public Tower getMotherTower() {
        return motherTower;
    }

    public void move() {
        double dist = targetPoint.distance(this.motherTower.getCentre());
        int deltaX = (int) (this.targetPoint.getX() - this.motherTower.getCentre().getX()); // Attention mettre des propreties pour ne pas modifier X et Y indépendament (fonction de modification),
        int deltaY = (int) (this.targetPoint.getY() - this.motherTower.getCentre().getY());
        double speed_x = speed * deltaX / dist;
        double speed_y = speed * deltaY / dist;
        int dx = (int) round(speed_x);
        int dy = (int) round(speed_y);
        if (abs(this.targetPoint.getY() - this.centre.getY()) > abs(dy) && abs(this.targetPoint.getX() - this.centre.getX()) > abs(dx)) {
            centre.setX(centre.getX() + dx);
            centre.setY(centre.getY() + dy);
        }
        else {
            alive = false;
            this.explode();

        }
        }


    public void update() {
        circle.setCenterX(centre.getX());
        circle.setCenterY(centre.getY());
        if (imageView != null){
            imageView.relocate(centre.getX()-(size/2),centre.getY()-(size/2));
        }
        }



    public void run(){
        while (alive){
            try {
                move();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean getAlive() {
        return alive;
    }
}

