import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.paint.Color;



import static java.lang.Math.*;

public class Bullet implements Runnable {
    private double speed=10;  // choisir si vient de la tour ou meme vitesse pour tt les balles
    private Point centre; // coordonnée
    private double damage;
    private double range;   // distance jusqu'à laquelle on est touché quand la bullet explose
    private Tower motherTower;
    private Point targetPoint;  // point d'arriver de la balle
    private boolean alive=true;
    private javafx.scene.shape.Circle circle;
    private Thread thread;


    public Bullet (double damage, Tower t, double range, Point targetPoint, Point originPoint) {
        this.damage = damage;
        this.motherTower = t;
        this.centre = originPoint;
        this.range = range;
        this.targetPoint = targetPoint;
        circle = new javafx.scene.shape.Circle();
        circle.setCenterX(centre.getX());
        circle.setCenterY(centre.getY());
        circle.setRadius(4);
        circle.setFill(new Color(1,1,0,1));
        thread=new Thread(this);
        thread.start();

    } // on peut ajouter speed si différent pour chaque tour

    public Node getShape() {
        return circle;
    }


    public double getDamage() {
        double damage = this.damage;
        return damage;
    }

    public void explode() {      //hurt les ennemis dont l'origine est dans la range de la bullet
        for(int i=0; i<Player.getPlayer().getEnemiesOnMap().size(); i++){
            Enemy enemy=Player.getPlayer().getEnemiesOnMap().get(i);
            if(enemy.getCentre().distance(this.centre)<range){enemy.hurt(this);}
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
            centre.setX(centre.getX() + dx);
            centre.setY(centre.getY() + dy);
        }
        else {
            alive = false;
            this.explode();
            Platform.runLater(new Runnable() {
                @Override public void run() {
                    PlayScreen.drawing.getChildren().remove(circle);
                }
            });
            PlayScreen.drawing.getBullets().remove(this);
        }
        }


    public void update(){
            circle.setCenterX(centre.getX());
            circle.setCenterY(centre.getY());
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

