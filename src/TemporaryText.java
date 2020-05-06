import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TemporaryText implements Updatable{
    private Boolean alive=true;
    private int time;
    private Text text;



    public TemporaryText(String text, int time, Point point){
        this.text=new Text(point.getX(),point.getY(),text);
        this.text.setFill(Color.WHITE);
        this.time=time;

    }

    @Override
    public void update() {
        time-=50;
        if (time<=0){alive=false;}
    }



    @Override
    public Node getShape() {
        return text;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }
}
