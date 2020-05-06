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
    private String type = "Raj tower";
    private String newPowerType = "Tsar Bomba";
    private double newPowerDuration = 0; // pas sur la durée , enlève des points de vie à tt les ennemis

    public RajTower(Point originPoint,Drawing d){
        super(originPoint,d);
        this.cost = rajTowerCost;
        this.range = newRange;
        this.damage = newDamage;
        this.reloadTime = newReloadTime;
        this.bulletRange = newBulletRange;
        this.towerType = type;
        this.powerType = newPowerType;


    }
    public static int getNewCost(){
        return rajTowerCost;
    }
    public static double getNewRange(){return newRange;}

    @Override
    public void powerActivation(){

        powerActive = true;
        numberOfKill = 0;
        //en gros permet de toucher tt les ennemis en meme temps
        ArrayList<Enemy> enemiestoremove=new ArrayList<>();
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
        image = new Image(Tower.class.getResourceAsStream("mouseCursor.png"));
        imageView.setImage(image);
        imageView.setFitWidth(size);
        imageView.setPreserveRatio(true);
        imageView.relocate(centre.getX()-(size/2),centre.getY()-(size/2));
        imageView.setRotate(120 + angle); //120 car position initial par rapport à l'image
        return imageView;
    }

}
