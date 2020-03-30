import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.*;
  /// pas definitif, juste un test de deuxieme fenetre
public class ParameterWindow {
      static int difficulty;

      public static int display(String title, String message){ // message peut diférer si on change les parametrtre depuis le muenu ou le jeu par exemple,: avant de commencer la partie, choisissez les parametre
        Stage parameterWindow = new Stage();                    // peut aussi etre fait dans la meme window avec une nouvelle scene. donc à discuter. MAis cette classe peut se renommer display parameter ou un brol du genre

        parameterWindow.initModality(Modality.APPLICATION_MODAL); // empeche de toucher à la fenetre de base avant d'avoir coupé cette page . utile pendant qu'on change les parametres
        parameterWindow.setTitle(title);
        parameterWindow.setMinWidth(300);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Apply and close");
        closeButton.setOnAction(e -> parameterWindow.close());

        // creation bouton sélection de difficulté
        final ComboBox<String> difficultySelection = new ComboBox<String>();
        difficultySelection.getItems().addAll(
                "Easy",
                "Normal",
                "Hard",
                "Insane"
        );
        difficultySelection.setValue("Normal");
        String difficultee = difficultySelection;
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

        //creation Page avec close button and label
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,closeButton, difficultySelection);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        parameterWindow.setScene(scene);
        parameterWindow.showAndWait();


        return difficulty;
    }
}
