import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        this.getChildren().add(new Tips("Bienvenue cher étudiant...",new Point(20,250),this));
        this.getChildren().add(labelGold);
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(20), event -> {
            for (int i = 0; i < moveables.size(); i++) {
                moveables.get(i).update(this);
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }
    public void drawLifeGold(){
        Platform.runLater(() -> {
            labelGold.relocate(20,400);
            labelGold.setTextFill(Color.web("FBF5FF"));
            labelGold.setText("Gold : " +Game.getPlayer().getGold()+"\nLives : "+Game.getPlayer().getLives()+"/"+Game.getPlayer().getMaxLives()+"\nWave :"+Game.getPlayer().getWave());
        });


    }

    public void drawSquare(Point centre,Color color, int size) {
        square = new Rectangle(size, size, color);
        square.setX(centre.getX() - size/2);
        square.setY(centre.getY()-size/2);
        this.getChildren().add(square);
    }



    public void draw(Moveable moveable){ // peut etre modifié
        moveables.add(moveable);
        this.getChildren().add(moveable.getShape());
    }

    public void setImage(Point centre,Image image, int size ){
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setX(centre.getX()-size/2);
        imageView.setY(centre.getY()-size/2);
        this.getChildren().add(imageView);
    }





    public void drawRoute(ArrayList<Point> route){
        //crée une suite de ligne, permet de faire des arcs de cercles ect facilement
        Path path = new Path();
        path.setStroke(Color.WHITE);


        MoveTo moveTo = new MoveTo();
        moveTo.setX(route.get(0).getX());
        moveTo.setY(route.get(0).getY());
        path.getElements().add(moveTo);

        Color color=Color.web("483576");

        int it = 0;
        for (Point point : route){ //trace les chemins
            if (it==0){this.drawSquare(route.get(0),color,40);}//dessine la base de départ
            else{
                LineTo lineTo = new LineTo();
                lineTo.setX(point.getX());
                lineTo.setY(point.getY());
                path.getElements().add(lineTo);
            }
            it++;
        }
        this.drawSquare(route.get(route.size()-1),color,40);//dessine la base d'arrivée
        this.getChildren().add(path);

    }

    public void removeMoveable(Moveable moveable){
        moveables.remove(moveable);
        this.getChildren().remove(moveable.getShape());
    }

    public void drawSaving(){
            Text sav = new Text(100,100,"Saving...");
            sav.setFill(Color.WHITE);
            this.getChildren().add(sav);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.getChildren().remove(sav);
    }
    public ArrayList<Moveable> getMoveables(){return moveables;}


        }
