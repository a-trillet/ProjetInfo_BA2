public class IceTower extends Tower {

    private static int iceTowercost = 150;
    private static  double newRange = 100;
    private static  double newDamage = 0;
    private static int newReloadTime = 5000;
    private static int newBulletRange = 40;
    private static int freezeTime = 3000;
    private String type = "Ice tower";

    public IceTower(Point origin) {
        super(origin);
        this.cost = iceTowercost;
        this.range = newRange;
        this.damage = newDamage;
        this.reloadTime = newReloadTime;
        this.bulletRange = newBulletRange;
        this.towerType = type;
    }
    public static int getNewCost(){
        return iceTowercost;
    }
    public static int getFreezeTime(){
        return freezeTime;
    }
}
