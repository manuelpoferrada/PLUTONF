module es.franciscodelosrios.plutonf {
    requires javafx.controls;
    requires javafx.fxml;


    opens es.franciscodelosrios.plutonf to javafx.fxml;
    exports es.franciscodelosrios.plutonf;
}