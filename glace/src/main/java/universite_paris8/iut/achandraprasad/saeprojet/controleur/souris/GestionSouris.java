package universite_paris8.iut.achandraprasad.saeprojet.controleur.souris;

import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Aelor;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Personnage;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Terrain;
import universite_paris8.iut.achandraprasad.saeprojet.vue.AelorVue;
import universite_paris8.iut.achandraprasad.saeprojet.vue.TerrainVue;

import java.awt.Point;
import java.util.List;

public class GestionSouris {

    private static Terrain terrain;
    private static TerrainVue terrainVue;
    private static Pane camera;
    private static Aelor aelor;
    private static AelorVue aelorVue;

    /**
     * Configure les écouteurs de souris pour la scène
     * @param scene La scène sur laquelle ajouter les écouteurs
     * @param terrain L'instance du terrain
     * @param terrainVue L'instance de la vue du terrain
     * @param camera Le pane camera pour les coordonnées
     * @param aelor L'instance du personnage Aelor
     * @param aelorVue L'instance de la vue d'Aelor
     */
    public static void configurerSouris(Scene scene, Terrain terrain, TerrainVue terrainVue, Pane camera, Aelor aelor, AelorVue aelorVue) {
        GestionSouris.terrain = terrain;
        GestionSouris.terrainVue = terrainVue;
        GestionSouris.camera = camera;
        GestionSouris.aelor = aelor;
        GestionSouris.aelorVue = aelorVue;

        // Écouteur pour les clics de souris
        scene.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) { // Clic gauche
                casserBlocSouris(event);
            }
        });

        // L'écouteur pour le mouvement de la souris a été supprimé car la fonctionnalité de mise en évidence des blocs a été retirée
    }

    /**
     * Casse un bloc à la position cliquée si il est à portée du joueur
     * @param event L'événement de clic de souris
     */
    private static void casserBlocSouris(MouseEvent event) {
        // Récupérer les coordonnées du clic
        double mouseX = event.getSceneX();
        double mouseY = event.getSceneY();

        // Ajuster les coordonnées en fonction de la position de la caméra
        double terrainX = mouseX - camera.getTranslateX();
        double terrainY = mouseY - camera.getTranslateY();


        // Obtenir le meilleur bloc à casser
        Point blocACasser = aelor.obtenirMeilleurBlocACasser((int)terrainX, (int)terrainY);

        if (blocACasser == null) {
            return;
        }

        int blocX = blocACasser.x;
        int blocY = blocACasser.y;

        // Essayer de casser le bloc
        if (terrain.casserBloc(blocX, blocY)) {
            // Rafraîchir l'affichage du terrain
            terrainVue.rafraichirTerrain();
        }
    }

    /**
     * Vérifie si le bloc est cassable par le joueur en utilisant le système amélioré
     * @param blocX Position X du bloc à casser (en pixels)
     * @param blocY Position Y du bloc à casser (en pixels)
     * @return true si le bloc est cassable, false sinon
     */
    private static boolean estBlocCassable(int blocX, int blocY) {
        return aelor.peutCasserBloc(blocX, blocY);
    }

    /**
     * Méthode utilitaire pour debug - affiche les blocs cassables
     */
    public static void afficherBlocsCassables() {
        List<Point> blocsCassables = aelor.obtenirBlocsCassables();

        for (Point bloc : blocsCassables) {
            int blocColonne = bloc.x / Terrain.TAILLE_TUILES;
            int blocLigne = bloc.y / Terrain.TAILLE_TUILES;
        }
    }
}
