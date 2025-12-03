package tallerSanta.modelos;

// Clase que representa la asignación de un regalo a un niño, incluyendo la identificación del niño, el código del regalo y la fecha de asignación.
// setters y getters para cada atributo, así como un método toString para representar la asignación en formato de cadena.
public class Asignacion {
    private String idNino;
    private String codigoRegalo;
    private String fechaAsignacion;

    public Asignacion() {}

    public Asignacion(String idNino, String codigoRegalo, String fechaAsignacion) {
        this.idNino = idNino;
        this.codigoRegalo = codigoRegalo;
        this.fechaAsignacion = fechaAsignacion;
    }

    // Getters y Setters
    public String getIdNino() { return idNino; }
    public void setIdNino(String idNino) { this.idNino = idNino; }
    
    public String getCodigoRegalo() { return codigoRegalo; }
    public void setCodigoRegalo(String codigoRegalo) { this.codigoRegalo = codigoRegalo; }
    
    public String getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(String fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }

    @Override
    public String toString() {
        return String.format("Niño: %s | Regalo: %s | Fecha: %s", 
                           idNino, codigoRegalo, fechaAsignacion);
    }
}