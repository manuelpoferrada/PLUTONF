package es.franciscodelosrios.plutonf.model;

public interface Acoplable {

    /**
     * Permite acoplar el modulo a otra nave
     * @param nuevaNave
     * @return true si se ha podido acoplar
     */
    boolean acoplarA(Nave nuevaNave);

    /**
     * Desacopla el modulo de una nave
     * @param nave
     * @return true si se ha desacoplado correctamente
     */
    boolean desacoplar(Nave nave);
}
