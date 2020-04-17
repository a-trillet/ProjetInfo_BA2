public class FireTower extends Tower{

    private static int fireTowercost=100;
    private  static  double newRange = 100;

    public FireTower(Point originPoint){
        super(originPoint,fireTowercost); //prc utilisé ds arg de Tower()
        this.range = newRange;
        
    }


}
