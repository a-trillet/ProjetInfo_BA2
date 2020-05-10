import java.util.ArrayList;

public class NormalEnemy extends Enemy {

    private static double maxLife = 50;
    private static int reward = 5;
    private static String type = "Normal enemy";
    private static int power = 1;


    public NormalEnemy(ArrayList<Point>trackPoints, Point origin, String lettre){
        super(trackPoints,lettre, origin, maxLife, reward);
        this.enemyType = type;
        this.enemyPower = power;
    }

}
