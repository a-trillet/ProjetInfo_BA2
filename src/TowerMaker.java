import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;


public class TowerMaker {
    private Drawing drawing;
    private Color color;
    private static ImageView imageView = new ImageView();

    public TowerMaker(Drawing d, String type, Point p) {
        this.drawing = d;
        Tower t = null;
        Image image = null;
        switch (type) {
            case "Raj tower":
                t = new RajTower(p);
                color = t.getColor();
                break;
            case "Massart tower":
                t= new MassartTower(p);
                color = t.getColor();
                break;
            case "Stack Overflow tower":
                t = new StackTower(p);
                color = t.getColor();
                break;
            case "Sycamore tower":
                t = new SycamoreTower(p);
                color = t.getColor();
                break;
        }
        if (t != null && CheckTowerOk(t)) {
            Game.getPlayer().addTower(t);
            drawing.drawTower(t);
            Game.getPlayer().addGold(-t.getCost());  //peut etre à bouger
            t.setActive(); //tower commence à tirer
            Game.getDrawing().getChildren().add(new Tips(1,new Point(20,250),Game.getDrawing()));
        }
    }


    public static boolean CheckTowerOk(Tower to) {
        boolean res = true;
        if (Game.getPlayer().getGold() < to.getCost()) {  //vérifie si le player a assez d'argent
            res = false;
        }
        for (Tower tower : Game.getPlayer().getTowerList()){ //vérifie si la tour touche d'autre tours
            if (tower.isOn(to.getCentre())) {
                res = false;
            }}
        if (!MapFactory.isNotOn(to.getCentre())){ //vérifie si la tour touche le chemin
            res=false;
        }
        return res;
    }


}
