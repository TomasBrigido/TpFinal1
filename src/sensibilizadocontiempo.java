import java.io.File;
import java.io.IOException;

public class sensibilizadocontiempo {
        private Matriz sensibilizadaConTiempo;

    public sensibilizadocontiempo() throws IOException {
        File miDir = new File (".");
        String path = miDir.getCanonicalPath();
        GeneradorMatrices g = new GeneradorMatrices(path + "/src/sensibilizadasConTiempo.txt");
        sensibilizadaConTiempo = g.cargarDatos();
    }

    public Matriz getSensibilizadaConTiempo(){
        return sensibilizadaConTiempo;
    }


    public boolean testVentanaDeTiempo(){
        return true;
    }

    public boolean setNuevoTimeStamp(){
        return true;
    }
}
