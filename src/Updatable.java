import javafx.scene.Node;

public interface Updatable {

    void update();
    Node getShape();
    boolean isAlive();
}
