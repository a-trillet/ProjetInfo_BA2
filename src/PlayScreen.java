import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PlayScreen {
    public static Drawing drawing= new Drawing();

    public BorderPane sceneView(){
        // Layout 3 jeu
        BorderPane borderPane = new BorderPane();
        StackPane map = new StackPane(); // permet de supperposer les différents éléments de la map (image, tours,..)
        drawing.drawSquare(new Point(100,100));// à enlever

        map.getChildren().addAll(drawing);
        GridPane shop = new GridPane();
        shop.setPadding(new Insets(10,10,10,10));
        shop.setVgap(8);
        shop.setHgap(10);
        Button fireTowerButton = new Button("fire tower");
        GridPane.setConstraints(fireTowerButton,0,0);
        Button iceTowerButton = new Button("ice tower");
        GridPane.setConstraints(iceTowerButton,1,0);
        shop.getChildren().addAll(fireTowerButton,iceTowerButton);


        borderPane.setCenter(map);
        borderPane.setRight(shop);
        return borderPane;
    }

}
