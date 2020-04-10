import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

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
        String infos = info.listString();
        GridPane infoBox = new GridPane();
        infoBox.setPrefWidth(200);
        infoBox.setPadding(new Insets(10,10,10,10));
        infoBox.setVgap(8);
        infoBox.setHgap(10);

        if (currentSelection instanceof Tower){
            if(((Tower)currentSelection).getLevel()<3){
                Button upgradeButton = new Button("Upgrade for" + ((Tower)currentSelection).getUpgradeCost());
                upgradeButton.setOnAction(e -> ((Tower) currentSelection).upgrade()); ///essayer de recuperer le message de upgrade(pas assez de gold) dans un label + mettre a jour en remmttant display
                GridPane.setConstraints(upgradeButton,0,6);
                infoBox.getChildren().addAll(upgradeButton);
            }
            else{Button upgradeButton = new Button("Maxed");
                GridPane.setConstraints(upgradeButton,0,6);
                infoBox.getChildren().addAll(upgradeButton);
            }


        }
        Button shopButton = new Button("Shop");
        shopButton.setOnAction(e ->PlayScreen.displayShop());
        GridPane.setConstraints(shopButton,0,8);

        Label label = new Label(infos);
        GridPane.setConstraints(label, 0,0);

        infoBox.getChildren().addAll(label, shopButton);
        borderPane.setRight(infoBox);
    }



}
