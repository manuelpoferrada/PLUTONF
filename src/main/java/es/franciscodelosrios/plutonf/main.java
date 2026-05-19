package es.franciscodelosrios.plutonf;
import es.franciscodelosrios.plutonf.dao.*;
import es.franciscodelosrios.plutonf.model.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

// PARA AGILIZAR EL PROYECTO HE CREADO UN MAIN CON IA, ASI PUEDO PROBAR MIS DAO DE FORMA MÁS RÁPIDA Y ANTES
// PUEDO EMPEZAR CON INTERFAZ Y CONTROLADORES

public class main {

    public static void main(String[] args) {
        try {
            probarNaveDAO();
            probarMisionDAO();
            probarModuloDAO();
            probarAstronautaDAO();
            probarMantenimientoDAO();
            probarIntervencionMantenimientoDAO();

            System.out.println("\nTODAS LAS PRUEBAS HAN TERMINADO");

        } catch (SQLException e) {
            System.out.println("ERROR EN BASE DE DATOS");
            System.out.println(e.getMessage());
        }
    }

    public static void probarNaveDAO() throws SQLException {
        System.out.println("\n===== PRUEBA NAVE DAO =====");

        Nave nave = new Nave(0, "Apolo DAM", 50000, 80, new Date(), "Preparada");

        if (NaveDAO.addNave(nave)) {
            System.out.println("Nave añadida correctamente");
        } else {
            System.out.println("No se ha podido añadir la nave");
        }

        List<Nave> naves = NaveDAO.findAll();
        for (int i = 0; i < naves.size(); i++) {
            System.out.println(naves.get(i).getIdNave() + " - " + naves.get(i).getNombre());
        }
    }

    public static void probarMisionDAO() throws SQLException {
        System.out.println("\n===== PRUEBA MISION DAO =====");

        Nave nave = buscarPrimeraNave();

        Mision mision = new Mision(0, "Marte", "Explorar superficie", nave);

        if (MisionDAO.addMision(mision)) {
            System.out.println("Misión añadida correctamente");
        } else {
            System.out.println("No se ha podido añadir la misión");
        }

        List<Mision> misiones = MisionDAO.findAll();
        for (int i = 0; i < misiones.size(); i++) {
            System.out.println(misiones.get(i).getIdMision() + " - " + misiones.get(i).getNombrePlaneta());
        }
    }

    public static void probarModuloDAO() throws SQLException {
        System.out.println("\n===== PRUEBA MODULO DAO =====");

        Nave nave = buscarPrimeraNave();

        Modulo modulo = new ModulosControl(0, "Control Central", "Sector A", 10, 95, 22, nave, 5);

        if (ModuloDAO.addModulo(modulo)) {
            System.out.println("Módulo añadido correctamente");
        } else {
            System.out.println("No se ha podido añadir el módulo");
        }

        List<Modulo> modulos = ModuloDAO.findAll();
        for (int i = 0; i < modulos.size(); i++) {
            System.out.println(modulos.get(i).getIdModulo() + " - " + modulos.get(i).getNombre());
        }
    }

    public static void probarAstronautaDAO() throws SQLException {
        System.out.println("\n===== PRUEBA ASTRONAUTA DAO =====");

        Modulo modulo = buscarPrimerModulo();

        Astronauta astronauta = new Astronauta(0, "12345678A", "Manuel", "Piloto", "Comandante", 25, 300, "España", modulo);

        if (AstronautaDAO.addAstronauta(astronauta)) {
            System.out.println("Astronauta añadido correctamente");
        } else {
            System.out.println("No se ha podido añadir el astronauta");
        }

        List<Astronauta> astronautas = AstronautaDAO.findAll();
        for (int i = 0; i < astronautas.size(); i++) {
            System.out.println(astronautas.get(i).getIdAstronauta() + " - " + astronautas.get(i).getNombre());
        }
    }

    public static void probarMantenimientoDAO() throws SQLException {
        System.out.println("\n===== PRUEBA MANTENIMIENTO DAO =====");

        Mantenimiento mantenimiento = new Mantenimiento(0, "Revisión de oxígeno", Prioridad.ALTA, 150, "2 horas", "Llave espacial");

        if (MantenimientoDAO.addMantenimiento(mantenimiento)) {
            System.out.println("Mantenimiento añadido correctamente");
        } else {
            System.out.println("No se ha podido añadir el mantenimiento");
        }

        List<Mantenimiento> mantenimientos = MantenimientoDAO.findAll();
        for (int i = 0; i < mantenimientos.size(); i++) {
            System.out.println(mantenimientos.get(i).getIdMantenimiento() + " - " + mantenimientos.get(i).getDescripcion());
        }
    }

    public static void probarIntervencionMantenimientoDAO() throws SQLException {
        System.out.println("\n===== PRUEBA INTERVENCION MANTENIMIENTO DAO =====");

        Mantenimiento mantenimiento = buscarPrimerMantenimiento();
        Astronauta astronauta = buscarPrimerAstronauta();
        Modulo modulo = buscarPrimerModulo();

        IntervencionMantenimiento intervencion = new IntervencionMantenimiento(
                0,
                mantenimiento,
                astronauta,
                modulo,
                new Date(),
                "Intervención realizada correctamente"
        );

        if (IntervencionMantenimientoDAO.addIntervencion(intervencion)) {
            System.out.println("Intervención añadida correctamente");
        } else {
            System.out.println("No se ha podido añadir la intervención");
        }

        List<IntervencionMantenimiento> intervenciones = IntervencionMantenimientoDAO.findAll();
        for (int i = 0; i < intervenciones.size(); i++) {
            System.out.println(intervenciones.get(i).getIdIntervencion() + " - " + intervenciones.get(i).getObservaciones());
        }
    }

    public static Nave buscarPrimeraNave() throws SQLException {
        Nave nave = null;
        List<Nave> naves = NaveDAO.findAll();

        if (naves.size() > 0) {
            nave = naves.get(0);
        }

        return nave;
    }

    public static Modulo buscarPrimerModulo() throws SQLException {
        Modulo modulo = null;
        List<Modulo> modulos = ModuloDAO.findAll();

        if (modulos.size() > 0) {
            modulo = modulos.get(0);
        }

        return modulo;
    }

    public static Astronauta buscarPrimerAstronauta() throws SQLException {
        Astronauta astronauta = null;
        List<Astronauta> astronautas = AstronautaDAO.findAll();

        if (astronautas.size() > 0) {
            astronauta = astronautas.get(0);
        }

        return astronauta;
    }

    public static Mantenimiento buscarPrimerMantenimiento() throws SQLException {
        Mantenimiento mantenimiento = null;
        List<Mantenimiento> mantenimientos = MantenimientoDAO.findAll();

        if (mantenimientos.size() > 0) {
            mantenimiento = mantenimientos.get(0);
        }

        return mantenimiento;
    }
}