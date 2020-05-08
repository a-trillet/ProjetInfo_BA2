
public class TowerMaker {
    private  Drawing drawing;

    public TowerMaker(Drawing d, String type, Point p) {
        this.drawing = d;
        Tower t = null;

        switch (type) {
            case "Raj tower":
                t = new RajTower(p);
                break;
            case "Massart tower":
                t= new MassartTower(p);
                break;
            case "Stack Overflow tower":
                t = new StackTower(p);
                break;
            case "Sycamore tower":
                t = new SycamoreTower(p);
                break;
        }
        if (t != null && CheckTowerOk(t)) {
            Game.getPlayer().addTower(t);
            drawing.drawTower(t);
            Game.getPlayer().addGold(-t.getCost());
            t.setActive();
           Game.getDrawing().getChildren().add(new Tips(1,new Point(20,250),Game.getDrawing()));
        }
    }


    public static boolean CheckTowerOk(Tower to) {      //vérifié si le joueur a assez d'argent, que le tour n'estnpas sur le chemin ou sur la poubelle
        boolean res = true;
        if (Game.getPlayer().getGold() < to.getCost()) {
            res = false;
        }
        for (Tower tower : Game.getPlayer().getTowerList()){
            if (tower.isOn(to.getCentre())) {
                res = false;
            }}
        if (!MapFactory.isNotOn(to.getCentre())){
            res=false;
        }
        if (to.isOn(new Point(855,395))){
            res = false;
        }
        return res;
    }
}
