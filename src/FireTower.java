public class FireTower extends Tower{

    private static int cost=4;
    public FireTower(Point originPoint){
        super(originPoint);
        
    }

    public static int getCost() {
        return cost;
    }
}
