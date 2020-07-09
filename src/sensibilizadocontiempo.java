import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.TooManyListenersException;

public class sensibilizadocontiempo  {
        private Matriz datoSensibilizadaConTiempo; //tengo que tener alpha,beta,tstmp,idhilo
        private Matriz transicionesTemporales;//Vector de transiciones temporales
        private int columnaAlpha;
        private int columbaBeta;
        private int columnaTimeStamp;
        private int columnaIDhilo;

    public sensibilizadocontiempo() throws IOException {

         columnaAlpha = 0;
         columbaBeta = 1;
         columnaTimeStamp = 2;
         columnaIDhilo = 3;


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
        int alpha = datoSensibilizadaConTiempo.getElemento(trancision.numeroTransicion(),columnaAlpha);
        System.out.println("Tiempo que debe transcurrir: "+ alpha);
        int tiempoTranscurrido = (int)tstmp.getTime() - datoSensibilizadaConTiempo.getElemento(trancision.numeroTransicion(),columnaTimeStamp);
        System.out.println("Tiempor desde que se sensibilizo la taransicion " + trancision.numeroTransicion() +" es: " + tiempoTranscurrido);
        if(tiempoTranscurrido>alpha){
            return true;
        }
        return false;
    }

    public void setNuevoTimeStamp(Matriz sensiibilizadas){
        Matriz aux = new Matriz(sensiibilizadas.getFilas(),1);
        aux = aux.comparar(sensiibilizadas,transicionesTemporales);

        for(int i = 0; i<aux.getFilas(); i++) {
            if(aux.getElemento(i,0)==1){
                long fecha = System.currentTimeMillis();
                Timestamp tstmp = new Timestamp(fecha);
                datoSensibilizadaConTiempo.asignarElemento((int)tstmp.getTime(),i,columnaTimeStamp);
            }
        }
    }

    public boolean testEsperando(Matriz transicion) {
        if(datoSensibilizadaConTiempo.getElemento(transicion.numeroTransicion(),columnaIDhilo)==0){
            return false;
        }
        return true;
    }

    public void setEsperando(Matriz transicion) {
        datoSensibilizadaConTiempo.asignarElemento((int)Thread.currentThread().getId(),transicion.numeroTransicion(),columnaIDhilo);

    }

    public void resetEsperando(Matriz transicion ) {
        datoSensibilizadaConTiempo.asignarElemento(0,transicion.numeroTransicion(),columnaIDhilo);
    }
}
