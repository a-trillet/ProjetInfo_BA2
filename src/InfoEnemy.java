import static java.lang.Math.round;

public class InfoEnemy extends Info {
    //info pour les enemis
    private int speed;
    private double life;
    private double lifeMax;
    private String enemyType;

    public InfoEnemy(Enemy enemy){
        super(enemy);
        this.speed = (int)round(enemy.getSpeed());
        this.life = enemy.getLifePoints();
        this.lifeMax = enemy.getMaxLifePoints();
        this.enemyType = enemy.getEnemyType();
    }

    @Override
    public String listString() {
        String strType = "Enemy of type : "+ enemyType;
        String strLife = "Life : "+ life;
        String strLifeMax = "Life Max : " + lifeMax;
        String strSpeed = "Speed : " + speed;

        return strType+"\n" + strLife+"\n" + strLifeMax+"\n" + strSpeed;
    }
}
