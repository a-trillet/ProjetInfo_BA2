import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.*;
public class ParameterScene {

      public static void display( String message, Stage window, Scene futurScene){

        //mise en place du nom du joueur
        TextField nameInput = new TextField();

        //message d'erreur
        Label labelError1 = new Label();
        labelError1.setText("");


          // creation bouton sélection de difficulté
        ComboBox<String> difficultySelection = new ComboBox<>();
        difficultySelection.getItems().addAll(
                "Easy",
                "Normal",
                "Hard",
                "Insane"
        );
        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Apply and close");
        closeButton.setOnAction(e -> {
          if (nameInput.getText() == "" || difficultySelection.getValue() == null){
            labelError1.setText("Error, enter a name"+"\n"+ "and choose difficulty" );
          }
          else{
            Game.player.setName(nameInput.getText());
            Game.player.setDifficulty(getDifficulty(difficultySelection));
            new MapPane(getDifficulty(difficultySelection));
            Game.player.reset();
            window.setScene(futurScene);
          }
        });


        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20,100,20,100));
        layout.getChildren().addAll(label, nameInput, difficultySelection,closeButton,labelError1);
        layout.setAlignment(Pos.CENTER);



        Scene scene = new Scene(layout, 400, 300);
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
