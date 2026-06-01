package es.franciscodelosrios.plutonf.plutonf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PlutonfApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PlutonfApplication.class.getResource("inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("PLUTONF");
        stage.setScene(scene);

        // Impide redimensionar la primera ventana
        stage.setResizable(false);

        stage.show();
    }
}
