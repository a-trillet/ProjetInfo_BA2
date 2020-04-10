
public class TowerMaker {
    private Drawing drawing;
    public TowerMaker(Drawing d,String type, Point p) {
        this.drawing = d;
        Tower t = null;  //à vérifier
        switch (type) {
            case "FIRE":
                t = new FireTower(p); break;
            case "ICE": break;
        }
        PlayScreen.towerType=null;
        if (t != null && CheckTowerOk(t)) {
            Player.getPlayer().addTower(t);
            drawing.drawSquare(p);

            //tower commence à tirer

            //}

        }
    }
        private boolean CheckTowerOk(Tower to){
            boolean res = false;
            if (Player.getPlayer().getGold() >= to.getCost()) {
                res = true;
            }
            return res;
        }
    }

