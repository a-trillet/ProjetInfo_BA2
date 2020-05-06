import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class MassartTower extends Tower {

    private static int massartTowercost = 150;
    private static  double newRange = 100;
    private static  double newDamage =5;
    private static int newReloadTime = 2000;
    private static int newBulletRange = 40;
    private static String type = "Massart tower";
    private static String newPowerType = "Total Slow";
    private static double newPowerDuration = 4000;
    private static Color newColor = new Color(0, 0.1, 1, 0.7);
    private static Image newImageTower = new Image(MassartTower.class.getResourceAsStream("massart.jpg"));

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
        this.imageTower = newImageTower;
        color = newColor;
    }

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
    public int getKillPower(){
        int killPower = 3;
        killPower += (this.getLevel()-1)*3;
        return killPower;
    }

    @Override
    public ImageView getImageBullet(Point centre, double angle){
        double size = 35;
        imageBullet = new Image(Tower.class.getResourceAsStream("turtle2.png"));
        imageView.setImage(imageBullet);
        imageView.setFitWidth(size);
        imageView.setPreserveRatio(true);
        imageView.relocate(centre.getX()-(size/2),centre.getY()-(size/2));
        imageView.setRotate(0 + angle);// rotate fonctionne dans le sens horlogique par rapport à l'écran
        return imageView;
    }
    public static int getNewCost(){
        return massartTowercost;
    } //pour infoTower car pas 1 tour en particulier

    public static double getNewRange(){return newRange;}
}

