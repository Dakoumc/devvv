package universite_paris8.iut.achandraprasad.saeprojet.modele;

public class Inventaire {
    // Items de base
    public int terre;
    public int herbe;
    public int pierre; // Pour le futur

    public String itemActuel; // "terre", "herbe", "pierre"

    public Inventaire() {
        this.terre = 50;
        this.herbe = 25;
        this.pierre = 0;
        this.itemActuel = "terre";
    }

    // Ajouter items
    public void ajouterTerre() {
        terre++;
    }

    public void ajouterHerbe() {
        herbe++;
    }

    public void ajouterPierre() {
        pierre++;
    }

    // Enlever items
    public boolean enleverTerre() {
        boolean aTerre = terre > 0;
        if (aTerre) {
            terre--;
            return true;
        }
        return false;
    }

    public boolean enleverHerbe() {
        boolean aHerbe = herbe > 0;
        if (aHerbe) {
            herbe--;
            return true;
        }
        return false;
    }

    public boolean enleverPierre() {
        boolean aPierre = pierre > 0;
        if (aPierre) {
            pierre--;
            return true;
        }
        return false;
    }

    // Changer item sélectionné
    public void changerVers(String item) {
        boolean peutUtiliserItem = peutUtiliser(item);
        if (peutUtiliserItem) {
            this.itemActuel = item;
            String nomItem = getNom(item);
            System.out.println(nomItem + " sélectionné(e)");
        }
    }

    // Vérifications
    public boolean peutUtiliser(String item) {
        switch (item) {
            case "terre":
                return terre > 0;
            case "herbe":
                return herbe > 0;
            case "pierre":
                return pierre > 0;
            default:
                return false;
        }
    }

    public boolean utiliserItemActuel() {
        switch (itemActuel) {
            case "terre":
                return enleverTerre();
            case "herbe":
                return enleverHerbe();
            case "pierre":
                return enleverPierre();
            default:
                return false;
        }
    }

    // Utilitaires
    public String getNom(String item) {
        switch (item) {
            case "terre":
                return "Terre";
            case "herbe":
                return "Herbe";
            case "pierre":
                return "Pierre";
            default:
                return "Inconnu";
        }
    }

    public int getCode(String item) {
        switch (item) {
            case "terre":
                return 3;
            case "herbe":
                return 2;
            case "pierre":
                return 4;
            default:
                return 3;
        }
    }

    public int getQuantite(String item) {
        switch (item) {
            case "terre":
                return terre;
            case "herbe":
                return herbe;
            case "pierre":
                return pierre;
            default:
                return 0;
        }
    }
}