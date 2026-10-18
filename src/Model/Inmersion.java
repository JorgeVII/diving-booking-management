package Model;

import java.time.LocalDate;

import Excepciones.NivelInsuficienteException;
import Excepciones.SinPlazasException;
import Interfaces.Reservable;

public class Inmersion implements Reservable{
    private String nombreInmersion;
    private LocalDate fecha;
    private int profundidad;
    private int plazasMaximas;
    private int plazasOcupadas;
    private Nivel nivelMinimo; 
    
    public Inmersion(String nombreInmersion, LocalDate fecha, int profundidad, int plazasMaximas, Nivel nivelMinimo) {
        this.nombreInmersion = nombreInmersion;
        this.fecha = fecha;
        this.profundidad = profundidad;
        this.plazasMaximas = plazasMaximas;
        this.plazasOcupadas = 0;
        this.nivelMinimo = nivelMinimo;
    }

    //Métodos utilizados para  en la clase con los atributos ----------------------------
    public boolean nivelSuficiente (Nivel nivelBuceador){
        return nivelBuceador.compareTo(nivelMinimo) >= 0;
    }

    public boolean hayPlazas (){
        return plazasOcupadas < plazasMaximas;
    }

    public void ocuparPlazas (){
        plazasOcupadas ++; // se llama cuando alguien reserva
    }

    public void liberarPlaza() {
        if (plazasOcupadas > 0) plazasOcupadas--; //se llama cuando alguien cancela
    }
    //-----------------------------------------------------------------------------------

    public String getNombreInmersion() {
        return nombreInmersion;
    }

    public void setNombreInmersion(String nombreInmersion) {
        this.nombreInmersion = nombreInmersion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }

    public int getPlazasMaximas() {
        return plazasMaximas;
    }

    public void setPlazasMaximas(int plazasMaximas) {
        this.plazasMaximas = plazasMaximas;
    }

    public Nivel getNivelMinimo() {
        return nivelMinimo;
    }

    public void setNivelMinimo(Nivel nivelMinimo) {
        this.nivelMinimo = nivelMinimo;
    }
    // En getters and setter no ponemos "plazasOcupadas" pueden romper la lógica, (podrian poner un set de 999 plazas)
    

    @Override
    public String toString(){
        return "Inmersión: " + getNombreInmersion() + "\nFecha: " + getFecha() + "\nProfundidad máxima: " + getProfundidad() + "m" + "\nPlazas máximas: " + getPlazasMaximas() + "\nTitulación requerida: " + getNivelMinimo() + "\n";
    }

    @Override
    public boolean puedeReservar(Buceador buceador) throws NivelInsuficienteException, SinPlazasException {

        // Validar nivel
        if (!nivelSuficiente(buceador.getNivel())) {
            throw new NivelInsuficienteException(
                    "El buceador no tiene el nivel mínimo requerido."
            );
        }

        // Validar plazas
        if (!hayPlazas()) {
            throw new SinPlazasException(
                    "No quedan plazas disponibles para esta inmersión."
            );
        }
        return true;
    }

    @Override
    public int getPlazasDisponibles() {
        return plazasMaximas - plazasOcupadas;
    }
    
}
