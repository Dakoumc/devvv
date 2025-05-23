package universite_paris8.iut.achandraprasad.saeprojet.controleur;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

import universite_paris8.iut.achandraprasad.saeprojet.modele.Personnage;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Terrain;
import universite_paris8.iut.achandraprasad.saeprojet.vue.PersonnageVue;
import universite_paris8.iut.achandraprasad.saeprojet.vue.TerrainVue;

public class Controleur implements Initializable {

    @FXML
    public TilePane tilepane;

    @FXML
    public Pane PanePrincipal;

    // GameLoop
    private Timeline gameLoop;

    // Éléments du jeu
    private Terrain terrain;
    private TerrainVue terrainVue;
    private Personnage perso;
    private PersonnageVue persoVue;

    /**
     * Initialise le contrôleur et les éléments du jeu
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialiser le terrain
        terrain = new Terrain();
        terrainVue = new TerrainVue(terrain, tilepane);

        // Initialiser le personnage
        perso = new Personnage(terrain);
        persoVue = new PersonnageVue(perso, PanePrincipal);

        // Initialiser et démarrer le GameLoop
        initGameLoop();
        gameLoop.play();
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
                    perso.update();

                    // Mettre à jour uniquement l'image du personnage (la position est automatique avec le binding)
                    persoVue.mettreAJour();

                    // Pour déboguer (à enlever en production)
                    // System.out.println("GameLoop: Direction=" + perso.getDirection() + ", Position=(" + perso.getX() + "," + perso.getY() + ")");
                }
        );

        gameLoop.getKeyFrames().add(kf);
    }
}