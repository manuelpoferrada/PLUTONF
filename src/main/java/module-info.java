module es.franciscodelosrios.plutonf {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;

    opens es.franciscodelosrios.plutonf.dataaccess to java.xml.bind;
    exports es.franciscodelosrios.plutonf.dataaccess;

    opens es.franciscodelosrios.plutonf.plutonf to javafx.fxml;
    exports es.franciscodelosrios.plutonf.plutonf;

    opens es.franciscodelosrios.plutonf.plutonf.controllers to javafx.fxml;
    exports es.franciscodelosrios.plutonf.plutonf.controllers;

    exports es.franciscodelosrios.plutonf.model;
    exports es.franciscodelosrios.plutonf;
}


