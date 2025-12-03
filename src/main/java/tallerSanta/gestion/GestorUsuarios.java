package tallerSanta.gestion;

import tallerSanta.modelos.Usuario;
import java.util.List;
import java.util.Scanner;

// Esta clase maneja el registro e inicio de sesión de usuarios.
 // El gestor de usuarios se encarga de manejar el registro e inicio de sesión de usuarios.
 // Proporciona métodos para registrar nuevos usuarios y autenticar usuarios existentes.
 // Utiliza un gestor JSON para cargar y guardar los datos de los usuarios en un archivo JSON.
 // Aquí se definen los métodos necesarios para interactuar con la lista de usuarios y realizar operaciones relacionadas.
public class GestorUsuarios {
    private List<Usuario> usuarios;
    private GestorJSON gestorJSON;
    private Scanner scanner;

    public GestorUsuarios(GestorJSON gestorJSON) {
        this.gestorJSON = gestorJSON;
        this.scanner = new Scanner(System.in);
        this.usuarios = gestorJSON.cargarUsuarios();
        
        // Usuario por defecto
        if (usuarios.isEmpty()) { // Si no hay usuarios, crear uno por defecto con las siguientes credenciales
            usuarios.add(new Usuario("admin", "admin123"));
            gestorJSON.guardarUsuarios(usuarios); // Guardar el usuario por defecto en el archivo JSON
        }
    }

    public boolean registrarUsuario() { // Método para registrar un nuevo usuario
        System.out.print("Nombre de usuario: ");
        String username = scanner.nextLine();
        
        // Validar si el usuario ya existe
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                System.out.println("Error, El usuario ya existe.");
                return false;
            }
        }
        
        // Solicitar la contraseña
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();
        
        // Agregar el nuevo usuario a la lista y guardar los cambios
        usuarios.add(new Usuario(username, password));
        gestorJSON.guardarUsuarios(usuarios);
        System.out.println("Exito, Usuario registrado con éxito.");
        return true;
    }

    public boolean iniciarSesion() { // Método para iniciar sesión de un usuario existente
        System.out.print("Nombre de usuario: ");
        String username = scanner.nextLine();
        
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();
        
        for (Usuario usuario : usuarios) { // Verificar las credenciales del usuario
            if (usuario.getUsername().equals(username) && 
                usuario.getPassword().equals(password)) {
                System.out.println("Exito, ¡Bienvenido " + username + "!");
                return true;
            }
        }
        
        System.out.println("Error, Credenciales incorrectas.");
        return false;
    }
}