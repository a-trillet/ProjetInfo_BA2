import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class BossEnemy extends Enemy {
    private static final long serialVersionUID = 1L;
    private static double maxLife = 500;
    private static int reward = 500;
    private static String type = "Github BOSS";
    private static int speed = 13;
    private static int power = 100;
    private static boolean loaded = false;
    protected transient ImageView selectedImage = new ImageView();
    protected transient Image image = new Image(BossEnemy.class.getResourceAsStream("Github.png"));

    public BossEnemy(ArrayList<Point> trackPoints, Point origin) {
        super(trackPoints,origin, maxLife, reward);
        this.enemyType = type;
        this.enemyPower = power;
        createImage();

    }
    protected void createImage(){
        selectedImage.setImage(image);
        selectedImage.setFitHeight(50);
        selectedImage.setPreserveRatio(true);
        selectedImage.relocate(origin.getX(),origin.getY());
    }
    public void update(){
        try {
            selectedImage.relocate(origin.getX()-25,origin.getY()-25);
        }
        catch (Exception e){e.printStackTrace();}
        }
    public Node getShape() {
        return selectedImage;
    }


}