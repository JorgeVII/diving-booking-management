package Model;

import java.time.LocalDate;

public class Reserva {
    private Buceador buceador;
    private Inmersion inmersion;
    private LocalDate fechaReserva;
    
    public Reserva(Buceador buceador, Inmersion inmersion) {
        this.buceador = buceador;
        this.inmersion = inmersion;
        this.fechaReserva = LocalDate.now();
    }

    public Buceador getBuceador() {
        return buceador;
    }

    public Inmersion getInmersion() {
        return inmersion;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }
    
    @Override
    public String toString() {
        return "Reserva -> Buceador: " + buceador.getNombre() + 
        " | Inmersión: " + inmersion.getNombreInmersion() + 
        " | Fecha reserva: " + fechaReserva;
    }

}
