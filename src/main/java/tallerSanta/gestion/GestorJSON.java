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

public class GestorJSON {
    private static final String ARCHIVO_USUARIOS = "usuarios.json";
    private static final String ARCHIVO_REGALOS = "regalos.json";
    private static final String ARCHIVO_NINOS = "ninos.json";
    private static final String ARCHIVO_ASIGNACIONES = "asignaciones.json";
    
    private Gson gson;

    public GestorJSON() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    // Métodos genéricos para cargar datos
    public <T> List<T> cargarDatos(String archivo, Type tipoLista) {
        List<T> datos = new ArrayList<>();
        
        try (FileReader reader = new FileReader(archivo)) {
            datos = gson.fromJson(reader, tipoLista);
            if (datos == null) {
                datos = new ArrayList<>();
            }
        } catch (IOException e) {
            // Si el archivo no existe, crear uno vacío
            guardarDatos(archivo, new ArrayList<>());
        }
        
        return datos;
    }

    // Métodos genéricos para guardar datos
    public <T> void guardarDatos(String archivo, List<T> datos) {
        try (FileWriter writer = new FileWriter(archivo)) {
            gson.toJson(datos, writer);
        } catch (IOException e) {
            System.out.println("❌ Error al guardar en " + archivo + ": " + e.getMessage());
        }
    }

    // Métodos específicos para cada tipo de dato
    public List<Usuario> cargarUsuarios() {
        Type tipoLista = new TypeToken<ArrayList<Usuario>>(){}.getType();
        return cargarDatos(ARCHIVO_USUARIOS, tipoLista);
    }

    public void guardarUsuarios(List<Usuario> usuarios) {
        guardarDatos(ARCHIVO_USUARIOS, usuarios);
    }

    public List<Regalo> cargarRegalos() {
        Type tipoLista = new TypeToken<ArrayList<Regalo>>(){}.getType();
        return cargarDatos(ARCHIVO_REGALOS, tipoLista);
    }

    public void guardarRegalos(List<Regalo> regalos) {
        guardarDatos(ARCHIVO_REGALOS, regalos);
    }

    public List<Nino> cargarNinos() {
        Type tipoLista = new TypeToken<ArrayList<Nino>>(){}.getType();
        return cargarDatos(ARCHIVO_NINOS, tipoLista);
    }

    public void guardarNinos(List<Nino> ninos) {
        guardarDatos(ARCHIVO_NINOS, ninos);
    }

    public List<Asignacion> cargarAsignaciones() {
        Type tipoLista = new TypeToken<ArrayList<Asignacion>>(){}.getType();
        return cargarDatos(ARCHIVO_ASIGNACIONES, tipoLista);
    }

    public void guardarAsignaciones(List<Asignacion> asignaciones) {
        guardarDatos(ARCHIVO_ASIGNACIONES, asignaciones);
    }
}