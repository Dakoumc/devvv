package universite_paris8.iut.achandraprasad.saeprojet.controleur;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

import universite_paris8.iut.achandraprasad.saeprojet.controleur.souris.GestionSouris;
import universite_paris8.iut.achandraprasad.saeprojet.controleur.touche.GestionTouches;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Aelor;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Terrain;
import universite_paris8.iut.achandraprasad.saeprojet.vue.BarreDeVieVue;
import universite_paris8.iut.achandraprasad.saeprojet.vue.PersonnageVue;
import universite_paris8.iut.achandraprasad.saeprojet.vue.TerrainVue;

public class Controleur implements Initializable {

    @FXML public TilePane tilepane;
    @FXML public Pane PanePrincipal;
    @FXML public HBox vie;
    @FXML public Pane camera;

    private Timeline gameLoop;
    private Terrain terrain;
    private TerrainVue terrainVue;
    private Aelor aelor;
    private PersonnageVue aelorVue;
    private BarreDeVieVue barreDeVieVue;
    private InventaireControleur inventaireControleur;
    private Pane inventairePane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        terrain = new Terrain();
        terrainVue = new TerrainVue(terrain, tilepane);
        aelor = new Aelor(terrain);
        aelorVue = new PersonnageVue(aelor, camera);
        barreDeVieVue = new BarreDeVieVue(aelor, vie);

        // INVENTAIRE
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/inventaire.fxml"));
            inventairePane = loader.load();
            inventaireControleur = loader.getController();
            inventaireControleur.setInventaire(aelor.getInventaire());
            PanePrincipal.getChildren().add(inventairePane);
        } catch (Exception e) {
            e.printStackTrace();
        }

        configurerCamera();

        PanePrincipal.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                GestionSouris.configurerSouris(newScene, terrain, terrainVue, camera, aelor, aelorVue);
            }
        });

        initGameLoop();
        gameLoop.play();
    }

    private void configurerCamera() {
        camera.translateXProperty().bind(aelor.xProperty().multiply(-1).add(200));
        camera.translateYProperty().bind(aelor.yProperty().multiply(-0.7).add(50));
    }

    private void initGameLoop() {
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(Duration.seconds(0.017), event -> {
            aelor.update();

            boolean inventaireOuvert = GestionTouches.isInventaireOuvert();
            if (inventaireControleur.isVisible() != inventaireOuvert) {
                inventaireControleur.setVisible(inventaireOuvert);
            }
            if (inventaireControleur.isVisible()) {
                inventaireControleur.mettreAJour();
            }
        });

        gameLoop.getKeyFrames().add(kf);
    }
}