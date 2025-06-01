package universite_paris8.iut.achandraprasad.saeprojet.vue;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import universite_paris8.iut.achandraprasad.saeprojet.controleur.touche.GestionTouches;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Personnage;

import java.security.Key;

public class PersonnageVue {

    private Personnage perso;
    private Pane panePrincipal;
    private ImageView imageViewPerso;
    private Image imageDroite;
    private Image imageGauche;
    private Image imageCourir;
    private Image imageCourirInverser;
    private Image imageCourir1;
    private Image imageCourirInverser1;
    private Image imageCourir2;
    private Image imageCourirInverser2;

    public PersonnageVue(Personnage perso, Pane panePrincipal) {
        this.perso = perso;
        this.panePrincipal = panePrincipal;

        // Charger les images
        this.imageDroite = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/persos/perso32.png").toExternalForm());
        this.imageGauche = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/persos/persoInverser32.png").toExternalForm());
        this.imageCourir = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/persos/persoCourir(32x20).png").toExternalForm());
        this.imageCourirInverser = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/persos/persoCourirInverser(32x20).png").toExternalForm());
        this.imageCourir1 = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/persos/persoCourir1(32x20).png").toExternalForm());
        this.imageCourirInverser1 = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/persos/persoCourirInverser1(32x20).png").toExternalForm());
        this.imageCourir2 = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/persos/persoCourir2(32x20).png").toExternalForm());
        this.imageCourirInverser2 = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/persos/persoCourirInverser2(32x20).png").toExternalForm());

        // Créer la vue du personnage
        créerVuePersonnage();

        // Configurer les contrôles quand la scène est disponible
        panePrincipal.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                GestionTouches.configurerTouches(newScene);
            }
        });
    }

    /**
     * Créer la vue du personnage (image)
     */
    private void créerVuePersonnage() {
        imageViewPerso = new ImageView(imageDroite);

        // Binding automatique des positions - Plus besoin de mettre à jour manuellement !
        imageViewPerso.translateXProperty().bind(perso.xProperty());
        imageViewPerso.translateYProperty().bind(perso.yProperty());

        panePrincipal.getChildren().add(imageViewPerso);
    }

    /**
     * Met à jour uniquement l'image du personnage en fonction de sa direction
     */
    public void mettreAJour() {
        switch (perso.getDirection()) {

            case 'D':
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.millis(10),
                                e -> imageViewPerso.setImage(imageCourir)),// Deuxième image après 100 ms
                        new KeyFrame(Duration.millis(100),
                                e -> imageViewPerso.setImage(imageCourir1)),
                        new KeyFrame(Duration.millis(30),
                                e -> imageViewPerso.setImage(imageCourir2))
                );
                timeline.setCycleCount(1); // Exécute une seule fois
                timeline.play();

                break;

            case 'G':
                Timeline timeline2 = new Timeline(
                        new KeyFrame(Duration.millis(10),
                                e -> imageViewPerso.setImage(imageCourirInverser)),// Deuxième image après 100 ms
                        new KeyFrame(Duration.millis(100),
                                e -> imageViewPerso.setImage(imageCourirInverser1)),
                        new KeyFrame(Duration.millis(30),
                                e -> imageViewPerso.setImage(imageCourirInverser2))
                );
                timeline2.setCycleCount(1); // Exécute une seule fois
                timeline2.play();
                break;

            case 'N':
                imageViewPerso.setImage(imageDroite);
                break;
        }
    }
}