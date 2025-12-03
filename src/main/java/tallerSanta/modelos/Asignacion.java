package tallerSanta.modelos;

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