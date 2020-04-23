public class NormalEnemy extends Enemy {

    private static double maxLife = 50;
    private static int reward = 10;
    private static String type = "Normal enemy";
    private static int speed = 10;
    private static int power = 1;  //plus ils sont puissant plus il te nique


    public NormalEnemy(Point centre){
        super(centre, maxLife, reward);
        this.enemyType = type;
        this.enemyPower = power;
        this.enemySpeed = speed;

    }

}
