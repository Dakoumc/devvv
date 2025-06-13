package universite_paris8.iut.achandraprasad.saeprojet.controleur;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import universite_paris8.iut.achandraprasad.saeprojet.controleur.touche.GestionTouches;
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
    private Timeline autoUpdate;

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

        // Démarrer l'auto-gestion - l'inventaire se gère maintenant tout seul !
        demarrerAutoGestion();
    }

    private void demarrerAutoGestion() {
        autoUpdate = new Timeline();
        autoUpdate.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(Duration.seconds(0.017), event -> {
            boolean inventaireOuvert = GestionTouches.isInventaireOuvert();

            if (isVisible() != inventaireOuvert) {
                setVisible(inventaireOuvert);
            }

            if (isVisible()) {
                mettreAJour();
            }
        });

        autoUpdate.getKeyFrames().add(kf);
        autoUpdate.play();
    }

    private void chargerImages() {
        try {
            String cheminTerre = chemins[0];
            String cheminHerbe = chemins[1];
            // String cheminPierre = chemins[2]; // pour le futur

            image1.setImage(new Image(getClass().getResource(cheminTerre).toExternalForm()));
            image2.setImage(new Image(getClass().getResource(cheminHerbe).toExternalForm()));
            // image3 pour le futur
        } catch (Exception e) {
            System.out.println("Images non trouvées");
        }
    }

    public void setInventaire(Inventaire inventaire) {
        this.inventaire = inventaire;
        mettreAJour();
    }

    @FXML private void selectionnerSlot1() {
        selectionner(0);
    }

    @FXML private void selectionnerSlot2() {
        selectionner(1);
    }

    @FXML private void selectionnerSlot3() {
        selectionner(2);
    }

    private void selectionner(int index) {
        boolean inventaireDisponible = inventaire != null;
        boolean indexValide = index < items.length;

        if (inventaireDisponible && indexValide) {
            char item = items[index];

            switch (item) {
                case 'T':
                    inventaire.changerVers("terre");
                    break;
                case 'H':
                    inventaire.changerVers("herbe");
                    break;
                case 'P':
                    inventaire.changerVers("pierre");
                    break;
            }

            mettreAJour();
        }
    }

    public void mettreAJour() {
        boolean inventaireDisponible = inventaire != null;
        if (!inventaireDisponible) {
            return;
        }

        // Quantités
        quantite1.setText(String.valueOf(inventaire.terre));
        quantite2.setText(String.valueOf(inventaire.herbe));
        quantite3.setText(String.valueOf(inventaire.pierre));

        // Sélection
        String nomItemActuel = inventaire.getNom(inventaire.itemActuel);
        labelSelection.setText("Sélectionné: " + nomItemActuel);

        // Highlights
        String styleSelection = "-fx-border-color: yellow; -fx-border-width: 3;";
        String styleNormal = "-fx-border-color: white; -fx-border-width: 2;";

        boolean terreSelectionnee = inventaire.itemActuel.equals("terre");
        boolean herbeSelectionnee = inventaire.itemActuel.equals("herbe");
        boolean pierreSelectionnee = inventaire.itemActuel.equals("pierre");

        slot1.setStyle(terreSelectionnee ? styleSelection : styleNormal);
        slot2.setStyle(herbeSelectionnee ? styleSelection : styleNormal);
        slot3.setStyle(pierreSelectionnee ? styleSelection : styleNormal);
    }

    public void setVisible(boolean visible) {
        fondInventaire.setVisible(visible);
        if (visible) {
            mettreAJour();
        }
    }

    public boolean isVisible() {
        return fondInventaire.isVisible();
    }
}