package universite_paris8.iut.achandraprasad.saeprojet.modele;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/*
    Cette classe définit la composition du terrain.
    C'est-à-dire quel type de tuile on trouve à quel endroit.
 */
public class Terrain {

    public List<Integer> tuiles = new ArrayList<Integer>();

    public static int TAILLE_TUILES = 16;

    private int[][] codesTuiles = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1},
            {3, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1},
            {2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3}
    };

/*
Ajout test pour faire marcher les collisions
    public int[][] recupCodesTuiles(int codeTuile) {
        for (int i = 0; i < TAILLE_TUILES; i++) {
            for (int j = 0; j < TAILLE_TUILES; j++) {
                if (codesTuiles[i][j] == codeTuile) {
                    tuiles.add(codeTuile);
                }
            }
        }
        return null;
    }
*/


    public int largeur ()  {
        return this.codesTuiles.length;
    }

    public int largeurEnPixels() {
        return this.largeur()*16;
    }

    public int hauteur ()  {
        return this.codesTuiles[0].length;
    }

    public int hauteurEnPixels() {
        return this.hauteur()*16;
    }



    public int codeTuile (int i, int j) {
        return this.codesTuiles[i][j];
    }


    public boolean estAccessible(int x, int y) {
        int ligne = y/16;
        int colonne = x/16;
        System.out.println("colonne"+colonne);
        System.out.println("ligne"+ligne);
        System.out.println(this.codesTuiles[ligne][colonne]);
        return this.codesTuiles[ligne][colonne] == 1;
    }


}