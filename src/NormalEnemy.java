public class NormalEnemy extends Enemy {

    private static double maxLife = 50;
    private static int reward = 10;


    public NormalEnemy(Point centre){
        super(centre, maxLife,reward);
    }

}
