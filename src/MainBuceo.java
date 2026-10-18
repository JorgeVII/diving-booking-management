import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import Model.Buceador;
import Model.Inmersion;
import Model.Nivel;
import Model.Reserva;
import Services.SistemaReservas;
import Utils.FicheroUtils;

public class MainBuceo {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        SistemaReservas sistema = new SistemaReservas();
        int opcion;

        //DATOS DE EJEMPLO AL ARRANCAR__________________________________________
        sistema.agregarInmersion(new Inmersion("Isla Hormigas", LocalDate.of(2026, 7, 10), 25, 8, Nivel.ADVANCED));
        sistema.agregarInmersion(new Inmersion("Pecio Avión Hundido", LocalDate.of(2026, 7, 15), 12, 10, Nivel.OPEN_WATER));
        sistema.agregarInmersion(new Inmersion("Cueva Mazarrón", LocalDate.of(2026, 8, 1), 40, 6, Nivel.RESCUE));

        //MENÚ PRINCIPAL_______________________________________________________
        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║    SISTEMA DE RESERVAS DE BUCEO      ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Registrar buceador               ║");
            System.out.println("║  2. Ver buceadores                   ║");
            System.out.println("║  3. Ver inmersiones disponibles      ║");
            System.out.println("║  4. Buscar inmersiones por nivel     ║");
            System.out.println("║  5. Hacer una reserva                ║");
            System.out.println("║  6. Ver todas las reservas           ║");
            System.out.println("║  7. Ver reservas de un buceador      ║");
            System.out.println("║  8. Guardar reservas                 ║");
            System.out.println("║  0. Salir                            ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Elige una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {

                case 1:
                    System.out.print("Nombre del buceador: ");
                    String nombre = scanner.nextLine();
                    System.out.println("Niveles disponibles: OPEN_WATER, ADVANCED, RESCUE, DIVEMASTER");
                    System.out.print("Nivel: ");
                    String nivelBuzo = scanner.nextLine().toUpperCase();
                    try {
                        Nivel nivel = Nivel.valueOf(nivelBuzo);
                        sistema.registrarBuceador(new Buceador(nombre, nivel));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Nivel no válido. Inténtalo de nuevo.");
                    }
                    break;

                case 2:
                    sistema.listarBuceadores();
                    break;

                case 3:
                    sistema.listarInmersiones();
                    break;

                case 4:
                    System.out.println("Niveles disponibles: OPEN_WATER, ADVANCED, RESCUE, DIVEMASTER");
                    System.out.print("Introduce tu nivel: ");
                    String nivelBusqueda = scanner.nextLine().toUpperCase();
                    try {
                        Nivel nivel = Nivel.valueOf(nivelBusqueda);
                        List<Inmersion> resultado = sistema.buscarPorNivel(nivel);
                        if (resultado.isEmpty()) {
                            System.out.println("No hay inmersiones disponibles para ese nivel.");
                        } else {
                            for (Inmersion i : resultado) {
                                System.out.println(i);
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Nivel no válido.");
                    }
                    break;

                case 5:
                    System.out.print("Nombre del buceador: ");
                    String nombreBuceador = scanner.nextLine();
                    System.out.print("Nombre de la inmersión: ");
                    String nombreInmersion = scanner.nextLine();

                    Buceador buceadorEncontrado = null;
                    for (Buceador b : sistema.getBuceadores()) {
                        if (b.getNombre().equalsIgnoreCase(nombreBuceador)) {
                            buceadorEncontrado = b;
                            break;
                        }
                    }

                    Inmersion inmersionEncontrada = null;
                    for (Inmersion i : sistema.getInmersiones()) {
                        if (i.getNombreInmersion().equalsIgnoreCase(nombreInmersion)) {
                            inmersionEncontrada = i;
                            break;
                        }
                    }

                    if (buceadorEncontrado == null) {
                        System.out.println("Buceador no encontrado.");
                    } else if (inmersionEncontrada == null) {
                        System.out.println("Inmersión no encontrada.");
                    } else {
                        sistema.realizarReserva(buceadorEncontrado, inmersionEncontrada);
                    }
                    break;

                case 6:
                    sistema.listarReservas();
                    break;

                case 7:
                    System.out.print("Nombre del buceador: ");
                    String nombreBusqueda = scanner.nextLine();

                    Buceador buceadorBuscado = null;
                    for (Buceador b : sistema.getBuceadores()) {
                        if (b.getNombre().equalsIgnoreCase(nombreBusqueda)) {
                            buceadorBuscado = b;
                            break;
                        }
                    }

                    if (buceadorBuscado == null) {
                        System.out.println("Buceador no encontrado.");
                    } else {
                        List<Reserva> reservas = sistema.reservasDeBuceador(buceadorBuscado);
                        if (reservas.isEmpty()) {
                            System.out.println("Este buceador no tiene reservas.");
                        } else {
                            for (Reserva r : reservas) {
                                System.out.println(r);
                            }
                        }
                    }
                    break;

                case 8:
                    FicheroUtils.guardarReservas(sistema.getReservas());
                    break;

                case 0:
                    System.out.println("Hasta el próximo buceo!");
                    break;

                default:
                    System.out.println("Opción no válida. Elige entre 0 y 8.");
                    break;
            }

        } while (opcion != 0);

        scanner.close();
    }
}
