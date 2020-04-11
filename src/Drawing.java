import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.ArrayList;

public class Drawing extends Pane  {
    private Color color = new Color(1,0,0,1);
    private javafx.scene.shape.Rectangle square ;
    private ArrayList<Enemy> enemies = new ArrayList<>();


    public Drawing(){
        super();
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (Enemy e : enemies) {
                    e.update();
                }

            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    public void drawSquare(Point centre) {
        square = new Rectangle(30, 30, color);
        square.setX(centre.getX() - 15);
        square.setY(centre.getY() - 15);
        this.getChildren().add(square);
    }
    public void draw(Enemy enemy,Shape shape){ // peut etre modifié
        enemies.add(enemy);
        this.getChildren().add(shape);
    }


        }