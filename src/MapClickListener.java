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
    private Drawing drawing;


    public MapClickable getCurrentSelection(){
        return currentSelection;
    }


    public MapClickListener (BorderPane borderPane, Drawing drawing){
        super();
        this.borderPane = borderPane;
        drawing.getChildren().add(circle);
    }

    private MapClickable clickedOn(MouseEvent e){    // retourne le mapclickable sur lequel on a cliqué

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



    public void handle(MouseEvent mouseEvent){
        if(mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED){
            currentSelection = clickedOn(mouseEvent);
            circle.setStroke(Color.TRANSPARENT);
            if (currentSelection == null){
                displayShop();
                System.out.println(mouseEvent.getX()+"      "+mouseEvent.getY());          // test coord souris
            }
            else {
                displayInfo("");
            }
        }

    }

    //affiche les information et bouton upgrade
    public void displayInfo(String messUpgrade){
        Info info = currentSelection.getInfo();


        String infos = info.listString();
        GridPane infoBox = new GridPane();
        infoBox.setPrefWidth(200);
        infoBox.setPadding(new Insets(10,0,0,5));
        infoBox.setVgap(8);


        //cercle de range

        if (info instanceof InfoTower){ //dessine le cercle de Range
            circle.setRadius(((InfoTower) info).getRange());
            Point centre = ((InfoTower) info).getCentre();
            circle.setCenterY(centre.getY());
            circle.setCenterX(centre.getX());
            circle.setStroke(((InfoTower) info).getColor());
            circle.setFill(Color.TRANSPARENT);
        }




        Label messageUpgrade = new Label(messUpgrade);    //mess uprgrade supprimable mais bon alz
        GridPane.setConstraints(messageUpgrade,0,9);
        Label msgErrPower = new Label();
        GridPane.setConstraints(msgErrPower,0,7);

        if (currentSelection instanceof Tower){
            if(((Tower)currentSelection).getLevel()<=3){
                Button upgradeButton = new Button("Upgrade for " + ((Tower)currentSelection).getUpgradeCost());
                upgradeButton.setOnAction(e ->{
                    messageUpgrade.setText(((Tower) currentSelection).upgrade());
                    displayInfo(messageUpgrade.getText());
                });
                GridPane.setConstraints(upgradeButton,0,8);
                infoBox.getChildren().addAll(upgradeButton);
            }
            else{Button upgradeButton = new Button("Maxed");
                upgradeButton.setOnAction(e -> messageUpgrade.setText("This tower is at max level"));
                GridPane.setConstraints(upgradeButton,0,8);
                infoBox.getChildren().addAll(upgradeButton);
            }

            Button powerButton = new Button(((Tower) currentSelection).getPowerType());
            powerButton.setOnMouseClicked(e ->{
                if (((Tower) currentSelection).getNumberOfKill()> ((Tower) currentSelection).getKillPower()) {
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
            HBox hb = new HBox(10);
            GridPane.setConstraints(hb,0,6);
            hb.getChildren().addAll(label,powerButton);

            infoBox.getChildren().add(hb);





        }


        Button shopButton = new Button("Shop");
        shopButton.setOnAction(e -> displayShop());
        GridPane.setConstraints(shopButton,0,15);

        Label label = new Label(infos);
        GridPane.setConstraints(label, 0,0);

        infoBox.getChildren().addAll(label, shopButton, messageUpgrade);
        infoBox.setGridLinesVisible(true);
        borderPane.setRight(infoBox);
    }

    public void displayShop(){
        GridPane shop = new GridPane();
        String messError = "You don't have enough money";
        String messPrix = "Prix : ";
        shop.setPrefWidth(200);
        shop.setPadding(new Insets(10,10,10,10));
        shop.setVgap(8);
        shop.setHgap(10);


        Label msgError = new Label();
        Label prix = new Label();
        GridPane.setConstraints(msgError,0,17);
        GridPane.setConstraints(prix,0,6);

        Button basicTowerButton = new Button("Stack tower");
        basicTowerButton.setOnMouseClicked(e-> {PlayScreen.towerType = "Stack Overflow tower"; prix.setText(messPrix + String.valueOf(StackTower.getNewCost()));});
        basicTowerButton.setOnAction(e -> { if (Game.getPlayer().getGold() < StackTower.getNewCost()){msgError.setText(messError);}});
        GridPane.setConstraints(basicTowerButton,0,0);


        Button iceTowerButton = new Button("Massart tower");
        iceTowerButton.setOnMouseClicked(e -> {PlayScreen.towerType="Massart tower" ; prix.setText(messPrix + String.valueOf(MassartTower.getNewCost()));});
        iceTowerButton.setOnAction(e -> {if (Game.getPlayer().getGold() < MassartTower.getNewCost()){msgError.setText(messError);}});
        GridPane.setConstraints(iceTowerButton,1,0);

        Button fireTowerButton = new Button("Fire tower");
        fireTowerButton.setOnMouseClicked(e -> {PlayScreen.towerType="FIRE" ; prix.setText(messPrix + String.valueOf(FireTower.getNewCost()));});
        fireTowerButton.setOnAction(e -> {if (Game.getPlayer().getGold() < FireTower.getNewCost()){msgError.setText(messError);}});
        GridPane.setConstraints(fireTowerButton,0,1);

        Button sniperTowerButton = new Button("Sycamore Tower");
        sniperTowerButton.setOnMouseClicked(( e-> {PlayScreen.towerType = "Sycamore tower" ; prix.setText(messPrix + String.valueOf(SycamoreTower.getNewCost()));}));
        sniperTowerButton.setOnAction(e -> {if (Game.getPlayer().getGold() < SycamoreTower.getNewCost()){msgError.setText(messError);}});
        GridPane.setConstraints(sniperTowerButton,1,1);



        Button nextWave = new Button("Next Wave");
        GridPane.setConstraints(nextWave, 0,25);
        nextWave.setOnAction(e -> {
            Game.getPlayer().getEnemyFactory().nextWave();
        });


        //intégration d'images test (je savais pas ou mettre)
        //Image image = new Image(getClass().getResourceAsStream("AntoineBg.jpg"));//que qd photo ds répertoire courant
        //ImageView imageView = new ImageView(image);
        //imageView.setFitWidth(35);
        //imageView.setFitHeight(47);
        //GridPane.setConstraints(imageView, 0,6);




        shop.getChildren().addAll(basicTowerButton, iceTowerButton, fireTowerButton, sniperTowerButton, nextWave, msgError, prix);
        borderPane.setRight(shop);




    }

}
