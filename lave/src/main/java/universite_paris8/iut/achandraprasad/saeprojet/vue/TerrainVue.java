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
//        Image ciel = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/ciel32.png").toExternalForm());
//        Image herbe = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/herbe32.png").toExternalForm());
//        Image terre = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/terre32.png").toExternalForm());
        Image lave = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/lave.png").toExternalForm());
        Image rocheLave = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/rocheLave.png").toExternalForm());
        Image rocherLave = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/rocherLave.png").toExternalForm());
        Image terreCuite = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/terreCuite.png").toExternalForm());
        Image tuieDonjon = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/tuieDonjon.png").toExternalForm());
        Image tuileDonjon = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/tuileDonjon.png").toExternalForm());
        Image terreSombre = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/terreSombre.png").toExternalForm());
        Image grandLave = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/grandLave.png").toExternalForm());

        tilePane.setPrefSize(terrain.hauteur() * Terrain.TAILLE_TUILES , terrain.largeur() * Terrain.TAILLE_TUILES);

        for (int i = 0; i < terrain.largeur() ; i++){
            for(int j = 0; j < terrain.hauteur() ; j++){
                switch (terrain.codeTuile(i,j)){
                    case 1 :
                        tilePane.getChildren().add(new ImageView(tuieDonjon));
                        break;
                    case 2 :
                        tilePane.getChildren().add(new ImageView(terreCuite));
                        break;
                    case 3 :
                        tilePane.getChildren().add(new ImageView(tuileDonjon));
                        break;
                    case 4 :
                        tilePane.getChildren().add(new ImageView(terreSombre));
                        break;
                    case 5 :
                        tilePane.getChildren().add(new ImageView(rocheLave));
                        break;
                    case 6 :
                        tilePane.getChildren().add(new ImageView(lave));
                        break;
                    case 7 :
                        tilePane.getChildren().add(new ImageView(grandLave));
                        break;
                    case 8 :
                        tilePane.getChildren().add(new ImageView(rocherLave));
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