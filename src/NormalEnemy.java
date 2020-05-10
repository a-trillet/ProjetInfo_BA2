import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


import java.util.ArrayList;

public class NormalEnemy extends Enemy  {
    private static double maxLife = 50;
    private static int reward = 10;
    private static String type = "Normal enemy";
    private static int power = 1;
    private static int speed=15;
    private transient Text lettreText;

    public NormalEnemy(ArrayList<Point>trackPoints, Point origin, String lettre){
        super(trackPoints, origin, maxLife, reward,speed);
        this.lettre=lettre;
        this.enemyType = type;
        this.enemyPower = power;
        setShape();


    }
    @Override
    public void setShape(){ //créer un Node à partir du string de la lettre
        lettreText=new Text(lettre);
        lettreText.setX(this.getCentre().getX()+7);
        lettreText.setY(this.getCentre().getY()+7);
        lettreText.setFill(new Color(1,0,0,1));
        lettreText.setFont(new Font(14));
        shape = lettreText;
    }
    public void update(){
        lettreText.setX(this.getCentre().getX()+7);
        lettreText.setY(this.getCentre().getY()+7);
        lettreText.setRotate(angle*360/2/Math.PI);
        if (this.getLifePoints()<=getMaxLifePoints()/2){lettreText.setFill(Color.web("FF7B2C"));}
    }

}
