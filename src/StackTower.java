import javafx.scene.paint.Color;

public class StackTower extends Tower {

    private static  int basicTowerCost = 100;
    private static  double newRange = 75;
    private static  double newDamage = 10;
    private static int newReloadTime = 500;
    private static int newBulletRange = 10;
    private String type = "Stack Overflow tower";
    private String newPowerType = "Burst Fire";

    public StackTower(Point origin,Drawing d){
        super(origin,d);
        this.cost = basicTowerCost;
        this.range = newRange;
        this.damage = newDamage;
        this.reloadTime = newReloadTime;
        this.bulletRange = newBulletRange;
        this.towerType = type;
        this.powerType = newPowerType;
    }
    public static int getNewCost(){
        return basicTowerCost;
    }



    @Override
    public void powerActivation(){}
}

