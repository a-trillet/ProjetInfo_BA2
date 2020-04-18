public class SniperTower extends  Tower {

    private static int snipeTowercost = 1000;
    private static  double newRange = 75;
    private static  double newDamage = 10000; //comme ça ça headshot
    private static int newReloadTime = 5000;
    private static int newBuletRange = 30; //parfois loupe son coup mais jsp si ça correspond bien
    private String type = "Sniper tower";

    public SniperTower(Point origin){
        super(origin, snipeTowercost);
        this.range = newRange;
        this.damage = newDamage;
        this.reloadTime = newReloadTime;
        this.bulletrange = newBuletRange;
        this.towerType = type;
    }
}
