package tallerSanta.gestion;

import tallerSanta.modelos.Nino;
import tallerSanta.modelos.Asignacion;
import java.util.List;
import java.util.Scanner;

public class GestorNinos {
    private List<Nino> ninos;
    private GestorJSON gestorJSON;
    private Scanner scanner;

    public GestorNinos(GestorJSON gestorJSON) {
        this.gestorJSON = gestorJSON;
        this.scanner = new Scanner(System.in);
        this.ninos = gestorJSON.cargarNinos();
    }

    public void registrarNino() {
        System.out.println("\n--- REGISTRAR NUEVO NIÑO ---");
        
        System.out.print("Identificación: ");
        String id = scanner.nextLine();
        
        // Validar si la identificación ya existe
        for (Nino nino : ninos) {
            if (nino.getIdentificacion().equals(id)) {
                System.out.println("❌ Ya existe un niño con esta identificación.");
                return;
            }
        }
        
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        
        int edad;
        while (true) {
            System.out.print("Edad: ");
            try {
                edad = Integer.parseInt(scanner.nextLine());
                if (edad <= 0 || edad > 18) {
                    System.out.println("❌ La edad debe estar entre 1 y 18 años.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("❌ La edad debe ser un número entero.");
            }
        }
        
        System.out.print("Ciudad: ");
        String ciudad = scanner.nextLine();
        
        System.out.print("Dirección detallada: ");
        String direccion = scanner.nextLine();
        
        ninos.add(new Nino(id, nombre, edad, ciudad, direccion));
        gestorJSON.guardarNinos(ninos);
        System.out.println("✅ Niño registrado con éxito.");
    }

    public void modificarNino() {
        System.out.print("\nIdentificación del niño a modificar: ");
        String id = scanner.nextLine();
        
        for (Nino nino : ninos) {
            if (nino.getIdentificacion().equals(id)) {
                System.out.println("Niño actual: " + nino);
                
                System.out.print("Nuevo nombre (actual: " + nino.getNombreCompleto() + "): ");
                String nuevoNombre = scanner.nextLine();
                if (!nuevoNombre.isEmpty()) {
                    nino.setNombreCompleto(nuevoNombre);
                }
                
                System.out.print("Nueva edad (actual: " + nino.getEdad() + "): ");
                String nuevaEdad = scanner.nextLine();
                if (!nuevaEdad.isEmpty()) {
                    try {
                        nino.setEdad(Integer.parseInt(nuevaEdad));
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Edad inválida.");
                    }
                }
                
                System.out.print("Nueva ciudad: ");
                String nuevaCiudad = scanner.nextLine();
                if (!nuevaCiudad.isEmpty()) {
                    nino.setCiudad(nuevaCiudad);
                }
                
                System.out.print("Nueva dirección: ");
                String nuevaDireccion = scanner.nextLine();
                if (!nuevaDireccion.isEmpty()) {
                    nino.setDireccion(nuevaDireccion);
                }
                
                gestorJSON.guardarNinos(ninos);
                System.out.println("✅ Niño modificado con éxito.");
                return;
            }
        }
        
        System.out.println("❌ No se encontró el niño.");
    }

    public void eliminarNino(List<Asignacion> asignaciones) {
        System.out.print("\nIdentificación del niño a eliminar: ");
        String id = scanner.nextLine();
        
        // Verificar si el niño tiene regalo asignado
        for (Asignacion asignacion : asignaciones) {
            if (asignacion.getIdNino().equals(id)) {
                System.out.println("❌ No se puede eliminar, el niño tiene un regalo asignado.");
                return;
            }
        }
        
        for (int i = 0; i < ninos.size(); i++) {
            if (ninos.get(i).getIdentificacion().equals(id)) {
                ninos.remove(i);
                gestorJSON.guardarNinos(ninos);
                System.out.println("✅ Niño eliminado con éxito.");
                return;
            }
        }
        
        System.out.println("❌ No se encontró el niño.");
    }

    public Nino buscarNinoPorId(String id) {
        for (Nino nino : ninos) {
            if (nino.getIdentificacion().equals(id)) {
                return nino;
            }
        }
        return null;
    }

    public void consultarNino() {
        System.out.print("\nIdentificación del niño a consultar: ");
        String id = scanner.nextLine();
        
        Nino nino = buscarNinoPorId(id);
        if (nino != null) {
            System.out.println("\n=== INFORMACIÓN DEL NIÑO ===");
            System.out.println("Identificación: " + nino.getIdentificacion());
            System.out.println("Nombre: " + nino.getNombreCompleto());
            System.out.println("Edad: " + nino.getEdad());
            System.out.println("Ciudad: " + nino.getCiudad());
            System.out.println("Dirección: " + nino.getDireccion());
            System.out.println("Tiene regalo asignado: " + (nino.isTieneRegalo() ? "Sí" : "No"));
        } else {
            System.out.println("❌ No se encontró el niño.");
        }
    }

    public List<Nino> getNinos() {
        return ninos;
    }

    public void marcarComoConRegalo(String idNino) {
        Nino nino = buscarNinoPorId(idNino);
        if (nino != null) {
            nino.setTieneRegalo(true);
            gestorJSON.guardarNinos(ninos);
        }
    }
}