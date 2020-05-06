import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.*;
public class ParameterScene {
      public static void display( Stage window, Scene futurScene){

        StackPane stackPane = new StackPane();
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(stackPane, 641, 402);

        //background
        Image image1 = new Image(ParameterScene.class.getResourceAsStream("ideaFinal.jpg"));
        BackgroundImage backgroundimage = new BackgroundImage(image1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);

        // box veticales
        VBox layout = new VBox();
        layout.setPadding(new Insets(150,20,0,20));
        layout.setSpacing(30);


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

        //close button

        Button closeButton = new Button("Apply and close");
        closeButton.setOnAction(e -> {
          if (nameInput.getText() == "" || difficultySelection.getValue() == null || mapSelection.getValue() == null){
            labelError1.setText("Error, enter a name"+"\n"+ "and choose difficulty" );
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
        layout.getChildren().addAll( nameInput, difficultySelection,mapSelection, closeButton,labelError1);
        layout.setAlignment(Pos.BOTTOM_CENTER);
        stackPane.getChildren().addAll(layout);







        window.setScene(scene);


    }
    private static String difficultyString(int diff){
      String difficulty = "";
      switch(diff) {
        case 1 :
          difficulty = "Easy";
          break;
        case 2:
          difficulty = "Normal";
          break;
        case 3:
          difficulty = "Hard";
          break;
        case  4 :
          difficulty = "Insane";
          break;
      }
      return difficulty;

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
