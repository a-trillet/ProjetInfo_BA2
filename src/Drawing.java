import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Drawing extends Pane  {
    private Color color = new Color(1,0,0,1);
    private javafx.scene.shape.Rectangle square = new Rectangle(30,30,color);


    public Drawing(){
        super();
    }

    public void drawSquare(Point centre){
        square.setX(centre.getX()-15);
        square.setY(centre.getY()-15);
        this.getChildren().add(square);
}


}
