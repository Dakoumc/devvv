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


    private final Image Coeur00 = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/coeurs/Coeur00.png").toExternalForm());
    private final Image Coeur25 = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/coeurs/Coeur25.png").toExternalForm());
    private final Image Coeur50 = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/coeurs/Coeur50.png").toExternalForm());
    private final Image Coeur75 = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/coeurs/Coeur75.png").toExternalForm());
    private final Image Coeur100 = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/coeurs/Coeur100.png").toExternalForm());

    public BarreDeVieVue (Personnage personnage, HBox vies) {
        this.vies = vies;
        this.personnage = personnage;

        for (int i = 0; i < 5 ; i++) {
            ImageView coeur100 = new ImageView(Coeur100);
            barreCoeurs.add(coeur100);
            vies.getChildren().add(coeur100);
        }
        mettreAjourVie();
    }

    public void mettreAjourVie(){
        int vie = personnage.getVieActuelle();
        for (int i = 0; i < personnage.getNbCoeurs(); i++) {
            int seuil = vie - i * 100;

            if (seuil >= 100) {
                barreCoeurs.get(i).setImage(Coeur100);
            } else if (seuil >= 75) {
                barreCoeurs.get(i).setImage(Coeur75);
            } else if (seuil >= 50) {
                barreCoeurs.get(i).setImage(Coeur50);
            } else if (seuil >= 25) {
                barreCoeurs.get(i).setImage(Coeur25);
            } else if (seuil > 0){
                barreCoeurs.get(i).setImage(Coeur00);
            }
        }
    }


}