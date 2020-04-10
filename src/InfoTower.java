import jdk.jfr.Frequency;

import java.util.ArrayList;

public class InfoTower extends  Info{
    //infos pour les tours
    private int damage;
    private int level;
    private int frequency;
    private double range;
    private String towerType; // à faire

    public InfoTower(Tower tower){
        super(tower);
        this.damage = tower.getDamage();   /// changer pour bullet type
        this.level = tower.getLevel();
        this.frequency = tower.getFrequency();
        this.range = tower.getRange();
    }

    @Override
    public String listString() {
        String strType = "Tower of type: ...";
        String strLevel = "Level : "+ level;
        String strDamage = "Damage : "+ damage;
        String strFrequency = "Frequency : "+ frequency;
        String strRange = "Range : "+range;

        return strType+"\n" + strLevel+"\n" + strDamage+"\n" + strFrequency+"\n" + strRange;
    }
}
