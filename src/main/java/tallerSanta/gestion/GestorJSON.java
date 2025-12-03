package tallerSanta.gestion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import tallerSanta.modelos.Usuario;
import tallerSanta.modelos.Regalo;
import tallerSanta.modelos.Nino;
import tallerSanta.modelos.Asignacion;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
 
//Esta clase gestiona la carga y guardado de datos en archivos JSON.
// El gestor de JSON se encarga de manejar la carga y guardado de datos en archivos JSON.
// Utiliza la biblioteca Gson para la serialización y deserialización de objetos.
// Aquí se definen métodos genéricos para cargar y guardar listas de diferentes tipos de datos en archivos JSON.
public class GestorJSON {
    private static final String ARCHIVO_USUARIOS = "usuarios.json";
    private static final String ARCHIVO_REGALOS = "regalos.json";
    private static final String ARCHIVO_NINOS = "ninos.json";
    private static final String ARCHIVO_ASIGNACIONES = "asignaciones.json";
    
    private Gson gson; // Instancia de Gson para manejar JSON

    public GestorJSON() { // Constructor que inicializa la instancia de Gson con formato
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    // Métodos genéricos para cargar datos.
    public <T> List<T> cargarDatos(String archivo, Type tipoLista) {
        List<T> datos = new ArrayList<>();
        
        // Intentar leer el archivo JSON y deserializarlo en una lista del tipo especificado
        try (FileReader reader = new FileReader(archivo)) {
            datos = gson.fromJson(reader, tipoLista);
            if (datos == null) {
                datos = new ArrayList<>();
            } // Si el archivo está vacío, inicializar una lista vacía
        } catch (IOException e) {
            guardarDatos(archivo, new ArrayList<>());
        }
        
        return datos; // Retornar la lista cargada o vacía
    }

    // Métodos genéricos para guardar datos
    // Intentar serializar la lista de datos y escribirla en el archivo JSON especificado
    public <T> void guardarDatos(String archivo, List<T> datos) {
        try (FileWriter writer = new FileWriter(archivo)) {
            gson.toJson(datos, writer); 
        } catch (IOException e) {
            System.out.println("Error al guardar en " + archivo + ": " + e.getMessage());
        }
    }

    // Métodos específicos para cada tipo de dato
    // Estos métodos utilizan los métodos genéricos para cargar y guardar listas de usuarios, regalos, niños y asignaciones.
    public List<Usuario> cargarUsuarios() {
        Type tipoLista = new TypeToken<ArrayList<Usuario>>(){}.getType();
        return cargarDatos(ARCHIVO_USUARIOS, tipoLista);
    }

    public void guardarUsuarios(List<Usuario> usuarios) { // Método para guardar la lista de usuarios
        guardarDatos(ARCHIVO_USUARIOS, usuarios);
    }

    public List<Regalo> cargarRegalos() { // Método para cargar la lista de regalos
        Type tipoLista = new TypeToken<ArrayList<Regalo>>(){}.getType();
        return cargarDatos(ARCHIVO_REGALOS, tipoLista);
    }

    public void guardarRegalos(List<Regalo> regalos) { // Método para guardar la lista de regalos
        guardarDatos(ARCHIVO_REGALOS, regalos);
    }

    public List<Nino> cargarNinos() { // Método para cargar la lista de niños
        Type tipoLista = new TypeToken<ArrayList<Nino>>(){}.getType();
        return cargarDatos(ARCHIVO_NINOS, tipoLista);
    }

    public void guardarNinos(List<Nino> ninos) { // Método para guardar la lista de niños
        guardarDatos(ARCHIVO_NINOS, ninos);
    }

    public List<Asignacion> cargarAsignaciones() { // Método para cargar la lista de asignaciones
        Type tipoLista = new TypeToken<ArrayList<Asignacion>>(){}.getType();
        return cargarDatos(ARCHIVO_ASIGNACIONES, tipoLista);
    }

    public void guardarAsignaciones(List<Asignacion> asignaciones) { // Método para guardar la lista de asignaciones
        guardarDatos(ARCHIVO_ASIGNACIONES, asignaciones);
    }
}