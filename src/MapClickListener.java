import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class MapClickListener implements EventHandler<MouseEvent> {


    private MapClickable currentSelection =  null;
    public BorderPane borderPane;


    public MapClickListener (BorderPane borderPane){
        super();
        this.borderPane = borderPane;
    }

    public void MousePressed(MouseEvent e){

    }

    private MapClickable clickedOn(MouseEvent e){    // retourne le mapclickable sur lequel on a cliqué

        ArrayList<Tower> towers = Player.getPlayer().getTowerList();
        ArrayList<Enemy> enemies = Player.getPlayer().getEnemiesOnMap();
        ArrayList<MapClickable> objects = new ArrayList<MapClickable>(towers);
        objects.addAll(enemies);

        MapClickable selection = null;

        for(MapClickable object : objects){
            if(object.isOn(new Point(e.getX(), e.getY()))){
                if (selection == null){
                   selection = object;
                }
                else if(object.getCentre().distance(new Point(e.getX(), e.getY())) < selection.getCentre().distance(new Point(e.getX(), e.getY()))){
                    selection = object;} // si on touche deux objects selectionne le plus proche
            }
        }
        return selection;
    }



    public void handle(MouseEvent mouseEvent){
        if(mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED){
            currentSelection = clickedOn(mouseEvent);
            if (currentSelection == null){
                PlayScreen.displayShop();
            }
            else {
                displayInfo(currentSelection.getInfo()); //(+cercle de range ,..)
            }
        }

    }
    private void displayInfo(Info info){
        Label label = new Label("coucou");
        VBox infoBox = new VBox();
        infoBox.getChildren().addAll(label);
        borderPane.setRight(infoBox);
    }



}
