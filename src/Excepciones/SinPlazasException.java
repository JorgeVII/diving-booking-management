package Excepciones;

public class SinPlazasException extends Exception { //Exepction es un sistema de alarmas personalizadas de Java
    public SinPlazasException(String mensaje) {
        super(mensaje);
    }
}
