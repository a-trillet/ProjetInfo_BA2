import java.util.ArrayList;

public class NormalEnemy extends Enemy {

    private static double maxLife = 50;
    private static int reward = 10;
    private static String type = "Normal enemy";
    private static int speed = 13;
    private static int power = 1;  //plus ils sont puissant plus il te nique


    public NormalEnemy(ArrayList<Point>trackPoints, Point origin, String lettre){
        super(trackPoints,lettre, origin, maxLife, reward);
        this.enemyType = type;
        this.enemyPower = power;
        this.enemySpeed = speed;

    }

}
