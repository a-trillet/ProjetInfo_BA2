import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TemporaryText implements Updatable, Runnable{
    private Boolean alive=true;
    private int time;
    private Text text;
    private Thread thread;


    public TemporaryText(String text, int time, Point point){
        this.text=new Text(point.getX(),point.getY(),text);
        this.text.setFill(Color.WHITE);
        this.time=time;
        this.thread=new Thread(this);
        thread.start();
    }

    @Override
    public void update(Drawing d) {
        if (!alive){
            Platform.runLater(()->d.removeUpdatable(this));
        }
    }



    @Override
    public Node getShape() {
        return text;
    }

    @Override
    public void run() {
        while (alive){
            if(time>0){
                time-=20;

            }
            else{alive=false;}
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
