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
        String power = "";
        switch (towerType){
            case "Stack Overflow tower":
                damage = StackTower.getNewDamage();
                reloadTime = StackTower.getNewReloadTime();
                range = StackTower.getNewRange();
                power = "Burst Fire. Ce pouvoir"+"\n"+"actif seulement quelques secondes"+"\n"+"permettra à la tour d'effectuer un"+"\n"+"tire en rafale à chaque fois"+"\n"+"que vous aurez tué suffisament"+"\n"+"d'ennemis";
                break;
            case "Massart tower":
                damage = MassartTower.getNewDamage();
                range = MassartTower.getNewRange();
                reloadTime = MassartTower.getNewReloadTime();
                power = "Total Slow. Ce pouvoir"+"\n"+"actif seulement quelques secondes"+"\n"+"permettra à la tour de ralentir"+"\n"+"les ennemis afin de mieux les"+"\n"+"atteindre à chaque fois que vous"+"\n"+"aurez tué suffisament d'ennemis";
                break;
            case "Raj tower":
                damage = RajTower.getNewDamage();
                reloadTime = RajTower.getNewReloadTime();
                range = RajTower.getNewRange();
                power = "Tsar Bomba. Ce pouvoir"+"\n"+"permettra à la tour d'infliger des"+"\n"+"dégats à l'entierté des ennemis"+"\n"+"se trouvant sur la map à chaque"+"\n"+"fois qu'elle aura tué"+"\n"+"suffisament d'ennemis";
                break;
            case "Sycamore tower":
                damage = SycamoreTower.getNewDamage();
                reloadTime = SycamoreTower.getNewReloadTime();
                range = SycamoreTower.getNewRange();
                power = "Double shot. Ce pouvoir" + "\n" + "actif seulement quelques secondes"+"\n"+"permettra à la tour de tirer 2"+"\n"+"balles sur des ennemis différents"+ "\n" +"à chaque fois qu'elle aura tué"+"\n"+"suffisament d'ennemis";
                break;
        }
        String strType = towerType;
        String strDamage = "Damage : " + (int)damage;
        String strFrequency = "Reload time : " + (double)(reloadTime)/1000 + " second";
        String strRange = "Range : " + (int)range;
        String strPower = "Power : " + power;

        return strType + "\n" + strDamage + "\n" + strFrequency + "\n" + strRange + "\n" + strPower ;
    }

    public double getRange() {
        return range;
    }



    public Point getCentre() {
        return centre;
    }



    public Color getColor() { return color; }


}
