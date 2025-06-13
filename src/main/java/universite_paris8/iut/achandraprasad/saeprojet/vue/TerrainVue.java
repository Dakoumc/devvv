package universite_paris8.iut.achandraprasad.saeprojet.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Terrain;

public class TerrainVue {

    private Terrain terrain;
    private TilePane tilePane;

    public TerrainVue(Terrain terrain, TilePane tilePane) {
        this.terrain = terrain;
        this.tilePane = tilePane;
        this.créerTerrainVue();
    }

    public void créerTerrainVue() {
        // Charger les images des blocs
        String cheminBase = "/universite_paris8/iut/achandraprasad/saeprojet/blocs/";
        Image ciel = new Image(getClass().getResource(cheminBase + "ciel32.png").toExternalForm());
        Image herbe = new Image(getClass().getResource(cheminBase + "herbe32.png").toExternalForm());
        Image terre = new Image(getClass().getResource(cheminBase + "terre32.png").toExternalForm());
        Image Pierre = new Image(getClass().getResource(cheminBase + "Pierre.png").toExternalForm());

        int largeurTerrain = terrain.hauteur() * Terrain.TAILLE_TUILES;
        int hauteurTerrain = terrain.largeur() * Terrain.TAILLE_TUILES;
        tilePane.setPrefSize(largeurTerrain, hauteurTerrain);

        int largeurEnCases = terrain.largeur();
        int hauteurEnCases = terrain.hauteur();

        for (int i = 0; i < largeurEnCases; i++) {
            for (int j = 0; j < hauteurEnCases; j++) {
                int codeTuile = terrain.codeTuile(i, j);

                switch (codeTuile) {
                    case 1:
                        tilePane.getChildren().add(new ImageView(ciel));
                        break;
                    case 2:
                        tilePane.getChildren().add(new ImageView(herbe));
                        break;
                    case 3:
                        tilePane.getChildren().add(new ImageView(terre));
                        break;
                    case 4:
                        tilePane.getChildren().add(new ImageView(Pierre));
                        break;
                }
            }
        }
    }

    /**
     * Rafraîchit l'affichage du terrain après modification
     */
    public void rafraichirTerrain() {
        // Vider le contenu actuel
        tilePane.getChildren().clear();

        // Recréer le terrain
        créerTerrainVue();
    }
}