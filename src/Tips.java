import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Tips extends Pane {
    private boolean disaplayed = false;
    private Drawing drawing;
    private String[] showStrings={"Mais arrête!!!\n Tu ne penses quand même pas pouvoir venir\n à bout de ce projet sans mon aide",
            "Encore un étudiant qui se croit plus malin\n que les autres,... ",
            "Si tu clique encore une fois,\n je m'en irai pour toujours,\n je serai ... MORT!!! "};
    private int itShow = 0;
    private Text text;
    public Tips(String text,Point point,Drawing d){
        super();
        drawing=d;
        this.relocate(point.getX(),point.getY());

        final ImageView selectedImage = new ImageView();
        //Image background = new Image("tipOfTheDay.jpg",200,140,false,false);
        Image background = new Image("tipOfTheDay.jpg");
        selectedImage.setImage(background);
        selectedImage.setFitHeight(140);
        selectedImage.setPreserveRatio(true);
        this.getChildren().addAll(selectedImage);

        this.text = new Text(20,40, text);
        this.text.setFill(Color.WHITE);
        this.getChildren().add(this.text);
        this.setOnMouseClicked(this::actions);
    }
    private void actions(MouseEvent e){
        Point p = new Point(e.getX(),e.getY());
        if(isOnCloseButton(p)){
            drawing.getChildren().remove(this);
        }
        else if (isOnCLose(p)){
            this.text.setText("Tu veux m'abandonner?\nJe ne t'ai même pas encore expliqué\nà quoi sert la croix 'X' ni 'Show'");
        }
        else if (isOnNext(p)){
            this.text.setText("Next Message");
        }
        else if (isOnPrevious(p)){
            this.text.setText("Previous message");
        }
        else if(isOnShow(p)){
            if(itShow == 3){
                drawing.getChildren().remove(this);
            }
            else {
                this.text.setText(showStrings[itShow] + "\n" + (itShow + 1) + "/3)");
                itShow++;
            }
        }
    }
    private boolean isOnCloseButton(Point p){
        Point closeButton =  new Point(300,5);
        return p.distance(closeButton)<8;
    }
    private boolean isOnCLose(Point p){
        return (250<p.getX() && p.getX()<302 && p.getY()<135 && 115<p.getY());
    }
    private boolean isOnNext(Point p){
        return (190<p.getX() && p.getX()<242 && p.getY()<135 && 115<p.getY());
    }
    private boolean isOnPrevious(Point p){
        return (116<p.getX() && p.getX()<181 && p.getY()<135 && 115<p.getY());
    }
    private boolean isOnShow(Point p){
        Point show = new Point(16,125);
        return p.distance(show)<8;
}
    public void setText(String s){
        text.setText(s);
    }

}










































