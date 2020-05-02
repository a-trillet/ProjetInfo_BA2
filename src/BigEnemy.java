import java.io.Serializable;
import java.util.ArrayList;

public class BigEnemy extends Enemy {
    private static double maxLife = 150;
    private static int reward = 50;
    private static String type = "Big enemy";
    private static int speed = 13;
    private static int power = 3;

    public BigEnemy(ArrayList<Point> trackPoints, Point origin) {
        super(trackPoints,"B",origin, maxLife, reward);
        this.enemyType = type;
        this.enemyPower = power;
        //this.enemySpeed = speed;
    }
}

