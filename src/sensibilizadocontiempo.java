import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.TooManyListenersException;

public class sensibilizadocontiempo  {
        private Matriz datoSensibilizadaConTiempo; //tengo que tener alpha,beta,tstmp,idhilo
        private Matriz transicionesTemporales;//Vector de transiciones temporales

    public sensibilizadocontiempo() throws IOException {

        File miDir = new File (".");
        String path = miDir.getCanonicalPath();

        GeneradorMatrices g = new GeneradorMatrices(path + "/src/matrices/datoSensibilizadasConTiempo.txt");
        datoSensibilizadaConTiempo = g.cargarDatos();

        GeneradorMatrices a = new GeneradorMatrices(path + "/src/matrices/transicionesTemporales.txt");
        transicionesTemporales = a.cargarDatos();



    }

    public Matriz getdatoSensibilizadaConTiempo(){
        return datoSensibilizadaConTiempo;
    }

    public boolean testVentanaDeTiempo(Matriz trancision){
        long fecha = System.currentTimeMillis();
        Timestamp tstmp = new Timestamp(fecha);
        int tiempoTranscurrido = (int)tstmp.getTime() - datoSensibilizadaConTiempo.getElemento(trancision.numeroTransicion(),3);
        int alpha = datoSensibilizadaConTiempo.getElemento(trancision.numeroTransicion(),0);
        System.out.println(tiempoTranscurrido);
        if(tiempoTranscurrido>alpha){
            return true;
        }
        return false;
    }

    public void setNuevoTimeStamp(Matriz sensiibilizadas){
        Matriz aux = new Matriz(sensiibilizadas.getFilas(),1);
        aux = aux.comparar(sensiibilizadas,transicionesTemporales);
        aux.imprimirMatriz();

        for(int i = 0; i<aux.getFilas(); i++) {
            if(aux.getElemento(i,0)==1){
                long fecha = System.currentTimeMillis();
                Timestamp tstmp = new Timestamp(fecha);
                datoSensibilizadaConTiempo.asignarElemento((int)tstmp.getTime(),i,2);
            }
        }
    }

    public boolean testEsperando(Matriz transicion) {
        if(datoSensibilizadaConTiempo.getElemento(transicion.numeroTransicion(),3)==0){
            return false;
        }
        return true;
    }

    public void setEsperando(Matriz transicion) {
        datoSensibilizadaConTiempo.asignarElemento((int)Thread.currentThread().getId(),transicion.numeroTransicion(),3);

    }

    public void resetEsperando(Matriz transicion ) {
        datoSensibilizadaConTiempo.asignarElemento(0,transicion.numeroTransicion(),3);
    }
}
