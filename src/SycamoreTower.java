import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class SycamoreTower extends  Tower {

    private static int[] newUpgradeCosts = {50,70,100,0};
    private static double[] newRanges = {100,150,200};
    private static double[] newDamages = {15,20, 30};
    private static int newReloadTime = 750;
    private static int newBulletRange = 13; //parfois loupe son coup mais jsp si ça correspond bien
    private static String type = "Sycamore tower";
    private static String newPowerType = "Double shoot";
    private static double[] newPowerDurations = {3500,4500,5500};
    private static Color newColor = new Color(1, 0, 1, 0.3);
    private transient static Image newImageTower = new Image(SycamoreTower.class.getResourceAsStream("sycamore.jpg"));
    private transient static Image imageBullet = new Image(Tower.class.getResourceAsStream("logoGoogle.png"));

    public SycamoreTower(Point origin){
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

    private Enemy selectSecondTarget(){   //Cette fonction renvoit l'ennemi, en range, le plus proche du centre de la tour, !! et qui n'est pas targetEnnemy
        Enemy target = null;
        Double dist = null;
        for (Enemy e : Game.getPlayer().getEnemiesOnMap()) {
            double sepa = this.centre.distance(e.getCentre());
            if ((target == null || sepa < dist) && sepa <= this.getRange() && e!= targetEnemy) {
                target = e;
                dist = sepa;
                break;
            }
        }

        return target;
    }

    @Override
    public void powerActivation(){                      //sert à diminuer le reload time dans le run the tower pour une rafale
        powerActive = true;
        powerStartTime = System.currentTimeMillis();

        numberOfKill = 0;
    }

    @Override
    public void shoot(){
        Tower t = this;
        double degats = this.getDamage();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                Game.getDrawing().draw(new Bullet(degats,t,bulletRange,targetEnemy.getCentre(),new Point(centre.getX(),centre.getY())));
                if (powerActive){
                    if (secondTargetEnemy != null){
                        Game.getDrawing().draw(new Bullet(degats,t,bulletRange,secondTargetEnemy.getCentre(),new Point(centre.getX(),centre.getY())));
                    }
                }
            }
        });
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
            if (powerActive){
                if (System.currentTimeMillis()< powerDurations[level-1]+ powerStartTime){
                    if (secondTargetEnemy == null || this.centre.distance(secondTargetEnemy.getCentre()) > this.getRange() || !secondTargetEnemy.isAlive() ) {
                        secondTargetEnemy = selectSecondTarget();
                    }
                }
                else{powerActive = false;}
            }

            if (targetEnemy != null) {
                shoot();
                System.out.println("shoot"+targetEnemy.getCentre().getY());
                try {
                        Thread.sleep(reloadTime);
                }
                catch (InterruptedException e) {
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
        double size = 14;
        ImageView imageView = new ImageView();
        imageBullet = new Image(Tower.class.getResourceAsStream("logoGoogle.png"));
        imageView.setImage(imageBullet);
        imageView.setFitWidth(size);
        imageView.setPreserveRatio(true);
        imageView.relocate(centre.getX()-(size/2),centre.getY()-(size/2));
        return imageView;
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


    @Override
    public int getKillPower(){
        int killPower = 5;
        killPower += (this.level-1)*5;
        return 0;
    }
    public static int getNewCost(){
        return newUpgradeCosts[0];
    }
    public static double getNewRange(){return newRanges[0];}
}
