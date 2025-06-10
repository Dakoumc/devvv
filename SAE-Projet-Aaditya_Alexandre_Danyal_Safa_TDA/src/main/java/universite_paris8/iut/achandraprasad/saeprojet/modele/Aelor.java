package universite_paris8.iut.achandraprasad.saeprojet.modele;

import universite_paris8.iut.achandraprasad.saeprojet.controleur.touche.GestionTouches;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant le personnage joueur Aelor
 * Étend la classe abstraite Personnage
 */
public class Aelor extends Personnage {

    // Rayon de détection pour casser des blocs (en pixels)
    private final int RAYON_CASSAGE = 48;

    /**
     * Constructeur
     *
     * @param terrain Le terrain du jeu
     */
    public Aelor(Terrain terrain) {
        super(terrain);
    }

    /**
     * Met à jour la position du personnage en fonction des entrées et de la physique
     */
    @Override
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
        if (GestionTouches.doitSauter() && !isEnAir()) {
            sauter();
        }

        // Appliquer la gravité
        appliquerPhysique();
    }

    /**
     * Vérifie si un bloc est cassable par le joueur
     * Amélioration de la détection de proximité
     *
     * @param blocX Position X du bloc en pixels
     * @param blocY Position Y du bloc en pixels
     * @return true si le bloc est cassable, false sinon
     */
    public boolean peutCasserBloc(int blocX, int blocY) {
        // Convertir en coordonnées de grille
        int blocColonne = blocX / Terrain.TAILLE_TUILES;
        int blocLigne = blocY / Terrain.TAILLE_TUILES;

        // Position du joueur en coordonnées de grille
        int joueurColonne = (getX() + LARGEUR_COLLISION / 2) / Terrain.TAILLE_TUILES;
        int joueurLigne = (getY() + HAUTEUR_COLLISION / 2) / Terrain.TAILLE_TUILES;

        // Vérifier si le bloc est adjacent (haut, bas, gauche, droite) mais pas en diagonale
        boolean estAdjacent = (
                // Même colonne, ligne adjacente (haut ou bas)
                (blocColonne == joueurColonne && Math.abs(blocLigne - joueurLigne) == 1) ||
                        // Même ligne, colonne adjacente (gauche ou droite)
                        (blocLigne == joueurLigne && Math.abs(blocColonne - joueurColonne) == 1)
        );

        // Vérifier si le bloc est à portée et adjacent (pas en diagonale)
        return estAdjacent;
    }

    /**
     * Retourne la liste des blocs cassables à proximité du joueur
     *
     * @return Liste des coordonnées des blocs cassables
     */
    public List<Point> obtenirBlocsCassables() {
        List<Point> blocsCassables = new ArrayList<>();

        // Position du joueur en coordonnées de grille
        int joueurColonne = (getX() + LARGEUR_COLLISION / 2) / Terrain.TAILLE_TUILES;
        int joueurLigne = (getY() + HAUTEUR_COLLISION / 2) / Terrain.TAILLE_TUILES;

        // Vérifier les 4 blocs adjacents (haut, bas, gauche, droite)
        // Positions relatives des blocs adjacents
        int[][] directions = {
                {0, -1}, // Haut
                {0, 1},  // Bas
                {-1, 0}, // Gauche
                {1, 0}   // Droite
        };

        for (int[] dir : directions) {
            int i = joueurLigne + dir[1];
            int j = joueurColonne + dir[0];

            // Vérifier les limites du terrain
            if (i >= 0 && i < terrain.largeur() && j >= 0 && j < terrain.hauteur()) {
                // Vérifier si le bloc est cassable (herbe ou terre)
                int codeTuile = terrain.codeTuile(i, j);
                if (codeTuile != 1) { // Si ce n'est pas du ciel
                    // Ajouter le bloc à la liste des blocs cassables
                    blocsCassables.add(new Point(j * Terrain.TAILLE_TUILES, i * Terrain.TAILLE_TUILES));
                }
            }
        }

        return blocsCassables;
    }

    /**
     * Détermine le meilleur bloc à casser en fonction de la position de la souris
     *
     * @param mouseX Position X de la souris
     * @param mouseY Position Y de la souris
     * @return Coordonnées du bloc à casser, ou null si aucun bloc n'est cassable
     */
    public Point obtenirMeilleurBlocACasser(int mouseX, int mouseY) {
        List<Point> blocsCassables = obtenirBlocsCassables();

        if (blocsCassables.isEmpty()) {
            return null;
        }

        // 🆕 VÉRIFICATION : Le clic est-il près d'Aelor ?
        int aelorCentreX = getX() + LARGEUR_COLLISION / 2;
        int aelorCentreY = getY() + HAUTEUR_COLLISION / 2;

        int deltaXAelor = mouseX - aelorCentreX;
        int deltaYAelor = mouseY - aelorCentreY;
        double distanceAelor = Math.sqrt(deltaXAelor * deltaXAelor + deltaYAelor * deltaYAelor);

        final double ZONE_CLIC_MAX = 80.0; // Rayon autour d'Aelor

        if (distanceAelor > ZONE_CLIC_MAX) {
            return null;
        }


        // 🆕 Si le clic est dans la zone d'Aelor, continuer normalement
        Point meilleurBloc = null;
        double distanceMin = Double.MAX_VALUE;

        for (Point bloc : blocsCassables) {
            int centreBlocX = bloc.x + Terrain.TAILLE_TUILES / 2;
            int centreBlocY = bloc.y + Terrain.TAILLE_TUILES / 2;

            int deltaX = mouseX - centreBlocX;
            int deltaY = mouseY - centreBlocY;
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            if (distance < distanceMin) {
                distanceMin = distance;
                meilleurBloc = bloc;
            }
        }

        return meilleurBloc;
    }

    // Constante pour le type de bloc par défaut à poser
    private final int TYPE_BLOC_DEFAUT = 3; // Terre par défaut

    /**
     * Vérifie si un bloc peut être posé à la position donnée
     * @param blocX Position X du bloc en pixels
     * @param blocY Position Y du bloc en pixels
     * @return true si un bloc peut être posé, false sinon
     */
    public boolean peutPoserBloc(int blocX, int blocY) {
        // Convertir en coordonnées de grille
        int blocColonne = blocX / Terrain.TAILLE_TUILES;
        int blocLigne = blocY / Terrain.TAILLE_TUILES;

        // Position du joueur en coordonnées de grille
        int joueurColonne = (getX() + LARGEUR_COLLISION / 2) / Terrain.TAILLE_TUILES;
        int joueurLigne = (getY() + HAUTEUR_COLLISION / 2) / Terrain.TAILLE_TUILES;

        // Vérifier si le bloc est adjacent (haut, bas, gauche, droite) mais pas en diagonale
        boolean estAdjacent = (
                // Même colonne, ligne adjacente (haut ou bas)
                (blocColonne == joueurColonne && Math.abs(blocLigne - joueurLigne) == 1) ||
                        // Même ligne, colonne adjacente (gauche ou droite)
                        (blocLigne == joueurLigne && Math.abs(blocColonne - joueurColonne) == 1)
        );

        // Vérifier si l'emplacement est vide (ciel) ET s'il a un support solide
        boolean espaceVide = terrain.estCaseVide(blocX, blocY);
        boolean aSupportSolide = terrain.aBlocSolideAdjacent(blocX, blocY);

        return estAdjacent && espaceVide && aSupportSolide;
    }

    /**
     * Vérifie si un clic est dans la zone valide autour d'Aelor
     * @param mouseX Position X de la souris
     * @param mouseY Position Y de la souris
     * @return true si le clic est dans la zone valide, false sinon
     */
    public boolean estDansZoneDeClicValide(int mouseX, int mouseY) {
        // Vérifier si le clic est à portée d'Aelor
        int aelorCentreX = getX() + LARGEUR_COLLISION / 2;
        int aelorCentreY = getY() + HAUTEUR_COLLISION / 2;

        int deltaX = mouseX - aelorCentreX;
        int deltaY = mouseY - aelorCentreY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        final double ZONE_CLIC_MAX = 40.0; // Même rayon que pour casser des blocs

        return distance <= ZONE_CLIC_MAX;
    }

    /**
     * Retourne la liste des emplacements où un bloc peut être posé
     * @return Liste des coordonnées des emplacements posables
     */
    public List<Point> obtenirEmplacementsPosables() {
        List<Point> emplacementsPosables = new ArrayList<>();

        // Position du joueur en coordonnées de grille
        int joueurColonne = (getX() + LARGEUR_COLLISION / 2) / Terrain.TAILLE_TUILES;
        int joueurLigne = (getY() + HAUTEUR_COLLISION / 2) / Terrain.TAILLE_TUILES;

        // Vérifier les 4 positions adjacentes (haut, bas, gauche, droite)
        int[][] directions = {
                {0, -1}, // Haut
                {0, 1},  // Bas
                {-1, 0}, // Gauche
                {1, 0}   // Droite
        };

        for (int[] dir : directions) {
            int i = joueurLigne + dir[1];
            int j = joueurColonne + dir[0];

            // Vérifier les limites du terrain
            if (i >= 0 && i < terrain.largeur() && j >= 0 && j < terrain.hauteur()) {
                // Vérifier si l'emplacement est vide (ciel)
                int codeTuile = terrain.codeTuile(i, j);
                if (codeTuile == 1) { // Si c'est du ciel
                    Point position = new Point(j * Terrain.TAILLE_TUILES, i * Terrain.TAILLE_TUILES);

                    // Vérifier si la position a un support solide
                    if (terrain.aBlocSolideAdjacent(position.x, position.y)) {
                        // Ajouter l'emplacement à la liste des emplacements posables
                        emplacementsPosables.add(position);
                    }
                }
            }
        }

        return emplacementsPosables;
    }

    /**
     * Affiche les emplacements posables avec support pour le débogage
     */
    public void afficherEmplacementsPosablesAvecSupport() {
        List<Point> emplacements = obtenirEmplacementsPosables();
        // Méthode vidée des System.out.println
    }

    /**
     * Détermine le meilleur emplacement pour poser un bloc en fonction de la position de la souris
     * @param mouseX Position X de la souris
     * @param mouseY Position Y de la souris
     * @return Coordonnées de l'emplacement pour poser un bloc, ou null si aucun emplacement n'est valide
     */
    public Point obtenirMeilleurEmplacementPose(int mouseX, int mouseY) {
        List<Point> emplacementsPosables = obtenirEmplacementsPosables();

        if (emplacementsPosables.isEmpty()) {
            return null;
        }

        // Vérification : Le clic est-il près d'Aelor ?
        int aelorCentreX = getX() + LARGEUR_COLLISION / 2;
        int aelorCentreY = getY() + HAUTEUR_COLLISION / 2;

        int deltaXAelor = mouseX - aelorCentreX;
        int deltaYAelor = mouseY - aelorCentreY;
        double distanceAelor = Math.sqrt(deltaXAelor * deltaXAelor + deltaYAelor * deltaYAelor);

        final double ZONE_CLIC_MAX = 80.0; // Même rayon que pour casser des blocs

        if (distanceAelor > ZONE_CLIC_MAX) {
            return null;
        }

        // Si le clic est dans la zone d'Aelor, trouver l'emplacement le plus proche du clic
        Point meilleurEmplacement = null;
        double distanceMin = Double.MAX_VALUE;

        for (Point emplacement : emplacementsPosables) {
            int centreEmplacementX = emplacement.x + Terrain.TAILLE_TUILES / 2;
            int centreEmplacementY = emplacement.y + Terrain.TAILLE_TUILES / 2;

            int deltaX = mouseX - centreEmplacementX;
            int deltaY = mouseY - centreEmplacementY;
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            if (distance < distanceMin) {
                distanceMin = distance;
                meilleurEmplacement = emplacement;
            }
        }

        return meilleurEmplacement;
    }

    /**
     * Retourne le type de bloc par défaut à poser
     * @return Type de bloc par défaut
     */
    public int getTypeBlocDefaut() {
        return TYPE_BLOC_DEFAUT;
    }
}