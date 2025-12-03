package tallerSanta.modelos;

public class Regalo {
    private String codigo;
    private String nombre;
    private String descripcion;
    private String marca;
    private int cantidad;

    public Regalo() {}

    public Regalo(String codigo, String nombre, String descripcion, String marca, int cantidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    @Override
    public String toString() {
        return String.format("Código: %s | Nombre: %s | Marca: %s | Cantidad: %d", 
                           codigo, nombre, marca, cantidad);
    }
}