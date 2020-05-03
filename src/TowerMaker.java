import javafx.scene.paint.Color;

public class TowerMaker {
    private Drawing drawing;
    private Color color;

    public TowerMaker(Drawing d, String type, Point p) {
        this.drawing = d;
        Tower t = null;
        switch (type) {
            case "FIRE":
                t = new FireTower(p,drawing);
                color = new Color(1,0,0,1);
                break;
            case "ICE":
                t= new IceTower(p,drawing);
                color =new Color(0,0.1,1,0.7);
                break;
            case "BASIC":
                t = new BasicTower(p,drawing);
                color = new Color(0, 1,0,1);
                break;
            case "SNIPER":
                t = new SniperTower(p,drawing);
                color = new Color(1,0,1,0.3);
                break;
        }
        if (t != null && CheckTowerOk(t)) {
            Game.getPlayer().addTower(t);
            drawing.drawSquare(p,color,30); //à changer par image
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
        if (!MapPane.isOn(to.getCentre())){ //vérifie si la tour touche le chemin
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
            case "Ice tower":
                color =new Color(0,0.1,1,0.7);
                break;
            case "Basic tower":
                color = new Color(0, 1,0,1);
                break;
            case "Sniper tower":
                color = new Color(1,0,1,0.3);
                break;
        }
        return color;
    }
}
