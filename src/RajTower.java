import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class RajTower extends Tower{

    private static int rajTowerCost = 150;
    private static  double newRange = 200;
    private static  double newDamage = 1000;
    private static int newReloadTime = 1500;
    private static int newBulletRange = 15;
    private static String type = "Raj tower";
    private static String newPowerType = "Tsar Bomba";
    private static Color newColor = new Color(1, 0, 0, 1);
    private transient static Image newImageTower = new Image(RajTower.class.getResourceAsStream("raj.jpg"));

    public RajTower(Point originPoint){
        super(originPoint);
        this.cost = rajTowerCost;
        this.range = newRange;
        this.damage = newDamage;
        this.reloadTime = newReloadTime;
        this.bulletRange = newBulletRange;
        this.towerType = type;
        this.powerType = newPowerType;
        this.imageTower = newImageTower;
        color = newColor;
    }

    @Override
    public void powerActivation(){

        powerActive = true;
        numberOfKill = 0;
        ArrayList<Enemy> enemiestoremove = new ArrayList<>();
        for (Enemy e : Game.getPlayer().getEnemiesOnMap()) {
            e.decreaseLife(damage);
            if (!e.isAlive()){enemiestoremove.add(e);}
        }
        for (Enemy enemytoremove : enemiestoremove){Game.getPlayer().removeEnemy(enemytoremove);}
        enemiestoremove.clear();
        powerActive = false;
    }
    @Override
    public ImageView getImageBullet(Point centre,double angle){
        double size = 20;
        ImageView imageView = new ImageView();
        imageBullet = new Image(Tower.class.getResourceAsStream("mouseCursor.png"));
        imageView.setImage(imageBullet);
        imageView.setFitWidth(size);
        imageView.setPreserveRatio(true);
        imageView.relocate(centre.getX()-(size/2),centre.getY()-(size/2));
        imageView.setRotate(120 + angle);
        return imageView;
    }

    @Override
    public int getKillPower(){
        int killPower = 5;
        killPower += (this.getLevel()-1)*5;
        return killPower;
    }
    public static Image getShape(){
        return newImageTower;
    }
    public static int getNewCost(){
        return rajTowerCost;
    }
    public static double getNewRange(){return newRange;}
}
