package es.franciscodelosrios.plutonf.model;

import java.util.Date;

public class IntervencionMantenimiento {

    /**
     * Atributos de IntervencionMantenimiento
     */
    private int idIntervencion;
    private Mantenimiento mantenimiento;
    private Astronauta astronauta;
    private Modulo modulo;
    private Date fechaIntervencion;
    private String observaciones;

    /**
     * Constructor vacío
     */
    public IntervencionMantenimiento() {}

    /**
     * Constructor con todos los atributos de IntervencionMantenimiento
     * @param idIntervencion
     * @param mantenimiento
     * @param astronauta
     * @param modulo
     * @param fechaIntervencion
     * @param observaciones
     */
    public IntervencionMantenimiento(int idIntervencion, Mantenimiento mantenimiento, Astronauta astronauta, Modulo modulo, Date fechaIntervencion, String observaciones) {
        this.idIntervencion = idIntervencion;
        this.mantenimiento = mantenimiento;
        this.astronauta = astronauta;
        this.modulo = modulo;
        this.fechaIntervencion = fechaIntervencion;
        this.observaciones = observaciones;
    }

    // GETTERS Y SETTERS

    public int getIdIntervencion() {
        return idIntervencion;
    }

    public void setIdIntervencion(int idIntervencion) {
        this.idIntervencion = idIntervencion;
    }

    public Mantenimiento getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(Mantenimiento mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

    public Astronauta getAstronauta() {
        return astronauta;
    }

    public void setAstronauta(Astronauta astronauta) {
        this.astronauta = astronauta;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public Date getFechaIntervencion() {
        return fechaIntervencion;
    }

    public void setFechaIntervencion(Date fechaIntervencion) {
        this.fechaIntervencion = fechaIntervencion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}