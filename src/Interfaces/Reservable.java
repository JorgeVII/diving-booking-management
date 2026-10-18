package Interfaces;

import Excepciones.NivelInsuficienteException;
import Excepciones.SinPlazasException;
import Model.Buceador;

public interface Reservable {
    boolean puedeReservar(Buceador buceador) throws NivelInsuficienteException, SinPlazasException;
    int getPlazasDisponibles();
}
