package tallerSanta.gestion;

import tallerSanta.modelos.Regalo;
import tallerSanta.modelos.Asignacion;
import java.util.List;
import java.util.Scanner;

public class GestorRegalos {
    private List<Regalo> regalos;
    private GestorJSON gestorJSON;
    private Scanner scanner;

    public GestorRegalos(GestorJSON gestorJSON) {
        this.gestorJSON = gestorJSON;
        this.scanner = new Scanner(System.in);
        this.regalos = gestorJSON.cargarRegalos();
    }

    public void registrarRegalo() {
        System.out.println("\n--- REGISTRAR NUEVO REGALO ---");
        
        System.out.print("Código del regalo: ");
        String codigo = scanner.nextLine();
        
        // Validar si el código ya existe
        for (Regalo regalo : regalos) {
            if (regalo.getCodigo().equals(codigo)) {
                System.out.println("❌ Ya existe un regalo con este código.");
                return;
            }
        }
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        
        System.out.print("Marca: ");
        String marca = scanner.nextLine();
        
        int cantidad;
        while (true) {
            System.out.print("Cantidad disponible: ");
            try {
                cantidad = Integer.parseInt(scanner.nextLine());
                if (cantidad <= 0) {
                    System.out.println("❌ La cantidad debe ser mayor a cero.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("❌ La cantidad debe ser un número entero.");
            }
        }
        
        regalos.add(new Regalo(codigo, nombre, descripcion, marca, cantidad));
        gestorJSON.guardarRegalos(regalos);
        System.out.println("✅ Regalo registrado con éxito.");
    }

    public void modificarRegalo() {
        System.out.print("\nCódigo del regalo a modificar: ");
        String codigo = scanner.nextLine();
        
        for (Regalo regalo : regalos) {
            if (regalo.getCodigo().equals(codigo)) {
                System.out.println("Regalo actual: " + regalo);
                
                System.out.print("Nuevo nombre (actual: " + regalo.getNombre() + "): ");
                String nuevoNombre = scanner.nextLine();
                if (!nuevoNombre.isEmpty()) {
                    regalo.setNombre(nuevoNombre);
                }
                
                System.out.print("Nueva descripción: ");
                String nuevaDesc = scanner.nextLine();
                if (!nuevaDesc.isEmpty()) {
                    regalo.setDescripcion(nuevaDesc);
                }
                
                System.out.print("Nueva marca: ");
                String nuevaMarca = scanner.nextLine();
                if (!nuevaMarca.isEmpty()) {
                    regalo.setMarca(nuevaMarca);
                }
                
                System.out.print("¿Reabastecer cantidad? (s/n): ");
                if (scanner.nextLine().equalsIgnoreCase("s")) {
                    System.out.print("Cantidad a agregar: ");
                    try {
                        int agregar = Integer.parseInt(scanner.nextLine());
                        regalo.setCantidad(regalo.getCantidad() + agregar);
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Cantidad inválida.");
                    }
                }
                
                gestorJSON.guardarRegalos(regalos);
                System.out.println("✅ Regalo modificado con éxito.");
                return;
            }
        }
        
        System.out.println("❌ No se encontró el regalo.");
    }

    public void eliminarRegalo(List<Asignacion> asignaciones) {
        System.out.print("\nCódigo del regalo a eliminar: ");
        String codigo = scanner.nextLine();
        
        // Verificar si el regalo está asignado a algún niño
        for (Asignacion asignacion : asignaciones) {
            if (asignacion.getCodigoRegalo().equals(codigo)) {
                System.out.println("❌ No se puede eliminar, el regalo está asignado a un niño.");
                return;
            }
        }
        
        for (int i = 0; i < regalos.size(); i++) {
            if (regalos.get(i).getCodigo().equals(codigo)) {
                regalos.remove(i);
                gestorJSON.guardarRegalos(regalos);
                System.out.println("✅ Regalo eliminado con éxito.");
                return;
            }
        }
        
        System.out.println("❌ No se encontró el regalo.");
    }

    public Regalo buscarRegaloPorCodigo(String codigo) {
        for (Regalo regalo : regalos) {
            if (regalo.getCodigo().equals(codigo)) {
                return regalo;
            }
        }
        return null;
    }

    public void consultarRegalo() {
        System.out.print("\nCódigo del regalo a consultar: ");
        String codigo = scanner.nextLine();
        
        Regalo regalo = buscarRegaloPorCodigo(codigo);
        if (regalo != null) {
            System.out.println("\n=== INFORMACIÓN DEL REGALO ===");
            System.out.println("Código: " + regalo.getCodigo());
            System.out.println("Nombre: " + regalo.getNombre());
            System.out.println("Descripción: " + regalo.getDescripcion());
            System.out.println("Marca: " + regalo.getMarca());
            System.out.println("Cantidad disponible: " + regalo.getCantidad());
        } else {
            System.out.println("❌ No se encontró el regalo.");
        }
    }

    public List<Regalo> getRegalos() {
        return regalos;
    }

    public boolean descontarCantidad(String codigo) {
        Regalo regalo = buscarRegaloPorCodigo(codigo);
        if (regalo != null && regalo.getCantidad() > 0) {
            regalo.setCantidad(regalo.getCantidad() - 1);
            gestorJSON.guardarRegalos(regalos);
            return true;
        }
        return false;
    }

    public void reabastecerRegalo() {
        System.out.print("\nCódigo del regalo a reabastecer: ");
        String codigo = scanner.nextLine();
        
        Regalo regalo = buscarRegaloPorCodigo(codigo);
        if (regalo != null) {
            System.out.println("Cantidad actual: " + regalo.getCantidad());
            System.out.print("Cantidad a agregar: ");
            
            try {
                int agregar = Integer.parseInt(scanner.nextLine());
                if (agregar <= 0) {
                    System.out.println("❌ La cantidad debe ser mayor a cero.");
                    return;
                }
                
                regalo.setCantidad(regalo.getCantidad() + agregar);
                gestorJSON.guardarRegalos(regalos);
                System.out.println("✅ Regalo reabastecido. Nueva cantidad: " + regalo.getCantidad());
            } catch (NumberFormatException e) {
                System.out.println("❌ Cantidad inválida.");
            }
        } else {
            System.out.println("❌ No se encontró el regalo.");
        }
    }
}