import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class EnemyFactory {

        // les différentes vagues
    private final int[][] easyWaves = {
            {2,1,0,0,0},
            {5,2,0,0,0},
            {6,2,1,0,0},
            {6,3,2,2,1},

    };
    private final int[][] normalWaves = {
            {2,1,0,0,0},
            {5,2,0,0,0},
            {6,2,1,0,0},
            {6,3,2,2,1},
            {6,3,3,3,1},
            {8,4,4,4,1},
    };

    private final int[][] hardWaves = {
            {2, 1, 0, 0, 0},
            {5, 2, 0, 0, 0},
            {6, 2, 1, 0, 0},
            {6, 3, 2, 2, 1},
            {6, 3, 3, 3, 1},
            {8, 4, 4, 4, 1},
            {9, 5, 5, 5, 2},
            {10, 6, 6, 6, 2},
    };

    private final int[][] insaneWaves = { //lignes = nb d'unité, colone = type d'ennemi : "normal, fast, big, rien"
            {2, 1, 0, 0, 0},
            {5, 2, 0, 0, 0},
            {6, 2, 1, 0, 0},
            {6, 3, 2, 2, 1},
            {6, 3, 3, 3, 1},
            {8, 4, 4, 4, 1},
            {9, 5, 5, 5, 2},
            {10, 6, 6, 6, 2},
            {12, 6, 6, 7, 2},
            {15, 6, 8, 8, 2},
            {16, 6, 9, 9, 3},
            {20, 8, 10, 10, 3},

    };
    private final int[][][] wavesDifficulties = {easyWaves, normalWaves, hardWaves, insaneWaves};

    private LinkedList<Enemy> createWave(int wave, int diff, Point entry){
        LinkedList<Enemy> waveList = new LinkedList<>();
        for (int j = 0 ; j<=2; j++){                                            //car seulement 3 types de monstres pour le moment
            for(int i = 1; i <= wavesDifficulties[diff-1][wave-1][j]; i++){
                switch(j){
                    case 0 : {
                        waveList.add(new NormalEnemy(entry));
                    }
                    case 1 : {
                        waveList.add(new FastEnemy(entry));
                    }
                    case 2 : {
                        waveList.add(new BigEnemy(entry));
                    }
                }
            }
        }
        return waveList;
    }
    public ArrayList<LinkedList<Enemy>> loadEnemyWaves(int difficulty, Point entry){
        ArrayList<LinkedList<Enemy>> enemyWaves = new ArrayList<>();
        for (int i =1; i <= wavesDifficulties[difficulty].length ; i++){
            LinkedList<Enemy> wave = createWave(i, difficulty, entry);
            Collections.shuffle(wave);
            enemyWaves.add(wave);
        }
        return enemyWaves;
    }





}