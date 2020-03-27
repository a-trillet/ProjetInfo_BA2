public class Enemy implements Killable {
    protected Point origin;
    boolean alive = true;
    double lifePoints = 0;
    double maxLifePoints = 0;



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
            this.die();
        }
    }

    @Override
    public void die() {
        this.alive = false;
    }
}
