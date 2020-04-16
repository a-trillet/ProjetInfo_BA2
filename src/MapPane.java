import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MapPane extends StackPane {
    private static Point entryPoint = new Point(200,50);
    public static Point getEntryPoint(){return entryPoint;}


    private static Point endPoint = new Point(600, 300);
    public static Point getEndPoint() {
        return endPoint;
    }

    public MapPane(){
        Rectangle exitSquare = new Rectangle(36, 36, Color.web("483576"));
        exitSquare.setX(endPoint.getX()-18);
        exitSquare.setY(endPoint.getY()-18);

        Rectangle entrySquare = new Rectangle(36, 36, Color.web("483576"));
        entrySquare.setX(entryPoint.getX()-18);
        entrySquare.setY(entryPoint.getY()-18);

        PlayScreen.drawing.getChildren().addAll(entrySquare, exitSquare);
    }
}
