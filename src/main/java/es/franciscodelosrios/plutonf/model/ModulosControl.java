package es.franciscodelosrios.plutonf.model;

public class ModulosControl extends Modulo {

    /**
     * Atributo específico de ModulosControl
     */
    private int nivelSeguridad;

    /**
     * Constructor vacío
     */
    public ModulosControl() {
        super();
    }

    /**
     * Constructor con todos los parámetros posibles para ModulosControl
     * @param idModulo
     * @param nombre
     * @param sector
     * @param capacidadMaxima
     * @param nivelOxigeno
     * @param temperaturaInterior
     * @param nave
     * @param nivelSeguridad
     */
    public ModulosControl(int idModulo, String nombre, String sector, int capacidadMaxima, double nivelOxigeno, double temperaturaInterior, Nave nave, int nivelSeguridad) {
        super(idModulo, nombre, sector, capacidadMaxima, nivelOxigeno, temperaturaInterior, nave);
        this.nivelSeguridad = nivelSeguridad;
    }


    // GETTERS Y SETTERS

    public int getNivelSeguridad() {
        return nivelSeguridad;
    }

    public void setNivelSeguridad(int nivelSeguridad) {
        this.nivelSeguridad = nivelSeguridad;
    }
}