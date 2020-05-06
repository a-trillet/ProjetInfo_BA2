import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class StackTower extends Tower {

    private static  int stackTowerCost = 100;
    private static  double newRange = 100;
    private static  double newDamage = 15;
    private static int newReloadTime = 750;
    private static int newBulletRange = 10;
    private static String type = "Stack Overflow tower";
    private static String newPowerType = "Burst Fire";
    private static double newPowerDuration = 5000;
    private static Color newColor = new Color(0, 1, 0, 1);
    private static Image newImageTower = new Image(StackTower.class.getResourceAsStream("stack.jpg"));

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
        this.imageTower = newImageTower;
        color = newColor;
    }

    @Override
    public void powerActivation(){                      //sert à diminuer le reload time dans le run the tower pour une rafale

        powerStartTime = System.currentTimeMillis();
        powerActive = true;
        numberOfKill = 0;
    }

    @Override
    public ImageView getImageBullet(Point centre, double angle){
        ImageView imageView = new ImageView();
        double size = 20;
        imageBullet = new Image(Tower.class.getResourceAsStream("handCursor.png"));
        imageView.setImage(imageBullet);
        imageView.setFitWidth(size);
        imageView.setPreserveRatio(true);
        imageView.relocate(centre.getX()-(size/2),centre.getY()-(size/2));
        imageView.setRotate(90 + angle);
        return imageView;
    }

    @Override
    public int getKillPower(){
        int killPower = 5;
        killPower += (this.getLevel()-1)*5;
        return killPower;
    }

    public static int getNewCost(){ return stackTowerCost;}
    public static double getNewRange(){return newRange;}
}

