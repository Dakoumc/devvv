package universite_paris8.iut.achandraprasad.saeprojet.vue;


import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

import javafx.scene.layout.Pane;

import universite_paris8.iut.achandraprasad.saeprojet.modele.Ennemie;


public class EnnemieVue {


    private final Ennemie ennemi;

    private final ImageView iv;


    public EnnemieVue(Ennemie ennemi, Pane camera) {

        this.ennemi = ennemi;

        Image sprite = new Image(getClass().getResource("/universite_paris8/iut/achandraprasad/saeprojet/monstres/Gobelin.png").toExternalForm());

        iv = new ImageView(sprite);

        iv.setFitWidth(32);

        iv.setFitHeight(32);

        // liaison automatique à la position du modèle

        iv.layoutXProperty().bind(ennemi.xProperty());

        iv.layoutYProperty().bind(ennemi.yProperty());

        camera.getChildren().add(iv); // on l’ajoute dans la « caméra »

    }


    public void mettreAJour() {

        // Ici on peut plus tard changer l’image selon l’état (marche, attaque…)

    }

}