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

    public void créerTerrainVue(){
        Image ciel = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/ciel32.png").toExternalForm());
        Image herbe = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/herbe32.png").toExternalForm());
        Image terre = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/terre32.png").toExternalForm());
        Image Pierre = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/Pierre.png").toExternalForm());


        tilePane.setPrefSize(terrain.hauteur() * Terrain.TAILLE_TUILES , terrain.largeur() * Terrain.TAILLE_TUILES);

        for (int i = 0; i < terrain.largeur() ; i++){
            for(int j = 0; j < terrain.hauteur() ; j++){
                switch (terrain.codeTuile(i,j)) {
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
     * NOUVELLE MÉTHODE : Rafraîchit l'affichage du terrain après modification
     */
    public void rafraichirTerrain() {
        // Vider le contenu actuel
        tilePane.getChildren().clear();

        // Recréer le terrain
        créerTerrainVue();
    }
}