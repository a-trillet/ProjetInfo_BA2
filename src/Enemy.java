import javafx.application.Platform;
import javafx.scene.Node;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class Enemy implements Killable, MapClickable, Updatable, Runnable, Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Point> trackPoints;
    private int nextPoint=0;
    protected double angle;
    private boolean alive = false;
    private double lifePoints;
    private ArrayList<Tower> targetingTowers = new ArrayList<>();
    private transient Thread t;
    private static double enemyVelocity = 12;//enemivelocity change alors que speed non
    private static double freezeDuration;
    private static double freezeStart;
    private static boolean frozen = false;
    private Point origin;
    protected String enemyType;
    private double enemySpeed;
    private double maxLifePoints;
    private int reward;
    protected int enemyPower;
    private boolean isOnTrack=false;
    protected transient Node shape;

    protected String lettre;


    public Enemy(ArrayList<Point> trackPoints,Point point, double life, int reward,int speed){
        this.enemySpeed=speed;
        this.trackPoints=trackPoints;
        this.origin=new Point(point.getX(),point.getY());
        this.lifePoints = life;
        this.maxLifePoints = life;
        this.reward = reward;
        t = new Thread(this);
        angle=Math.atan2((trackPoints.get(0).getY()-origin.getY()),(trackPoints.get(0).getX()-origin.getX()));
    }

    public void move(){
        //se déplace dans la direction du prochain point de la liste trackPoint, et change de Point quand la distance entre l'ennemi et le point est petite
        if (origin.distance(trackPoints.get(nextPoint)) > enemyVelocity/15+2) {
            double dist = trackPoints.get(nextPoint).distance(this.origin);
            int deltaX = (int) (this.trackPoints.get(nextPoint).getX() - origin.getX());
            int deltaY = (int) (this.trackPoints.get(nextPoint).getY() - origin.getY());

            double dx = enemyVelocity / 15 * deltaX / dist;
            double dy = enemyVelocity / 15 * deltaY / dist;
            origin.setX(origin.getX() + dx);
            origin.setY(origin.getY() + dy);
        }
        else {
            origin.setX(trackPoints.get(nextPoint).getX());
            origin.setY(trackPoints.get(nextPoint).getY());  //nextpoint c'est un int qui definit l'endroitde la liste ou l'element  est un point qu 'il va atteindre
            if (nextPoint==0){isOnTrack=true;}
            if (trackPoints.size() - 1 > nextPoint) {
                nextPoint++;
                angle = Math.atan2((trackPoints.get(nextPoint).getY() - trackPoints.get(nextPoint - 1).getY()), (trackPoints.get(nextPoint).getX() - trackPoints.get(nextPoint - 1).getX()));
            }
            else {
                reachEndPoint();
            }
        }
    }

    @Override
    public void run() {

        while (alive) {
            if (frozen && System.currentTimeMillis()> freezeStart + freezeDuration){  //unfreeeze
                frozen = false;
                enemyVelocity = enemySpeed;    //revient à sa vitesse de base
            }
            this.move();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void hurt(Bullet bullet) {
        decreaseLife(bullet.getDamage());
        if (!targetingTowers.contains(bullet.getMotherTower())){
            targetingTowers.add(bullet.getMotherTower());
        }
    }

    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException{
        if(Game.isOnGame) {
                aInputStream.defaultReadObject();
                setShape();
                enemyVelocity = 12;
                frozen = false;
                t = new Thread(this);
                this.setAlive();
        }
        else{aInputStream.defaultReadObject();}
    }

    @Override
    public boolean isOn(Point p){
        boolean res= false;
        if (p.distance(this.origin)<30){res=true;}  //On peut modifier pour pouvoir cliquer sur tt la carré
        return res;
    }

    private void killed(){  //prévient toutes les tourelles qui le vise qu'il est mort + die()
        die();
        Game.getPlayer().addGold(reward);
        for(Tower killer: targetingTowers) {
            killer.targetIsDead(this);
        }
    }

    private void die() {
        Platform.runLater(() -> Game.getDrawing().getChildren().add(new Tips(2,new Point(20,250),Game.getDrawing())));
        alive = false;
        isOnTrack = false;
        if(this instanceof BossEnemy){
            Game.win();
        }
    }

    private void reachEndPoint(){
        this.die();
        Game.getPlayer().decreaseLife(this.getEnemyPower());
    }

    public void update(){
    }

    public void decreaseLife(double dmg){
        lifePoints -= dmg;
        if(this.lifePoints <= 0){
            this.killed();
        }
        //met à jour display info
        if (PlayScreen.getMapClickListener().getCurrentSelection() == this) {
            Platform.runLater(() -> PlayScreen.getMapClickListener().displayInfo());
        }
    }

    @Override
    public Info getInfo() {
        return new InfoEnemy(this);
    }

    @Override
    public Node getShape() {
        return shape;
    }

    public double getSpeed() {
        if(frozen){
            enemyVelocity = 0;
        }
        return enemyVelocity;
    }

    public int getReward() {
        return reward;
    }

    public double getLifePoints() {
        return lifePoints;
    }

    public double getMaxLifePoints() {
        return maxLifePoints;
    }

    public boolean isAlive(){
        return alive;
    }

    public String getEnemyType(){
        return enemyType;
    }

    public int getEnemyPower(){
        return enemyPower;
    }

    public Point getCentre(){ return this.origin; }

    public void setAlive(){
        this.alive = true;
        this.t.start();
        Platform.runLater(()->Game.getDrawing().draw(this));
    }

    public static void setFrozen(boolean bol){frozen = bol;}

    public static void setFreezeDuration(double duration){freezeDuration = duration;}

    public static void setFreezeStart(double startTime){freezeStart = startTime;}

    public static void setEnemyVelocity(double velocity){enemyVelocity = velocity;}

    public boolean isOnTrack(){return isOnTrack;}

    public void setShape(){}


}
