import java.util.ArrayList;

public class Word implements Runnable{
    private ArrayList<Enemy> lettres = new ArrayList<>();

    public Word(String s, Point origin, ArrayList<Point> route){

        if (s.equals("BOSS")){
            Enemy enemy = new BossEnemy(route, new Point(origin.getX(),origin.getY()));
            lettres.add(enemy);
        }
        else {
            for (String lettre : s.split("")) {
                //les enmemis sont créés avec une origine décallée pour former un mot
                Enemy enemy = new NormalEnemy(route, new Point(origin.getX(), origin.getY()), lettre);
                origin.setX(origin.getX() + 10);
                lettres.add(enemy);
            }
        }
    }


    public ArrayList<Enemy> getLettres(){
        return lettres;
    }

    @Override
    public void run() {

    }
}
