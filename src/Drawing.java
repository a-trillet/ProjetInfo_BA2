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
    private Label labelGold;
    private static ArrayList<Bullet> bullets=new ArrayList<>();


    public Drawing(){
        super();
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (Enemy e : Player.getPlayer().getEnemiesOnMap()) {
                    e.update();
                }
                for (Bullet b : bullets) {
                        b.update();
                }
                Player player=Player.getPlayer();
                labelGold.setText("Gold : "+player.getGold()+"\nLives : "+player.getLives()+"/"+player.getMaxLives()+"\nWave :"+player.getWave());
            }}));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }
    public void drawLifeGold(){

        labelGold = new Label();
        labelGold.relocate(20,400);
        labelGold.setTextFill(Color.web("FBF5FF"));
        Player.getPlayer().addGold(0);


        this.getChildren().add(labelGold);


    }

    public void drawSquare(Point centre,Color color) {
        square = new Rectangle(30, 30, color);
        square.setX(centre.getX() - 15);
        square.setY(centre.getY() - 15);
        this.getChildren().add(square);
    }
    public void draw(Node shape){ // peut etre modifié
        this.getChildren().add(shape);
    }

    public void drawbullet(Bullet bullet){
        this.getChildren().add(bullet.getShape());
        bullets.add(bullet);
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

    public ArrayList<Bullet> getBullets(){return bullets;}



        }
