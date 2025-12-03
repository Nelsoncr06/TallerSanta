package tallerSanta.reportes;

import tallerSanta.modelos.*;
import tallerSanta.gestion.GestorRegalos;
import tallerSanta.gestion.GestorNinos;
import tallerSanta.gestion.GestorAsignaciones;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GeneradorReportes {
    private GestorRegalos gestorRegalos;
    private GestorNinos gestorNinos;
    private GestorAsignaciones gestorAsignaciones;

    public GeneradorReportes(GestorRegalos gestorRegalos, GestorNinos gestorNinos, 
                            GestorAsignaciones gestorAsignaciones) {
        this.gestorRegalos = gestorRegalos;
        this.gestorNinos = gestorNinos;
        this.gestorAsignaciones = gestorAsignaciones;
    }

    public void reporte1_InventarioRegalos() {
        System.out.println("\n=== REPORTE 1: INVENTARIO ACTUAL DE REGALOS ===");
        System.out.println("=".repeat(50));
        
        List<Regalo> regalos = gestorRegalos.getRegalos();
        if (regalos.isEmpty()) {
            System.out.println("📭 No hay regalos en el inventario.");
            return;
        }
        
        int totalRegalos = 0;
        for (Regalo regalo : regalos) {
            System.out.println(regalo);
            totalRegalos += regalo.getCantidad();
        }
        
        System.out.println("=".repeat(50));
        System.out.println("Total de tipos de regalos: " + regalos.size());
        System.out.println("Total de unidades disponibles: " + totalRegalos);
    }

    public void reporte2_ListadoNinos() {
        System.out.println("\n=== REPORTE 2: LISTADO COMPLETO DE NIÑOS REGISTRADOS ===");
        System.out.println("=".repeat(50));
        
        List<Nino> ninos = gestorNinos.getNinos();
        if (ninos.isEmpty()) {
            System.out.println("📭 No hay niños registrados.");
            return;
        }
        
        for (int i = 0; i < ninos.size(); i++) {
            System.out.println((i + 1) + ". " + ninos.get(i));
        }
        
        System.out.println("=".repeat(50));
        System.out.println("Total de niños registrados: " + ninos.size());
    }

    public void reporte3_DetalleAsignaciones() {
        System.out.println("\n=== REPORTE 3: DETALLE DE REGALOS ASIGNADOS ===");
        System.out.println("=".repeat(60));
        
        List<Asignacion> asignaciones = gestorAsignaciones.getAsignaciones();
        if (asignaciones.isEmpty()) {
            System.out.println("📭 No hay asignaciones registradas.");
            return;
        }
        
        for (Asignacion asignacion : asignaciones) {
            Nino nino = gestorNinos.buscarNinoPorId(asignacion.getIdNino());
            Regalo regalo = gestorRegalos.buscarRegaloPorCodigo(asignacion.getCodigoRegalo());
            
            if (nino != null && regalo != null) {
                System.out.println("Niño: " + nino.getNombreCompleto() + 
                                 " (ID: " + nino.getIdentificacion() + ")");
                System.out.println("Regalo: " + regalo.getNombre() + 
                                 " (Código: " + regalo.getCodigo() + ")");
                System.out.println("Fecha: " + asignacion.getFechaAsignacion());
                System.out.println("-".repeat(40));
            }
        }
        
        System.out.println("Total de asignaciones: " + asignaciones.size());
    }

    public void reporte4_NinosSinRegalo() {
        System.out.println("\n=== REPORTE 4: NIÑOS SIN REGALO ASIGNADO ===");
        System.out.println("=".repeat(50));
        
        List<Nino> ninos = gestorNinos.getNinos();
        boolean hayNinosSinRegalo = false;
        
        for (Nino nino : ninos) {
            if (!nino.isTieneRegalo()) {
                System.out.println(nino);
                hayNinosSinRegalo = true;
            }
        }
        
        if (!hayNinosSinRegalo) {
            System.out.println("🎉 ¡Todos los niños tienen regalo asignado!");
        }
    }

    public void reporte5_RegalosPorMarca() {
        System.out.print("\nIngrese la marca a buscar: ");
        String marca = new java.util.Scanner(System.in).nextLine();
        
        System.out.println("\n=== REPORTE 5: REGALOS DE LA MARCA '" + marca.toUpperCase() + "' ===");
        System.out.println("=".repeat(50));
        
        List<Regalo> regalos = gestorRegalos.getRegalos();
        boolean encontrados = false;
        
        // Mostrar en consola
        for (Regalo regalo : regalos) {
            if (regalo.getMarca().equalsIgnoreCase(marca)) {
                System.out.println(regalo);
                encontrados = true;
            }
        }
        
        if (!encontrados) {
            System.out.println("❌ No se encontraron regalos de la marca '" + marca + "'.");
            return;
        }
        
        // Guardar en archivo de texto
        try (FileWriter writer = new FileWriter("regalos_" + marca + ".txt")) {
            writer.write("=== REGALOS DE LA MARCA: " + marca.toUpperCase() + " ===\n");
            writer.write("=".repeat(50) + "\n");
            
            for (Regalo regalo : regalos) {
                if (regalo.getMarca().equalsIgnoreCase(marca)) {
                    writer.write(regalo.toString() + "\n");
                }
            }
            
            writer.write("=".repeat(50) + "\n");
            writer.write("Reporte generado el: " + new java.util.Date() + "\n");
            
            System.out.println("\n✅ Reporte guardado en: regalos_" + marca + ".txt");
        } catch (IOException e) {
            System.out.println("❌ Error al guardar el reporte: " + e.getMessage());
        }
    }
}