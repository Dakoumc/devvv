package universite_paris8.iut.achandraprasad.saeprojet.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Camera;
import universite_paris8.iut.achandraprasad.saeprojet.vue.PersonnageVue;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.util.List;

public abstract class Personnage {

    // Utilisation d'IntegerProperty pour les positions
    private IntegerProperty xProperty = new SimpleIntegerProperty(300);
    private IntegerProperty yProperty = new SimpleIntegerProperty(400); // Spawn en l'air pour tester la gravité

    protected Terrain terrain;

    public final int GROUND_LEVEL = Terrain.TAILLE_TUILES * 10; // Niveau du sol à l'herbe (ligne 10)

    // Limites de la map
    private final int LIMITE_X_MIN = 0;

    private final int LIMITE_Y_MIN = 0;

    private final int LIMITE_Y_MAX;

    private final int LIMITE_X_MAX;

    // Dimensions de collision du personnage
    protected final int LARGEUR_COLLISION = 20;
    protected final int HAUTEUR_COLLISION = 32;

    // Paramètres physiques
    protected double velociteY = 0;        // Vitesse verticale
    protected final double GRAVITE = 0.6;  // Force de gravité
    protected final double FORCE_SAUT = -10; // Force du saut (négatif car l'axe Y est vers le bas)

    // Vitesse de déplacement horizontal
    protected final int vitesse = 4;

    // Direction actuelle (D = Droite, G = Gauche, N = Neutre)
    protected char direction = 'N';

    // État du personnage
    protected boolean auSol = false; // Commence en l'air

    public Personnage(Terrain terrain) {
        this.terrain = terrain;
        this.xProperty = xProperty();
        this.yProperty = yProperty();
        this.LIMITE_X_MAX = terrain.hauteurEnPixels();
        this.LIMITE_Y_MAX = terrain.largeurEnPixels();
    }

    public int getX() {
        return xProperty.get();
    }

    public int getY() {
        return yProperty.get();
    }

    public void setxProperty(int xProperty) {
        this.xProperty.set(xProperty);
    }

    public void setyProperty(int yProperty) {
        this.yProperty.set(yProperty);
    }

    public IntegerProperty xProperty() {
        return xProperty;
    }

    public IntegerProperty yProperty() {
        return yProperty;
    }

    /**
     * Met à jour la position du personnage en fonction des entrées et de la physique
     * Cette méthode doit être implémentée par les classes dérivées
     */
    public abstract void update();

    public void deplacerDroite() {
        int newX = xProperty.get() + vitesse;
        int currentY = yProperty.get();

        // Vérification des collisions horizontales (limite droite)
        if (newX + LARGEUR_COLLISION <= LIMITE_X_MAX &&
                terrain.estAccessible(newX + LARGEUR_COLLISION - 1, currentY) &&
                terrain.estAccessible(newX + LARGEUR_COLLISION - 1, currentY + HAUTEUR_COLLISION - 1)) {
            xProperty.set(newX);
        }
    }

    public void deplacerGauche() {
        int newX = xProperty.get() - vitesse;
        int currentY = yProperty.get();

        // Vérification des collisions horizontales (limite gauche)
        if (newX >= LIMITE_X_MIN &&
                terrain.estAccessible(newX, currentY) &&
                terrain.estAccessible(newX, currentY + HAUTEUR_COLLISION - 1)) {
            xProperty.set(newX);
        }
    }

    /**
     * Fait sauter le personnage en appliquant une force vers le haut
     */
    protected void sauter() {
        if (auSol) {
            velociteY = FORCE_SAUT;
            auSol = false;
        }
    }

    /**
     * Applique la physique (gravité) au personnage et gère les collisions verticales
     */
    public void appliquerPhysique() {
        // CORRECTION: Toujours appliquer la gravité d'abord
        velociteY += GRAVITE;


        // Calculer la nouvelle position Y
        int newY = yProperty.get() + (int)velociteY;
        int currentX = xProperty.get();


        // CORRECTION: Vérifier d'abord les limites de la map
        if (newY >= LIMITE_Y_MAX) {
            newY = LIMITE_Y_MAX;
            velociteY = 0;
            auSol = true;
            yProperty.set(newY);
            return;
        }


        if (newY <= LIMITE_Y_MIN) {
            newY = LIMITE_Y_MIN;
            velociteY = 0;
            yProperty.set(newY);
            return;
        }


        // Variables pour détecter les collisions
        boolean collision = false;


        if (velociteY > 0) {

            // Chute - vérifier collision avec le sol
            // CORRECTION : Vérifier le bas du personnage
            int basPersonnage = newY + HAUTEUR_COLLISION;


            // Vérifier les deux coins inférieurs du personnage
            boolean solGauche = !terrain.estAccessible(currentX, basPersonnage);
            boolean solDroite = !terrain.estAccessible(currentX + LARGEUR_COLLISION - 1, basPersonnage);


            if (solGauche || solDroite) {
                // Collision détectée - ajuster la position
                int ligneSol = basPersonnage / Terrain.TAILLE_TUILES;
                newY = (ligneSol * Terrain.TAILLE_TUILES) - HAUTEUR_COLLISION;
                velociteY = 0;
                auSol = true;
                collision = true;
            }
        } else if (velociteY < 0) {
            // Saut - vérifier collision avec le plafond
            // CORRECTION : Vérifier le haut du personnage
            boolean plafondGauche = !terrain.estAccessible(currentX, newY);
            boolean plafondDroite = !terrain.estAccessible(currentX + LARGEUR_COLLISION - 1, newY);


            if (plafondGauche || plafondDroite) {
                // Collision avec le plafond
                int lignePlafond = newY / Terrain.TAILLE_TUILES;
                newY = (lignePlafond + 1) * Terrain.TAILLE_TUILES;
                velociteY = 0;
                collision = true;
            }
        }


        // Si pas de collision, on n'est plus au sol
        if (!collision && auSol) {
            // CORRECTION: Vérifier s'il y a encore du sol sous les pieds
            int basPersonnage = yProperty.get() + HAUTEUR_COLLISION + 1; // +1 pour vérifier juste en dessous
            boolean solGauche = !terrain.estAccessible(currentX, basPersonnage);
            boolean solDroite = !terrain.estAccessible(currentX + LARGEUR_COLLISION - 1, basPersonnage);


            if (!solGauche && !solDroite) {
                auSol = false; // Plus de sol, commencer à tomber
            }
        }


        // Appliquer la nouvelle position Y
        yProperty.set(newY);
    }


    /**
     * @return Vrai si le personnage est en l'air (en train de sauter)
     */
    public boolean isEnAir() {
        return !auSol;
    }


    /**
     * @return La direction actuelle du personnage
     */
    public char getDirection() {
        return direction;
    }
}
