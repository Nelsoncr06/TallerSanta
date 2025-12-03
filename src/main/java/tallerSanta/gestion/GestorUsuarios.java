package tallerSanta.gestion;

import tallerSanta.modelos.Usuario;
import java.util.List;
import java.util.Scanner;

public class GestorUsuarios {
    private List<Usuario> usuarios;
    private GestorJSON gestorJSON;
    private Scanner scanner;

    public GestorUsuarios(GestorJSON gestorJSON) {
        this.gestorJSON = gestorJSON;
        this.scanner = new Scanner(System.in);
        this.usuarios = gestorJSON.cargarUsuarios();
        
        // Usuario por defecto
        if (usuarios.isEmpty()) {
            usuarios.add(new Usuario("admin", "admin123"));
            gestorJSON.guardarUsuarios(usuarios);
        }
    }

    public boolean registrarUsuario() {
        System.out.print("Nombre de usuario: ");
        String username = scanner.nextLine();
        
        // Validar si el usuario ya existe
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                System.out.println("❌ El usuario ya existe.");
                return false;
            }
        }
        
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();
        
        usuarios.add(new Usuario(username, password));
        gestorJSON.guardarUsuarios(usuarios);
        System.out.println("✅ Usuario registrado con éxito.");
        return true;
    }

    public boolean iniciarSesion() {
        System.out.print("Nombre de usuario: ");
        String username = scanner.nextLine();
        
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();
        
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && 
                usuario.getPassword().equals(password)) {
                System.out.println("✅ ¡Bienvenido " + username + "!");
                return true;
            }
        }
        
        System.out.println("❌ Credenciales incorrectas.");
        return false;
    }
}