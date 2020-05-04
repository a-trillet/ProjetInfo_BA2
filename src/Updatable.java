import javafx.scene.Node;

public interface Updatable {
    void update(Drawing d);
    Node getShape();
    //void freeze (pour mettre sur pause)
}
