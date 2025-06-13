package universite_paris8.iut.achandraprasad.saeprojet.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Aelor;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Personnage;

import java.util.ArrayList;
import java.util.List;

public class BarreDeVieVue {
    private HBox vies;
    private final List<ImageView> barreCoeurs = new ArrayList<>();
    private final Personnage personnage;

    private final Image Coeur00;
    private final Image Coeur25;
    private final Image Coeur50;
    private final Image Coeur75;
    private final Image Coeur100;

    public BarreDeVieVue(Personnage personnage, HBox vies) {
        this.vies = vies;
        this.personnage = personnage;

        // Charger les images des cœurs
        String cheminBase = "/universite_paris8/iut/achandraprasad/saeprojet/coeurs/";
        this.Coeur00 = new Image(getClass().getResource(cheminBase + "Coeur00.png").toExternalForm());
        this.Coeur25 = new Image(getClass().getResource(cheminBase + "Coeur25.png").toExternalForm());
        this.Coeur50 = new Image(getClass().getResource(cheminBase + "Coeur50.png").toExternalForm());
        this.Coeur75 = new Image(getClass().getResource(cheminBase + "Coeur75.png").toExternalForm());
        this.Coeur100 = new Image(getClass().getResource(cheminBase + "Coeur100.png").toExternalForm());

        // Initialiser les cœurs
        int nombreCoeurs = 5;
        for (int i = 0; i < nombreCoeurs; i++) {
            ImageView coeur100 = new ImageView(Coeur100);
            barreCoeurs.add(coeur100);
            vies.getChildren().add(coeur100);
        }

        mettreAjourVie();
    }

    public void mettreAjourVie() {
        int vie = personnage.getVieActuelle();
        int nombreCoeurs = personnage.getNbCoeurs();

        for (int i = 0; i < nombreCoeurs; i++) {
            int seuil = vie - i * 100;

            boolean coeurPlein = seuil >= 100;
            boolean coeur75Pourcent = seuil >= 75;
            boolean coeur50Pourcent = seuil >= 50;
            boolean coeur25Pourcent = seuil >= 25;
            boolean coeurPresque = seuil > 0;

            if (coeurPlein) {
                barreCoeurs.get(i).setImage(Coeur100);
            } else if (coeur75Pourcent) {
                barreCoeurs.get(i).setImage(Coeur75);
            } else if (coeur50Pourcent) {
                barreCoeurs.get(i).setImage(Coeur50);
            } else if (coeur25Pourcent) {
                barreCoeurs.get(i).setImage(Coeur25);
            } else if (coeurPresque) {
                barreCoeurs.get(i).setImage(Coeur00);
            }
        }
    }
}