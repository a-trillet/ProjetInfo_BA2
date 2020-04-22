public class IceTower extends Tower {

    private static int iceTowercost = 150;
    private static  double newRange = 65;
    private static  double newDamage = 0;
    private static int newReloadTime = 1500;
    private static int newBuletRange = 200;
    //ajouter freeze
    private String type = "Ice tower";

    public IceTower(Point origin) {
        super(origin);
        this.cost = iceTowercost;
        this.range = newRange;
        this.damage = newDamage;
        this.reloadTime = newReloadTime;
        this.bulletrange = newBuletRange;
        this.towerType = type;
    }
}
