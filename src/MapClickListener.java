import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;

public class MapClickListener implements EventHandler<MouseEvent> {

    private MapClickable currentSelection =  null;
    private BorderPane borderPane;
    private Circle circle = new Circle();
    private PlayScreen playScreen;

    public MapClickListener (BorderPane borderPane, Drawing drawing, PlayScreen playScreen){
        super();
        this.playScreen = playScreen;
        this.borderPane = borderPane;
        drawing.getChildren().add(circle);
    }

    private MapClickable clickedOn(MouseEvent e){

        ArrayList<Tower> towers = Game.getPlayer().getTowerList();
        ArrayList<Enemy> enemies = Game.getPlayer().getEnemiesOnMap();
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

    public void handle(MouseEvent mouseEvent){                  //affiche displayInfo si on clique sur un ennemi ou une tour, ou le shop par défault
        if(mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED){
            currentSelection = clickedOn(mouseEvent);
            circle.setStroke(Color.TRANSPARENT);
            if (currentSelection == null){
                playScreen.displayShop();
            }
            else {
                displayInfo();
            }
        }
    }

    //affiche les informations des tours ou des ennemis
    public void displayInfo(){
        Info info = currentSelection.getInfo();

        String infos = info.listString();
        GridPane infoBox = new GridPane();
        infoBox.setPrefWidth(200);
        infoBox.setPadding(new Insets(10,0,0,5));
        infoBox.setVgap(8);



        if (info instanceof InfoTower){                     //dessine le cercle de Range
            circle.setRadius(((InfoTower) info).getRange());
            Point centre = ((InfoTower) info).getCentre();
            circle.setCenterY(centre.getY());
            circle.setCenterX(centre.getX());
            circle.setStroke(((InfoTower) info).getColor());
            circle.setFill(Color.TRANSPARENT);
        }

        Label msgErrPower = new Label();
        GridPane.setConstraints(msgErrPower,0,7);


        if (currentSelection instanceof Tower){
            Button sellButton = new Button("Sell for "+((Tower)currentSelection).getSellPrice());
            sellButton.setOnMouseClicked((e->{
                Game.getPlayer().removeTower((Tower) currentSelection);
                circle.setStroke(Color.TRANSPARENT);
                playScreen.displayShop();
            }));
            GridPane.setConstraints(sellButton,0,9);
            if(((Tower)currentSelection).getLevel()<3){
                Button upgradeButton = new Button("Upgrade for " + ((Tower)currentSelection).getUpgradeCost());
                upgradeButton.setOnAction(e ->{
                    ((Tower) currentSelection).upgrade();
                    displayInfo();
                });
                GridPane.setConstraints(upgradeButton,0,8);

                infoBox.getChildren().addAll(upgradeButton);
            }
            else{Button upgradeButton = new Button("Maxed");
                GridPane.setConstraints(upgradeButton,0,8);
                infoBox.getChildren().addAll(upgradeButton);
            }

            Button powerButton = new Button(((Tower) currentSelection).getPowerType());
            powerButton.setOnMouseClicked(e ->{
                if (((Tower) currentSelection).getNumberOfKill()>= ((Tower) currentSelection).getKillPower()) {
                    ((Tower)currentSelection).powerActivation();
                }
                else{
                    msgErrPower.setText("Kill "+((Tower) currentSelection).getKillPower()+" ennemies to active "+((Tower) currentSelection).getPowerType());
                    GridPane.setConstraints(msgErrPower,0,7);
                    infoBox.getChildren().add(msgErrPower);
                }
            });
            Label label = new Label();
            label.setText("Power: ");
            HBox hBox = new HBox(10);
            GridPane.setConstraints(hBox,0,6);
            hBox.getChildren().addAll(label,powerButton);
            infoBox.getChildren().addAll(hBox,sellButton);
        }

        Button shopButton = new Button("Shop");
        shopButton.setOnAction(e -> playScreen.displayShop());
        GridPane.setConstraints(shopButton,0,15);

        Label label = new Label(infos);
        GridPane.setConstraints(label, 0,0);

        infoBox.getChildren().addAll(label, shopButton);
        borderPane.setRight(infoBox);
    }

    public MapClickable getCurrentSelection(){
        return currentSelection;
    }

}
