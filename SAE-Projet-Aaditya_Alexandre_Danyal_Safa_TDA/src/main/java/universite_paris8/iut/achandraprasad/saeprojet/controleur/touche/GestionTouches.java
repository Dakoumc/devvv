package universite_paris8.iut.achandraprasad.saeprojet.controleur.touche;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import java.util.HashMap;
import java.util.Map;

public class GestionTouches {

    private static Map<Character, Boolean> touchesActives = new HashMap<>();

    // Direction actuelle du personnage (D = Droite, G = Gauche, N = Neutre)
    private static char direction = 'N';

    // Indique si le personnage doit sauter
    private static boolean saut = false;

    /**
     * Configure les écouteurs de touches pour la scène
     * @param scene La scène sur laquelle ajouter les écouteurs
     */
    public static void configurerTouches(Scene scene) {
        // Initialiser les touches
        touchesActives.put('D', false); // Droite
        touchesActives.put('G', false); // Gauche
        touchesActives.put('S', false); // Saut

        // Écouteur pour les touches appuyées
        scene.setOnKeyPressed(event -> {
            String code = event.getCode().toString();

            if (code.equals("RIGHT") || code.equals("D")) {
                touchesActives.put('D', true);
            }
            else if (code.equals("LEFT") || code.equals("Q")) {
                touchesActives.put('G', true);
            }
            else if (code.equals("UP") || code.equals("SPACE") || code.equals("Z")) {
                touchesActives.put('S', true);
            }

            // Mettre à jour la direction
            updateDirection();
        });

        // Écouteur pour les touches relâchées
        scene.setOnKeyReleased(event -> {
            String code = event.getCode().toString();

            if (code.equals("RIGHT") || code.equals("D")) {
                touchesActives.put('D', false);
            }
            else if (code.equals("LEFT") || code.equals("Q")) {
                touchesActives.put('G', false);
            }
            else if (code.equals("UP") || code.equals("SPACE") || code.equals("Z")) {
                touchesActives.put('S', false);
            }

            // Mettre à jour la direction
            updateDirection();
        });
    }

    /**
     * Met à jour la direction basée sur les touches actives
     */
    private static void updateDirection() {
        if (touchesActives.get('D') && !touchesActives.get('G')) {
            direction = 'D';
        } else if (!touchesActives.get('D') && touchesActives.get('G')) {
            direction = 'G';
        } else {
            direction = 'N'; // Neutre - aucune direction ou directions opposées
        }

        // Mise à jour du statut du saut
        saut = touchesActives.get('S');
    }

    /**
     * @return La direction actuelle du personnage
     */
    public static char getDirection() {
        return direction;
    }

    /**
     * @return Vrai si le personnage doit sauter
     */
    public static boolean doitSauter() {
        boolean devraitSauter = saut;
        // Réinitialiser le saut après l'avoir lu pour éviter des sauts multiples
        if (devraitSauter) {
            saut = false;
        }
        return devraitSauter;
    }

    /**
     * @return Vrai si la touche droite est active
     */
    public static boolean isDroiteActive() {
        return touchesActives.get('D');
    }

    /**
     * @return Vrai si la touche gauche est active
     */
    public static boolean isGaucheActive() {
        return touchesActives.get('G');
    }
}