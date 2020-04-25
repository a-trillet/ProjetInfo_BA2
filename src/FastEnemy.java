import java.util.ArrayList;

public class FastEnemy extends Enemy {
    private static double MaxLife = 20;
    private static int reward = 10;
    private static String type = "Fast enemy";
    private static int speed = 20;
    private static int power = 1;

    public FastEnemy(ArrayList<Point>trackPoints) {
        super(trackPoints, MaxLife, reward,speed);
        this.enemyType = type;
        this.enemyPower = power;
        this.enemySpeed = speed;
    }
}
