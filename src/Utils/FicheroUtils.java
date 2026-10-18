package Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Model.Reserva;

public class FicheroUtils {
    
private static final String RUTA = "reservas.txt";

    // ── GUARDAR ─────────────────────────────────────────────────────
    public static void guardarReservas(List<Reserva> reservas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA))) {
            for (Reserva r : reservas) {
                writer.write(
                    r.getBuceador().getNombre() + ";" +
                    r.getBuceador().getNivel() + ";" +
                    r.getInmersion().getNombreInmersion() + ";" +
                    r.getFechaReserva()
                );
                writer.newLine();
            }
            System.out.println("Reservas guardadas correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar las reservas: " + e.getMessage());
        }
    }

    // ── CARGAR ──────────────────────────────────────────────────────
    public static ArrayList<String[]> cargarReservas() {
        ArrayList<String[]> datos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.isBlank()) {
                    datos.add(linea.split(";"));
                }
            }
            System.out.println("Reservas cargadas correctamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar las reservas: " + e.getMessage());
        }
        return datos;
    }
}
