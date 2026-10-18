package Services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Excepciones.NivelInsuficienteException;
import Excepciones.SinPlazasException;
import Model.Buceador;
import Model.Inmersion;
import Model.Nivel;
import Model.Reserva;

public class SistemaReservas {

    //Todas las clases creadas

    private List<Buceador> buceadores;
    private List<Inmersion> inmersiones;
    private List<Reserva> reservas;

    public SistemaReservas() {
        buceadores = new ArrayList<>();
        inmersiones = new ArrayList<>();
        reservas = new ArrayList<>();
    }

    public List<Buceador> getBuceadores() {
        return buceadores;
    }

    public List<Inmersion> getInmersiones() {
        return inmersiones;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    //Agregar BUCEADORES________________________________________

    public void registrarBuceador(Buceador buceador) {

        buceadores.add(buceador);
    }

    public void listarBuceadores() {

        if (buceadores.isEmpty()) {

            System.out.println("No hay buceadores.");
            return;
        }

        buceadores.forEach(System.out::println);
    }

    //Agregar INMERSIONES_______________________________________

    public void agregarInmersion(Inmersion inmersion) {

        inmersiones.add(inmersion);
    }

    public void listarInmersiones() {

        if (inmersiones.isEmpty()) {

            System.out.println("No hay inmersiones.");
            return;
        }

        inmersiones.forEach(System.out::println);
    }

    // Buscar inmersiones según nivel
    public List<Inmersion> buscarPorNivel(Nivel nivel) {

        return inmersiones.stream()

                .filter(i -> i.getNivelMinimo().compareTo(nivel) <= 0)
                .collect(Collectors.toList());
    }

    //Agregar RESERVAS_____________________________________________________

    public void realizarReserva(Buceador buceador,Inmersion inmersion) {
        try {
            // Validar reserva
            inmersion.puedeReservar(buceador);

            // Crear reserva
            Reserva reserva = new Reserva(buceador, inmersion);

            reservas.add(reserva);

            // Ocupar plaza
            inmersion.ocuparPlazas();

            System.out.println("Reserva realizada.");

        }

        catch (NivelInsuficienteException | SinPlazasException e) {

            System.out.println(e.getMessage());
        }
    }

    // Mostrar reservas
    public void listarReservas() {

        if (reservas.isEmpty()) {

            System.out.println("No hay reservas.");
            return;
        }

        reservas.forEach(System.out::println);
    }

    // Buscar reservas de un buceador
    public List<Reserva> reservasDeBuceador(
            Buceador buceador) {

        return reservas.stream()

                .filter(r ->r.getBuceador().equals(buceador))
                .collect(Collectors.toList());
    }

    // Contar reservas
    public long contarReservas() {

        return reservas.stream().count();
    }

}
