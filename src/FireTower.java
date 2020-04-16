public class FireTower extends Tower{

    private static int cost=20;
    public FireTower(Point originPoint){
        super(originPoint);
        
    }

    public int getCost() {
        return cost;
    }
}
