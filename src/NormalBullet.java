import java.util.ArrayList;

public class NormalBullet extends Bullet {
    public NormalBullet(double damage, Tower t, double range, Point targetPoint, Point originPoint) {
        super(damage, t, range, targetPoint, originPoint);
    }

    @Override
    public void explode() {      //hurt les ennemis dont l'origine est dans la range de la bullet
        Enemy target = null;
        Double dist = null;
        for (Enemy e : Game.getPlayer().getEnemiesOnMap()) {
            double sepa = this.getCentre().distance(e.getCentre());
            if ((target == null || sepa < dist) && sepa <= this.getRange()&&e.isOnTrack()) {
                target = e;
                dist = sepa;
            }
        }
        if(target!=null){
            target.hurt(this);
            if (!target.isAlive()){Game.getPlayer().removeEnemy(target);
        }
        }
    }
}

