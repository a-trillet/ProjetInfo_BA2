import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TemporaryText implements Moveable, Runnable{
    private Boolean alive=true;
    private int time;
    private Text text;
    private Thread thread;


    public TemporaryText(String text, int time){
        this.text=new Text(100,100,text);
        this.text.setFill(Color.WHITE);
        this.time=time;
        this.thread=new Thread(this);
        thread.start();
    }

    @Override
    public void update(Drawing d) {
        if (!alive){d.removeMoveable(this);}
    }

    @Override
    public void move() {

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
