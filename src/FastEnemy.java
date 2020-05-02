import java.util.ArrayList;

public class FastEnemy extends Enemy {
    private static double MaxLife = 20;
    private static int reward = 10;
    private static String type = "Fast enemy";
    private static int speed = 13;
    private static int power = 1;

    public FastEnemy(ArrayList<Point>trackPoints, Point origin) {
        super(trackPoints,"F", origin, MaxLife, reward);
        this.enemyType = type;
        this.enemyPower = power;
        //this.enemySpeed = speed;
    }
}
