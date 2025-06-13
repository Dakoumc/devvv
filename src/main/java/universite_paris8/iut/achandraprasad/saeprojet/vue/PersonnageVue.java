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

    // Dernière direction non neutre
    private char dernierDirection = 'D';

    // Variables pour l'animation de course
    private int frameIndex = 0;
    private long lastUpdateTime = 0;
    private final long ANIMATION_DELAI = 150;

    public PersonnageVue(Personnage perso, Pane panePrincipal) {
        this.perso = perso;
        this.panePrincipal = panePrincipal;

        // Charger les images
        chargerImages();

        // Créer la vue du personnage
        creerVuePersonnage();

        // Écouter automatiquement les changements du modèle
        configurerListeners();

        // Configurer les contrôles quand la scène est disponible
        panePrincipal.sceneProperty().addListener((obs, oldScene, newScene) -> {
            boolean nouvelleSceneDisponible = newScene != null;
            if (nouvelleSceneDisponible) {
                GestionTouches.configurerTouches(newScene);
            }
        });
    }

    /**
     * Charge toutes les images du personnage
     */
    private void chargerImages() {
        String cheminBase = "/universite_paris8/iut/achandraprasad/saeprojet/persos/";

        this.imageDroite = new Image(getClass().getResource(cheminBase + "perso32.png").toExternalForm());
        this.imageGauche = new Image(getClass().getResource(cheminBase + "persoInverser32.png").toExternalForm());
        this.imageCourir = new Image(getClass().getResource(cheminBase + "persoCourir(32x20).png").toExternalForm());
        this.imageCourirInverser = new Image(getClass().getResource(cheminBase + "persoCourirInverser(32x20).png").toExternalForm());
        this.imageCourir1 = new Image(getClass().getResource(cheminBase + "persoCourir1(32x20).png").toExternalForm());
        this.imageCourirInverser1 = new Image(getClass().getResource(cheminBase + "persoCourirInverser1(32x20).png").toExternalForm());
        this.imageCourir2 = new Image(getClass().getResource(cheminBase + "persoCourir2(32x20).png").toExternalForm());
        this.imageCourirInverser2 = new Image(getClass().getResource(cheminBase + "persoCourirInverser2(32x20).png").toExternalForm());
    }

    /**
     * Créer la vue du personnage (image)
     */
    private void creerVuePersonnage() {
        imageViewPerso = new ImageView(imageDroite);

        // Binding automatique des positions
        imageViewPerso.translateXProperty().bind(perso.xProperty());
        imageViewPerso.translateYProperty().bind(perso.yProperty());

        panePrincipal.getChildren().add(imageViewPerso);
    }

    /**
     * Configure les listeners automatiques sur le modèle
     * La vue se met à jour automatiquement quand le modèle change !
     */
    private void configurerListeners() {
        // Écouter les changements de position pour déclencher l'animation
        perso.xProperty().addListener((observable, oldValue, newValue) -> {
            mettreAJourAnimation();
        });

        perso.yProperty().addListener((observable, oldValue, newValue) -> {
            mettreAJourAnimation();
        });
    }

    /**
     * Met à jour l'animation du personnage (appelée automatiquement)
     */
    private void mettreAJourAnimation() {
        char direction = perso.getDirection();
        long currentTime = System.currentTimeMillis();

        boolean directionDroite = direction == 'D';
        boolean directionGauche = direction == 'G';
        boolean directionNeutre = direction == 'N';

        if (directionDroite) {
            dernierDirection = 'D';

            boolean tempsPourNouvelleFrame = currentTime - lastUpdateTime >= ANIMATION_DELAI;
            if (tempsPourNouvelleFrame) {
                frameIndex = (frameIndex + 1) % 3;
                lastUpdateTime = currentTime;

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
        } else if (directionGauche) {
            dernierDirection = 'G';

            boolean tempsPourNouvelleFrame = currentTime - lastUpdateTime >= ANIMATION_DELAI;
            if (tempsPourNouvelleFrame) {
                frameIndex = (frameIndex + 1) % 3;
                lastUpdateTime = currentTime;

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
        } else if (directionNeutre) {
            frameIndex = 0;

            boolean derniereDirectionGauche = dernierDirection == 'G';
            if (derniereDirectionGauche) {
                imageViewPerso.setImage(imageGauche);
            } else {
                imageViewPerso.setImage(imageDroite);
            }
        }
    }

    /**
     * Méthode publique pour mise à jour manuelle si nécessaire
     * (mais maintenant elle n'est plus appelée grâce aux listeners automatiques)
     */
    public void mettreAJour() {
        mettreAJourAnimation();
    }
}