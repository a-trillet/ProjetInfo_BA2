import java.util.ArrayList;
import java.util.LinkedList;

public class Player {
    private static int gold = 700;
    private static int lifePoints;
    private static ArrayList<Enemy> enemiesOnMap = new ArrayList<>();
    private static ArrayList<Tower> towerList = new ArrayList<>();
    private static int difficulty = 2;
    private static Player instance = null; // pour qu'on ne puisse construire qu'un seul objet player, si on veut avoir des parties sauvergardée, il suffira de créer un fonction charger, qui puisera dans un fichier et completera le profil
    private static final int[] startingLives = {20, 18, 16, 15};
    private static final int startingGold = 100;
    private static int wave = 1;
    private static final ArrayList<LinkedList<Enemy>> enemyWaves = loadWaveFile("");
    private static  LinkedList<Enemy> currentEnemyQueue = enemyWaves.get(wave);


    public static synchronized Player getPlayer(){
        if(instance == null){
            instance = new Player();
        }
        return instance;
    }
    public void loadProfile( String saveFile){}    // futur alternative à reset

    private static ArrayList<LinkedList<Enemy>> loadWaveFile(String file){
        ArrayList<LinkedList<Enemy>> waves = new ArrayList<LinkedList<Enemy>>();

        return new ArrayList<>();
    }
    public void reset(){                            // (re)initialisation de la partie, à completer en remettant vagues à 0,ect; si on veut vraiment pouvoir reset, plutot la completer  faire dans une autre fonction
        lifePoints = startingLives[difficulty-1];
        gold = startingGold;
    }

    public ArrayList<Enemy> getEnemiesOnMap() {
        return enemiesOnMap;
    }

    public ArrayList<Tower> getTowerList() {
        return towerList;
    }

    public int getLives(){ return lifePoints;}    // ATTENTION obligé de faire Player.getPlayer().getLives pour créer un nouveau joueur, mais comme il est static on s'en fout on, accede à la classe directement , d'ou la méthode static getPlayer

    public int getGold(){ return gold;}

    public int getDifficulty(){ return difficulty;}

    public void decreaseLife(int dmg){
        lifePoints -= dmg;
    }

    public void addGold(int amount){
        gold += amount;
    }
    public void setDifficulty(int diff){
        difficulty = diff;
    }
    public void addTower(Tower tower){
        towerList.add(tower);
    }
    public void nextWave(){wave++;}
}
