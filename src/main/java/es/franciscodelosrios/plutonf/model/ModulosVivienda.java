package es.franciscodelosrios.plutonf.model;

public class ModulosVivienda extends Modulo{
    /**
     * Atributos especifico de ModulosVivienda
     */
    private int numCamas;

    /**
     * Constructor vacío
     */
    public ModulosVivienda() {
        super();
    }

    /**
     * Constructor completo para poder especificar lo máxximo posible ModulosVivienda
     * @param idModulo
     * @param nombre
     * @param sector
     * @param capacidadMaxima
     * @param nivelOxigeno
     * @param temperaturaInterior
     * @param nave
     * @param numCamas
     */
    public ModulosVivienda(int idModulo, String nombre, String sector, int capacidadMaxima, double nivelOxigeno, double temperaturaInterior, Nave nave, int numCamas) {
        super(idModulo, nombre, sector, capacidadMaxima, nivelOxigeno, temperaturaInterior, nave);
        this.numCamas = numCamas;
    }

    // GETTERS Y SETTERS

    public int getNumCamas() {
        return numCamas;
    }

    public void setNumCamas(int numCamas) {
        this.numCamas = numCamas;
    }
}
