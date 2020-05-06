import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;

public class PlayScreen{
    private Drawing drawing;
    public static BorderPane borderPane = new BorderPane();
    public static String towerType = null;
    public static MapClickListener mapClickListener;
    private static Pane map = new Pane(); // permet de supperposer les différents éléments de la map (image, tours,..)

    private static ImageView selectedImage = new ImageView();
    private static Image image = new Image(BossEnemy.class.getResourceAsStream("run.jpg"));

    public PlayScreen(Drawing d){
        this.drawing=d;
        Image image1 = new Image(PlayScreen.class.getResourceAsStream("intellij.jpg"));
        BackgroundImage backgroundimage = new BackgroundImage(image1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        map.setBackground(background);
        mapClickListener= new MapClickListener(borderPane,drawing,this);

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
        basicTowerButton.setOnMouseClicked(e-> {
            PlayScreen.towerType = "Stack Overflow tower";
            prix.setText(messPrix + String.valueOf(StackTower.getNewCost()));
            drawing.creatingTowerSquare(StackTower.getNewRange());
        });
        basicTowerButton.setOnAction(e -> { if (Game.getPlayer().getGold() < StackTower.getNewCost()){msgError.setText(messError);}});
        GridPane.setConstraints(basicTowerButton,0,0);


        Button iceTowerButton = new Button("Massart tower");
        iceTowerButton.setOnMouseClicked(e -> {
            PlayScreen.towerType="Massart tower" ;
            prix.setText(messPrix + String.valueOf(MassartTower.getNewCost()));
            drawing.creatingTowerSquare(MassartTower.getNewRange());
        });
        iceTowerButton.setOnAction(e -> {if (Game.getPlayer().getGold() < MassartTower.getNewCost()){msgError.setText(messError);}});
        GridPane.setConstraints(iceTowerButton,1,0);

        Button fireTowerButton = new Button("Raj tower");
        fireTowerButton.setOnMouseClicked(e -> {
            PlayScreen.towerType="Raj tower" ;
            prix.setText(messPrix + String.valueOf(RajTower.getNewCost()));
            drawing.creatingTowerSquare(RajTower.getNewRange());
        });
        fireTowerButton.setOnAction(e -> {if (Game.getPlayer().getGold() < RajTower.getNewCost()){msgError.setText(messError);}});
        GridPane.setConstraints(fireTowerButton,0,1);

        Button sniperTowerButton = new Button("Sycamore Tower");
        sniperTowerButton.setOnMouseClicked(( e-> {
            PlayScreen.towerType = "Sycamore tower" ;
            prix.setText(messPrix + String.valueOf(SycamoreTower.getNewCost()));
            drawing.creatingTowerSquare(SycamoreTower.getNewRange());
        }));
        sniperTowerButton.setOnAction(e -> {if (Game.getPlayer().getGold() < SycamoreTower.getNewCost()){msgError.setText(messError);}});
        GridPane.setConstraints(sniperTowerButton,1,1);



        /*Button nextWave = new Button("Next Wave");
        GridPane.setConstraints(nextWave, 0,25);
        nextWave.setOnAction(e -> {
            Game.getPlayer().getEnemyFactory().nextWave();
        });*/


        //intégration d'images test (je savais pas ou mettre)
        //Image image = new Image(getClass().getResourceAsStream("AntoineBg.jpg"));//que qd photo ds répertoire courant
        //ImageView imageView = new ImageView(image);
        //imageView.setFitWidth(35);
        //imageView.setFitHeight(47);
        //GridPane.setConstraints(imageView, 0,6);




        shop.getChildren().addAll(basicTowerButton, iceTowerButton, fireTowerButton, sniperTowerButton, msgError, prix);
        borderPane.setRight(shop);




    }

    public static void drawRun(){
        selectedImage.setImage(image);
        selectedImage.setFitHeight(50);
        selectedImage.setPreserveRatio(true);
        selectedImage.relocate(600,80);

        selectedImage.setOnMouseClicked(e->Game.getPlayer().getEnemyFactory().nextWave());
        map.getChildren().add(selectedImage);
    }
    public BorderPane sceneView(){
        //La map

        map.getChildren().addAll(drawing);
        //drawing.setOnMouseClicked(e->{if (towerType!=null){new TowerMaker(drawing,towerType,new Point(e.getX(),e.getY()));}});
        //drawing.setOnMouseReleased(new TowerButtonListener(drawing));
        //map.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));
        drawing.setOnMouseClicked(e->{
            drawing.removeCreatingTower();
            if ( towerType!=null ) {
                new TowerMaker(drawing, towerType, new Point(e.getX(), e.getY()));
                towerType=null;
            }


        });
        drawing.drawLifeGold();





        //le shop

        displayShop();
        //Info

        borderPane.setCenter(map);

        // listenners
        map.setOnMouseClicked(mapClickListener);

        // bouton test
        Button testButton = new Button("Save");
        testButton.setOnMouseClicked(e-> {
            try {
                Game.save();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
       /* Button testButton2 = new Button("test222");
        testButton2.relocate(15,200);
        testButton2.setOnMouseClicked(e-> {
            Game.player.newTower.SetActive();
            drawing.drawSquare(Game.player.newTower.getCentre(),new Color(0, 1,0,1));
        });*/
        map.getChildren().addAll(testButton);




        return borderPane;
    }








}






































