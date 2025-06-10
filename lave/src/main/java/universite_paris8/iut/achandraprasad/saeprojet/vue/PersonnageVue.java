package universite_paris8.iut.achandraprasad.saeprojet.vue;

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
    private Image imageCourir;
    private Image imageCourirInverser;
    private Image imageCourir1;
    private Image imageCourirInverser1;
    private Image imageCourir2;
    private Image imageCourirInverser2;

    // Dernière direction non neutre (D ou G)
    private char dernierDirection = 'D';

    // Variables pour l'animation de course
    private int frameIndex = 0;
    private long lastUpdateTime = 0;
    private final long ANIMATION_DELAI = 150; // Délai entre les frames en millisecondes

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
        char direction = perso.getDirection();
        long currentTime = System.currentTimeMillis();

        switch (direction) {
            case 'D':
                // Mettre à jour la dernière direction
                dernierDirection = 'D';

                // Vérifier si c'est le moment de mettre à jour l'animation
                if (currentTime - lastUpdateTime >= ANIMATION_DELAI) {
                    // Mettre à jour l'index de frame et le temps
                    frameIndex = (frameIndex + 1) % 3;
                    lastUpdateTime = currentTime;

                    // Choisir l'image en fonction de l'index de frame
                    switch (frameIndex) {
                        case 0:
                            imageViewPerso.setImage(imageCourir);
                            break;
                        case 1:
                            imageViewPerso.setImage(imageCourir1);
                            break;
                        case 2:
                            imageViewPerso.setImage(imageCourir2);
                            break;
                    }
                }
                break;

            case 'G':
                // Mettre à jour la dernière direction
                dernierDirection = 'G';

                // Vérifier si c'est le moment de mettre à jour l'animation
                if (currentTime - lastUpdateTime >= ANIMATION_DELAI) {
                    // Mettre à jour l'index de frame et le temps
                    frameIndex = (frameIndex + 1) % 3;
                    lastUpdateTime = currentTime;

                    // Choisir l'image en fonction de l'index de frame
                    switch (frameIndex) {
                        case 0:
                            imageViewPerso.setImage(imageCourirInverser);
                            break;
                        case 1:
                            imageViewPerso.setImage(imageCourirInverser1);
                            break;
                        case 2:
                            imageViewPerso.setImage(imageCourirInverser2);
                            break;
                    }
                }
                break;

            case 'N':
                // Réinitialiser l'index de frame quand on s'arrête
                frameIndex = 0;

                // Utiliser la dernière direction pour déterminer quelle image afficher
                if (dernierDirection == 'G') {
                    imageViewPerso.setImage(imageGauche);
                } else {
                    imageViewPerso.setImage(imageDroite);
                }
                break;
        }
    }
}
