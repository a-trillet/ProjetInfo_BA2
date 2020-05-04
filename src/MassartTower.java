import javafx.scene.paint.Color;

public class MassartTower extends Tower {

    private static int massartTowercost = 150;
    private static  double newRange = 100;
    private static  double newDamage = 0;
    private static int newReloadTime = 5000;
    private static int newBulletRange = 40;
    private static double newFreezeTime = 2000;
    private String type = "Massart tower";

    public MassartTower(Point origin,Drawing d) {
        super(origin,d);
        this.cost = massartTowercost;
        this.range = newRange;
        this.damage = newDamage;
        this.reloadTime = newReloadTime;
        this.bulletRange = newBulletRange;
        this.towerType = type;
        this.freezeTime = newFreezeTime;
    }
    public static int getNewCost(){
        return massartTowercost;
    }

}
