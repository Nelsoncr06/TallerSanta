package tallerSanta.modelos;

// Clase que representa a un niño con sus datos personales y si ha recibido un regalo.
// Contiene atributos como identificación, nombre completo, edad, ciudad, dirección y un indicador de si tiene regalo asignado.
public class Nino {
    private String identificacion;
    private String nombreCompleto;
    private int edad;
    private String ciudad;
    private String direccion;
    private boolean tieneRegalo;

    public Nino() {
        this.tieneRegalo = false;
    }

    public Nino(String identificacion, String nombreCompleto, int edad, String ciudad, String direccion) {
        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.tieneRegalo = false;
    }

    // Getters y Setters
    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
    
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public boolean isTieneRegalo() { return tieneRegalo; }
    public void setTieneRegalo(boolean tieneRegalo) { this.tieneRegalo = tieneRegalo; }

    @Override
    public String toString() { // Representación en cadena del niño
        return String.format("ID: %s | Nombre: %s | Edad: %d | Ciudad: %s", 
                           identificacion, nombreCompleto, edad, ciudad);
    }
}