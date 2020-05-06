import javafx.scene.paint.Color;

public class SycamoreTower extends  Tower {

    private static int sycamoreTowercost = 100;
    private static  double newRange = 75;
    private static  double newDamage = 5;
    private static int newReloadTime = 2000;
    private static int newBulletRange = 13; //parfois loupe son coup mais jsp si ça correspond bien
    private String type = "Sycamore tower";
    private String newPowerType = "Double shoot";
    private double newPowerDuration = 6000;

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
    }
    public static int getNewCost(){
        return sycamoreTowercost;
    }
    public static double getNewRange(){return newRange;}

    @Override
    public void powerActivation(){                      //sert à diminuer le reload time dans le run the tower pour une rafale
        powerActive = true;
        powerStartTime = System.currentTimeMillis();

        numberOfKill = 0;
    }
}
