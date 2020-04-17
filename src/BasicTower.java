public class BasicTower extends Tower {

    private static  int basicTowerCost = 100;
    private static  double newRange = 75;
    private static  double newDamage = 10;
    private static int newReloadTime = 500;
    private static int newBuletRange = 100;

    public BasicTower(Point origin){
        super(origin, basicTowerCost);
        this.range = newRange;
        this.damage = newDamage;
        this.reloadTime = newReloadTime;
        this.bulletrange = newBuletRange;
    }
}
