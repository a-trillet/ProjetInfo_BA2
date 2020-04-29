import javafx.scene.Node;

public interface Moveable {
    void update();
    void move();
    Node getShape();
    //void freeze (pour mettre sur pause)
}
