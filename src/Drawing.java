import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private Label labelGold = new Label();
    private ArrayList<Updatable> updatables=new ArrayList<>();
    private ArrayList<Tower> towers=new ArrayList<>();

    private javafx.scene.shape.Rectangle towerSquare = new Rectangle(30, 30, Color.GREEN);
    private javafx.scene.shape.Circle towerCircle= new Circle(10,Color.TRANSPARENT);

    public Drawing(){
        super();
       this.getChildren().add(new Tips(0,new Point(20,250),this));
        this.getChildren().add(labelGold);
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(20), event -> {
            ArrayList<Updatable> updatablestoremove=new ArrayList<>();
            for(Updatable updatable:updatables){
                updatable.update();

                if (!updatable.isAlive()) {
                    updatablestoremove.add(updatable);
                }
            }
            for (Updatable updatabletoremove : updatablestoremove){removeUpdatable(updatabletoremove);}
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }
    public void drawLifeGold(){
        Platform.runLater(() -> {
            labelGold.relocate(20,400);
            labelGold.setTextFill(Color.web("FBF5FF"));
            labelGold.setText("SkillPoints : " +Game.getPlayer().getGold()+"\nMotivation : "+Game.getPlayer().getLives()+"/"+Game.getPlayer().getMaxLives()+"\nWave :"+Game.getPlayer().getWave());
        });


    }

    public void creatingTowerSquare(double range){
        towerCircle.setRadius(range);
        towerCircle.setStroke(Color.GREEN);
        towerCircle.relocate(300-range,300-range);
        towerSquare.relocate(300-15,300-15);
        this.setOnMouseMoved(e->{
            if (this.isOn(e)) {
                if (PlayScreen.towerType!=null){
                towerCircle.relocate(e.getX()-(range),e.getY()-(range));
                towerSquare.relocate(e.getX() - 15, e.getY() - 15);
                if(TowerMaker.CheckTowerOk(new Tower(new Point(e.getX(),e.getY())))){
                    towerSquare.setFill(Color.GREEN);
                    towerCircle.setStroke(Color.GREEN);
                }
                else{
                    towerSquare.setFill(Color.RED);
                    towerCircle.setStroke(Color.RED);
                }}
            }
    });
        this.getChildren().addAll(towerCircle,towerSquare);
    }


    public void removeCreatingTower(){
        this.getChildren().removeAll(towerSquare,towerCircle);
    }

    public boolean isOn(MouseEvent e){
        double x = e.getX();
        double y = e.getY();
        return (x<1100 && x>20 && y>20 && y<600 );
    }
    public void drawSquare(Point centre,Color color, int size) {
        square = new Rectangle(size, size, color);
        square.setX(centre.getX() - size/2);
        square.setY(centre.getY()-size/2);
        this.getChildren().add(square);
    }



    public void draw(Updatable updatable){ // peut etre modifié
        updatables.add(updatable);
        this.getChildren().add(updatable.getShape());
    }


    public void drawTower(Tower tower){
        towers.add(tower);
        this.getChildren().add(tower.getTowerShape());
    }

    public void removeTower(Tower tower){
        towers.remove(tower);
        this.getChildren().remove(tower.getTowerShape());
    }



    public void drawallRoute(){
        for (ArrayList<Point> route : MapFactory.getAllRoutes()){drawRoute(route);}
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

    public void removeUpdatable(Updatable updatable){
        updatables.remove(updatable);
        this.getChildren().removeAll(updatable.getShape());

    }

    public ArrayList<Updatable> getUpdatables(){return updatables;}


        }
