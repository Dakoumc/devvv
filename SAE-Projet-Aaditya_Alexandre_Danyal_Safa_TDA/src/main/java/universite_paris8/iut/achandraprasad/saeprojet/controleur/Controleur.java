package universite_paris8.iut.achandraprasad.saeprojet.controleur;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import universite_paris8.iut.achandraprasad.saeprojet.controleur.souris.GestionSouris;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Aelor;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Ennemie;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Gobelin;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Terrain;
import universite_paris8.iut.achandraprasad.saeprojet.vue.AelorVue;
import universite_paris8.iut.achandraprasad.saeprojet.vue.EnnemieVue;
import universite_paris8.iut.achandraprasad.saeprojet.vue.TerrainVue;

public class Controleur implements Initializable {

    @FXML
    public TilePane tilepane;

    @FXML
    public Pane PanePrincipal;

    @FXML
    public Pane camera; // NOUVEAU: Le pane camera du FXML

    // GameLoop
    private Timeline gameLoop;

    // Éléments du jeu
    private Terrain terrain;
    private TerrainVue terrainVue;
    private Aelor aelor;
    private AelorVue aelorVue;

    private final List<Ennemie> ennemis = new ArrayList<>();

    private final List<EnnemieVue> ennemisVue = new ArrayList<>();

    /**
     * Initialise le contrôleur et les éléments du jeu
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialiser le terrain
        terrain = new Terrain();
        terrainVue = new TerrainVue(terrain, tilepane);

        // Initialiser le personnage Aelor
        aelor = new Aelor(terrain);
        aelorVue = new AelorVue(aelor, camera); // Dans camera, pas PanePrincipal

        Gobelin gobelin = new Gobelin(terrain, 600, 300); // coordonnées ± au centre

        EnnemieVue gobelinVue = new EnnemieVue(gobelin, camera);

        ennemis.add(gobelin);

        ennemisVue.add(gobelinVue);

        // MAGIC: Binding automatique de la caméra !
        configurerCamera();

        // Configurer la gestion de souris avec les références d'Aelor et sa vue
        PanePrincipal.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                // Passer les références d'Aelor et sa vue
                GestionSouris.configurerSouris(newScene, terrain, terrainVue, camera, aelor, aelorVue);
            }
        });

        // Initialiser et démarrer le GameLoop
        initGameLoop();
        gameLoop.play();
    }

    /**
     * Configure le binding automatique de la caméra
     */
    private void configurerCamera() {
        // La caméra suit le personnage automatiquement !

        /*

        camera.translateXProperty().bind(
                aelor.xProperty().multiply(-1).add(200/2)  // 600 = largeur de la fenêtre
        );

        */


        camera.translateYProperty().bind(
                aelor.yProperty().multiply(-0.7).add(50)  // 800 = hauteur de la fenêtre
        );
    }

    /**
     * Initialise le GameLoop pour les mises à jour régulières du jeu
     */
    private void initGameLoop() {
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        // Créer un KeyFrame qui s'exécute environ 60 fois par seconde (60 FPS)
        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.017), // environ 60 FPS (1/60 ≈ 0.017)
                event -> {
                    // Mettre à jour le modèle du personnage
                    aelor.update();

                    // Mettre à jour l'image du personnage
                    aelorVue.mettreAJour();

                    // Ennemis

                    for (Ennemie e : ennemis)   e.update();

                    for (EnnemieVue v : ennemisVue) v.mettreAJour();

                    // PLUS BESOIN de gérer la caméra manuellement !
                    // Le binding s'en occupe automatiquement ✨
                }
        );

        gameLoop.getKeyFrames().add(kf);
    }
}
