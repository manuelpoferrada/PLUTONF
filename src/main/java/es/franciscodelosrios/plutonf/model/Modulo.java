package es.franciscodelosrios.plutonf.model;

public abstract class Modulo {

    /**
     * Atributos de Modulo
     */
    protected int idModulo;
    protected String nombre;
    protected String sector;
    protected int capacidadMaxima;
    protected double nivelOxigeno;
    protected double temperaturaInterior;
    protected Nave nave;

    /**
     * Constructor vacío
     */
    public Modulo(){}

    /**
     * Constructor completo con todos los atributos
     * @param idModulo
     * @param nombre
     * @param sector
     * @param capacidadMaxima
     * @param nivelOxigeno
     * @param temperaturaInterior
     * @param nave
     */
    public Modulo(int idModulo, String nombre, String sector, int capacidadMaxima, double nivelOxigeno, double temperaturaInterior, Nave nave) {
        this.idModulo = idModulo;
        this.nombre = nombre;
        this.sector = sector;
        this.capacidadMaxima = capacidadMaxima;
        this.nivelOxigeno = nivelOxigeno;
        this.temperaturaInterior = temperaturaInterior;
        this.nave = nave;
    }

    // GETTERS Y SETTERS


    public int getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(int idModulo) {
        this.idModulo = idModulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public double getNivelOxigeno() {
        return nivelOxigeno;
    }

    public void setNivelOxigeno(double nivelOxigeno) {
        this.nivelOxigeno = nivelOxigeno;
    }

    public double getTemperaturaInterior() {
        return temperaturaInterior;
    }

    public void setTemperaturaInterior(double temperaturaInterior) {
        this.temperaturaInterior = temperaturaInterior;
    }

    public Nave getNave() {
        return nave;
    }

    public void setNave(Nave nave) {
        this.nave = nave;
    }
}