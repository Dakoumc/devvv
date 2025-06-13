package universite_paris8.iut.achandraprasad.saeprojet.vue;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Aelor;
import universite_paris8.iut.achandraprasad.saeprojet.modele.Terrain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Vue spécifique pour le personnage Aelor
 * Étend la classe PersonnageVue avec des fonctionnalités visuelles supplémentaires
 */
public class AelorVue extends PersonnageVue {

    private Aelor aelor;
    private Pane camera;

    /**
     * Constructeur
     * @param aelor Le personnage Aelor
     * @param camera Le pane caméra pour l'affichage
     */
    public AelorVue(Aelor aelor, Pane camera) {
        super(aelor, camera);
        this.aelor = aelor;
        this.camera = camera;
    }

    /**
     * Met à jour l'affichage du personnage
     */
    @Override
    public void mettreAJour() {
        // Appeler la méthode de la classe parente pour mettre à jour l'image du personnage
        super.mettreAJour();
    }



    /**
     * Met en évidence le bloc qui sera cassé en fonction de la position de la souris
     * @param mouseX Position X de la souris
     * @param mouseY Position Y de la souris
     */
    public void mettreEnEvidenceBlocCible(int mouseX, int mouseY) {
    }
}
