
public class TowerMaker {
    private Drawing drawing;

    public TowerMaker(Drawing d, String type, Point p) {
        this.drawing = d;
        Tower t = null;  //à vérifier
        switch (type) {
            case "FIRE":
                t = new FireTower(p);
                break;
            case "ICE":
                break;
        }
        PlayScreen.towerType = null;
        if (t != null && CheckTowerOk(t)) {
            Player.getPlayer().addTower(t);
            drawing.drawSquare(p);
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
