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
            Player.getPlayer().addTower(t);
            drawing.drawSquare(p,color);
            Player.getPlayer().addGold(-t.getCost());  //peut etre à bouger

            //tower commence à tirer  / peut etre apas nécessaire

            //}

        }
    }

    private boolean CheckTowerOk(Tower to) {
        boolean res = false;
        if (Player.getPlayer().getGold() >= to.getCost()) {
            res = true;
        }
        for (Tower tower : Player.getPlayer().getTowerList()){
            if (tower.isOn(to.getCentre())) {
                res = false;
            }}
        return res;
    }
}
