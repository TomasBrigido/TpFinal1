import java.io.File;
import java.io.IOException;


public class RdP{
    private Matriz sensibilizadas;
    private sensibilizadocontiempo sensibilizadasConTiempo;
    private Matriz incidencia;
    private Matriz marcaActual;
    private Matriz marcaInicial;
    private int numeroDeTrancisiones;
    private int numeroDePlazas;
    private boolean esperando;
    private int contradorTareas;
    private int contradorMemoria;

    public RdP() throws IOException {

        sensibilizadasConTiempo = new sensibilizadocontiempo();

        File miDir = new File (".");
        String path = miDir.getCanonicalPath();

        GeneradorMatrices g = new GeneradorMatrices(path + "/src/matrices/incidencia.txt");
        incidencia = g.cargarDatos();

        numeroDeTrancisiones = g.determinarColumnas();
        numeroDePlazas = g.determinarFilas();

        sensibilizadas = new Matriz(numeroDeTrancisiones,1);

        g.setPath(path + "/src/matrices/marcaInicial.txt");
        marcaInicial = g.cargarDatos();



        marcaActual = new Matriz(numeroDeTrancisiones,1);
        marcaActual=marcaInicial;

    }

    public sensibilizadocontiempo getSensibilizadasConTiempo(){
        return sensibilizadasConTiempo;
    }

    public int getNumeroDeTrancisiones() {
        return numeroDeTrancisiones;
    }

    public Matriz sensibilizadas(){
        Matriz transiciones = new Matriz(numeroDeTrancisiones,1);
        transiciones.asignarElemento(1,0,0);

        for(int i=0; i<numeroDeTrancisiones; i++) {
            Matriz aux = new Matriz(numeroDePlazas,1);
                    aux = aux.multiplicar(incidencia,transiciones);
            Matriz aux2 = new Matriz(numeroDePlazas,1);
                    aux2 = aux2.suma(marcaActual, aux);
            boolean m = aux2.valida();
            if(m){
                sensibilizadas.asignarElemento(1,i,0);
            }else{
                sensibilizadas.asignarElemento(0,i,0);
            }
            transiciones.rotar();

        }

        return sensibilizadas;
    }

    public boolean Disparar(Matriz transicion,boolean temporal){
        boolean k = true;
        if(temporal) {//si es temporal
            boolean ventana = sensibilizadasConTiempo.testVentanaDeTiempo(transicion);//veo si estoy en la ventana de tiempo
            if (ventana) {
                esperando = sensibilizadasConTiempo.testEsperando(transicion);//veo si no hay nadie durmiendo esperando esa trancision o si el hilo que entro viene de un sleep por esa misma trancision

                if (esperando) {
                    k = false;
                } else {
                    //sensibilizadasConTiempo.setNuevoTimeStamp(sensibilizadas());//nose que hace este metodo
                    k = true; //k en true porque no hay nadie esperando para disparar y estoy dentro de la ventana de tiempo
                }
            } else {
                //mutex.release();
                sensibilizadasConTiempo.setEsperando(transicion);//setear que estoy durmiendo por la trancision con el id
                //sleep();
                k = false;
            }
        }

        if(k){ //si el k es true es porque puedo dispara sino retorno false
            System.out.println("Imprimo la matriz transicion;");
            transicion.imprimirMatriz();
            Matriz aux = new Matriz(numeroDePlazas, 1);
            aux = aux.multiplicar(incidencia, transicion);
            Matriz aux2 = new Matriz(numeroDePlazas, 1);
            aux2 = aux2.suma(marcaActual, aux);
            System.out.println("imprimo la matriz marca actual: ");
            aux2.imprimirMatriz();
            boolean matrizValida = aux2.valida();
            if (matrizValida) {
                if(temporal) {
                    sensibilizadasConTiempo.resetEsperando(transicion);//reseteo el id del hilo en la tracision
                }
                sensibilizadasConTiempo.setNuevoTimeStamp(sensibilizadas());//arrancar los contadores de tiempo de las matrices con tiempo sensibilizadas y para los contadores de las trancisions que se desensibilzaron
                marcaActual = aux2;
                return true;
            }
            return false;
        }
        else return false;

    }

    public int getContadorTarea(int i) {
        return 0;
    }

    public int estadoMemoria(int i) {
        return 0;
    }

    public boolean isFull(int n) {
        return false;
    }

}