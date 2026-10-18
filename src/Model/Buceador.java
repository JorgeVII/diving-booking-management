package Model;

public class Buceador {
    private String nombre;
    private Nivel nivel;
    
    public Buceador(String nombre, Nivel nivel) {

        if (nombre == null || nombre.isBlank()) { //No puede haber ni nombre vacio || Ni espacios en blanco
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        if (nivel == null) {
            throw new IllegalArgumentException("El nivel no puede ser null");
        }

        this.nombre = nombre;
        this.nivel = nivel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "Buceador -> " + "Nombre: " + this.nombre + " /Nivel: " + this.nivel;
    }
}
