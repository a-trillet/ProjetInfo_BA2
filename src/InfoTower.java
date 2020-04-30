import javafx.scene.paint.Color;
import jdk.jfr.Frequency;

import java.util.ArrayList;

public class InfoTower extends  Info {
    //infos pour les tours
    private double damage;
    private int level;
    private int frequency;
    private double range;
    private String towerType;
    private Point centre;


    public InfoTower(Tower tower) {
        super(tower);
        this.damage = tower.getDamage();   /// changer pour bullet type
        this.level = tower.getLevel();
        this.frequency = tower.getFrequency();
        this.range = tower.getRange();
        this.towerType = tower.getTowerType();
        this.centre = tower.getCentre();


    }

    @Override
    public String listString() {
        String strType = "Tower type : " + towerType;
        String strLevel = "Level : " + level;
        String strDamage = "Damage : " + damage;
        String strFrequency = "Frequency : " + frequency;
        String strRange = "Range : " + range;

        return strType + "\n" + strLevel + "\n" + strDamage + "\n" + strFrequency + "\n" + strRange;
    }

    public double getRange() {
        return range;
    }

    ;

    public Point getCentre() {
        return centre;
    }

    ;

    public Color getColor() {
        Color color = new Color(0, 0, 0, 0);
        switch (towerType) {
            case "Ice tower":
                color = new Color(0, 0.1, 1, 0.7);
                break;
            case "Fire tower":
                color = new Color(1, 0, 0, 1);
                break;
            case "Basic tower":
                color = new Color(0, 1, 0, 1);
                break;
            case "Sniper tower":
                color = new Color(1, 0, 1, 0.3);
                break;
        }
        return color;
    }


}
