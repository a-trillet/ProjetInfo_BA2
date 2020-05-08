import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.*;
public class ParameterScene {
      public static void display( Stage window, Scene futurScene){

        StackPane stackPane = new StackPane();
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(300,0,0,60));
        Scene scene = new Scene(stackPane, 641, 402);

        //background
        Image image1 = new Image(ParameterScene.class.getResourceAsStream("Images/ideaFinal.jpg"));
        BackgroundImage backgroundimage = new BackgroundImage(image1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);

        // box veticales
        VBox layout = new VBox();
        layout.setPadding(new Insets(0,0,10,0));
        layout.setSpacing(25);


        //mise en place du nom du joueur
        TextField nameInput = new TextField("Name");
        nameInput.setMaxWidth(120);
        nameInput.setOnMouseClicked(e -> nameInput.clear());


          // creation bouton sélection de difficulté
        ComboBox<String> difficultySelection = new ComboBox<>();
        difficultySelection.getItems().addAll(
                "Easy",
                "Normal",
                "Hard",
                "Insane"
        );
        difficultySelection.setPromptText("Select Difficulty");

        //création sélection de map
        ComboBox<String> mapSelection = new ComboBox<>();
        mapSelection.getItems().addAll(
                "Easy",
                "Normal",
                "Hard",
                "Insane",
                "create your map"
        );
        mapSelection.setPromptText("Select your map");
        mapSelection.setOnAction(e->getMapSelection(mapSelection,window,scene));


        //message d'erreur
        Label labelError1 = new Label();
        labelError1.setText("");
        GridPane.setConstraints(labelError1,0,0);
        gridPane.getChildren().add(labelError1);

        //close button
        Button closeButton = new Button("Apply and close");
        closeButton.setOnAction(e -> {
          if (nameInput.getText() == "" || difficultySelection.getValue() == null || mapSelection.getValue() == null){
            labelError1.setText("Error, enter a name"+"\n"+ ", choose difficulty and select your map" );
          }
          else{
            Game.getDrawing().drawallRoute();
            Game.getPlayer().setName(nameInput.getText());
            Game.getPlayer().setDifficulty(getDifficulty(difficultySelection));
            Game.getPlayer().loadMap();
            Game.getPlayer().reset();
            PlayScreen.drawRun();
            Game.getDrawing().drawLifeGold();
            window.setScene(futurScene);
          }
        });

        stackPane.setBackground(background);
        layout.getChildren().addAll( nameInput, difficultySelection,mapSelection, closeButton);
        layout.setAlignment(Pos.BOTTOM_CENTER);
        stackPane.getChildren().addAll(gridPane,layout);


        window.setScene(scene);
    }
    private static int getDifficulty(ComboBox<String> difficultySelection){
      String difficultee = difficultySelection.getValue();
      int difficulty = 2;
      switch(difficultee) {
        case "Easy" :
          difficulty = 1;
          break;
        case "Normal":
          difficulty = 2;
          break;
        case "Hard":
          difficulty = 3;
          break;
        case  "Insane" :
          difficulty = 4;
          break;
      }
      return difficulty;
    }

  private static void getMapSelection(ComboBox<String> combobox, Stage window,Scene scene){
    String mapchoice = combobox.getValue();
    switch(mapchoice) {
      case "Easy" :
        new MapFactory(1);
        break;
      case "Normal":
        new MapFactory(2);
        break;
      case "Hard":
        new MapFactory(3);
        break;
      case  "Insane" :
        new MapFactory(4);
        break;
      case "create your map":
        MapEditor mapEditor = new MapEditor( window, scene);
        window.setScene(new Scene(mapEditor,1128,581));
        break;
    }
  }
}
