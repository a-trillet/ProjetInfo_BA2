import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class StackTower extends Tower {


    private static int[] newUpgradeCosts = {50,70,100,0};
    private static double[] newRanges = {100,150,200};
    private static double[] newDamages = {15,20, 30};
    private static int newReloadTime = 750;
    private static int newBulletRange = 10;
    private static String type = "Stack Overflow tower";
    private static String newPowerType = "Burst Fire";
    private static double[] newPowerDurations = {3500,4500,5000};
    private static Color newColor = new Color(0, 1, 0, 1);
    private transient static Image newImageTower = new Image(StackTower.class.getResourceAsStream("Images/stack.jpg"));
    private transient static  Image newimageBullet = new Image(Tower.class.getResourceAsStream("Images/handCursor.png"));


    public StackTower(Point origin){
        super(origin);
        this.upgradeCosts = newUpgradeCosts;
        this.ranges = newRanges;
        this.damages = newDamages;
        this.reloadTime = newReloadTime;
        this.bulletRange = newBulletRange;
        this.towerType = type;
        this.powerType = newPowerType;
        this.powerDurations = newPowerDurations;
        color = newColor;
    }

    @Override
    public void powerActivation(){                      //sert à diminuer le reload time dans le run the tower pour une rafale

        powerStartTime = System.currentTimeMillis();
        powerActive = true;
        numberOfKill = 0;
    }

    @Override
    public void run() {
        //le premier thread.sleep est important pour que le run ne se lance pas avant que tout soit loaded
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(active) {
            if (targetEnemy == null || this.centre.distance(targetEnemy.getCentre()) > this.getRange() || !targetEnemy.isAlive() ) {
                targetEnemy = selectTarget();
            }
            if (targetEnemy != null) {
                shoot();
                System.out.println("shoot"+targetEnemy.getCentre().getY());
                try {
                    if (powerActive){
                        if(System.currentTimeMillis()< powerDurations[level-1]+ powerStartTime){
                            Thread.sleep((int)(reloadTime/(2*level)));
                        }
                        else{
                            powerActive = false;
                            Thread.sleep(reloadTime);
                        }
                    }
                    else{
                        Thread.sleep(reloadTime);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // c'est pour pas attendre une seconde si on a pas tiré, et que le run ne fasse pas buger le programme avec un while (true) sans sleep
            else {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public ImageView getImageBullet(Point centre, double angle){
        ImageView imageView = new ImageView();
        double size = 20;
        imageView.setImage(newimageBullet);
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

    @Override
    public void setTowerShape(){
        ImageView imageView = new ImageView(newImageTower);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        imageView.setX(centre.getX()-30/2);
        imageView.setY(centre.getY()-30/2);
        shape=imageView;
    }



    public static int getNewCost(){ return newUpgradeCosts[0];}
    public static double getNewRange(){return newRanges[0];}
    public static int getNewReloadTime(){return newReloadTime;}
    public static double getNewDamage(){return newDamages[0];}
}

