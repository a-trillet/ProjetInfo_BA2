import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import javafx.scene.control.Label;

import java.awt.*;
import java.util.ArrayList;

public class Drawing extends Pane  {
    private Color color = new Color(1,0,0,1);
    private javafx.scene.shape.Rectangle square ;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private Label labelGold;


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
    public void drawLifeGold(){

        labelGold = new Label();
        labelGold.relocate(20,400);
        labelGold.setTextFill(Color.web("FBF5FF"));
        Player.getPlayer().addGold(0);


        this.getChildren().add(labelGold);


    }
    public void changeGoldLives(int gold, int life, int maxLife, int wave){
        //permet la modification d'un element java fx depuis un thread (pour decrease life)
        // !!! ne pas utiliser dans des gros boulots de loop, car peut planter. ici, c'est juste un label changé toutes les seconde donc on s'en fout je crois

        Platform.runLater(new Runnable() {
            @Override public void run() {                                  //trouver alternative !!!!!!!!!!!!!!!!!
                labelGold.setText("Gold : "+gold+"\nLives : "+life+"/"+maxLife+"\nWave :"+wave);
            }
        });


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