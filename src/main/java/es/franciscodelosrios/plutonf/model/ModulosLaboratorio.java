package es.franciscodelosrios.plutonf.model;

public class ModulosLaboratorio extends Modulo implements Acoplable{

    /**
     * Atributo unico de ModulosLaboratorio
     */
    private int numExperimentos;

    /**
     * Constructor vacío
     */
    public ModulosLaboratorio() {
        super();
    }

    /**
     * Constructor vacío para especificar los maximo posible en ModulosLaboratorio
     * @param idModulo
     * @param nombre
     * @param sector
     * @param capacidadMaxima
     * @param nivelOxigeno
     * @param temperaturaInterior
     * @param nave
     * @param numExperimentos
     */
    public ModulosLaboratorio(int idModulo, String nombre, String sector, int capacidadMaxima, double nivelOxigeno, double temperaturaInterior, Nave nave, int numExperimentos) {
        super(idModulo, nombre, sector, capacidadMaxima, nivelOxigeno, temperaturaInterior, nave);
        this.numExperimentos = numExperimentos;
    }

    // GETTERS Y SETTERS

    public int getNumExperimentos() {
        return numExperimentos;
    }

    public void setNumExperimentos(int numExperimentos) {
        this.numExperimentos = numExperimentos;
    }

    @Override
    public boolean acoplarA(Nave nuevaNave) {
        return false;
    }

    @Override
    public Nave desacoplar() {
        Nave navePrevia = null;
        return navePrevia;
    }
}
