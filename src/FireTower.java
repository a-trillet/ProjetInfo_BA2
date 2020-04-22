public class FireTower extends Tower{

    private static int fireTowerCost = 150;
    private static  double newRange = 200;
    private static  double newDamage = 25;
    private static int newReloadTime = 1500;
    private static int newBulletRange = 10;
    private String type = "Fire tower";

    public FireTower(Point originPoint){
        super(originPoint);
        this.cost = fireTowerCost;
        this.range = newRange;
        this.damage = newDamage;
        this.reloadTime = newReloadTime;
        this.bulletRange = newBulletRange;
        this.towerType = type;

    }


}
