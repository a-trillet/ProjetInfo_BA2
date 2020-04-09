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

}
