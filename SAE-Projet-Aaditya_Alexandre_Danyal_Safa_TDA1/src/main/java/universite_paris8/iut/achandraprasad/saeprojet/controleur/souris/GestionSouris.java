package universite_paris8.iut.achandraprasad.saeprojet.controleur.souris;

import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Terrain;
import universite_paris8.iut.achandraprasad.saeprojet.vue.TerrainVue;

public class GestionSouris {

    private static Terrain terrain;
    private static TerrainVue terrainVue;
    private static Pane camera;

    /**
     * Configure les écouteurs de souris pour la scène
     * @param scene La scène sur laquelle ajouter les écouteurs
     * @param terrain L'instance du terrain
     * @param terrainVue L'instance de la vue du terrain
     * @param camera Le pane camera pour les coordonnées
     */
    public static void configurerSouris(Scene scene, Terrain terrain, TerrainVue terrainVue, Pane camera) {
        GestionSouris.terrain = terrain;
        GestionSouris.terrainVue = terrainVue;
        GestionSouris.camera = camera;

        // Écouteur pour les clics de souris
        scene.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) { // Clic gauche
                casserBlocSouris(event);
            }
        });
    }

    /**
     * Casse un bloc à la position cliquée
     * @param event L'événement de clic de souris
     */
    private static void casserBlocSouris(MouseEvent event) {
        // Récupérer les coordonnées du clic
        double mouseX = event.getSceneX();
        double mouseY = event.getSceneY();

        // Ajuster les coordonnées en fonction de la position de la caméra
        double terrainX = mouseX - camera.getTranslateX();
        double terrainY = mouseY - camera.getTranslateY();

        // Convertir en coordonnées du terrain
        int x = (int) terrainX;
        int y = (int) terrainY;

        System.out.println("Clic détecté à: mouseX=" + mouseX + ", mouseY=" + mouseY);
        System.out.println("Position dans le terrain: x=" + x + ", y=" + y);

        // Essayer de casser le bloc
        if (terrain.casserBloc(x, y)) {
            // Rafraîchir l'affichage du terrain
            terrainVue.rafraichirTerrain();
            System.out.println("Bloc cassé avec succès !");
        } else {
            System.out.println("Impossible de casser ce bloc.");
        }
    }
}