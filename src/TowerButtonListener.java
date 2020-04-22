
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class TowerButtonListener implements EventHandler<MouseEvent> {

    Drawing drawing;
    static String towerType=null;
    private String s =null;
    public TowerMaker tM;


    public TowerButtonListener(Drawing d){
        drawing = d;
    }
    public void setS(String s){this.s=s;}
    public void mousePressed(MouseEvent e) {
        //towerType=s;
        System.out.println(towerType);
    }

    public void mouseReleased(MouseEvent e) {
        System.out.println("OK");
        //tM = new TowerMaker(drawing,towerType,new Point(e.getX(),e.getY()));
        new TowerMaker(drawing,towerType,new Point(e.getX(),e.getY()));

    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED){
            mousePressed(mouseEvent);
        }
        if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED && towerType!=null){
            mouseReleased(mouseEvent);
        }
    }
}

