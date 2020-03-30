import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.*;
  /// pas definitif, juste un test de deuxieme fenetre
public class ParameterWindow {


      public static void display(String title, String message){ // message peut diférer si on change les parametrtre depuis le muenu ou le jeu par exemple,: avant de commencer la partie, choisissez les parametre
        Stage parameterWindow = new Stage();                    // peut aussi etre fait dans la meme window avec une nouvelle scene. donc à discuter. MAis cette classe peut se renommer display parameter ou un brol du genre

        parameterWindow.initModality(Modality.APPLICATION_MODAL); // empeche de toucher au programme de base avant d'avoir coupé cette page . utile pendant qu'on change les parametres
        parameterWindow.setTitle(title);
        parameterWindow.setMinWidth(300);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close the window");
        closeButton.setOnAction(e -> parameterWindow.close());


        //creation Page avec close button and label
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        parameterWindow.setScene(scene);
        parameterWindow.showAndWait();
    }
}
