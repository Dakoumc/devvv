package universite_paris8.iut.achandraprasad.saeprojet.modele;

import universite_paris8.iut.achandraprasad.saeprojet.controleur.touche.GestionTouches;

/**
 * Classe représentant le personnage joueur Aelor
 * Étend la classe abstraite Personnage
 */
public class Aelor extends Personnage {

    private Inventaire inventaire;

    /**
     * Constructeur
     */
    public Aelor(Terrain terrain) {
        super(terrain);
        this.inventaire = new Inventaire();
    }

    /**
     * Met à jour la position du personnage
     */
    @Override
    public void update() {
        direction = GestionTouches.getDirection();

        if (direction == 'D') {
            deplacerDroite();
        } else if (direction == 'G') {
            deplacerGauche();
        }

        if (GestionTouches.doitSauter() && !isEnAir()) {
            sauter();
        }

        appliquerPhysique();
    }

    /**
     * Vérifie si on peut interagir avec un bloc
     */
    public boolean peutInteragir(int sourisX, int sourisY) {
        int joueurX = getX() + LARGEUR_COLLISION / 2;
        int joueurY = getY() + HAUTEUR_COLLISION / 2;

        int deltaX = sourisX - joueurX;
        int deltaY = sourisY - joueurY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance > 80) return false;

        int joueurColonne = joueurX / Terrain.TAILLE_TUILES;
        int joueurLigne = joueurY / Terrain.TAILLE_TUILES;
        int blocColonne = sourisX / Terrain.TAILLE_TUILES;
        int blocLigne = sourisY / Terrain.TAILLE_TUILES;

        return (joueurColonne == blocColonne && Math.abs(joueurLigne - blocLigne) == 1) ||
                (joueurLigne == blocLigne && Math.abs(joueurColonne - blocColonne) == 1);
    }

    /**
     * Casse un bloc
     */
    public boolean casserBloc(int sourisX, int sourisY) {
        if (!peutInteragir(sourisX, sourisY)) return false;

        int typeCasse = terrain.casserBloc(sourisX, sourisY);
        if (typeCasse != 0) {
            collecterBloc(typeCasse);
            return true;
        }
        return false;
    }

    /**
     * Pose un bloc
     */
    public boolean poserBloc(int sourisX, int sourisY) {
        if (!peutInteragir(sourisX, sourisY)) return false;
        if (!terrain.estCaseVide(sourisX, sourisY)) return false;
        if (!terrain.aBlocSolideAdjacent(sourisX, sourisY)) return false;
        if (!inventaire.peutUtiliser(inventaire.itemActuel)) return false;

        boolean pose = terrain.poserBloc(sourisX, sourisY, inventaire.getCode(inventaire.itemActuel));
        if (pose) {
            inventaire.utiliserItemActuel();
        }
        return pose;
    }

    /**
     * Type de bloc par défaut
     */
    public int getTypeBlocDefaut() {
        return inventaire.getCode(inventaire.itemActuel);
    }

    /**
     * Getters
     */
    public Inventaire getInventaire() {
        return inventaire;
    }

    /**
     * Collecte un bloc cassé
     */
    public void collecterBloc(int typeBloc) {
        switch (typeBloc) {
            case 2: inventaire.ajouterHerbe(); break;
            case 3: inventaire.ajouterTerre(); break;
            case 4: inventaire.ajouterPierre(); break;
        }
    }
}