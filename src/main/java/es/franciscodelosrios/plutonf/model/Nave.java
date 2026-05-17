package es.franciscodelosrios.plutonf.model;

import java.util.Date;

public class Nave {
    /**
     * Atributos
     */
    private int idNave;
    private String nombre;
    private double alcance;
    private double combustibleActual;
    private Date fechaLanzamiento;
    private String estadoNave;

    /**
     * Constructor vacío
     */
    public Nave() {}

    /**
     * Constructor completo
     * @param idNave
     * @param nombre
     * @param alcance
     * @param combustibleActual
     * @param fechaLanzamiento
     * @param estadoNave
     */
    public Nave(int idNave, String nombre, double alcance, double combustibleActual, Date fechaLanzamiento, String estadoNave) {
        this.idNave = idNave;
        this.nombre = nombre;
        this.alcance = alcance;
        this.combustibleActual = combustibleActual;
        this.fechaLanzamiento = fechaLanzamiento;
        this.estadoNave = estadoNave;
    }

    // GETTERS Y SETTERS

    public int getIdNave() {
        return idNave;
    }

    public void setIdNave(int idNave) {
        this.idNave = idNave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getAlcance() {
        return alcance;
    }

    public void setAlcance(double alcance) {
        this.alcance = alcance;
    }

    public double getCombustibleActual() {
        return combustibleActual;
    }

    public void setCombustibleActual(double combustibleActual) {
        this.combustibleActual = combustibleActual;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getEstadoNave() {
        return estadoNave;
    }

    public void setEstadoNave(String estadoNave) {
        this.estadoNave = estadoNave;
    }

    /**
     * Mostramos una nave, ayuda para cuando mostramos un ID y nombre del nave en una tabla que no es suya
     * @return
     */
    @Override
    public String toString() {
        return idNave + " - " + nombre;
    }
}
