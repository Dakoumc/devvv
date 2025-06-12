package universite_paris8.iut.achandraprasad.saeprojet.modele;

public abstract class Ennemie extends Personnage {


    public Ennemie(Terrain terrain, double x, double y) {

        super(terrain);          // on récupère toute la physique

        setxProperty((int) x);   // position de départ

        setyProperty((int) y);

    }

    /** IA minimaliste : pour l’instant il ne fait rien (statique).

     TODO suivi du joueur, ronde etc... */


    @Override

    public void update() {

        appliquerPhysique();


    }

}