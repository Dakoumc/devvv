package universite_paris8.iut.achandraprasad.saeprojet.modele;

import universite_paris8.iut.achandraprasad.saeprojet.controleur.touche.GestionTouches;

/**
 * Classe représentant le personnage joueur Aelor
 * Étend la classe abstraite Personnage
 */
public class Aelor extends Personnage {

    private Inventaire inventaire;


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

        boolean vaADroite = direction == 'D';
        boolean vaAGauche = direction == 'G';

        if (vaADroite) {
            deplacerDroite();
        } else if (vaAGauche) {
            deplacerGauche();
        }

        boolean doitSauter = GestionTouches.doitSauter();
        boolean estAuSol = !isEnAir();

        if (doitSauter && estAuSol) {
            sauter();
        }

        appliquerPhysique();
        marcherSurBlocDanger();
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

        boolean tropLoin = distance > 80;
        if (tropLoin) {
            return false;
        }

        int joueurColonne = joueurX / Terrain.TAILLE_TUILES;
        int joueurLigne = joueurY / Terrain.TAILLE_TUILES;
        int blocColonne = sourisX / Terrain.TAILLE_TUILES;
        int blocLigne = sourisY / Terrain.TAILLE_TUILES;

        // Vérifier interaction verticale (même colonne, lignes adjacentes)
        boolean memeColonne = joueurColonne == blocColonne;
        boolean ligneAdjacente = Math.abs(joueurLigne - blocLigne) == 1;
        boolean interactionVerticale = memeColonne && ligneAdjacente;

        // Vérifier interaction horizontale (même ligne, colonnes adjacentes)
        boolean memeLigne = joueurLigne == blocLigne;
        boolean colonneAdjacente = Math.abs(joueurColonne - blocColonne) == 1;
        boolean interactionHorizontale = memeLigne && colonneAdjacente;

        return interactionVerticale || interactionHorizontale;
    }


    public boolean casserBloc(int sourisX, int sourisY) {
        boolean peutInteragirAvecBloc = peutInteragir(sourisX, sourisY);
        if (!peutInteragirAvecBloc) {
            return false;
        }

        int typeCasse = terrain.casserBloc(sourisX, sourisY);
        boolean blocCasse = typeCasse != 0;

        if (blocCasse) {
            collecterBloc(typeCasse);
            return true;
        }

        return false;
    }


    public boolean poserBloc(int sourisX, int sourisY) {
        boolean peutInteragirAvecBloc = peutInteragir(sourisX, sourisY);
        if (!peutInteragirAvecBloc) {
            return false;
        }

        boolean caseVide = terrain.estCaseVide(sourisX, sourisY);
        if (!caseVide) {
            return false;
        }

        boolean aBlocAdjacent = terrain.aBlocSolideAdjacent(sourisX, sourisY);
        if (!aBlocAdjacent) {
            return false;
        }

        boolean peutUtiliserItem = inventaire.peutUtiliser(inventaire.itemActuel);
        if (!peutUtiliserItem) {
            return false;
        }

        int codeBloc = inventaire.getCode(inventaire.itemActuel);
        boolean blocPose = terrain.poserBloc(sourisX, sourisY, codeBloc);

        if (blocPose) {
            inventaire.utiliserItemActuel();
        }

        return blocPose;
    }


    public int getTypeBlocDefaut() {
        return inventaire.getCode(inventaire.itemActuel);
    }


    public Inventaire getInventaire() {
        return inventaire;
    }

    public void collecterBloc(int typeBloc) {
        switch (typeBloc) {
            case 2:
                inventaire.ajouterHerbe();
                break;
            case 3:
                inventaire.ajouterTerre();
                break;
            case 4:
                inventaire.ajouterPierre();
                break;
        }
    }
}