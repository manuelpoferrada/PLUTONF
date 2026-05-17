package es.franciscodelosrios.plutonf.model;

public class Mantenimiento {
    /**
     * Atributos de Mantenimiento
     */
    private int idMantenimiento;
    private String descripcion;
    private Prioridad prioridad;
    private double costeRecursos;
    private String duracionEntidad;
    private String herramientaNecesaria;

    /**
     * Constructor vacío
     */
    public Mantenimiento() {}

    /**
     * Constructor completo
     */
    public Mantenimiento(int idMantenimiento, String descripcion, Prioridad prioridad, double costeRecursos, String duracionEntidad, String herramientaNecesaria) {
        this.idMantenimiento = idMantenimiento;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.costeRecursos = costeRecursos;
        this.duracionEntidad = duracionEntidad;
        this.herramientaNecesaria = herramientaNecesaria;
    }

    // GETTERS Y SETTERS

    public int getIdMantenimiento() {
        return idMantenimiento;
    }

    public void setIdMantenimiento(int idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public double getCosteRecursos() {
        return costeRecursos;
    }

    public void setCosteRecursos(double costeRecursos) {
        this.costeRecursos = costeRecursos;
    }

    public String getDuracionEntidad() {
        return duracionEntidad;
    }

    public void setDuracionEntidad(String duracionEntidad) {
        this.duracionEntidad = duracionEntidad;
    }

    public String getHerramientaNecesaria() {
        return herramientaNecesaria;
    }

    public void setHerramientaNecesaria(String herramientaNecesaria) {
        this.herramientaNecesaria = herramientaNecesaria;
    }

    /**
     * String para que pueda aparecer en tablas ajenas y no de error
     * @return descripcion y su idMantenimiento
     */
    @Override
    public String toString() {
        return idMantenimiento  + " - " + descripcion;
    }
}