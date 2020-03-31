public class FireBullet extends Bullet {
    //ajouter image bullet
    private double speed; // je pense qu'il vaut mieux que ca siot de lka mere comme toutes les bullet en ont une, on pourra dire bullet.get speed

    public FireBullet(Tower t, Point targetPoint ){
        super(4, t, 4, targetPoint); //4 pour l'exemple
    }

} // c'est une balle de feu ou tirer une balle? Antoine
