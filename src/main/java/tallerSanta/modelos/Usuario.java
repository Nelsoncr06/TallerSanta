package tallerSanta.modelos;

// Clase que representa un usuario con nombre de usuario y contraseña.
// Contiene getters y setters para cada atributo, así como un método toString para representar el usuario en formato de cadena.
public class Usuario {
    private String username;
    private String password;

    public Usuario() {}

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters y Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() { // Representación en cadena del usuario
        return "Usuario: " + username;
    }
}