package tallerSanta.reportes;

import tallerSanta.modelos.*;
import tallerSanta.gestion.GestorRegalos;
import tallerSanta.gestion.GestorNinos;
import tallerSanta.gestion.GestorAsignaciones;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

// Esta clase se encarga de generar diversos reportes relacionados con los regalos, niños y asignaciones.
 // El generador de reportes utiliza los gestores de regalos, niños y asignaciones para obtener los datos necesarios y generar informes detallados.
 // Proporciona métodos para generar diferentes tipos de reportes, como inventario de regalos, listado de niños, detalle de asignaciones, entre otros.
 // Cada método de reporte imprime la información en la consola y, en un caso, guarda los reportes en un archivo de texto.
public class GeneradorReportes { // Clase que genera diversos reportes relacionados con los regalos, niños y asignaciones.
    private GestorRegalos gestorRegalos;
    private GestorNinos gestorNinos;
    private GestorAsignaciones gestorAsignaciones;

    public GeneradorReportes(GestorRegalos gestorRegalos, GestorNinos gestorNinos, 
                            GestorAsignaciones gestorAsignaciones) { // Constructor que inicializa los gestores necesarios para generar los reportes.
        this.gestorRegalos = gestorRegalos;
        this.gestorNinos = gestorNinos;
        this.gestorAsignaciones = gestorAsignaciones;
    }

    public void reporte1_InventarioRegalos() { // Método para generar el reporte de inventario actual de regalos
        System.out.println("\n=== REPORTE 1: INVENTARIO ACTUAL DE REGALOS ===");
        System.out.println("=".repeat(50)); // Línea separadora
        
        List<Regalo> regalos = gestorRegalos.getRegalos();
        if (regalos.isEmpty()) { // Verificar si hay regalos registrados
            System.out.println("No hay regalos en el inventario.");
            return;
        }
        
        int totalRegalos = 0;
        for (Regalo regalo : regalos) { // Mostrar cada regalo y acumular la cantidad total
            System.out.println(regalo);
            totalRegalos += regalo.getCantidad();
        }
        // Mostrar totales
        System.out.println("=".repeat(50));
        System.out.println("Total de tipos de regalos: " + regalos.size());
        System.out.println("Total de unidades disponibles: " + totalRegalos);
    }

    public void reporte2_ListadoNinos() { // Método para generar el reporte de listado completo de niños registrados
        System.out.println("\n=== REPORTE 2: LISTADO COMPLETO DE NIÑOS REGISTRADOS ===");
        System.out.println("=".repeat(50));
        
        List<Nino> ninos = gestorNinos.getNinos(); // Obtener la lista de niños
        if (ninos.isEmpty()) { // Verificar si hay niños registrados
            System.out.println("No hay niños registrados.");
            return;
        }
        
        for (int i = 0; i < ninos.size(); i++) { // Mostrar cada niño con su índice
            System.out.println((i + 1) + ". " + ninos.get(i)); // Mostrar el niño
        }
        
        System.out.println("=".repeat(50));
        System.out.println("Total de niños registrados: " + ninos.size());
    }

    public void reporte3_DetalleAsignaciones() { // Método para generar el reporte de detalle de regalos asignados a niños
        System.out.println("\n=== REPORTE 3: DETALLE DE REGALOS ASIGNADOS ===");
        System.out.println("=".repeat(60)); // Línea separadora
        
        List<Asignacion> asignaciones = gestorAsignaciones.getAsignaciones(); // Obtener la lista de asignaciones
        if (asignaciones.isEmpty()) { // Verificar si hay asignaciones registradas
            System.out.println("No hay asignaciones registradas.");
            return;
        }
        
        for (Asignacion asignacion : asignaciones) { // Mostrar detalle de cada asignación
            Nino nino = gestorNinos.buscarNinoPorId(asignacion.getIdNino()); // Buscar el niño por su ID
            Regalo regalo = gestorRegalos.buscarRegaloPorCodigo(asignacion.getCodigoRegalo()); // Buscar el regalo por su código
            
            if (nino != null && regalo != null) { // Verificar que ambos existan, niño y regalo para mostrar la información
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

    public void reporte4_NinosSinRegalo() { // Método para generar el reporte de niños sin regalo asignado
        System.out.println("\n=== REPORTE 4: NIÑOS SIN REGALO ASIGNADO ===");
        System.out.println("=".repeat(50));
        
        List<Nino> ninos = gestorNinos.getNinos(); // Obtener la lista de niños
        boolean hayNinosSinRegalo = false; // Bool para verificar si hay niños sin regalo
        
        for (Nino nino : ninos) { // Mostrar solo los niños que no tienen regalo asignado
            if (!nino.isTieneRegalo()) { // Verificar si el niño no tiene regalo
                System.out.println(nino);
                hayNinosSinRegalo = true; // Marcar que se encontró al menos un niño sin regalo
            }
        }
        
        if (!hayNinosSinRegalo) {
            System.out.println("¡Todos los niños tienen regalo asignado!");
        }
    }

    public void reporte5_RegalosPorMarca() { // Método para generar el reporte de regalos por marca
        System.out.print("\nIngrese la marca a buscar: ");
        String marca = new java.util.Scanner(System.in).nextLine();
        
        System.out.println("\n=== REPORTE 5: REGALOS DE LA MARCA '" + marca.toUpperCase() + "' ==="); // Título del reporte
        System.out.println("=".repeat(50));
        
        List<Regalo> regalos = gestorRegalos.getRegalos(); // Obtener la lista de regalos
        boolean encontrados = false; // Bool para verificar si se encontraron regalos de la marca
        
        // Mostrar en consola
        for (Regalo regalo : regalos) { // Recorrer la lista de regalos
            if (regalo.getMarca().equalsIgnoreCase(marca)) { // Comparar la marca ignorando mayúsculas/minúsculas
                System.out.println(regalo);
                encontrados = true; // Marcar que se encontró al menos un regalo de la marca
            }
        }
        
        if (!encontrados) { // Si no se encontraron regalos, mostrar mensaje y salir
            System.out.println("Error, No se encontraron regalos de la marca '" + marca + "'.");
            return;
        }
        
        // Guardar en archivo de texto
        try (FileWriter writer = new FileWriter("regalos_" + marca + ".txt")) { // Crear archivo con nombre basado en la marca
            writer.write("=== REGALOS DE LA MARCA: " + marca.toUpperCase() + " ===\n");
            writer.write("=".repeat(50) + "\n");
            
            for (Regalo regalo : regalos) { // Recorrer la lista de regalos nuevamente para escribir en el archivo
                if (regalo.getMarca().equalsIgnoreCase(marca)) { 
                    writer.write(regalo.toString() + "\n"); // Escribir el regalo en el archivo
                }
            }
            
            writer.write("=".repeat(50) + "\n"); 
            writer.write("Reporte generado el: " + new java.util.Date() + "\n"); // Fecha de generación del reporte
            
            System.out.println("\nExito, Reporte guardado en: regalos_" + marca + ".txt");
        } catch (IOException e) { // Manejar errores de escritura en el archivo
            System.out.println("Error al guardar el reporte: " + e.getMessage());
        }
    }
}