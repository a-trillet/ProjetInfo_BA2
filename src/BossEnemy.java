import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class BossEnemy extends Enemy {
    private static double maxLife = 500;
    private static int reward = 500;
    private static String type = "Github BOSS";
    private static int speed = 10;
    private static int power = 100;
    private transient ImageView selectedImage;
    private static Image image = new Image(BossEnemy.class.getResourceAsStream("Images/Github.png"));

    public BossEnemy(ArrayList<Point> trackPoints, Point origin) {
        super(trackPoints,origin, maxLife, reward,speed,power,type);
        setShape();

    }
    @Override
    public void setShape(){
        selectedImage=new ImageView();
        selectedImage.setImage(image);
        selectedImage.setFitHeight(50);
        selectedImage.setPreserveRatio(true);
        selectedImage.relocate(this.getCentre().getX(),this.getCentre().getY());
            }
    public void update(){
        try {
            selectedImage.relocate(this.getCentre().getX()-25,this.getCentre().getY()-25);
        }
        catch (Exception e){e.printStackTrace();}
        }
    
    public Node getShape(){return selectedImage;}

}