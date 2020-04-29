import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name = "john";
    private  int gold = 700;
    private  int lifePoints;
    private  ArrayList<Enemy> enemiesOnMap = new ArrayList<>();
    private  ArrayList<Tower> towerList = new ArrayList<>();
    private  int difficulty = 1;
    private  final int[] startingLives = {20, 18, 16, 15};
    private  final int startingGold = 300;
    private  int wave = 0;
    private  EnemyFactory enemyFactory;

    public Player(){
        PlayScreen.drawing.drawLifeGold();
        new MapPane();

    }


    public void loadProfile(String saveFile) {
    }    // futur alternative à reset

    public void reset() {                            // (re)initialisation de la partie, à completer en remettant vagues à 0,ect; si on veut vraiment pouvoir reset, plutot la completer  faire dans une autre fonction
        lifePoints = startingLives[difficulty - 1];
        gold = startingGold;
        towerList = new ArrayList<>();
        enemiesOnMap = new ArrayList<>();
        new MapPane();
        enemyFactory = new EnemyFactory(difficulty);
        PlayScreen.drawing.drawLifeGold();

    }

    public ArrayList<Enemy> getEnemiesOnMap() {
        return enemiesOnMap;
    }

    public ArrayList<Tower> getTowerList() {
        return towerList;
    }

    public int getLives() {
        return lifePoints;
    }

    public int getGold() {
        return gold;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void decreaseLife(int dmg) {
        lifePoints -= dmg;
        PlayScreen.drawing.drawLifeGold();
        //if (lifePoints<=0){lose()}
    }

    public void addGold(int amount) {
        gold += amount;
        PlayScreen.drawing.drawLifeGold();

    }

    public void setDifficulty(int diff) {
        difficulty = diff;
    }

    public void addTower(Tower tower) {
        towerList.add(tower);
    }

    public void nextWave() {
        wave++;
        PlayScreen.drawing.drawLifeGold();
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public int getWave() {
        return wave;
    }

    public EnemyFactory getEnemyFactory() {
        return enemyFactory;
    }

    public void addEnemy(Enemy e){
        enemiesOnMap.add(e);

    }

    public int getMaxLives(){ return startingLives[difficulty-1];}
    public String getName(){return name;}
    public void setName(String name){this.name = name;}



}