package tallerSanta;

import tallerSanta.gestion.*;
import tallerSanta.reportes.GeneradorReportes;
import java.util.Scanner;

public class TallerSantaMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("🎅 ===========================================");
        System.out.println("🎅    BIENVENIDO AL TALLER DE SANTA");
        System.out.println("🎅 ===========================================");
        
        // Inicializar gestores
        GestorJSON gestorJSON = new GestorJSON();
        GestorUsuarios gestorUsuarios = new GestorUsuarios(gestorJSON);
        
        // Menú de autenticación
        boolean autenticado = false;
        while (!autenticado) {
            System.out.println("\n=== SISTEMA DE AUTENTICACIÓN ===");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            switch (opcion) {
                case 1:
                    autenticado = gestorUsuarios.iniciarSesion();
                    break;
                case 2:
                    gestorUsuarios.registrarUsuario();
                    break;
                case 3:
                    System.out.println("👋 ¡Hasta pronto!");
                    scanner.close();
                    return;
                default:
                    System.out.println("❌ Opción no válida.");
            }
        }
        
        // Inicializar el resto de gestores después de la autenticación
        GestorRegalos gestorRegalos = new GestorRegalos(gestorJSON);
        GestorNinos gestorNinos = new GestorNinos(gestorJSON);
        GestorAsignaciones gestorAsignaciones = new GestorAsignaciones(gestorJSON);
        GeneradorReportes generadorReportes = new GeneradorReportes(
            gestorRegalos, gestorNinos, gestorAsignaciones
        );
        
        // Menú principal
        boolean salir = false;
        while (!salir) {
            System.out.println("\n🎅 ===== TALLER DE SANTA - MENÚ PRINCIPAL =====");
            System.out.println("1. Gestión de Regalos");
            System.out.println("2. Gestión de Niños");
            System.out.println("3. Asignación de Regalos");
            System.out.println("4. Reportes");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            
            try {
                int opcionPrincipal = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                switch (opcionPrincipal) {
                    case 1:
                        menuGestionRegalos(gestorRegalos, gestorAsignaciones, scanner);
                        break;
                    case 2:
                        menuGestionNinos(gestorNinos, gestorAsignaciones, scanner);
                        break;
                    case 3:
                        menuAsignaciones(gestorRegalos, gestorNinos, gestorAsignaciones, scanner);
                        break;
                    case 4:
                        menuReportes(generadorReportes, scanner);
                        break;
                    case 5:
                        salir = true;
                        System.out.println("🎅 ¡Gracias por usar el Taller de Santa!");
                        break;
                    default:
                        System.out.println("❌ Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
                scanner.nextLine(); // Limpiar buffer en caso de error
            }
        }
        
        scanner.close();
    }
    
    private static void menuGestionRegalos(GestorRegalos gestorRegalos, 
                                         GestorAsignaciones gestorAsignaciones,
                                         Scanner scanner) {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("\n📦 === GESTIÓN DE REGALOS ===");
            System.out.println("1. Registrar nuevo regalo");
            System.out.println("2. Modificar regalo");
            System.out.println("3. Eliminar regalo");
            System.out.println("4. Consultar regalo por código");
            System.out.println("5. Reabastecer inventario");
            System.out.println("6. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion) {
                case 1:
                    gestorRegalos.registrarRegalo();
                    break;
                case 2:
                    gestorRegalos.modificarRegalo();
                    break;
                case 3:
                    gestorRegalos.eliminarRegalo(gestorAsignaciones.getAsignaciones());
                    break;
                case 4:
                    gestorRegalos.consultarRegalo();
                    break;
                case 5:
                    gestorRegalos.reabastecerRegalo();
                    break;
                case 6:
                    regresar = true;
                    break;
                default:
                    System.out.println("❌ Opción no válida.");
            }
        }
    }
    
    private static void menuGestionNinos(GestorNinos gestorNinos,
                                       GestorAsignaciones gestorAsignaciones,
                                       Scanner scanner) {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("\n👦 === GESTIÓN DE NIÑOS ===");
            System.out.println("1. Registrar nuevo niño");
            System.out.println("2. Modificar datos de niño");
            System.out.println("3. Eliminar registro de niño");
            System.out.println("4. Consultar niño por identificación");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion) {
                case 1:
                    gestorNinos.registrarNino();
                    break;
                case 2:
                    gestorNinos.modificarNino();
                    break;
                case 3:
                    gestorNinos.eliminarNino(gestorAsignaciones.getAsignaciones());
                    break;
                case 4:
                    gestorNinos.consultarNino();
                    break;
                case 5:
                    regresar = true;
                    break;
                default:
                    System.out.println("❌ Opción no válida.");
            }
        }
    }
    
    private static void menuAsignaciones(GestorRegalos gestorRegalos,
                                       GestorNinos gestorNinos,
                                       GestorAsignaciones gestorAsignaciones,
                                       Scanner scanner) {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("\n🎁 === ASIGNACIÓN DE REGALOS ===");
            System.out.println("1. Asignar regalo a niño");
            System.out.println("2. Buscar asignaciones por niño");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion) {
                case 1:
                    gestorAsignaciones.asignarRegalo(gestorNinos, gestorRegalos);
                    break;
                case 2:
                    gestorAsignaciones.buscarAsignacionPorNino();
                    break;
                case 3:
                    regresar = true;
                    break;
                default:
                    System.out.println("❌ Opción no válida.");
            }
        }
    }
    
    private static void menuReportes(GeneradorReportes generadorReportes,
                                   Scanner scanner) {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("\n📊 === GENERACIÓN DE REPORTES ===");
            System.out.println("1. Inventario actual de regalos");
            System.out.println("2. Listado completo de niños registrados");
            System.out.println("3. Detalle de regalos asignados a cada niño");
            System.out.println("4. Listado de niños sin regalo asignado");
            System.out.println("5. Regalos por marca (guardar en archivo)");
            System.out.println("6. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion) {
                case 1:
                    generadorReportes.reporte1_InventarioRegalos();
                    break;
                case 2:
                    generadorReportes.reporte2_ListadoNinos();
                    break;
                case 3:
                    generadorReportes.reporte3_DetalleAsignaciones();
                    break;
                case 4:
                    generadorReportes.reporte4_NinosSinRegalo();
                    break;
                case 5:
                    generadorReportes.reporte5_RegalosPorMarca();
                    break;
                case 6:
                    regresar = true;
                    break;
                default:
                    System.out.println("❌ Opción no válida.");
            }
        }
    }
}