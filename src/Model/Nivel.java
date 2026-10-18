package Model;

public enum Nivel {
    OPEN_WATER ("Open Water", 18),
    ADVANCED("Advanced", 30),
    RESCUE("Rescue Diver", 40),
    DIVEMASTER("Divemaster", 40);

    private String nombre;
    private int profundidad;
    
    private Nivel(String nombre, int profundidad) {
        this.nombre = nombre;
        this.profundidad = profundidad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getProfundidad() {
        return profundidad;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
