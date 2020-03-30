public class FireBullet extends Bullet {
    //ajouter image bullet
    private double speed;

    public FireBullet(Tower t, Point targetPoint ){
        super(4, t, 4, targetPoint); //4 pour l'exemple
    }

}
