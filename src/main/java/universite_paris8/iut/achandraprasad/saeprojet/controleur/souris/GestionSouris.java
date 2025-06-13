package universite_paris8.iut.achandraprasad.saeprojet.controleur.souris;

import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Aelor;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Terrain;
import universite_paris8.iut.achandraprasad.saeprojet.vue.PersonnageVue;
import universite_paris8.iut.achandraprasad.saeprojet.vue.TerrainVue;

public class GestionSouris {

    private static Terrain terrain;
    private static TerrainVue terrainVue;
    private static Pane camera;
    private static Aelor aelor;
    private static PersonnageVue aelorVue;

    /**
     * Configure les écouteurs de souris pour la scène
     * @param scene La scène sur laquelle ajouter les écouteurs
     * @param terrain L'instance du terrain
     * @param terrainVue L'instance de la vue du terrain
     * @param camera Le pane camera pour les coordonnées
     * @param aelor L'instance du personnage Aelor
     * @param aelorVue L'instance de la vue d'Aelor
     */
    public static void configurerSouris(Scene scene, Terrain terrain, TerrainVue terrainVue,
                                        Pane camera, Aelor aelor, PersonnageVue aelorVue) {
        GestionSouris.terrain = terrain;
        GestionSouris.terrainVue = terrainVue;
        GestionSouris.camera = camera;
        GestionSouris.aelor = aelor;
        GestionSouris.aelorVue = aelorVue;

        scene.setOnMouseClicked(evenement -> {
            // Coordonnées simples
            int sourisX = (int)(evenement.getSceneX() - camera.getTranslateX());
            int sourisY = (int)(evenement.getSceneY() - camera.getTranslateY());

            boolean clicGauche = evenement.getButton() == MouseButton.PRIMARY;
            boolean clicDroit = evenement.getButton() == MouseButton.SECONDARY;

            if (clicGauche) {
                // Casser bloc
                boolean blocCasse = aelor.casserBloc(sourisX, sourisY);
                if (blocCasse) {
                    terrainVue.rafraichirTerrain();
                }
            } else if (clicDroit) {
                // Poser bloc
                boolean blocPose = aelor.poserBloc(sourisX, sourisY);
                if (blocPose) {
                    terrainVue.rafraichirTerrain();
                }
            }
        });
    }
}