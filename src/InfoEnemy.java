public class InfoEnemy extends Info {
    //info pour les enemis
    private int speed;
    private double life;
    private double lifeMax;
    private String enemyType; //à faire

    public InfoEnemy(Enemy enemy){
        super(enemy);
        this.speed = enemy.getSpeed();
        this.life = enemy.getLifePoints();
        this.lifeMax = enemy.getMaxLifePoints();
    }

    @Override
    public String listString() {
        return null;
    }
}
