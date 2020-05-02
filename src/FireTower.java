import javafx.scene.paint.Color;

public class FireTower extends Tower{

    private static int fireTowerCost = 150;
    private static  double newRange = 200;
    private static  double newDamage = 25;
    private static int newReloadTime = 1500;
    private static int newBulletRange = 15;
    private String type = "Fire tower";

    public FireTower(Point originPoint,Drawing d){
        super(originPoint,d);
        this.cost = fireTowerCost;
        this.range = newRange;
        this.damage = newDamage;
        this.reloadTime = newReloadTime;
        this.bulletRange = newBulletRange;
        this.towerType = type;

    }
    public static int getNewCost(){
        return fireTowerCost;
    }


}
