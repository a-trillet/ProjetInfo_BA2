import javafx.scene.layout.StackPane;

public class MapPane extends StackPane {
    private static Point entryPoint = new Point(200,50);
    public static Point getEntryPoint(){return entryPoint;}

    private static Point endPoint = new Point(600, 300);

    public static Point getEndPoint() {
        return endPoint;
    }
}
