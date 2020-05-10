import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class MassartTower extends Tower {

    private static int[] newUpgradeCosts = {50,70,100,0};
    private static double[] newRanges = {100,150,200};
    private static double[] newDamages = {15,20, 30};
    private static int newReloadTime = 2000;
    private static int newBulletRange = 40;
    private static String type = "Massart tower";
    private static String newPowerType = "Total Slow";
    private static double[] newPowerDurations = {4000,5000,6000};
    private static Color newColor = new Color(0, 0.1, 1, 0.7);
    private transient static Image newImageTower = new Image(MassartTower.class.getResourceAsStream("Images/massart.jpg"));
    private transient static Image imageBullet = new Image(Tower.class.getResourceAsStream("Images/turtle2.png"));

    public MassartTower(Point origin) {
        super(origin);
        this.upgradeCosts = newUpgradeCosts;
        this.ranges = newRanges;
        this.damages = newDamages;
        this.reloadTime = newReloadTime;
        this.bulletRange = newBulletRange;
        this.towerType = type;
        this.powerType = newPowerType;
        this.powerDurations = newPowerDurations;
        this.color = newColor;
    }

    @Override
    public void powerActivation(){                      //sert à diminuer le reload time dans le run the tower pour une rafale
        powerStartTime = System.currentTimeMillis();
        powerActive = true;
        numberOfKill = 0;
        Enemy.setEnemyVelocity(6);          //peut choisir la vitesse des enemis durant 'activation du power
        Enemy.setFrozen(true);
        Enemy.setFreezeDuration(powerDurations[level-1]);
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
        ImageView imageView = new ImageView();
        imageView.setImage(imageBullet);
        imageView.setFitWidth(size);
        imageView.setPreserveRatio(true);
        imageView.relocate(centre.getX()-(size/2),centre.getY()-(size/2));
        imageView.setRotate(0 + angle);// rotate fonctionne dans le sens horlogique par rapport à l'écran
        return imageView;
    }

    @Override
    public void setTowerShape(){
        ImageView imageView = new ImageView(newImageTower);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        imageView.setX(centre.getX()-30/2);
        imageView.setY(centre.getY()-30/2);
        shape=imageView;
    }

    @Override
    public void shoot(Point point){
        Tower t = this;
        double degats = this.getDamage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Game.getDrawing().draw(new ZoneBullet(degats,t,bulletRange,point,new Point(centre.getX(),centre.getY())));
            }
        });
    }

    public static int getNewCost(){
        return newUpgradeCosts[0];
    } //pour infoTower car pas 1 tour en particulier
    public static double getNewRange(){return newRanges[0];}
    public static int getNewReloadTime(){return newReloadTime;}
    public static double getNewDamage(){return newDamages[0];}
}

