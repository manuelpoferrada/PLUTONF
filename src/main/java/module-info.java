module es.franciscodelosrios.plutonf {
    requires javafx.controls;
    requires javafx.fxml;


    opens es.franciscodelosrios.plutonf to javafx.fxml;
    exports es.franciscodelosrios.plutonf;
    exports es.franciscodelosrios.plutonf.plutonf;
    opens es.franciscodelosrios.plutonf.plutonf to javafx.fxml;
    exports es.franciscodelosrios.plutonf.plutonf.controllers;
    opens es.franciscodelosrios.plutonf.plutonf.controllers to javafx.fxml;
}