package universite_paris8.iut.achandraprasad.saeprojet.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Personnage {
    private Personnage aelor;
    int nbCoeurs = 5;
    private int vieMax = nbCoeurs * 100;
    private int vieActuelle = 500;

    // Utilisation d'IntegerProperty pour les positions
    private IntegerProperty xProperty = new SimpleIntegerProperty(300);
    private IntegerProperty yProperty = new SimpleIntegerProperty(400);

    protected Terrain terrain;

    public final int GROUND_LEVEL = Terrain.TAILLE_TUILES * 10; // Niveau du sol à l'herbe

    // Limites de la map
    private final int LIMITE_X_MIN = 0;
    private final int LIMITE_Y_MIN = 0;
    private final int LIMITE_Y_MAX;
    private final int LIMITE_X_MAX;

    // Dimensions de collision du personnage
    public final int LARGEUR_COLLISION = 20;
    public final int HAUTEUR_COLLISION = 32;

    // Paramètres physiques
    private double velociteY = 0;        // Vitesse verticale
    private final double GRAVITE = 0.6;  // Force de gravité
    private final double FORCE_SAUT = -7.5; // Force du saut

    // Vitesse de déplacement horizontal
    private final int vitesse = 4;

    // Direction actuelle
    public char direction = 'N';

    // État du personnage
    private boolean auSol = false;

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
     */
    public abstract void update();

    public void deplacerDroite() {
        int newX = xProperty.get() + vitesse;
        int currentY = yProperty.get();

        // Vérification des limites
        boolean dansLimitesX = newX + LARGEUR_COLLISION <= LIMITE_X_MAX;

        if (dansLimitesX) {
            // Vérification des collisions horizontales
            int positionTestX = newX + LARGEUR_COLLISION - 1;
            boolean positionHauteAccessible = terrain.estAccessible(positionTestX, currentY);
            boolean positionBasseAccessible = terrain.estAccessible(positionTestX, currentY + HAUTEUR_COLLISION - 1);

            if (positionHauteAccessible && positionBasseAccessible) {
                xProperty.set(newX);
            }
        }
    }

    public void deplacerGauche() {
        int newX = xProperty.get() - vitesse;
        int currentY = yProperty.get();

        // Vérification des limites
        boolean dansLimitesX = newX >= LIMITE_X_MIN;

        if (dansLimitesX) {
            // Vérification des collisions horizontales
            boolean positionHauteAccessible = terrain.estAccessible(newX, currentY);
            boolean positionBasseAccessible = terrain.estAccessible(newX, currentY + HAUTEUR_COLLISION - 1);

            if (positionHauteAccessible && positionBasseAccessible) {
                xProperty.set(newX);
            }
        }
    }

    /**
     * Fait sauter le personnage en appliquant une force vers le haut
     */
    public void sauter() {
        if (auSol) {
            velociteY = FORCE_SAUT;
            auSol = false;
        }
    }

    /**
     * Applique la physique (gravité) au personnage et gère les collisions verticales
     */
    public void appliquerPhysique() {
        // Toujours appliquer la gravité d'abord
        velociteY += GRAVITE;

        // Calculer la nouvelle position Y
        int newY = yProperty.get() + (int)velociteY;
        int currentX = xProperty.get();

        // Vérifier les limites de la map
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

        boolean collision = false;

        if (velociteY > 0) {
            // Chute - vérifier collision avec le sol
            int basPersonnage = newY + HAUTEUR_COLLISION;

            boolean solGauche = !terrain.estAccessible(currentX, basPersonnage);
            boolean solDroite = !terrain.estAccessible(currentX + LARGEUR_COLLISION - 1, basPersonnage);

            if (solGauche || solDroite) {
                // Collision détectée
                int ligneSol = basPersonnage / Terrain.TAILLE_TUILES;
                newY = (ligneSol * Terrain.TAILLE_TUILES) - HAUTEUR_COLLISION;
                velociteY = 0;
                auSol = true;
                collision = true;
            }
        } else if (velociteY < 0) {
            // Saut - vérifier collision avec le plafond
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
            // Vérifier s'il y a encore du sol sous les pieds
            int basPersonnage = yProperty.get() + HAUTEUR_COLLISION + 1;
            boolean solGauche = !terrain.estAccessible(currentX, basPersonnage);
            boolean solDroite = !terrain.estAccessible(currentX + LARGEUR_COLLISION - 1, basPersonnage);

            boolean aSolSousLesPieds = solGauche || solDroite;
            if (!aSolSousLesPieds) {
                auSol = false; // Plus de sol, commencer à tomber
            }
        }

        // Appliquer la nouvelle position Y
        yProperty.set(newY);
    }

    /**
     * @return Vrai si le personnage est en l'air
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

    // Barre de vie : Personnage
    public int getVieMax() {
        return vieMax;
    }

    public void setVieMax(int vieMax) {
        this.vieMax = vieMax;
    }

    public int getVieActuelle() {
        return vieActuelle;
    }

    public int getNbCoeurs() {
        return nbCoeurs;
    }

    public void setVieActuelle(int vieActuelle) {
        this.vieActuelle = vieActuelle;
    }

    //private int pointsDeVie = 500;
    private boolean gameOver = false;

    public void subirDegats(int degats) {

        if(!gameOver) {

            vieActuelle-= degats;
            if (vieActuelle < 0) vieActuelle = 0;
            System.out.println("PV restants : " + vieActuelle);

            if (vieActuelle == 0 && !gameOver) {
                System.out.println("Game Over");
                gameOver = true;
            }
        }

    }

    public void soigner(int soins) {
        vieActuelle = vieActuelle + soins;
        if (vieActuelle > vieMax) {
            vieActuelle = vieMax;
        }
    }


    public void marcherSurBlocDanger() {

        int x = (getX() / terrain.TAILLE_TUILES + 1);
        int y = (getY() / terrain.TAILLE_TUILES + 1);

        // Vérifie que les coordonnées sont dans les limites du tableau
        if (y >= 0 && y < terrain.hauteur() && x >= 0 && x < terrain.largeur()) {
            int codeTuile = terrain.codeTuile(y, x);

            if (codeTuile == 4) {
                System.out.println("IL y a un bloc dangereux!");
                subirDegats(25);
            }
//            } else {
//                System.out.println("Le bloc est fiable!.");
//            }
        }
    }
}