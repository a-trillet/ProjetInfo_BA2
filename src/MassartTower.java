import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class MassartTower extends Tower {

    private static int massartTowercost = 150;
    private static  double newRange = 100;
    private static  double newDamage =5;
    private static int newReloadTime = 2000;
    private static int newBulletRange = 40;
    private String type = "Massart tower";
    private String newPowerType = "Total Slow";
    private double newPowerDuration = 4000;

    public MassartTower(Point origin,Drawing d) {
        super(origin,d);
        this.cost = massartTowercost;
        this.range = newRange;
        this.damage = newDamage;
        this.reloadTime = newReloadTime;
        this.bulletRange = newBulletRange;
        this.towerType = type;
        this.powerType = newPowerType;
        this.powerDuration = newPowerDuration;
    }
    public static int getNewCost(){
        return massartTowercost;
    }
    public static double getNewRange(){return newRange;}

    @Override
    public void powerActivation(){                      //sert à diminuer le reload time dans le run the tower pour une rafale

        powerStartTime = System.currentTimeMillis();
        powerActive = true;
        numberOfKill = 0;
        Enemy.setEnemyVelocity(6);          //peut choisir la vitesse des enemis durant 'activation du power
        Enemy.setFrozen(true);
        Enemy.setFreezeDuration(newPowerDuration);
        Enemy.setFreezeStart(powerStartTime);


    }
    @Override
    public ImageView getImageBullet(Point centre, double angle){
        double size = 35;
        image = new Image(Tower.class.getResourceAsStream("turtle2.png"));
        imageView.setImage(image);
        imageView.setFitWidth(size);
        imageView.setPreserveRatio(true);
        imageView.relocate(centre.getX()-(size/2),centre.getY()-(size/2));
        imageView.setRotate(0 + angle);// rotate fonctionne dans le sens horlogique par rapport à l'écran
        return imageView;
    }
}

