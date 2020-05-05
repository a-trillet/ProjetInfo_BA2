import javafx.scene.paint.Color;

public class MassartTower extends Tower {

    private static int massartTowercost = 150;
    private static  double newRange = 100;
    private static  double newDamage = 0;
    private static int newReloadTime = 5000;
    private static int newBulletRange = 40;
    private String type = "Massart tower";
    private String newPowerType = "Total Slow";
    private double newPowerDuration = 3000;

    public MassartTower(Point origin,Drawing d) {
        super(origin,d);
        this.cost = massartTowercost;
        this.range = newRange;
        this.damage = newDamage;
        this.reloadTime = newReloadTime;
        this.bulletRange = newBulletRange;
        this.towerType = type;
        this.powerType = newPowerType;
        this.powerDuration = newPowerDuration;
    }
    public static int getNewCost(){
        return massartTowercost;
    }

    @Override
    public void powerActivation(){                      //sert à diminuer le reload time dans le run the tower pour une rafale

        powerStartTime = System.currentTimeMillis();
        powerActive = true;
        numberOfKill = 0;
        Enemy.setEnemyVelocity(6);
        Enemy.setFrozen(true);
        Enemy.setFreezeDuration(newPowerDuration);
        Enemy.setFreezeStart(powerStartTime);


    }
}

