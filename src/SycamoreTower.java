import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class SycamoreTower extends  Tower {

    private static int sycamoreTowercost = 100;
    private static  double newRange = 75;
    private static  double newDamage = 5;
    private static int newReloadTime = 2000;
    private static int newBulletRange = 13; //parfois loupe son coup mais jsp si ça correspond bien
    private static String type = "Sycamore tower";
    private static String newPowerType = "Double shoot";
    private static double newPowerDuration = 6000;
    private static Color newColor = new Color(1, 0, 1, 0.3);
    private static Image newImageTower = new Image(SycamoreTower.class.getResourceAsStream("sycamore.jpg"));

    public SycamoreTower(Point origin, Drawing d){
        super(origin,d);
        this.cost = sycamoreTowercost;
        this.range = newRange;
        this.damage = newDamage;
        this.reloadTime = newReloadTime;
        this.bulletRange = newBulletRange;
        this.towerType = type;
        this.powerType = newPowerType;
        this.powerDuration = newPowerDuration;
        this.imageTower = newImageTower;
        color = newColor;
    }

    @Override
    public void powerActivation(){                      //sert à diminuer le reload time dans le run the tower pour une rafale
        powerActive = true;
        powerStartTime = System.currentTimeMillis();

        numberOfKill = 0;
    }
    @Override
    public ImageView getImageBullet(Point centre, double angle){
        double size = 14;
        imageBullet = new Image(Tower.class.getResourceAsStream("logoGoogle.png"));
        imageView.setImage(imageBullet);
        imageView.setFitWidth(size);
        imageView.setPreserveRatio(true);
        imageView.relocate(centre.getX()-(size/2),centre.getY()-(size/2));
        return imageView;
    }


    @Override
    public int getKillPower(){
        int killPower = 5;
        killPower += (this.level-1)*5;
        return 0;
    }
    public static int getNewCost(){
        return sycamoreTowercost;
    }
    public static double getNewRange(){return newRange;}
}
