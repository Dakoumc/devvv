module universite_paris8.iut.achandraprasad.saeprojet {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens universite_paris8.iut.achandraprasad.saeprojet to javafx.fxml;
    exports universite_paris8.iut.achandraprasad.saeprojet;
    exports universite_paris8.iut.achandraprasad.saeprojet.controleur;
    opens universite_paris8.iut.achandraprasad.saeprojet.controleur to javafx.fxml;
}