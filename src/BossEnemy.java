import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class BossEnemy extends Enemy {
    private static final long serialVersionUID = 1L;
    private static double maxLife = 500;
    private static int reward = 500;
    private static String type = "Github BOSS";
    private static int speed = 10;
    private static int power = 100;
    private static boolean loaded = false;
    private transient ImageView selectedImage = new ImageView();
    private transient Image image = new Image(BossEnemy.class.getResourceAsStream("Images/Github.png"));

    public BossEnemy(ArrayList<Point> trackPoints, Point origin) {
        super(trackPoints,origin, maxLife, reward,speed);
        this.enemyType = type;
        this.enemyPower = power;
        setShape();

    }
    @Override
    public void setShape(){
        selectedImage.setImage(image);
        selectedImage.setFitHeight(50);
        selectedImage.setPreserveRatio(true);
        selectedImage.relocate(this.getCentre().getX(),this.getCentre().getY());
        shape=selectedImage;
    }
    public void update(){
        try {
            selectedImage.relocate(this.getCentre().getX()-25,this.getCentre().getY()-25);
        }
        catch (Exception e){e.printStackTrace();}
        }
    


}