import java.io.File;
import java.io.IOException;


public class RdP {
    private Matriz sensibilizadas;
    private sensibilizadocontiempo sensibilizadasConTiempo;
    private Matriz incidencia;
    private Matriz marcaActual;
    private Matriz marcaInicial;
    private int numeroDeTransiciones;
    private int numeroDePlazas;
    private boolean esperando;
    private int contradorTareas;
    private int contradorMemoria;

    public RdP() throws IOException {

        sensibilizadasConTiempo = new sensibilizadocontiempo();

        File miDir = new File(".");
        String path = miDir.getCanonicalPath();

        GeneradorMatrices g = new GeneradorMatrices(path + "/src/matrices/test/incidencia.txt");
        incidencia = g.cargarDatos();

        numeroDeTransiciones = g.determinarColumnas();
        numeroDePlazas = g.determinarFilas();

        sensibilizadas = new Matriz(numeroDeTransiciones, 1);

        g.setPath(path + "/src/matrices/test/marcaInicial.txt");
        marcaInicial = g.cargarDatos();

        marcaActual = new Matriz(numeroDeTransiciones, 1);
        marcaActual = marcaInicial;
    }

    public sensibilizadocontiempo getSensibilizadasConTiempo() {
        return sensibilizadasConTiempo;
    }

    public int getNumeroDeTransiciones() {
        return numeroDeTransiciones;
    }

    /*! \brief Metodo que verifica una a una que transiciones estan sensibilizadas. Para ello, genera un vector columna
    *          en el que cada indice pertenece a una transicion. El vector tiene un solo valor 1 y los demas son 0s; el valor 1
    *          representa el disparo en la transicion, luego se  verifica que la matriz resultante sea valida segun la marca actual.
    *          Si es valida se va armando el vector columna con valor 1 en los indices en los que la transicion esta sensibilizada.
    *   \return Matriz (objeto) de transiciones sensibilizadas segun la logica de la red de Petri.
    */
    public Matriz sensibilizadas(){
        Matriz transiciones = new Matriz(numeroDeTransiciones,1);
        transiciones.asignarElemento(1,0,0);
        Matriz tmp = new Matriz(numeroDeTransiciones, 1);

        for(int i=0; i<numeroDeTransiciones; i++) {
            Matriz aux = new Matriz(numeroDePlazas,1);
            aux = aux.multiplicar(incidencia,transiciones);
            Matriz aux2 = new Matriz(numeroDePlazas,1);
            aux2 = aux2.suma(marcaActual, aux);
            boolean m = aux2.valida();
            if(m){
                tmp.asignarElemento(1,i,0);
            }else{
                tmp.asignarElemento(0,i,0);
            }
            transiciones.rotar();
        }
        sensibilizadas=tmp;
        return tmp;
    }

    public boolean Disparar(Matriz transicion,boolean temporal){
        boolean k = true;
        //boolean temporal= sensibilizadasConTiempo.;
        if(true) {//si es temporal
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
                if(sensibilizadasConTiempo.getdatoSensibilizadaConTiempo().getElemento(transicion.numeroTransicion(), 3) == -1){
                    sensibilizadasConTiempo.setEsperando(transicion);//setear que estoy durmiendo por la trancision con el id
                }
                Tarea t = (Tarea) Thread.currentThread();
                t.setTiempoDormir(100);
                k = false;
            }
        }

        if(k){ //si el k es true es porque puedo dispara sino retorno false
            Matriz pre= sensibilizadas(); //Obtengo la matriz de sensibilizados pre disparo.

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
                marcaActual = aux2;
                Matriz pos = sensibilizadas();
                sensibilizadasConTiempo.actualizarSensibilizadoT(pre,pos);//Actualizo los tstamp de las transiciones que correspondan
                //sensibilizadasConTiempo.setNuevoTimeStamp(sensibilizadas());//arrancar los contadores de tiempo de las matrices con tiempo sensibilizadas y para los contadores de las trancisions que se desensibilzaron
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