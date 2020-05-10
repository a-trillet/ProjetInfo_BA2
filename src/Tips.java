import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Tips extends Pane {
    private Drawing drawing;
    private String[] showStrings={"Mais arrête!!!\n Tu ne penses quand même pas pouvoir venir\n à bout de ce projet sans mon aide",
            "Encore un étudiant qui se croit plus malin\n que les autres,... ",
            "Si tu cliques encore une fois,\n je m'en irai pour toujours,\n je serai ... MORT!!! "};
    private String[][] textStrings = {
            {
                "Bienvenue cher étudiant , \n Vous vous apprêtez à passer des heures sur ce projet,\n Il convient donc de faire connaissance.",
                "Je suis Tip of the day, \nUn message automatique qui vous guidera le long de ce projet" ,
                "J'ai cru comprendre que vous deviez faire un tower defence, right ?\nJe vois que vous commencez avec quelques skillPoints,\nVous devriez les utiliser pour construire votre première tourelle\nElle vous aidera a résoudre les bugs de votre code",
                "La 'Massart Tower en coute justement seulement 150,\n vous pourrez même l'améliorer pour 50 de plus,\n Par contre, elle est un peu lente",
            },
            {
                "Parfait! Vous vous débrouillez bien pour un étudiant,\nIl est temps de tester votre début de code\nIl suffit d'appuyer sur le gros bouton RUN"
            },

            {
                "Regardez, résoudre une lettre de ce bug vous à rapporté des SkillPoints,\n Par contre si un bug atteint la sortie, vous perdrez en motivation\nRésolvez en plus et vous pourrez vous offrir l'aide d'autres tours",
                 "L'indien est très fort mais coute très cher en compréhension,\nSycamore est capable de tirer deux balles en même temps,\nStackOverflow tire en rafale d'inforamtions, l'efficacité accesible à tous "
            },
            {
                "Ho non, un bug n'a pas été résolu... \nVous commencer à perdre patience",
                 "Avec une motivation de 0, 90 pourcent des étudiants abandonne leur projet"
            },
            {
                "Le pouvoir d'une de vos tours est chargé\nCliquez dessus pour afficher ses infos et activer son pouvoir spécial ",
                 "Stack Overflow : tir en rafale\nRaj, l'indien: peut atomiser tous les ennemis avec sa TSAR Bomba \nSycamore tower: tire deux balles en même temps\nMassart tower: Ralentis les bugs"
            },
            {
                "Incroyable, votre code marche parfaitement\nHonnêtement, je n'y croyais pas du tout\navec vos 200SkillPoints de débutant\nSnif, vous allez me manquer..."
            },
            {"ATTENTION ce n'est pas fini,\nil faut encore push le projet sur Github..."}

    };
    private int itShow = 0;
    private int itText = 0;
    private int textNumber = -1;
    private Text text;
    private static boolean[] notSeen = {true,true,true,true,true,true,true,true};

    public Tips(int number,Point point,Drawing d){
        super();
        drawing=d;
        this.relocate(point.getX(),point.getY());
        if(notSeen[number]) {
            notSeen[number] = false;
            final ImageView selectedImage = new ImageView();
            Image background = new Image("Images/tipOfTheDay.jpg");
            selectedImage.setImage(background);
            selectedImage.setFitHeight(160);
            selectedImage.setPreserveRatio(true);
            this.getChildren().addAll(selectedImage);

            this.text = new Text(20, 40, "");
            this.text.setFill(Color.WHITE);
            textNumber = number;
            this.text.setText(textStrings[textNumber][0]);
            this.getChildren().add(this.text);
            this.setOnMouseClicked(this::actions);
            //this.setViewOrder(-number-1);
        }
    }

    public Tips(String text,Point point,Drawing d){
        super();
        drawing=d;
        this.relocate(point.getX(),point.getY());

        final ImageView selectedImage = new ImageView();
        Image background = new Image("Images/tipOfTheDay.jpg");
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
            Platform.runLater(()->drawing.getChildren().remove(this));
        }
        else if (isOnCLose(p)){
            Platform.runLater(()->drawing.getChildren().remove(this));
        }
        else if (isOnNext(p)){
            if (textNumber != -1 && itText+1<textStrings[textNumber].length){
                itText++;
                this.text.setText(textStrings[textNumber][itText]);
            }
        }
        else if (isOnPrevious(p)){
            if (textNumber != -1 && itText-1>=0){
                itText--;
                this.text.setText(textStrings[textNumber][itText]);
            }
        }
        else if(isOnShow(p)){
            if(itShow == 3){
                for(int i = 0;i < 8;i++){
                    notSeen[i]=false;
                }
                drawing.getChildren().remove(this);
            }
            else {
                this.text.setText(showStrings[itShow] + "\n" + (itShow + 1) + "/3)");
                itShow++;
            }
        }
    }
    private boolean isOnCloseButton(Point p){
        Point closeButton =  new Point(341,5);
        return p.distance(closeButton)<8;
    }

    private boolean isOnCLose(Point p){
        return (284<p.getX() && p.getX()<369 && p.getY()<155 && 134<p.getY());
    }

    private boolean isOnNext(Point p){
        return (214<p.getX() && p.getX()<276 && p.getY()<155 && 134<p.getY());
    }

    private boolean isOnPrevious(Point p){
        return (132<p.getX() && p.getX()<206 && p.getY()<155 && 134<p.getY());
    }

    private boolean isOnShow(Point p){
        Point show = new Point(17,144);
        return p.distance(show)<8;
}
    public void setText(String s){
        text.setText(s);
    }

}










































