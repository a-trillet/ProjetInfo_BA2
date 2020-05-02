import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.ProtocolFamily;
import java.util.ArrayList;


public class Enemy implements Killable, MapClickable, Moveable, Runnable, Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Point> trackPoints;
    private int nextPoint=0;
    private Point origin;
    private double angle;

    private boolean alive = false;
    private double lifePoints;
    private ArrayList<Tower> targetingTowers = new ArrayList<>(); // les tours qui le cible actuelement
    private transient Thread t;
    private String lettre;
    private transient Text lettreText;
    private Color color;
    private boolean frozen = false;
    private double freezeStartTime;
    private double freezeDuration;

    //attributs venant des s-classe
    protected String enemyType;
    private double enemySpeed=12;
    protected double maxLifePoints;
    protected int reward;
    protected int enemyPower;     //cbdDeVieRetireraPlayerSiArriveaLaFin


    public Enemy(ArrayList<Point> trackPoints,String lettre ,Point point, double life, int reward){
        this.trackPoints=trackPoints;   //liste de points par lesquels l'ennemi passe (point de changement de direction)
        origin=new Point(point.getX(),point.getY());
        this.lifePoints = life;
        this.maxLifePoints = life;
        this.reward = reward;
        t = new Thread(this);
        this.lettre=lettre;
        createLettre(lettre);
        angle=Math.atan2((trackPoints.get(nextPoint).getY()-origin.getY()),(trackPoints.get(nextPoint).getX()-origin.getX()));
    }
    private void createLettre(String lettre){
        lettreText=new Text(lettre);
        lettreText.setX(origin.getX());
        lettreText.setY(origin.getY());
        lettreText.setFill(new Color(1,0,0,1));
        lettreText.setFont(new Font(15));
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
    }

    //prévient toutes les tourelles qui le vise qu'il est mort + die()
    private void killed(){
        die();
        Game.getPlayer().addGold(reward);
        for(Tower killer: targetingTowers){
            killer.targetIsDead(this);
        }
    }

    //retire le cercle et retire de ennemies on map dans Player
    private void die() {
        this.alive = false;
        //on peut pas toucher à des element javafx depuis un autre thread

        //Platform.runLater(() -> PlayScreen.drawing.getChildren().remove(Moveable));
        Game.getPlayer().getEnemiesOnMap().remove(this);

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
        if (origin.distance(trackPoints.get(nextPoint))>enemySpeed){
            double dist = trackPoints.get(nextPoint).distance(origin);
            int deltaX = (int) (this.trackPoints.get(nextPoint).getX() - origin.getX());
            int deltaY = (int) (this.trackPoints.get(nextPoint).getY() - origin.getY());

            double dx = enemySpeed/15  * deltaX / dist;
            double dy = enemySpeed/15  * deltaY / dist;
            origin.setX(origin.getX() + dx);
            origin.setY(origin.getY() + dy);


        }
        else{
            origin.setX(trackPoints.get(nextPoint).getX());
            origin.setY(trackPoints.get(nextPoint).getY());  //nextpoint c'est un int qui definit l'endroitde la liste ou l'element  est un point qu 'il va atteindre
            if (trackPoints.size()-1>nextPoint){
                nextPoint++;
                angle=Math.atan2((trackPoints.get(nextPoint).getY()-trackPoints.get(nextPoint-1).getY()),(trackPoints.get(nextPoint).getX()-trackPoints.get(nextPoint-1).getX()));

            }
            else {reachEndPoint();}
            }
        }

    @Override
    public Node getShape() {
        return lettreText;
    }


    private void reachEndPoint(){
        this.die();
        Game.getPlayer().decreaseLife(this.getEnemyPower());

    }


    public void update(Drawing drawing){
        if (alive) {
            lettreText.setX(origin.getX());
            lettreText.setY(origin.getY());
            lettreText.setRotate(angle*360/2/Math.PI);
            if (lifePoints<=maxLifePoints/2){lettreText.setFill(Color.web("FF7B2C"));}
        }
        else {drawing.removeMoveable(this);}
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
            Platform.runLater(() -> PlayScreen.mapClickListener.displayInfo(""));
        }

        }


    @Override
    public void run() {
        System.out.println("X: "+ this.getCentre().getX()+"enemy object run");
        while (alive) {
            if (frozen && freezeDuration < System.currentTimeMillis()-freezeStartTime){  //freezetime et freeze duration assocé à tt les eemis frozen
                unFreeze();
            }
            if (!frozen){this.move();}  //bouge uniquement si pas freeze
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public int getReward() {
        return reward;
    }

    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException
    {
        if(Game.isOnGame) {
            aInputStream.defaultReadObject();
            createLettre(lettre);
            t = new Thread(this);
            this.setAlive();
        }
        else{aInputStream.defaultReadObject();}
    }
}


































































