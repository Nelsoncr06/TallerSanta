package tallerSanta.gestion;

import tallerSanta.modelos.Asignacion;
import tallerSanta.modelos.Nino;
import tallerSanta.modelos.Regalo;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class GestorAsignaciones {
    private List<Asignacion> asignaciones;
    private GestorJSON gestorJSON;
    private Scanner scanner;
    private SimpleDateFormat dateFormat;

    public GestorAsignaciones(GestorJSON gestorJSON) {
        this.gestorJSON = gestorJSON;
        this.scanner = new Scanner(System.in);
        this.asignaciones = gestorJSON.cargarAsignaciones();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    public void asignarRegalo(GestorNinos gestorNinos, GestorRegalos gestorRegalos) {
        System.out.println("\n--- ASIGNAR REGALO A NIÑO ---");
        
        System.out.print("Identificación del niño: ");
        String idNino = scanner.nextLine();
        
        Nino nino = gestorNinos.buscarNinoPorId(idNino);
        if (nino == null) {
            System.out.println("❌ No se encontró el niño.");
            return;
        }
        
        // Verificar si ya tiene regalo asignado
        if (nino.isTieneRegalo()) {
            System.out.println("❌ El niño ya tiene un regalo asignado.");
            return;
        }
        
        System.out.print("Código del regalo: ");
        String codigoRegalo = scanner.nextLine();
        
        Regalo regalo = gestorRegalos.buscarRegaloPorCodigo(codigoRegalo);
        if (regalo == null) {
            System.out.println("❌ No se encontró el regalo.");
            return;
        }
        
        // Verificar si hay cantidad disponible
        if (regalo.getCantidad() <= 0) {
            System.out.println("❌ No hay cantidad disponible de este regalo.");
            return;
        }
        
        // Crear asignación
        String fecha = dateFormat.format(new Date());
        Asignacion nuevaAsignacion = new Asignacion(idNino, codigoRegalo, fecha);
        asignaciones.add(nuevaAsignacion);
        
        // Descontar cantidad del regalo
        if (gestorRegalos.descontarCantidad(codigoRegalo)) {
            // Marcar niño como con regalo
            gestorNinos.marcarComoConRegalo(idNino);
            
            // Guardar asignaciones
            gestorJSON.guardarAsignaciones(asignaciones);
            
            System.out.println("✅ Regalo asignado con éxito.");
            System.out.println("Fecha de asignación: " + fecha);
        } else {
            System.out.println("❌ Error al descontar la cantidad del regalo.");
        }
    }

    public void buscarAsignacionPorNino() {
        System.out.print("\nIdentificación del niño: ");
        String idNino = scanner.nextLine();
        
        boolean encontrada = false;
        System.out.println("\n=== ASIGNACIONES DEL NIÑO ===");
        
        for (Asignacion asignacion : asignaciones) {
            if (asignacion.getIdNino().equals(idNino)) {
                System.out.println(asignacion);
                encontrada = true;
            }
        }
        
        if (!encontrada) {
            System.out.println("📭 El niño no posee asignaciones.");
        }
    }

    public List<Asignacion> getAsignaciones() {
        return asignaciones;
    }

    public String obtenerRegaloAsignado(String idNino) {
        for (Asignacion asignacion : asignaciones) {
            if (asignacion.getIdNino().equals(idNino)) {
                return asignacion.getCodigoRegalo();
            }
        }
        return null;
    }
}