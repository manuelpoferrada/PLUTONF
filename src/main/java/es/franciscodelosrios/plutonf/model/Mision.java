package es.franciscodelosrios.plutonf.model;

public class Mision {

    /**
     * Atributos de Mision
     */
    private int idMision;
    private String nombrePlaneta;
    private String objetivo;
    private Nave nave;

    /**
     * Constructor vacío
     */
    public Mision() {}

    /**
     * Constructor completo
     */
    public Mision(int idMision, String nombrePlaneta, String objetivo, Nave nave) {
        this.idMision = idMision;
        this.nombrePlaneta = nombrePlaneta;
        this.objetivo = objetivo;
        this.nave = nave;
    }

    // GETTERS Y SETTERS

    public int getIdMision() {
        return idMision;
    }

    public void setIdMision(int idMision) {
        this.idMision = idMision;
    }

    public String getNombrePlaneta() {
        return nombrePlaneta;
    }

    public void setNombrePlaneta(String nombrePlaneta) {
        this.nombrePlaneta = nombrePlaneta;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public Nave getNave() {
        return nave;
    }

    public void setNave(Nave nave) {
        this.nave = nave;
    }
}
