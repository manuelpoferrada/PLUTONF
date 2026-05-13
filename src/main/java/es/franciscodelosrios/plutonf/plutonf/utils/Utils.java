package es.franciscodelosrios.plutonf.plutonf.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class Utils {

    /**
     * Comprobamos si un campo esta vacio
     * @param campo que se va a comprobar
     * @return true si lo esta y false si no
     */
    public static boolean campoVacio(TextField campo) {
        boolean vacio = false;

        if (campo.getText() == null || campo.getText().equals("")) {
            vacio = true;
        }

        return vacio;
    }

    /**
     * Limpiamos los campos, muy util porque tenemos muchos botones en la que
     * su funcion es limpiar campos de texto
     * @param campos podemos recibir muchos campos a limpiar de hay el "TextField..."
     */
    public static void limpiarCampos(TextField... campos) {
        for (int i = 0; i < campos.length; i++) {
            campos[i].clear();
        }
    }

    /**
     * Convertimos a numero un texto recibido
     * @param texto que va a ser convertido a numero
     * @return el texto pasado a numero entero pero si no se ha conseguido devuelve -1
     */
    public static int convertirEntero(String texto) {
        int numero = -1;

        try {
            numero = Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            numero = -1;
        }

        return numero;
    }

    /**
     * Mismo metodo que convertir a entero pero esta vez en Double
     * @param texto a convertir en Double
     * @return el texto transformado en numero
     */
    public static double convertirDouble(String texto) {
        double numero = -1;

        try {
            numero = Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            numero = -1;
        }

        return numero;
    }

    /**
     * Mostramos un mensaje, muy util para todas las ventanas
     * @param titulo principal a mostrar
     * @param mensaje el contenido que saldra debajo del titulo
     */
    public static void mostrarMensaje(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Mostramos algún mensaje de error si algo ha salido mal
     * @param titulo prinicpal a mostrar
     * @param mensaje el contenido que va a salir debajo del titulo
     */
    public static void mostrarError(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}