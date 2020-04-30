import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class Drawing extends Pane  {
    private javafx.scene.shape.Rectangle square ;
    private javafx.scene.shape.Circle circle;
    private Label labelGold = new Label();
    private ArrayList<Moveable> moveables=new ArrayList<>();


    public Drawing(){
        super();
        this.getChildren().add(labelGold);
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for(int i =0;i< moveables.size();i++){moveables.get(i).update();}
                //labelGold.setText("Gold : "+Game.player.getGold()+"\nLives : "+Game.player.getLives()+"/"+Game.player.getMaxLives()+"\nWave :"+Game.player.getWave());
            }}));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }
    public void drawLifeGold(){
        Platform.runLater(() -> {
            labelGold.relocate(20,400);
            labelGold.setTextFill(Color.web("FBF5FF"));
            labelGold.setText("Gold : "+Game.player.getGold()+"\nLives : "+Game.player.getLives()+"/"+Game.player.getMaxLives()+"\nWave :"+Game.player.getWave());
        });


    }

    public void drawSquare(Point centre,Color color) {
        square = new Rectangle(30, 30, color);
        square.setX(centre.getX() - 15);
        square.setY(centre.getY() - 15);
        this.getChildren().add(square);
    }

    public void drawCircle(Point centre, Color color, double radius){
        circle = new Circle(centre.getX(), centre.getY(), radius);
        circle.setStroke(color);
        circle.setFill(Color.TRANSPARENT);
    }
    public void draw(Moveable moveable){ // peut etre modifié
        moveables.add(moveable);
        this.getChildren().add(moveable.getShape());
    }




    public void drawRoute(ArrayList<Point> route){
        //crée une suite de ligne, permet de faire des arcs de cercles ect facilement
        Path path = new Path();
        path.setStroke(Color.WHITE);


        MoveTo moveTo = new MoveTo();
        moveTo.setX(route.get(0).getX());
        moveTo.setY(route.get(0).getY());
        path.getElements().add(moveTo);

        int it = 0;

        for (Point point : route){
            if( it !=0) {
                LineTo lineTo = new LineTo();
                lineTo.setX(point.getX());
                lineTo.setY(point.getY());

                path.getElements().add(lineTo);
            }
            it++;
        }
        this.getChildren().add(path);

    }

    public void removeMoveable(Moveable moveable){
        moveables.remove(moveable);
        this.getChildren().remove(moveable.getShape());
    }


        }
