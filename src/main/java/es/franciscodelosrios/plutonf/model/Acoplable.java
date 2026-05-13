package es.franciscodelosrios.plutonf.model;

public interface Acoplable {
    /**
     * Permite cambiar el módulo de una nave a otra
     * @param nuevaNave La nave de destino
     * @return verdadero si el acoplamiento se puede hacer
     */
    boolean acoplarA(Nave nuevaNave);

    /**
     * Desvincula el módulo de su nave actual
     * @return La nave de la que se ha desacoplado
     */
    Nave desacoplar();
}
