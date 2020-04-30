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
        boolean res = true;
        if (Game.player.getGold() < to.getCost()) {  //vérifie si le player a assez d'argent
            res = false;
        }
        for (Tower tower : Game.player.getTowerList()){ //vérifie si la tour touche d'autre tours
            if (tower.isOn(to.getCentre())) {
                res = false;
            }}
        if (!MapPane.isOn(to.getCentre())){ //vérifie si la tour touche le chemin
            res=false;
            System.out.println("trop proche du chemin");
        }
        return res;
    }
}
