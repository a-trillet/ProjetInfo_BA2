import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.util.converter.BigDecimalStringConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class EnemyFactory implements Runnable, Serializable {
    private static final long serialVersionUID = 1L;

    public EnemyFactory(int diff){
        loadEnemyWaves(diff);


    }

        // les différentes vagues
    private static final String[] easyWaves ={
            "Bug",
            "Big Error",
            "Java Exception"
    };


    private static final String[] normalWaves = {
            "Bug",
            "Big Error",
            "Java Exception"
    };

    private static final String[] hardWaves = {
            "Bug",
            "Big Error",
            "Java Exception"
    };

    private static final String[] insaneWaves = {
            "Bug",
            "Big Error",
            "Java Exception"

    };

    private static final String[][] wavesDifficulties = {easyWaves, normalWaves, hardWaves, insaneWaves};


    public static ArrayList<LinkedList<Word>> allWaves = new ArrayList<>();

    public static LinkedList<Word> activeWave;

    private static boolean waveInProgress = false;


    private static LinkedList<Word> buildWave(int wave, int diff, ArrayList<ArrayList<Point>>allroute){
        LinkedList<Word> sentence =  new LinkedList<>();
        Point origin = new Point(50,50);
        Random r =new Random();
        for (String word : wavesDifficulties[diff-1][wave-1].split(" ")) {
            sentence.add(new Word(word, new Point(origin.getX(), origin.getY()),MapPane.getAllRoutes().get(r.nextInt(allroute.size()-1))));

            origin.setX(origin.getX()+(7*(1+word.length())));                  //place les mots en lignes, séparés
        }
        return sentence;
    }


    private static void  loadEnemyWaves(int difficulty){
        ArrayList<LinkedList<Word>> enemyWaves = new ArrayList<>();
        for (int i =1; i <= wavesDifficulties[difficulty-1].length ; i++){
            LinkedList<Word> wave = buildWave(i, difficulty,MapPane.getAllRoutes());
            enemyWaves.add(wave);
        }
        allWaves = enemyWaves;

    }
    public  void nextWave(){
        if(Game.player.getWave() <allWaves.size() && !waveInProgress) {

            activeWave = allWaves.get(Game.player.getWave());
            Game.player.nextWave();
            System.out.println("Wave " + Game.player.getWave());
            waveInProgress = true;
            launchWave();
        }
        else{ System.out.println("A wave is already in progress or it is the last one");}
    }
    public void launchWave(){
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run(){
        try {

            /*for (Word word : activeWave) {
                word.drawWord();
            }
            Thread.sleep(1500);*/
            for (Word word : activeWave) {
                for(Enemy e : word.getLettres()){
                    e.setAlive();
                    Thread.sleep(20);
                }
            }
            // le jeu est sauvé quand tous les élément de la wave sont sortis
            Thread.sleep(100);
            Game.save();
            //PlayScreen.drawing.drawSaving();           // marche pas

            Thread.sleep(10000);   // faire que l'on puisse cliquer sur next wave que quand la wave est suffisament loin
            waveInProgress = false;
            System.out.println("fin de vague");




        } catch (Exception e) {
            e.printStackTrace();
        }
    }


// faire un persone factory dans player et un constructeur qui crée cette fameuse liste




}