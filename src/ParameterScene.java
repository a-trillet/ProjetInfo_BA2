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


      public static void display( Stage window, Scene futurScene,Drawing drawing){

        StackPane rootPane = new StackPane();

        GridPane gridPane = new GridPane();

        VBox layout = new VBox();
        layout.setPadding(new Insets(160,20,0,20));
        layout.setSpacing(30);



        //background
        Image image1 = new Image(PlayScreen.class.getResourceAsStream("ideaFinal.jpg"));
        BackgroundImage backgroundimage = new BackgroundImage(image1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);

        //mise en place du nom du joueur
        TextField nameInput = new TextField();
        nameInput.setPrefColumnCount(2);

        //label pour name / difficulty
        Label nameLabel = new Label();
        nameLabel.setText("Name : ");
        GridPane.setConstraints(nameLabel,5,5);
        Label difficultyLabel = new Label();
        difficultyLabel.setText("Difficulty");
        GridPane.setConstraints(difficultyLabel,10,500);
        gridPane.getChildren().addAll(nameLabel,difficultyLabel);


          // creation bouton sélection de difficulté
        ComboBox<String> difficultySelection = new ComboBox<>();
        difficultySelection.getItems().addAll(
                "Easy",
                "Normal",
                "Hard",
                "Insane"
        );

        //message d'erreur
        Label labelError1 = new Label();
        labelError1.setText("");

        //close button
        Button closeButton = new Button("Apply and close");
        closeButton.setOnAction(e -> {
          if (nameInput.getText() == "" || difficultySelection.getValue() == null){
            labelError1.setText("Error, enter a name"+"\n"+ "and choose difficulty" );
          }
          else{
            Game.getPlayer().setName(nameInput.getText());
            Game.getPlayer().setDifficulty(getDifficulty(difficultySelection));
            new MapPane(getDifficulty(difficultySelection),drawing);
            Game.getPlayer().reset();
            window.setScene(futurScene);
          }
        });


        layout.getChildren().addAll( nameInput, difficultySelection,closeButton,labelError1);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(background);
        rootPane.getChildren().addAll(layout, gridPane);






        Scene scene = new Scene(rootPane, 641, 402);
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
}
