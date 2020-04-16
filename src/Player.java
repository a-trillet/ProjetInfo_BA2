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

    public Player(){
        reset();
    }



    public static synchronized Player getPlayer() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    public void loadProfile(String saveFile) {
    }    // futur alternative à reset

    public void reset() {                            // (re)initialisation de la partie, à completer en remettant vagues à 0,ect; si on veut vraiment pouvoir reset, plutot la completer  faire dans une autre fonction
        lifePoints = startingLives[difficulty - 1];
        gold = startingGold;
        towerList = new ArrayList<>();
        enemiesOnMap = new ArrayList<>();
        enemyFactory = new EnemyFactory(difficulty, MapPane.getEntryPoint());

    }

    public ArrayList<Enemy> getEnemiesOnMap() {
        return enemiesOnMap;
    }

    public ArrayList<Tower> getTowerList() {
        return towerList;
    }

    public int getLives() {
        return lifePoints;
    }    // ATTENTION obligé de faire Player.getPlayer().getLives pour créer un nouveau joueur, mais comme il est static on s'en fout on, accede à la classe directement , d'ou la méthode static getPlayer

    public int getGold() {
        return gold;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void decreaseLife(int dmg) {
        lifePoints -= dmg;
        PlayScreen.drawing.changeGoldLives(gold,lifePoints, startingLives[difficulty-1], wave);/// modifie le compteur de vie
    }

    public void addGold(int amount) {
        gold += amount;
        PlayScreen.drawing.changeGoldLives(gold,lifePoints, startingLives[difficulty-1], wave);

    }

    public void setDifficulty(int diff) {
        difficulty = diff;
    }

    public void addTower(Tower tower) {
        towerList.add(tower);
    }

    public void nextWave() {
        wave++;
        PlayScreen.drawing.changeGoldLives(gold,lifePoints, startingLives[difficulty-1], wave);
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

    public void addEnemy(Enemy e){
        enemiesOnMap.add(e);
    }

    public int getMaxLives(){ return startingLives[difficulty-1];}
}