import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class TowerMaker {
    private Drawing drawing;
    private Color color;

    public TowerMaker(Drawing d, String type, Point p) {
        this.drawing = d;
        Tower t = null;
        Image image = new Image(TowerMaker.class.getResourceAsStream("stack.jpg"));//par défault
        switch (type) {
            case "FIRE":
                t = new FireTower(p,drawing);
                color = new Color(1,0,0,1);
                 image = new Image(TowerMaker.class.getResourceAsStream("raj.jpg"));
                break;
            case "Massart tower":
                t= new MassartTower(p,drawing);
                color =new Color(0,0.1,1,0.7);
                image = new Image(TowerMaker.class.getResourceAsStream("massart.jpg"));
                break;
            case "Stack Overflow tower":
                t = new StackTower(p,drawing);
                color = new Color(0, 1,0,1);
                image = new Image(TowerMaker.class.getResourceAsStream("stack.jpg"));
                break;
            case "Sycamore tower":
                t = new SycamoreTower(p,drawing);
                color = new Color(1,0,1,0.3);
                image = new Image(TowerMaker.class.getResourceAsStream("sycamore.jpg"));
                break;
        }
        if (t != null && CheckTowerOk(t)) {
            Game.getPlayer().addTower(t);
            drawing.drawSquare(p,color,30); //à changer par image
            drawing.setImage(p,image,30);
            Game.getPlayer().addGold(-t.getCost());  //peut etre à bouger
            t.setActive(); //tower commence à tirer
        }
    }

    public boolean CheckTowerOk(Tower to) {
        boolean res = true;
        if (Game.getPlayer().getGold() < to.getCost()) {  //vérifie si le player a assez d'argent
            res = false;
        }
        for (Tower tower : Game.getPlayer().getTowerList()){ //vérifie si la tour touche d'autre tours
            if (tower.isOn(to.getCentre())) {
                res = false;
            }}
        if (!MapFactory.isOn(to.getCentre())){ //vérifie si la tour touche le chemin
            res=false;
        }
        return res;
    }
    public static Color getColor(String towerType){
        Color color = new Color(0,0,0,0);
        switch (towerType) {
            case "Fire tower":
                color = new Color(1,0,0,1);
                break;
            case "Massart tower":
                color =new Color(0,0.1,1,0.7);
                break;
            case "Stack Overflow tower":
                color = new Color(0, 1,0,1);
                break;
            case "Sniper tower":
                color = new Color(1,0,1,0.3);
                break;
        }
        return color;
    }
    public static Image getImage(String towerType){
        Image image = new Image(TowerMaker.class.getResourceAsStream("stack.jpg"));//par défault
        switch (towerType) {
            case "FIRE":
                image = new Image(TowerMaker.class.getResourceAsStream("raj.jpg"));
                break;
            case "Massart tower":
                image = new Image(TowerMaker.class.getResourceAsStream("massart.jpg"));
                break;
            case "Stack Overflow tower":
                image = new Image(TowerMaker.class.getResourceAsStream("stack.jpg"));
                break;
            case "Sycamore tower":
                image = new Image(TowerMaker.class.getResourceAsStream("sycamore.jpg"));
                break;
        }
        return image;
    }
}
