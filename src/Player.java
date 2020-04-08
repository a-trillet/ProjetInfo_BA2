import java.util.ArrayList;

public class Player {
    private int gold;
    private int lifepoints;
    private ArrayList<Enemy> enemiesOnMap = new ArrayList<>();
    private ArrayList<Tower> towerList = new ArrayList<>();


    public ArrayList<Enemy> getEnemiesOnMap() {
        return enemiesOnMap;
    }

    public ArrayList<Tower> getTowerList() {
        return towerList;
    }
}
