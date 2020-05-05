import javafx.scene.Node;

public interface Updatable {
    void update(Drawing d);
    Node getShape();
}
