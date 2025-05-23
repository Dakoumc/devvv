package universite_paris8.iut.achandraprasad.saeprojet.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import universite_paris8.iut.achandraprasad.saeprojet.controleur.touche.GestionTouches;

import java.awt.*;
import java.util.List;

public class Personnage {

    // Utilisation d'IntegerProperty pour les positions
    private IntegerProperty xProperty = new SimpleIntegerProperty(50);
    private IntegerProperty yProperty = new SimpleIntegerProperty(235);

    private Terrain terrain;

    // Position initiale au sol
    private final int SOL_Y = 144;

    // Limites de la map
    private final int LIMITE_X_MIN = 0;
    private final int LIMITE_X_MAX = 311;
    private final int LIMITE_Y_MIN = 0;
    private final int LIMITE_Y_MAX = 214;

    // Paramètres physiques
    private double velociteY = 0;        // Vitesse verticale
    private final double GRAVITE = 0.8;  // Force de gravité
    private final double FORCE_SAUT = -8.0; // Force du saut (négatif car l'axe Y est vers le bas)

    // Vitesse de déplacement horizontal
    private final int vitesse = 2;

    // Direction actuelle (D = Droite, G = Gauche, N = Neutre)
    private char direction = 'N';

    // État du personnage
    private boolean auSol = true;


    public Personnage(Terrain terrain) {
        this.terrain = terrain;
    }

    public int getX() {
        return xProperty.get();
    }

    public int getY() {
        return yProperty.get();
    }

    public IntegerProperty xProperty() {
        return xProperty;
    }

    public IntegerProperty yProperty() {
        return yProperty;
    }

    /**
     * Met à jour la position du personnage en fonction des entrées et de la physique
     */
    public void update() {
        // Mettre à jour la direction depuis le gestionnaire de touches
        direction = GestionTouches.getDirection();

        // Déplacement horizontal
        if (direction == 'D') {
            deplacerDroite();
        } else if (direction == 'G') {
            deplacerGauche();
        }

        // Gestion du saut
        if (GestionTouches.doitSauter() && auSol) {
            sauter();
        }

        // Appliquer la gravité
        appliquerPhysique();
    }

    public void deplacerDroite() {
        int newX = xProperty.get() + vitesse;
        System.out.println("D" + yProperty.get());

        // Vérification des collisions horizontales (limite droite)
        if (newX+9 <= LIMITE_X_MAX && terrain.estAccessible(newX+9, yProperty.get()) && terrain.estAccessible(newX+9, yProperty.get()+15)) {
            xProperty.set(newX);
        }
    }

    public void deplacerGauche() {
        int newX = xProperty.get() - vitesse;


        // Vérification des collisions horizontales (limite gauche)
        if (newX >= LIMITE_X_MIN && terrain.estAccessible(newX, yProperty.get()) && terrain.estAccessible(newX, yProperty.get()+15)) {
            xProperty.set(newX);
        }
    }

    /**
     * Fait sauter le personnage en appliquant une force vers le haut
     */
    private void sauter() {
        if (auSol) {
            velociteY = FORCE_SAUT;
            auSol = false;
        }
    }

    /**
     * Applique la physique (gravité) au personnage et gère les collisions verticales
     */
    private void appliquerPhysique() {
        // Appliquer la gravité à la vitesse verticale
        velociteY += GRAVITE;

        // Mettre à jour la position Y avec la vitesse
        int newY = yProperty.get() + (int)velociteY;

        // Vérifier les collisions verticales
        if (newY >= SOL_Y) {
            // Collision avec le sol
            newY = SOL_Y;
            velociteY = 0;
            auSol = true;
        } else if (newY <= LIMITE_Y_MIN) {
            // Collision avec le plafond
            newY = LIMITE_Y_MIN;
            velociteY = 0;
        }

        // Vérifier les limites verticales de la map
        if (newY <= LIMITE_Y_MAX && newY >= LIMITE_Y_MIN) {
            yProperty.set(newY);
        }

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