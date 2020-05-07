import javafx.scene.paint.Color;
import jdk.jfr.Frequency;

import java.util.ArrayList;

public class InfoTower extends  Info {
    //infos pour les tours
    private double damage;
    private int level;
    private int reloadTime;
    private double range;
    private String towerType;
    private Point centre;
    private int numberKill;
    private Color color;


    public InfoTower(Tower tower) {
        super(tower);
        this.damage = tower.getDamage();   /// changer pour bullet type
        this.level = tower.getLevel();
        this.reloadTime = tower.getReloadTime();
        this.range = tower.getRange();
        this.towerType = tower.getTowerType();
        this.centre = tower.getCentre();
        this.numberKill = tower.getNumberOfKill();
        this.color = tower.getColor();


    }

    @Override
    public String listString() {
        String strType = "Tower type : " + towerType;
        String strLevel = "Level : " + level;
        String strDamage = "Damage : " + damage;
        String strFrequency = "Reload time : " + (double)(reloadTime)/1000 + " second";
        String strRange = "Range : " + range;
        String strNumberOfKill = "Kill Counter : "+ numberKill;

        return strType + "\n" + strLevel + "\n" + strDamage + "\n" + strFrequency + "\n" + strRange + "\n"+ strNumberOfKill;
    }

    public static String initialList(String towerType){
        double damage = 0;
        int reloadTime = 0;
        double range = 0;
        switch (towerType){
            case "Stack Overflow tower":
                damage = StackTower.getNewDamage();
                reloadTime = StackTower.getNewReloadTime();
                range = StackTower.getNewRange();
                break;
            case "Massart tower":
                damage = MassartTower.getNewDamage();
                range = MassartTower.getNewRange();
                reloadTime = MassartTower.getNewReloadTime();
                break;
            case "Raj tower":
                damage = RajTower.getNewDamage();
                reloadTime = RajTower.getNewReloadTime();
                range = RajTower.getNewRange();
                break;
            case "Sycamore tower":
                damage = SycamoreTower.getNewDamage();
                reloadTime = SycamoreTower.getNewReloadTime();
                range = SycamoreTower.getNewRange();
                break;

        }
        String strType = towerType;
        String strDamage = "Damage : " + (int)damage;
        String strFrequency = "Reload time : " + (double)(reloadTime)/1000 + " second";
        String strRange = "Range : " + (int)range;

        return strType + "\n" + strDamage + "\n" + strFrequency + "\n" + strRange;
    }

    public double getRange() {
        return range;
    }



    public Point getCentre() {
        return centre;
    }



    public Color getColor() { return color; }


}
