package universite_paris8.iut.achandraprasad.saeprojet.modele;

import universite_paris8.iut.achandraprasad.saeprojet.controleur.touche.GestionTouches;

/**
 * Classe représentant le personnage joueur Aelor
 * Étend la classe abstraite Personnage
 */
public class Aelor extends Personnage {

    private final int TYPE_BLOC_DEFAUT = 3; // Terre par défaut

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
     * Vérifie si on peut interagir avec un bloc à cette position
     */
    public boolean peutInteragir(int sourisX, int sourisY) {
        int joueurX = getX() + LARGEUR_COLLISION / 2;
        int joueurY = getY() + HAUTEUR_COLLISION / 2;

        // Distance simple
        int deltaX = sourisX - joueurX;
        int deltaY = sourisY - joueurY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance > 80) return false;

        // Conversion en grille
        int joueurColonne = joueurX / Terrain.TAILLE_TUILES;
        int joueurLigne = joueurY / Terrain.TAILLE_TUILES;
        int blocColonne = sourisX / Terrain.TAILLE_TUILES;
        int blocLigne = sourisY / Terrain.TAILLE_TUILES;

        // Adjacent mais pas diagonal
        return (joueurColonne == blocColonne && Math.abs(joueurLigne - blocLigne) == 1) ||
                (joueurLigne == blocLigne && Math.abs(joueurColonne - blocColonne) == 1);
    }

    /**
     * Casse un bloc si possible
     */
    public boolean casserBloc(int sourisX, int sourisY) {
        if (!peutInteragir(sourisX, sourisY)) return false;

        int typeCasse = terrain.casserBloc(sourisX, sourisY);
        if (typeCasse != 0) {
            return true;
        }
        return false;
    }

    /**
     * Pose un bloc si possible
     */
    public boolean poserBloc(int sourisX, int sourisY) {
        if (!peutInteragir(sourisX, sourisY)) return false;
        if (!terrain.estCaseVide(sourisX, sourisY)) return false;
        if (!terrain.aBlocSolideAdjacent(sourisX, sourisY)) return false;

        return terrain.poserBloc(sourisX, sourisY, getTypeBlocDefaut());
    }

    /**
     * Retourne le type de bloc par défaut à poser
     * @return Type de bloc par défaut
     */
    public int getTypeBlocDefaut() {
        return TYPE_BLOC_DEFAUT;
    }
}