import javafx.scene.paint.Color;

public class SniperTower extends  Tower {

    private static int sniperTowercost = 1000;
    private static  double newRange = 75;
    private static  double newDamage = 10000; //comme ça ça headshot
    private static int newReloadTime = 5000;
    private static int newBulletRange = 30; //parfois loupe son coup mais jsp si ça correspond bien
    private String type = "Sniper tower";

    public SniperTower(Point origin, Drawing d){
        super(origin,d);
        this.cost = sniperTowercost;
        this.range = newRange;
        this.damage = newDamage;
        this.reloadTime = newReloadTime;
        this.bulletRange = newBulletRange;
        this.towerType = type;
    }
    public static int getNewCost(){
        return sniperTowercost;
    }
}
