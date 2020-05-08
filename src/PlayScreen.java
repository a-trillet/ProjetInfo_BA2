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
        shop.setPrefWidth(200);
        shop.setPadding(new Insets(10,10,10,10));
        shop.setVgap(8);
        shop.setHgap(10);


        String messError = "You don't have enough money";
        String messPrix = "Prix : ";
        Label msgError = new Label();
        Label price = new Label();


        Button characButton = new Button("Characteristics");

        Button stackTowerButton = new Button("Stack tower");
        stackTowerButton.setOnMouseClicked(e-> {
            PlayScreen.towerType = "Stack Overflow tower";
            shop.getChildren().add(characButton);
            characButton.setOnMouseClicked(event-> inititalCharacteristics("Stack Overflow tower"));
            price.setText(messPrix + StackTower.getNewCost());
            drawing.creatingTowerSquare(StackTower.getNewRange());
        });
        stackTowerButton.setOnAction(e -> { if (Game.getPlayer().getGold() < StackTower.getNewCost()){msgError.setText(messError);}});


        Button massartTowerButton = new Button("Massart tower");
        massartTowerButton.setOnMouseClicked(e -> {
            PlayScreen.towerType="Massart tower" ;
            shop.getChildren().add(characButton);
            characButton.setOnMouseClicked(event-> inititalCharacteristics("Massart tower"));
            price.setText(messPrix + MassartTower.getNewCost());
            drawing.creatingTowerSquare(MassartTower.getNewRange());
        });
        massartTowerButton.setOnAction(e -> {if (Game.getPlayer().getGold() < MassartTower.getNewCost()){msgError.setText(messError);}});

        Button rajTowerButton = new Button("Raj tower");
        rajTowerButton.setOnMouseClicked(e -> {
            PlayScreen.towerType="Raj tower" ;
            shop.getChildren().add(characButton);
            characButton.setOnMouseClicked(event-> inititalCharacteristics("Raj tower"));
            price.setText(messPrix + RajTower.getNewCost());
            drawing.creatingTowerSquare(RajTower.getNewRange());
        });
        rajTowerButton.setOnAction(e -> {if (Game.getPlayer().getGold() < RajTower.getNewCost()){msgError.setText(messError);}});

        Button sycamoreTowerButton = new Button("Sycamore Tower");
        sycamoreTowerButton.setOnMouseClicked(( e-> {
            PlayScreen.towerType = "Sycamore tower" ;
            shop.getChildren().add(characButton);
            characButton.setOnMouseClicked(event-> inititalCharacteristics("Sycamore tower"));
            price.setText(messPrix + SycamoreTower.getNewCost());
            drawing.creatingTowerSquare(SycamoreTower.getNewRange());
        }));
        sycamoreTowerButton.setOnAction(e -> {if (Game.getPlayer().getGold() < SycamoreTower.getNewCost()){msgError.setText(messError);}});

        GridPane.setConstraints(characButton,0,23);
        GridPane.setConstraints(stackTowerButton,0,0);
        GridPane.setConstraints(massartTowerButton,0,1);
        GridPane.setConstraints(rajTowerButton,0,2);
        GridPane.setConstraints(sycamoreTowerButton,0,3);

        GridPane.setConstraints(msgError,0,17);
        GridPane.setConstraints(price,0,6);
        shop.getChildren().addAll(stackTowerButton,massartTowerButton,rajTowerButton, sycamoreTowerButton,msgError,price);
        borderPane.setRight(shop);
    }
    private void inititalCharacteristics(String towerType){
        GridPane infoBox = new GridPane();
        infoBox.setPrefWidth(200);
        infoBox.setPadding(new Insets(10,10,10,10));
        infoBox.setVgap(8);
        infoBox.setHgap(10);

        Button backButton = new Button("Back >>");
        backButton.setOnMouseClicked(e-> displayShop());
        GridPane.setConstraints(backButton,0,4);


        String infos = InfoTower.initialList(towerType);

        Label features = new Label(infos);
        Label history = new Label();
        GridPane.setConstraints(features, 0,0);
        infoBox.getChildren().addAll(features,backButton);
        borderPane.setRight(infoBox);
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

        map.getChildren().addAll(testButton);

        return borderPane;
    }
}






































