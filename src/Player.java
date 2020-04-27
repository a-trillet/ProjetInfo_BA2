import java.util.ArrayList;
import java.util.LinkedList;

public class Player {
    private static int gold = 700;
    private static int lifePoints;
    private static ArrayList<Enemy> enemiesOnMap = new ArrayList<>();
    private static ArrayList<Tower> towerList = new ArrayList<>();
    private static int difficulty = 1;
    private static Player instance = null; // pour qu'on ne puisse construire qu'un seul objet player, si on veut avoir des parties sauvergardée, il suffira de créer un fonction charger, qui puisera dans un fichier et completera le profil
    private static final int[] startingLives = {20, 18, 16, 15};
    private static final int startingGold = 300;
    private static int wave = 0;
    private static EnemyFactory enemyFactory;
    private static ArrayList<Bullet> bullets=new ArrayList<>();

    public Player(){
        reset();
    }


    public void loadProfile(String saveFile) {
    }    // futur alternative à reset

    public void reset() {                            // (re)initialisation de la partie, à completer en remettant vagues à 0,ect; si on veut vraiment pouvoir reset, plutot la completer  faire dans une autre fonction
        lifePoints = startingLives[difficulty - 1];
        gold = startingGold;
        towerList = new ArrayList<>();
        enemiesOnMap = new ArrayList<>();
        new MapPane();
        ArrayList<ArrayList<Point>> a =new ArrayList<>();
        enemyFactory = new EnemyFactory(difficulty, MapPane.getAllRoutes());


    }

    public static ArrayList<Enemy> getEnemiesOnMap() {
        return enemiesOnMap;
    }

    public static ArrayList<Tower> getTowerList() {
        return towerList;
    }

    public static ArrayList<Bullet> getBullets(){return bullets;}

    public static void addbullet(Bullet bullet){bullets.add(bullet);}

    public static void removebullet(Bullet bullet){
        bullets.remove(bullet);
    }

    public static int getLives() {
        return lifePoints;
    }    // ATTENTION obligé de faire Player.getPlayer().getLives pour créer un nouveau joueur, mais comme il est static on s'en fout on, accede à la classe directement , d'ou la méthode static getPlayer

    public static int getGold() {
        return gold;
    }

    public static int getDifficulty() {
        return difficulty;
    }

    public static void decreaseLife(int dmg) {
        lifePoints -= dmg;
    }

    public static void addGold(int amount) {
        gold += amount;

    }

    public static void setDifficulty(int diff) {
        difficulty = diff;
    }

    public static void addTower(Tower tower) {
        towerList.add(tower);
    }

    public static void nextWave() {
        wave++;
    }

    public static int getLifePoints() {
        return lifePoints;
    }

    public static int getWave() {
        return wave;
    }

    public static EnemyFactory getEnemyFactory() {
        return enemyFactory;
    }

    public static void addEnemy(Enemy e){
        enemiesOnMap.add(e);
    }

    public static int getMaxLives(){ return startingLives[difficulty-1];}



}