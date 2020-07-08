import java.io.File;
import java.io.IOException;

public class sensibilizadocontiempo {
        private Matriz sensibilizadaConTiempo; //tengo que tener alpha,beta,tstmp,idhilo

    public sensibilizadocontiempo() throws IOException {
        File miDir = new File (".");
        String path = miDir.getCanonicalPath();
        GeneradorMatrices g = new GeneradorMatrices(path + "/src/matrices/sensibilizadasConTiempo.txt");
        sensibilizadaConTiempo = g.cargarDatos();
    }

    public Matriz getSensibilizadaConTiempo(){
        return sensibilizadaConTiempo;
    }


    public boolean testVentanaDeTiempo(Matriz trancision){
        return true;
    }

    public boolean setNuevoTimeStamp(){
        return true;
    }

    public boolean testEsperando(Matriz transicion) {
        return true;
    }

    public void setEsperando(Matriz transicion) {
    }

    public void resetEsperando(Matriz tracision ) {
    }
}
