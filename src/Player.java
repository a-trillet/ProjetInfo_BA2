import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

    private static final long serialVersionUID = 2L;
    private String name = "john";
    private  int gold =150;
    private  int lifePoints;
    private  ArrayList<Enemy> enemiesOnMap = new ArrayList<>();
    private  ArrayList<Tower> towerList = new ArrayList<>();
    private  int difficulty = 1;
    private  final int[] startingLives = {20, 18, 16, 14};
    private  final int startingGold = 150;
    private  int wave = 0;
    private  transient EnemyFactory enemyFactory;
    private ArrayList<ArrayList<Point>> allRoutes;

    public Player(){}

    public void  loadMap(){
        allRoutes = MapFactory.getAllRoutes();
    }

    public void reset() {
        lifePoints = startingLives[difficulty - 1];
        gold = startingGold;
        towerList = new ArrayList<>();
        enemiesOnMap = new ArrayList<>();
        enemyFactory = new EnemyFactory(difficulty,allRoutes);
    }

    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException
    {
        aInputStream.defaultReadObject();
        if(Game.isOnGame){
            enemyFactory = new EnemyFactory(difficulty,allRoutes);
            Game.getDrawing().drawLifeGold();
        }
    }

    public void removeTower(Tower tower){
        towerList.remove(tower);
        int sellPrice=tower.sell();
        this.addGold(sellPrice);
    }

    public void decreaseLife(int dmg) {
        lifePoints -= dmg;
        if (lifePoints<=0){Game.lose();}
        Platform.runLater(()->Game.getDrawing().getChildren().add(new Tips(3,new Point(20,250),Game.getDrawing())));
        Game.getDrawing().drawLifeGold();
    }

    public void addGold(int amount) {
        gold += amount;
        Game.getDrawing().drawLifeGold();

    }

    public void addTower(Tower tower) {
        towerList.add(tower);
    }

    public void nextWave() {
        wave++;
        Game.getDrawing().drawLifeGold();
    }

    public void addEnemy(Enemy e){
        enemiesOnMap.add(e);
    }

    public void removeEnemy(Enemy e){
        enemiesOnMap.remove(e);
    }


    public int getLives() { return lifePoints; }

    public int getGold() {
        return gold;
    }

    public ArrayList<Enemy> getEnemiesOnMap() {
        return enemiesOnMap;
    }

    public ArrayList<Tower> getTowerList() {
        return towerList;
    }

    public ArrayList<ArrayList<Point>> getAllRoutes(){return allRoutes;}

    public int getWave() {
        return wave;
    }

    public EnemyFactory getEnemyFactory() {
        return enemyFactory;
    }

    public int getMaxLives(){ return startingLives[difficulty-1];}

    public String getName(){return name;}

    public void setName(String name){this.name = name;}

    public void setDifficulty(int diff) {
        difficulty = diff;
    }

    public int getDifficulty(){
        return difficulty;
    }
}