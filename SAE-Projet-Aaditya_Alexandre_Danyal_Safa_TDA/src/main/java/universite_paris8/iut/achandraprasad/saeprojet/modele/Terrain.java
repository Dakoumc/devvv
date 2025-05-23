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
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 1, 1, 1},
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

    public int hauteur ()  {
        return this.codesTuiles[0].length;
    }

    public int codeTuile (int i, int j) {
        return this.codesTuiles[i][j];
    }


    public boolean estAccessible(int x, int y) {

        return this.codesTuiles[x / 16][y / 16] == 1;
    }


}   