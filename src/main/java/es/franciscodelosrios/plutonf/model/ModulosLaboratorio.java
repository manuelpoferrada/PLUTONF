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

    /**
     * Metodo que acopla un modulo a una nave
     * @param nuevaNave La nave de destino
     * @return
     */
    @Override
    public boolean acoplarA(Nave nuevaNave) {
        boolean acoplado = false;

        if (nuevaNave != null) {
            this.nave = nuevaNave;
            acoplado = true;
        }

        return acoplado;
    }

    /**
     * Metodo que devuelve si se ha acoplado correctamente un modulo a una nave
     * @param nave
     * @return
     */
    @Override
    public boolean desacoplar(Nave nave) {

        boolean desacoplado = false;

        if (this.nave != null && this.nave.equals(nave)) {
            this.nave = null;
            desacoplado = true;
        }

        return desacoplado;
    }
}
