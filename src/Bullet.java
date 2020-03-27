import java.util.ArrayList;
import java.util.Arrays;

public class Bullet {
    protected double speed;
    protected Point centre; // coordonnée
    protected double damage;
    protected double range;   // distance jusqu'à laquelle on est touché quand la bullet explose
    protected ArrayList<Enemy> enemies;
    protected Tower motherTower;

    public Bullet(){} // constructeur à faire

    public double getDamage(){return damage;}

    public void explode(){      //hurt les ennemis dont l'origine est dans la range de la bullet
        for(Enemy e : enemies){
            if( this.centre.distance(e.getOrigin()) <= range){
                e.hurt(this);
            }
        }
    }
    public Tower getMotherTower(){return motherTower;}

}
