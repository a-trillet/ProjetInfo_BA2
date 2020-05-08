import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayScreen{
    private Drawing drawing;
    private static BorderPane borderPane = new BorderPane();
    private static String towerType = null;
    private static MapClickListener mapClickListener;
    private static Pane map = new Pane();
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
            drawing.removeCreatingTower();
            PlayScreen.towerType = "Stack Overflow tower";
            price.setText(messPrix + StackTower.getNewCost());
            drawing.creatingTowerSquare(StackTower.getNewRange());
            if(!shop.getChildren().contains(characButton)) {
                shop.getChildren().add(characButton);
                characButton.setOnMouseClicked(event -> inititalCharacteristics("Stack Overflow tower"));
            }
        });
        stackTowerButton.setOnAction(e -> { if (Game.getPlayer().getGold() < StackTower.getNewCost()){msgError.setText(messError);}});


        Button massartTowerButton = new Button("Massart tower");
        massartTowerButton.setOnMouseClicked(e -> {
            drawing.removeCreatingTower();
            PlayScreen.towerType="Massart tower" ;
            price.setText(messPrix + MassartTower.getNewCost());
            drawing.creatingTowerSquare(MassartTower.getNewRange());
            if(!shop.getChildren().contains(characButton)) {
                shop.getChildren().add(characButton);
                characButton.setOnMouseClicked(event -> inititalCharacteristics("Massart tower"));
            }
        });
        massartTowerButton.setOnAction(e -> {if (Game.getPlayer().getGold() < MassartTower.getNewCost()){msgError.setText(messError);}});

        Button rajTowerButton = new Button("Raj tower");
        rajTowerButton.setOnMouseClicked(e -> {
            drawing.removeCreatingTower();
            PlayScreen.towerType="Raj tower" ;
            price.setText(messPrix + RajTower.getNewCost());
            drawing.creatingTowerSquare(RajTower.getNewRange());
            if(!shop.getChildren().contains(characButton)) {
                shop.getChildren().add(characButton);
                characButton.setOnMouseClicked(event -> inititalCharacteristics("Raj tower"));
            }
        });
        rajTowerButton.setOnAction(e -> {if (Game.getPlayer().getGold() < RajTower.getNewCost()){msgError.setText(messError);}});

        Button sycamoreTowerButton = new Button("Sycamore Tower");
        sycamoreTowerButton.setOnMouseClicked(( e-> {
            drawing.removeCreatingTower();
            PlayScreen.towerType = "Sycamore tower" ;
            price.setText(messPrix + SycamoreTower.getNewCost());
            drawing.creatingTowerSquare(SycamoreTower.getNewRange());
            if(!shop.getChildren().contains(characButton)) {
                shop.getChildren().add(characButton);
                characButton.setOnMouseClicked(event -> inititalCharacteristics("Sycamore tower"));
            }
        }));
        sycamoreTowerButton.setOnAction(e -> {if (Game.getPlayer().getGold() < SycamoreTower.getNewCost()){msgError.setText(messError);}});

        GridPane.setConstraints(characButton,0,10);
        GridPane.setConstraints(stackTowerButton,0,0);
        GridPane.setConstraints(massartTowerButton,0,1);
        GridPane.setConstraints(rajTowerButton,0,2);
        GridPane.setConstraints(sycamoreTowerButton,0,3);

        GridPane.setConstraints(msgError,0,15);
        GridPane.setConstraints(price,0,6);
        shop.getChildren().addAll(stackTowerButton,massartTowerButton,rajTowerButton, sycamoreTowerButton,msgError,price);
        borderPane.setRight(shop);
    }
    private void inititalCharacteristics(String towerType){
        GridPane infoBox = new GridPane();
        infoBox.setPrefWidth(200);
        infoBox.setPadding(new Insets(10,0,10,10));
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
        selectedImage.relocate(750,40);

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


        return borderPane;
    }

    public static MapClickListener getMapClickListener(){return mapClickListener;}
    public static String getTowerType(){return towerType;}
}






































