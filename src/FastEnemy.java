public class FastEnemy extends Enemy {
    private static double MaxLife = 20;
    private static int reward = 5;
    private static String enemyType = "Fast enemy";

    public FastEnemy(Point origin) {
        super(origin, MaxLife, reward);
    }
}
