import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class RajTower extends Tower{
    private static int[] newUpgradeCosts = {50,70,100,0};
    private static double[] newRanges = {100,150,200};
    private static double[] newDamages = {15,20, 30};
    private static int newReloadTime = 1500;
    private static int newBulletRange = 15;
    private static String type = "Raj tower";
    private static String newPowerType = "Tsar Bomba";
    private static Color newColor = new Color(1, 0, 0, 1);
    private transient static Image newImageTower = new Image(RajTower.class.getResourceAsStream("raj.jpg"));

    public RajTower(Point originPoint){
        super(originPoint);
        this.upgradeCosts = newUpgradeCosts;
        this.ranges = newRanges;
        this.damages = newDamages;
        this.reloadTime = newReloadTime;
        this.bulletRange = newBulletRange;
        this.towerType = type;
        this.powerType = newPowerType;
        color = newColor;
    }

    @Override
    public void powerActivation(){

        powerActive = true;
        numberOfKill = 0;
        ArrayList<Enemy> enemiestoremove = new ArrayList<>();
        for (Enemy e : Game.getPlayer().getEnemiesOnMap()) {
            e.decreaseLife(damages[level-1]);
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

    @Override
    public void setTowerShape(){
        ImageView imageView = new ImageView(newImageTower);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        imageView.setX(centre.getX()-30/2);
        imageView.setY(centre.getY()-30/2);
        shape= imageView;
    }
    public static int getNewCost(){
        return newUpgradeCosts[0];
    }
    public static double getNewRange(){return newRanges[0];}
    public static int getNewReloadTime(){return newReloadTime;}
    public static double getNewDamage(){return newDamages[0];}
}
