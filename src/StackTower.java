import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class StackTower extends Tower {

    private static  int stackTowerCost = 100;
    private static  double newRange = 100;
    private static  double newDamage = 15;
    private static int newReloadTime = 750;
    private static int newBulletRange = 10;
    private String type = "Stack Overflow tower";
    private String newPowerType = "Burst Fire";
    private double newPowerDuration = 5000;

    public StackTower(Point origin,Drawing d){
        super(origin,d);
        this.cost = stackTowerCost;
        this.range = newRange;
        this.damage = newDamage;
        this.reloadTime = newReloadTime;
        this.bulletRange = newBulletRange;
        this.towerType = type;
        this.powerType = newPowerType;
        this.powerDuration = newPowerDuration;
    }
    public static int getNewCost(){
        return stackTowerCost;
    }
    public static double getNewRange(){return newRange;}



    @Override
    public void powerActivation(){                      //sert à diminuer le reload time dans le run the tower pour une rafale

        powerStartTime = System.currentTimeMillis();
        powerActive = true;
        numberOfKill = 0;
    }

    @Override
    public ImageView getImageBullet(Point centre, double angle){
        double size = 20;
        image = new Image(Tower.class.getResourceAsStream("handCursor.png"));
        imageView.setImage(image);
        imageView.setFitWidth(size);
        imageView.setPreserveRatio(true);
        imageView.relocate(centre.getX()-(size/2),centre.getY()-(size/2));
        imageView.setRotate(90 + angle);
        return imageView;
    }
}

