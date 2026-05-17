package es.franciscodelosrios.plutonf.model;

public class Astronauta {

    /**
     * Atributos de Astronauta
     */
    private int idAstronauta;
    private String dni;
    private String nombre;
    private String habilidadPrincipal;
    private String rango;
    private int edad;
    private int horasVuelo;
    private String nacionalidad;
    private Modulo modulo;

    /**
     * Constructor vacío
     */
    public Astronauta() {}

    /**
     * Constructor completo
     */
    public Astronauta(int idAstronauta, String dni, String nombre, String habilidadPrincipal, String rango, int edad, int horasVuelo, String nacionalidad, Modulo modulo) {
        this.idAstronauta = idAstronauta;
        this.dni = dni;
        this.nombre = nombre;
        this.habilidadPrincipal = habilidadPrincipal;
        this.rango = rango;
        this.edad = edad;
        this.horasVuelo = horasVuelo;
        this.nacionalidad = nacionalidad;
        this.modulo = modulo;
    }

    // GETTERS Y SETTERS


    public int getIdAstronauta() {
        return idAstronauta;
    }

    public void setIdAstronauta(int idAstronauta) {
        this.idAstronauta = idAstronauta;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHabilidadPrincipal() {
        return habilidadPrincipal;
    }

    public void setHabilidadPrincipal(String habilidadPrincipal) {
        this.habilidadPrincipal = habilidadPrincipal;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getHorasVuelo() {
        return horasVuelo;
    }

    public void setHorasVuelo(int horasVuelo) {
        this.horasVuelo = horasVuelo;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    /**
     * String para que pueda aparecer en tablas ajenas y no de error
     * @return idAstronauta  y su nombre
     */
    @Override
    public String toString() {
        return idAstronauta   + " - " + nombre;
    }
}