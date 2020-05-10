import java.util.ArrayList;

public class ZoneBullet extends Bullet{
    public ZoneBullet(double damage, Tower t, double range, Point targetPoint, Point originPoint) {
        super(damage, t, range, targetPoint, originPoint);
    }

    @Override
    public void explode() {      //hurt les ennemis dont l'origine est dans la range de la bullet
        ArrayList<Enemy> enemiestoremove=new ArrayList<>();
        for( Enemy e: Game.getPlayer().getEnemiesOnMap()) {
            if (e.getCentre().distance(this.getCentre()) < this.getRange() && e.isAlive()) {
                e.hurt(this);
            }
            if (!e.isAlive()) {
                enemiestoremove.add(e);
            }
        }
        for (Enemy enemytoremove : enemiestoremove){
            Game.getPlayer().removeEnemy(enemytoremove);
        }
    }
}
