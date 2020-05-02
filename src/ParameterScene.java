import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.*;
  /// pas definitif, juste un test de deuxieme fenetre
public class ParameterScene {

      public static void display( String message, Stage window, Scene futurScene,Player player){ // message peut diférer si on change les parametrtre depuis le muenu ou le jeu par exemple,: avant de commencer la partie, choisissez les parametre



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
                  Game.player.setDifficulty(getDifficulty(difficultySelection));
                  new MapPane(getDifficulty(difficultySelection));
                  window.setScene(futurScene);
        });



        //creation Page avec close button and label
        VBox layout = new VBox(10);                           //// Vbox met les element à la vertical, H box à l'horizontale
        layout.getChildren().addAll(label,closeButton, difficultySelection);
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
