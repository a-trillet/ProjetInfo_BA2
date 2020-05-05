import javafx.scene.paint.Color;

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
        Enemy.setTsarBombaDamage(damage);
        Enemy.setGeneralHurt(true);
        powerActive = false;
    }

}
