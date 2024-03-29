import javafx.application.Platform;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class EnemyFactory implements Runnable, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String[] easyWaves ={
                "Bug",
                "Big Error",
                "Java Exception",
                "BOSS"
    };
    private static final String[] normalWaves = {
            "Bug",
            "Big Error",
            "Java Exception",
            "Indice out of bounds",
            "Concurent Modification",
            "BOSS"
    };
    private static final String[] hardWaves = {
            "Bug",
            "Big Error",
            "Java Exception",
            "Indice out of bounds",
            "Not on FX application thread",
            "Reflect Invocation target exception",
            "BOSS"
    };
    private static final String[] insaneWaves = {
            "BOSS"

    };
    private static final String[][] wavesDifficulties = {easyWaves, normalWaves, hardWaves, insaneWaves};
    public static ArrayList<LinkedList<Word>> allWaves = new ArrayList<>();
    public static LinkedList<Word> activeWave;
    private static boolean waveInProgress = false;


    public EnemyFactory(int diff,ArrayList<ArrayList<Point>> allroutes){
        loadEnemyWaves(diff,allroutes);
    }

    @Override
    public void run(){
        try {

            for (Word word : activeWave) {
                for(Enemy e : word.getLettres()){
                    if (e instanceof BossEnemy){
                        Platform.runLater(()->Game.getDrawing().getChildren().add(new Tips(5,new Point(20,250),Game.getDrawing())));
                        Thread.sleep(7000);
                        Platform.runLater(()->Game.getDrawing().getChildren().add(new Tips(6,new Point(20,250),Game.getDrawing())));
                    }
                    Game.getPlayer().addEnemy(e);
                    e.setAlive();
                }
            }
            // le jeu est sauvé quand tous les élément de la wave sont sortis
            Thread.sleep(500);
            Game.save();

            Platform.runLater(()->{Game.getDrawing().draw(new TemporaryText("Saving...",3000,new Point(790,170),20));});
            Thread.sleep(8000);   // faire que l'on puisse cliquer sur next wave que quand la wave est suffisament loin
            waveInProgress = false;
            System.out.println("fin de vague");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static LinkedList<Word> buildWave(int wave, int diff,ArrayList<ArrayList<Point>> allRoutes){ //creer des vagues d'ennemis qui sont des mots et des ennemis qui sont les lettres du mot

        LinkedList<Word> sentence =  new LinkedList<>();
        Point origin = new Point(80,74);
        Random r =new Random();
        for (String word : wavesDifficulties[diff-1][wave-1].split(" ")) {
            sentence.add(new Word(word, new Point(origin.getX(), origin.getY()),allRoutes.get(r.nextInt(allRoutes.size()))));

            origin.setX(origin.getX()+(10*(1+word.length())));
        }
        return sentence;
    }

    private static void  loadEnemyWaves(int difficulty,ArrayList<ArrayList<Point>> allRoutes){
        ArrayList<LinkedList<Word>> enemyWaves = new ArrayList<>();
        for (int i =1; i <= wavesDifficulties[difficulty-1].length ; i++){
            LinkedList<Word> wave = buildWave(i, difficulty,allRoutes);
            enemyWaves.add(wave);
        }
        allWaves = enemyWaves;

    }

    public  void nextWave(){
        if(Game.getPlayer().getWave() <allWaves.size()) {
            if(!waveInProgress){
                activeWave = allWaves.get(Game.getPlayer().getWave());
                Game.getPlayer().nextWave();
                System.out.println("Wave " + Game.getPlayer().getWave());
                waveInProgress = true;
                launchWave();
            }
            else{ System.out.println("A wave is already in progress or it is the last one");}
        }

    }

    public void launchWave(){
        Thread thread = new Thread(this);
        thread.start();
    }

}