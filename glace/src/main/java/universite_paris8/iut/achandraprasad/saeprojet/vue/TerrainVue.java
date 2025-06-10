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

//    public void créerTerrainVue(){
//        Image ciel = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/ciel32.png").toExternalForm());
//        Image herbe = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/herbe32.png").toExternalForm());
//        Image terre = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/terre32.png").toExternalForm());
//
//        tilePane.setPrefSize(terrain.hauteur() * Terrain.TAILLE_TUILES , terrain.largeur() * Terrain.TAILLE_TUILES);
//
//        for (int i = 0; i < terrain.largeur() ; i++){
//            for(int j = 0; j < terrain.hauteur() ; j++){
//                switch (terrain.codeTuile(i,j)){
//                    case 1 :
//                        tilePane.getChildren().add(new ImageView(ciel));
//                        break;
//                    case 2 :
//                        tilePane.getChildren().add(new ImageView(herbe));
//                        break;
//                    case 3 :
//                        tilePane.getChildren().add(new ImageView(terre));
//                        break;
//                }
//            }
//        }
//    }

    public void créerTerrainVue(){
//        Image ciel = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/ciel32.png").toExternalForm());
//        Image herbe = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/herbe32.png").toExternalForm());
//        Image terre = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/terre32.png").toExternalForm());
////
        Image piqueGlace  = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/piqueGlace.png").toExternalForm());
        Image neige = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/neige.png").toExternalForm());
        Image solneige1 = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/solneige1.png").toExternalForm());
        Image tuieDonjon = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/tuieDonjon.png").toExternalForm());
        Image tuileMapGlace = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/tuileMapGlace.png").toExternalForm());
        Image blocGlaçons = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/blocGlaçons.png").toExternalForm());
        Image verreGlas = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/verreGlas.png").toExternalForm());
        Image solneige2 = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/solneige2.png").toExternalForm());
        Image piqueGlaceSol = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/blocs/piqueGlaceSol.png").toExternalForm());



        tilePane.setPrefSize(terrain.hauteur() * Terrain.TAILLE_TUILES , terrain.largeur() * Terrain.TAILLE_TUILES);

        for (int i = 0; i < terrain.largeur() ; i++){
            for(int j = 0; j < terrain.hauteur() ; j++){
                switch (terrain.codeTuile(i,j)){
                    case 6 :
                        tilePane.getChildren().add(new ImageView(piqueGlace));
                        break;
                    case 8 :
                        tilePane.getChildren().add(new ImageView(neige));
                        break;
                    case 9 :
                        tilePane.getChildren().add(new ImageView(solneige1));
                        break;
                    case 10 :
                        tilePane.getChildren().add(new ImageView(tuieDonjon));
                        break;
                    case 11 :
                        tilePane.getChildren().add(new ImageView(tuileMapGlace));
                        break;
                    case 13 :
                        tilePane.getChildren().add(new ImageView(blocGlaçons));
                        break;
                    case 14 :
                        tilePane.getChildren().add(new ImageView(verreGlas));
                        break;
                    case 15 :
                        tilePane.getChildren().add(new ImageView(solneige2));
                        break;
                    case 16 :
                        tilePane.getChildren().add(new ImageView(piqueGlaceSol));
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