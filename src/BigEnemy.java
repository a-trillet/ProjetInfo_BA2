public class BigEnemy extends Enemy{
    private static double maxLife = 150;
    private static int reward = 50;
    private static String enemyType = "Big enemy";

    public BigEnemy(Point origin) {
        super(origin, maxLife, reward);
    }
}

