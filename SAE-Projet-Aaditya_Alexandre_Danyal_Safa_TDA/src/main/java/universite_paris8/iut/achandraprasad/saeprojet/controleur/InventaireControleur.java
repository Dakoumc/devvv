package universite_paris8.iut.achandraprasad.saeprojet.controleur;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Inventaire;

import java.net.URL;
import java.util.ResourceBundle;

public class InventaireControleur implements Initializable {

    @FXML private Pane fondInventaire;
    @FXML private Pane slot1, slot2, slot3;
    @FXML private ImageView image1, image2, image3;
    @FXML private Label quantite1, quantite2, quantite3;
    @FXML private Label labelSelection;

    private Inventaire inventaire;

    // Configuration simple avec CHARS
    private char[] items = {'T', 'H', 'P'}; // T=Terre, H=Herbe, P=Pierre
    private String[] chemins = {
            "/universite_paris8/iut/achandraprasad/saeprojet/blocs/terre32.png",
            "/universite_paris8/iut/achandraprasad/saeprojet/blocs/herbe32.png",
            "/universite_paris8/iut/achandraprasad/saeprojet/blocs/pierre32.png"
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chargerImages();
        fondInventaire.setVisible(false);
    }

    private void chargerImages() {
        try {
            image1.setImage(new Image(getClass().getResource(chemins[0]).toExternalForm()));
            image2.setImage(new Image(getClass().getResource(chemins[1]).toExternalForm()));
            image3.setImage(new Image(getClass().getResource(chemins[2]).toExternalForm()));
        } catch (Exception e) {
            System.out.println("Images non trouvées");
        }
    }

    public void setInventaire(Inventaire inventaire) {
        this.inventaire = inventaire;
        mettreAJour();
    }

    @FXML private void selectionnerSlot1() { selectionner(0); }
    @FXML private void selectionnerSlot2() { selectionner(1); }
    @FXML private void selectionnerSlot3() { selectionner(2); }

    private void selectionner(int index) {
        if (inventaire != null && index < items.length) {
            char item = items[index];
            switch (item) {
                case 'T': inventaire.changerVers("terre"); break;
                case 'H': inventaire.changerVers("herbe"); break;
                case 'P': inventaire.changerVers("pierre"); break;
            }
            mettreAJour();
        }
    }

    public void mettreAJour() {
        if (inventaire != null) {
            // Quantités
            quantite1.setText(String.valueOf(inventaire.terre));
            quantite2.setText(String.valueOf(inventaire.herbe));
            quantite3.setText(String.valueOf(inventaire.pierre));

            // Sélection
            labelSelection.setText("Sélectionné: " + inventaire.getNom(inventaire.itemActuel));

            // Highlights
            slot1.setStyle(inventaire.itemActuel.equals("terre") ?
                    "-fx-border-color: yellow; -fx-border-width: 3;" :
                    "-fx-border-color: white; -fx-border-width: 2;");
            slot2.setStyle(inventaire.itemActuel.equals("herbe") ?
                    "-fx-border-color: yellow; -fx-border-width: 3;" :
                    "-fx-border-color: white; -fx-border-width: 2;");
            slot3.setStyle(inventaire.itemActuel.equals("pierre") ?
                    "-fx-border-color: yellow; -fx-border-width: 3;" :
                    "-fx-border-color: white; -fx-border-width: 2;");
        }
    }

    public void setVisible(boolean visible) {
        fondInventaire.setVisible(visible);
        if (visible) mettreAJour();
    }

    public boolean isVisible() {
        return fondInventaire.isVisible();
    }
}