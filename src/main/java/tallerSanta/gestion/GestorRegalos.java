package tallerSanta.gestion;

import tallerSanta.modelos.Regalo;
import tallerSanta.modelos.Asignacion;
import java.util.List;
import java.util.Scanner;

// Esta clase maneja el registro, modificación, eliminación y consulta de regalos.
 // El gestor de regalos se encarga de manejar el registro, modificación, eliminación y consulta de regalos.
 // Proporciona métodos para interactuar con la lista de regalos y realizar operaciones relacionadas.
public class GestorRegalos {
    private List<Regalo> regalos;
    private GestorJSON gestorJSON;
    private Scanner scanner;

    public GestorRegalos(GestorJSON gestorJSON) { // Constructor que inicializa el gestor de regalos con un gestor JSON para cargar y guardar datos.
        this.gestorJSON = gestorJSON;
        this.scanner = new Scanner(System.in);
        this.regalos = gestorJSON.cargarRegalos(); // Cargar la lista de regalos desde el archivo JSON
    }

    public void registrarRegalo() {
        System.out.println("\n--- REGISTRAR NUEVO REGALO ---");
        
        System.out.print("Código del regalo: ");
        String codigo = scanner.nextLine();
        
        // Validar si el código ya existe
        for (Regalo regalo : regalos) {
            if (regalo.getCodigo().equals(codigo)) {
                System.out.println("Error, Ya existe un regalo con este código.");
                return;
            }
        }
        
        // Solicitar otros datos del regalo
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        
        System.out.print("Marca: ");
        String marca = scanner.nextLine();
        
        int cantidad;
        while (true) { // Validar que la cantidad sea un número entero positivo
            System.out.print("Cantidad disponible: ");
            try {
                cantidad = Integer.parseInt(scanner.nextLine());
                if (cantidad <= 0) {
                    System.out.println("Error, La cantidad debe ser mayor a cero.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error, La cantidad debe ser un número entero.");
            }
        }
        
        // Agregar el nuevo regalo a la lista y guardar los cambios
        regalos.add(new Regalo(codigo, nombre, descripcion, marca, cantidad));
        gestorJSON.guardarRegalos(regalos);
        System.out.println("Exito, Regalo registrado con éxito.");
    }

    public void modificarRegalo() { // Método para modificar los datos de un regalo existente
        System.out.print("\nCódigo del regalo a modificar: ");
        String codigo = scanner.nextLine();
        
        for (Regalo regalo : regalos) { // Buscar el regalo por código
            if (regalo.getCodigo().equals(codigo)) { // Si se encuentra el regalo, solicitar nuevos datos
                System.out.println("Regalo actual: " + regalo);
                
                System.out.print("Nuevo nombre (actual: " + regalo.getNombre() + "): ");
                String nuevoNombre = scanner.nextLine();
                if (!nuevoNombre.isEmpty()) { // Si se ingresa un nuevo nombre, actualizarlo
                    regalo.setNombre(nuevoNombre);
                }
                
                System.out.print("Nueva descripción: ");
                String nuevaDesc = scanner.nextLine();
                if (!nuevaDesc.isEmpty()) { // Si se ingresa una nueva descripción, actualizarla
                    regalo.setDescripcion(nuevaDesc);
                }
                
                System.out.print("Nueva marca: ");
                String nuevaMarca = scanner.nextLine();
                if (!nuevaMarca.isEmpty()) { // Si se ingresa una nueva marca, actualizarla
                    regalo.setMarca(nuevaMarca);
                }
                
                System.out.print("¿Reabastecer cantidad? (s/n): ");
                if (scanner.nextLine().equalsIgnoreCase("s")) { // Si se desea reabastecer, solicitar la cantidad a agregar
                    System.out.print("Cantidad a agregar: ");
                    try {
                        int agregar = Integer.parseInt(scanner.nextLine());
                        regalo.setCantidad(regalo.getCantidad() + agregar);
                    } catch (NumberFormatException e) {
                        System.out.println("Error, Cantidad inválida.");
                    }
                }
                
                gestorJSON.guardarRegalos(regalos); // Guardar los cambios en el archivo JSON
                System.out.println("Exito, Regalo modificado con éxito.");
                return;
            }
        }
        
        System.out.println("Error, No se encontró el regalo.");
    }

    public void eliminarRegalo(List<Asignacion> asignaciones) { // Método para eliminar un regalo, verificando que no esté asignado a ningún niño
        System.out.print("\nCódigo del regalo a eliminar: ");
        String codigo = scanner.nextLine();
        
        // Verificar si el regalo está asignado a algún niño
        for (Asignacion asignacion : asignaciones) {
            if (asignacion.getCodigoRegalo().equals(codigo)) {
                System.out.println("Error, No se puede eliminar, el regalo está asignado a un niño.");
                return;
            }
        }
        
        for (int i = 0; i < regalos.size(); i++) { // Buscar el regalo por código para eliminarlo
            if (regalos.get(i).getCodigo().equals(codigo)) { 
                regalos.remove(i); // Eliminar el regalo de la lista
                gestorJSON.guardarRegalos(regalos);
                System.out.println("Exito, Regalo eliminado con éxito.");
                return;
            }
        }
        
        System.out.println("Error, No se encontró el regalo.");
    }

    public Regalo buscarRegaloPorCodigo(String codigo) { // Método para buscar un regalo por su código
        for (Regalo regalo : regalos) { // Recorrer la lista de regalos
            if (regalo.getCodigo().equals(codigo)) { // Si se encuentra el regalo, retornarlo
                return regalo;
            }
        }
        return null;
    }

    public void consultarRegalo() { // Método para consultar la información de un regalo por su código
        System.out.print("\nCódigo del regalo a consultar: ");
        String codigo = scanner.nextLine();
        
        Regalo regalo = buscarRegaloPorCodigo(codigo);
        if (regalo != null) { // Si se encuentra el regalo, mostrar su información
            System.out.println("\n=== INFORMACION DEL REGALO ===");
            System.out.println("Código: " + regalo.getCodigo());
            System.out.println("Nombre: " + regalo.getNombre());
            System.out.println("Descripción: " + regalo.getDescripcion());
            System.out.println("Marca: " + regalo.getMarca());
            System.out.println("Cantidad disponible: " + regalo.getCantidad());
        } else {
            System.out.println("Error, No se encontró el regalo.");
        }
    }

    public List<Regalo> getRegalos() { // Método para obtener la lista de regalos
        return regalos;
    }

    public boolean descontarCantidad(String codigo) { // Método para descontar la cantidad disponible de un regalo al asignarlo
        Regalo regalo = buscarRegaloPorCodigo(codigo);
        if (regalo != null && regalo.getCantidad() > 0) { // Verificar que el regalo exista y tenga cantidad disponible
            regalo.setCantidad(regalo.getCantidad() - 1); // Descontar 1 de la cantidad
            gestorJSON.guardarRegalos(regalos); // Guardar los cambios en el archivo JSON
            return true; // Retornar true si se descontó exitosamente
        }
        return false; // Retornar false si no se pudo descontar
    }

    public void reabastecerRegalo() {  // Método para reabastecer la cantidad de un regalo existente
        System.out.print("\nCódigo del regalo a reabastecer: ");
        String codigo = scanner.nextLine();
        
        Regalo regalo = buscarRegaloPorCodigo(codigo);
        if (regalo != null) { // Si se encuentra el regalo, solicitar la cantidad a agregar
            System.out.println("Cantidad actual: " + regalo.getCantidad());
            System.out.print("Cantidad a agregar: ");
            
            try { // Validar que la cantidad ingresada sea un número entero positivo
                int agregar = Integer.parseInt(scanner.nextLine());
                if (agregar <= 0) {
                    System.out.println("Error, La cantidad debe ser mayor a cero.");
                    return;
                }
                
                regalo.setCantidad(regalo.getCantidad() + agregar); // Actualizar la cantidad del regalo
                gestorJSON.guardarRegalos(regalos); // Guardar los cambios en el archivo JSON
                System.out.println("Exito, Regalo reabastecido. Nueva cantidad: " + regalo.getCantidad());
            } catch (NumberFormatException e) { // Manejar error de formato numérico
                System.out.println("Error, Cantidad inválida.");
            }
        } else {
            System.out.println("Error, No se encontró el regalo.");
        }
    }
}