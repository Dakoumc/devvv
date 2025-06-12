package universite_paris8.iut.achandraprasad.saeprojet.controleur;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

import universite_paris8.iut.achandraprasad.saeprojet.controleur.souris.GestionSouris;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Aelor;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Terrain;
import universite_paris8.iut.achandraprasad.saeprojet.vue.BarreDeVieVue;
import universite_paris8.iut.achandraprasad.saeprojet.vue.PersonnageVue;
import universite_paris8.iut.achandraprasad.saeprojet.vue.TerrainVue;

public class Controleur implements Initializable {

    @FXML
    public TilePane tilepane;

    @FXML
    public Pane PanePrincipal;

    @FXML
    public HBox vie;

    @FXML
    public Pane camera;

    // GameLoop
    private Timeline gameLoop;

    // Éléments du jeu
    private Terrain terrain;
    private TerrainVue terrainVue;
    private Aelor aelor;
    private PersonnageVue aelorVue;
    private BarreDeVieVue barreDeVieVue;

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

        aelorVue = new PersonnageVue(aelor,camera);

        barreDeVieVue = new BarreDeVieVue(aelor,vie);


        // MAGIC: Binding automatique de la caméra !
        configurerCamera();

        // Configurer la gestion de souris
        PanePrincipal.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
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
        camera.translateYProperty().bind(
                aelor.yProperty().multiply(-0.7).add(50)
        );
    }

    /**
     * Initialise le GameLoop pour les mises à jour régulières du jeu
     */
    private void initGameLoop() {
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.017), // environ 60 FPS
                event -> {
                    // SEUL le modèle est mis à jour ici (principe MVC respecté)
                    aelor.update();
                    // La vue se met à jour automatiquement grâce aux bindings !
                }
        );

        gameLoop.getKeyFrames().add(kf);
    }
}