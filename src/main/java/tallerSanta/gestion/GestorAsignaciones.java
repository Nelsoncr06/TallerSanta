package tallerSanta.gestion;

import tallerSanta.modelos.Asignacion;
import tallerSanta.modelos.Nino;
import tallerSanta.modelos.Regalo;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

// Esta clase maneja la lógica relacionada con la asignación de regalos a los niños.
 // El gestor de asignaciones se encarga de manejar la lógica relacionada con la asignación de regalos a los niños.
public class GestorAsignaciones {
    private List<Asignacion> asignaciones;
    private GestorJSON gestorJSON;
    private Scanner scanner;
    private SimpleDateFormat dateFormat;

 // Constructor que inicializa el gestor de asignaciones con un gestor JSON para cargar y guardar datos.
    public GestorAsignaciones(GestorJSON gestorJSON) {
        this.gestorJSON = gestorJSON;
        this.scanner = new Scanner(System.in);
        this.asignaciones = gestorJSON.cargarAsignaciones();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }
    // Método para asignar un regalo a un niño, verificando que el niño no tenga ya un regalo asignado y que haya disponibilidad del regalo.
    public void asignarRegalo(GestorNinos gestorNinos, GestorRegalos gestorRegalos) {
        System.out.println("\n--- ASIGNAR REGALO A NIÑO ---");
        
        System.out.print("Identificación del niño: ");
        String idNino = scanner.nextLine();
        
        Nino nino = gestorNinos.buscarNinoPorId(idNino);
        if (nino == null) {
            System.out.println("No se encontró el niño.");
            return;
        }
        
        // Verificar si ya tiene regalo asignado
        if (nino.isTieneRegalo()) {
            System.out.println("Error, El niño ya tiene un regalo asignado.");
            return;
        }
        
        System.out.print("Código del regalo: ");
        String codigoRegalo = scanner.nextLine();
        
        Regalo regalo = gestorRegalos.buscarRegaloPorCodigo(codigoRegalo);
        if (regalo == null) {
            System.out.println("Error, No se encontró el regalo.");
            return;
        }
        
        // Verificar si hay cantidad disponible
        if (regalo.getCantidad() <= 0) {
            System.out.println("Error, No hay cantidad disponible de este regalo.");
            return;
        }
        
        // Crear asignación y agregarla a la lista , con la fecha actual
        String fecha = dateFormat.format(new Date());
        Asignacion nuevaAsignacion = new Asignacion(idNino, codigoRegalo, fecha);
        asignaciones.add(nuevaAsignacion);
        
        // Descontar cantidad del regalo
        if (gestorRegalos.descontarCantidad(codigoRegalo)) {
            // Marcar al niño como que tiene regalo
            gestorNinos.marcarComoConRegalo(idNino);
            
            // Guardar asignaciones, niños y regalos actualizados
            gestorJSON.guardarAsignaciones(asignaciones);
            
            System.out.println("Regalo asignado con éxito.");
            System.out.println("Fecha de asignación: " + fecha);
        } else {
            System.out.println("Error al descontar la cantidad del regalo.");
        }
    }

    // Método para buscar y mostrar todas las asignaciones de un niño dado su identificación.
    public void buscarAsignacionPorNino() {
        System.out.print("\nIdentificación del niño: ");
        String idNino = scanner.nextLine();
        // bool para verificar si se encontraron asignaciones, en caso contrario mostrar mensaje
        boolean encontrada = false; 
        System.out.println("\n=== ASIGNACIONES DEL NIÑO ===");
        
        // Recorrer la lista de asignaciones y mostrar las que coincidan con el ID del niño
        for (Asignacion asignacion : asignaciones) {
            if (asignacion.getIdNino().equals(idNino)) {
                System.out.println(asignacion);
                encontrada = true;
            }
        }
        
        if (!encontrada) { // Ninguna asignación encontrada
            System.out.println("El niño no posee asignaciones.");
        }
    }

    public List<Asignacion> getAsignaciones() { // Método getter para obtener la lista de asignaciones
        return asignaciones;
    }

    // Método para obtener el código del regalo asignado a un niño dado su identificación.
    public String obtenerRegaloAsignado(String idNino) {
        for (Asignacion asignacion : asignaciones) { // Recorrer la lista de asignaciones
            if (asignacion.getIdNino().equals(idNino)) {
                return asignacion.getCodigoRegalo();
            }
        }
        return null; // Retorna null si no se encuentra ninguna asignación
    }
}