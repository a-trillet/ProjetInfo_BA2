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
    public MapClickable getCurrentSelection(){
        return currentSelection;
    }


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
                displayShop();
                System.out.println(mouseEvent.getX()+"      "+mouseEvent.getY());           // test coord souris
            }
            else {
                displayInfo("");
            }
        }

    }

    //affiche les information et bouton upgrade
    public void displayInfo(String messUpgrade){     ///+cercle range
        Info info = currentSelection.getInfo();
        String infos = info.listString();
        GridPane infoBox = new GridPane();
        infoBox.setPrefWidth(200);
        infoBox.setPadding(new Insets(10,10,10,10));
        infoBox.setVgap(8);
        infoBox.setHgap(10);


        Label messageUpgrade = new Label(messUpgrade);    //mess uprgrade supprimable mais bon alz
        GridPane.setConstraints(messageUpgrade,0,7);
        if (currentSelection instanceof Tower){
            if(((Tower)currentSelection).getLevel()<=3){
                Button upgradeButton = new Button("Upgrade for " + ((Tower)currentSelection).getUpgradeCost());
                upgradeButton.setOnAction(e ->{
                    messageUpgrade.setText(((Tower) currentSelection).upgrade());
                    displayInfo(messageUpgrade.getText());
                });
                GridPane.setConstraints(upgradeButton,0,6);
                infoBox.getChildren().addAll(upgradeButton);
            }
            else{Button upgradeButton = new Button("Maxed");
                upgradeButton.setOnAction(e -> messageUpgrade.setText("This tower is at max level"));
                GridPane.setConstraints(upgradeButton,0,6);
                infoBox.getChildren().addAll(upgradeButton);
            }


        }
        Button shopButton = new Button("Shop");
        shopButton.setOnAction(e -> displayShop());
        GridPane.setConstraints(shopButton,0,8);

        Label label = new Label(infos);
        GridPane.setConstraints(label, 0,0);

        infoBox.getChildren().addAll(label, shopButton, messageUpgrade);
        borderPane.setRight(infoBox);
    }

    public void displayShop(){
        GridPane shop = new GridPane();
        String messError = new String("You don't have enough money");
        shop.setPrefWidth(200);
        shop.setPadding(new Insets(10,10,10,10));
        shop.setVgap(8);
        shop.setHgap(10);


        Label msgError = new Label();
        GridPane.setConstraints(msgError,0,7);

        Button basicTowerButton = new Button("Basic tower");
        basicTowerButton.setOnMouseClicked(e-> PlayScreen.towerType = "BASIC");
        basicTowerButton.setOnAction(e -> {if (Player.getPlayer().getGold() < BasicTower.getNewCost()){msgError.setText(messError);}});
        GridPane.setConstraints(basicTowerButton,0,0);

        Button iceTowerButton = new Button("Ice tower");
        iceTowerButton.setOnMouseClicked(e->PlayScreen.towerType="ICE");
        iceTowerButton.setOnAction(e -> {if (Player.getPlayer().getGold() < IceTower.getNewCost()){msgError.setText(messError);}});
        GridPane.setConstraints(iceTowerButton,1,0);

        Button fireTowerButton = new Button("Fire tower");
        //fireTowerButton.setOnMouseClicked(e->PlayScreen.towerType="FIRE");
        fireTowerButton.setOnAction(e -> {if (Player.getPlayer().getGold() < FireTower.getNewCost()){msgError.setText(messError);}});
        System.out.println("prix"+FireTower.getNewCost());  //prob : il donne d'abord la valeur initiale à firecost
        GridPane.setConstraints(fireTowerButton,0,1);

        Button sniperTowerButton = new Button("Sniper Tower");
        sniperTowerButton.setOnMouseClicked((e-> PlayScreen.towerType = "SNIPER"));
        sniperTowerButton.setOnAction(e -> {if (Player.getPlayer().getGold() < SniperTower.getNewCost()){msgError.setText(messError);}});
        GridPane.setConstraints(sniperTowerButton,1,1);


        Button nextWave = new Button("Next Wave");
        GridPane.setConstraints(nextWave, 0,9);
        nextWave.setOnAction(e -> {
            Player.getEnemyFactory().nextWave();
        });




        shop.getChildren().addAll(basicTowerButton, iceTowerButton, fireTowerButton, sniperTowerButton, nextWave, msgError);
        borderPane.setRight(shop);




    }

}
