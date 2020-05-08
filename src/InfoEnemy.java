import static java.lang.Math.round;

public class InfoEnemy extends Info {
    private int speed;
    private double life;
    private double lifeMax;
    private String enemyType;
    private int power;
    private int reward;

    public InfoEnemy(Enemy enemy) {
        super(enemy);
        this.speed = (int) round(enemy.getSpeed());
        this.life = enemy.getLifePoints();
        this.lifeMax = enemy.getMaxLifePoints();
        this.enemyType = enemy.getEnemyType();
        this.power = enemy.getEnemyPower();
        this.reward = enemy.getReward();
    }

    @Override
    public String listString() {
        String strType = "Enemy of type : " + enemyType;
        String strLife = "Life : " + life;
        String strLifeMax = "Life Max : " + lifeMax;
        String strSpeed = "Speed : " + speed;
        String strPower = "Power : " + power;
        String strReward = "Reward : " + reward;

        return strType + "\n" + strLife + "\n" + strLifeMax + "\n" + strSpeed + "\n" + strPower + "\n"+ strReward;
    }
}
