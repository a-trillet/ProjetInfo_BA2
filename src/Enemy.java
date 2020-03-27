public class Enemy implements Killable {
    protected Point origin;
    protected boolean alive = true;
    protected double lifePoints = 0;
    protected double maxLifePoints = 0;



    public Enemy( Point origin, double life){
        this.origin = origin;
        this.lifePoints = life;
        this.maxLifePoints = life;
    }

    public Point getOrigin(){
        return this.origin;
    }

    @Override
    public void hurt(Bullet bullet) {
        this.lifePoints -= bullet.getDamage();
        if(this.lifePoints <= 0){
            this.die(bullet.getMotherTower());
        }
    }

    @Override
    public void die(Tower killer) {
        this.alive = false;
        killer.targetIsDead(this);
    }
}
