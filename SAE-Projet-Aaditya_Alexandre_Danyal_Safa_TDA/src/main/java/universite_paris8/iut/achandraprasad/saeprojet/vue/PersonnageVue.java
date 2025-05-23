package universite_paris8.iut.achandraprasad.saeprojet.vue;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.achandraprasad.saeprojet.controleur.touche.GestionTouches;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Personnage;

public class PersonnageVue {

    private Personnage perso;
    private Pane panePrincipal;
    private ImageView imageViewPerso;
    private Image imageDroite;
    private Image imageGauche;

    public PersonnageVue(Personnage perso, Pane panePrincipal) {
        this.perso = perso;
        this.panePrincipal = panePrincipal;

        // Charger les images
        this.imageDroite = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/perso.png").toExternalForm());
        this.imageGauche = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/persoInverser.png").toExternalForm());

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
        // Mettre à jour l'image en fonction de la direction
        switch (perso.getDirection()) {
            case 'D':
                imageViewPerso.setImage(imageDroite);
                break;
            case 'G':
                imageViewPerso.setImage(imageGauche);
                break;
            // Pas de changement si direction = 'N' (neutre)
        }

        // Plus besoin de mettre à jour la position - c'est fait automatiquement par le binding !
    }
}
