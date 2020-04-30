import javafx.scene.paint.Color;

public class TowerMaker {
    private Drawing drawing;

    public TowerMaker(Drawing d, String type, Point p) {
        this.drawing = d;
        Color color = new Color(0,0,0,0);
        Tower t = null;  //à vérifier
        switch (type) {
            case "FIRE":
                t = new FireTower(p);
                color = new Color(1,0,0,1);
                break;
            case "ICE":
                t= new IceTower(p);
                color =new Color(0,0.1,1,0.7);
                break;
            case "BASIC":
                t = new BasicTower(p);
                color = new Color(0, 1,0,1);
                break;
            case "SNIPER":
                t = new SniperTower(p);
                color = new Color(1,0,1,0.3);
                break;
        }
        PlayScreen.towerType = null;
        if (t != null && CheckTowerOk(t)) {
            Game.player.addTower(t);
            drawing.drawSquare(p,color);
            Game.player.addGold(-t.getCost());  //peut etre à bouger
            t.SetActive();//tower commence à tirer

            //}
        }



    }

    public boolean CheckTowerOk(Tower to) {
        boolean res = false;
        if (Game.player.getGold() >= to.getCost()) {
            res = true;
        }
        for (Tower tower : Game.player.getTowerList()){
            if (tower.isOn(to.getCentre())) {
                res = false;
            }}
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
