import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class EnemyFactory implements Runnable {
    private static Thread thread;


    public EnemyFactory(int diff, Point entry){
        loadEnemyWaves(diff, entry);
        this.thread = new Thread(this);

    }

        // les différentes vagues
    private static final int[][] easyWaves = {
            {2,1,0,0,0},
            {5,2,0,0,0},
            {6,2,1,0,0},
            {6,3,2,2,1},

    };
    private static final int[][] normalWaves = {
            {2,1,0,0,0},
            {5,2,0,0,0},
            {6,2,1,0,0},
            {6,3,2,2,1},
            {6,3,3,3,1},
            {8,4,4,4,1},
    };

    private static final int[][] hardWaves = {
            {2, 1, 0, 0, 0},
            {5, 2, 0, 0, 0},
            {6, 2, 1, 0, 0},
            {6, 3, 2, 2, 1},
            {6, 3, 3, 3, 1},
            {8, 4, 4, 4, 1},
            {9, 5, 5, 5, 2},
            {10, 6, 6, 6, 2},
    };

    private static final int[][] insaneWaves = { //lignes = nb d'unité, colone = type d'ennemi : "normal, fast, big, rien"
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
    private static final int[][][] wavesDifficulties = {easyWaves, normalWaves, hardWaves, insaneWaves};

    private static LinkedList<Enemy> createWave(int wave, int diff, Point entry){                                   //// ATTENTION index out of bound 6 array
        LinkedList<Enemy> waveList = new LinkedList<>();
        for (int j = 0 ; j<3; j++){                                            //car seulement 3 types de monstres pour le moment
            for(int i = 1; i <= wavesDifficulties[diff-1][wave-1][j]; i++){
                switch(j){
                    case 0 : {
                        waveList.add(new NormalEnemy(entry));
                        break;
                    }
                    case 1 : {
                        waveList.add(new FastEnemy(entry));
                        break;
                    }
                    case 2 : {
                        waveList.add(new BigEnemy(entry));
                        break;
                    }
                    default: {System.out.println("le programme tente de créer autre chose que 0 1 2");}
                }
            }
        }
        return waveList;
    }


    public static ArrayList<LinkedList<Enemy>> allWaves = new ArrayList<>();

    public static LinkedList<Enemy> activeWave;

    private static boolean waveInProgress = false;

    private

    static void  loadEnemyWaves(int difficulty, Point entry){
        ArrayList<LinkedList<Enemy>> enemyWaves = new ArrayList<>();
        for (int i =1; i <= wavesDifficulties[difficulty-1].length ; i++){
            LinkedList<Enemy> wave = createWave(i, difficulty, entry);
            Collections.shuffle(wave);
            enemyWaves.add(wave);
        }
        allWaves = enemyWaves;

    }
    public static void nextWave(){
        if(Player.getWave() <allWaves.size()) {
            Player p = Player.getPlayer();
            activeWave = allWaves.get(Player.getWave());
            Player.getPlayer().nextWave();
            System.out.println("Wave " + Player.getWave());
            waveInProgress = true;                                      //je sais plus pourquoi j'en avait besoin
            launchEnemy();

        }
    }
    public static void launchEnemy(){
        thread.start();
    }

    @Override
    public void run(){
        try {

            for(int indice = 0; indice <=activeWave.size();indice++) {
                Thread.sleep(1000);            //a faire diminuer quand la wave augmente
                System.out.println("ennemi..."+indice);      //test
                if (indice == activeWave.size()) {
                    waveInProgress = false;
                    System.out.println("fin de vague");
                } else {

                    activeWave.get(indice).setAlive();
                    Player.getPlayer().addEnemy(activeWave.get(indice));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


// faire un persone factory dans player et un constructeur qui crée cette fameuse liste




}