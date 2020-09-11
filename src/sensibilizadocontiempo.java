import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

public class sensibilizadocontiempo {

    public static int offset = 2147483647;
    private Matriz datoSensibilizadaConTiempo; //tengo que tener alpha,beta,tstmp,idhilo,tiempoDeDormida
    private Matriz transicionesTemporales;//Vector de transiciones temporales
    private int columnaAlpha;
    private int columbaBeta;
    private int columnaTimeStamp;
    private int columnaIDhilo;
    private int columnaTiempoDeDormida;


    public sensibilizadocontiempo() throws IOException {

        columnaAlpha = 0;
        columbaBeta = 1;
        columnaTimeStamp = 2;
        columnaIDhilo = 3;
        columnaTiempoDeDormida = 4;

        File miDir = new File(".");
        String path = miDir.getCanonicalPath();

        GeneradorMatrices g = new GeneradorMatrices(path + "/src/matrices/datoSensibilizadasConTiempo.txt");
        datoSensibilizadaConTiempo = g.cargarDatos();

        GeneradorMatrices a = new GeneradorMatrices(path + "/src/matrices/transicionesTemporales.txt");
        transicionesTemporales = a.cargarDatos();
    }

    public Matriz getdatoSensibilizadaConTiempo() {
        return datoSensibilizadaConTiempo;
    }


    /*! \brief Metodo que verifica si la transicion temporal se encuentra sensibilizada por tiempo en el momento actual.
     *         Para ello compara el tiempo actual con los valores alfa y beta de la transicion en cuestion.
     *  \param transicion Objeto Matriz en la que se encuentra la transicion a consultar.
     *  \return True si se verifica que el hilo está dentro de la ventana de disparo de la transicion. False en caso contrario.
     */
    public boolean testVentanaDeTiempo(Matriz trancision) { //Matriz transicion = tiene un 1 en la transicion a disparar y 0 en las demas
        long fecha = System.currentTimeMillis();
        Timestamp tstmp = new Timestamp(fecha);
        int alpha = datoSensibilizadaConTiempo.getElemento(trancision.numeroTransicion(), columnaAlpha);
        int beta = datoSensibilizadaConTiempo.getElemento(trancision.numeroTransicion(), columbaBeta);
        Logger.println("Tiempo que debe transcurrir: " + alpha,false);
        int tiempoTranscurrido = (int) tstmp.getTime() - datoSensibilizadaConTiempo.getElemento(trancision.numeroTransicion(), columnaTimeStamp);
        Logger.println("Tiempor desde que se sensibilizo la taransicion " + trancision.numeroTransicion() + " es: " + tiempoTranscurrido,false);
        if (tiempoTranscurrido >= alpha && tiempoTranscurrido < beta) {
            return true;
        }
        return false;
    }

    /*! \brief Metodo setea el timeStamp en las transiciones temporales que estan sensibilizadas.
     *  \param Matriz (o vector columna), con las transiciones sensibilizadas (tiene un 1 en la sensibilizaca, 0 en las otras)
    */
    public void setNuevoTimeStamp(Matriz sensibilizadas) {
        Matriz aux = new Matriz(sensibilizadas.getFilas(), 1);
        aux = aux.comparar(sensibilizadas, transicionesTemporales);

        for (int i = 0; i < aux.getFilas(); i++) {
            if (aux.getElemento(i, 0) == 1) {
                long fecha = System.currentTimeMillis();
                Timestamp tstmp = new Timestamp(fecha);
                datoSensibilizadaConTiempo.asignarElemento((int) tstmp.getTime(), i, columnaTimeStamp);
            }
        }
    }

    /*! \brief Metodo que verifica en la matriz "datoSensibilizadaConTiempo" si la transicion pasada como parametro ya
     *         tiene un hilo esperando (que fue el primero en llegar y esperar) para ejecutar la transicion sensibilizada por tiempo.
     *  \param transicion Objeto Matriz en la que se encuentra la transicion en la que se verifica si hay un hilo esperando.
     *  \return True si hay un hilo esperando en esa transicion, False en caso contrario.
     */
    public boolean testEsperando(Matriz transicion) {
        int hiloEsperando = datoSensibilizadaConTiempo.getElemento(transicion.numeroTransicion(),columnaIDhilo);
        if (hiloEsperando == -1 || hiloEsperando == Thread.currentThread().getId()){
            // el falso significa que no hay nadie esperando antes. La matriz de temporales deberia ser de -1 en la cuarta columna.
            return false;
        }
        return true;
    }

    /*! \brief Metodo que setea la ultima columna de la matriz "datoSensibilizadaConTiempo" con el id del hilo que llama a este metodo.
     *  \param transicion Objeto Matriz en la que se encuentra la transicion de la que se setea el dato de ID de hilo.
     */
    public void setEsperando(Matriz transicion) {
        datoSensibilizadaConTiempo.asignarElemento((int)Thread.currentThread().getId(),transicion.numeroTransicion(),columnaIDhilo);
    }

    /*! \brief Metodo que setea la ultima columna de la matriz "datoSensibilizadaConTiempo" con un valor nulo (en este caso -1),
     *         con el que se representará que en la transicion pasada como parametro no hay hilo esperando el tiempo minimo para dispararla.
     *  \param transicion Objeto Matriz en la que se encuentra la transicion de la que se setea el dato nulo.
     */
    public void resetEsperando(Matriz transicion) {
        datoSensibilizadaConTiempo.asignarElemento(-1,transicion.numeroTransicion(),columnaIDhilo);
        datoSensibilizadaConTiempo.asignarElemento(0,transicion.numeroTransicion(),columnaTiempoDeDormida);
    }

    public void actualizarSensibilizadoT(Matriz pre_sens, Matriz pos_sens){
        /*
        Deberian entrar dos parametros: la marca actual pre disparo y la marca actual pos disparo o mejor dicho los sensibilizados pre y pos
        se deberia hacer una or exclusiva (ante distintos que se ponga en uno) si estaba sensibilizado antes y ahora no el tstamp se pone en 0
        si estaba no sensibilizada antes y ahora si lo está, hay que ponerle el tstamp actual en la columna 3. Por ultimo se tiene que tener en cuenta
        que ademas de esa comparacion se debe comparar con la matriz que indica cual transicion es temporal y cual no.
        Aca pasarian estos escenarios:
            -- sensibilizados y luego no sensibilizados hay que apagar el contador (meterle una actualizacion)
            -- sens y sens hay que dejar el contador con el valor de tstamp que tiene
            -- no sens y no sens hay que dejar el contador en 0
         */

        Matriz a = new Matriz(pre_sens.getFilas(), pre_sens.getColumnas());
        a = a.xor(pre_sens,pos_sens);

        for (int i = 0; i < a.getFilas(); i++) {
            // lo logica aca es: si tiene un valor 1 la salida del xor es porque cambió el valor de pre a pos (0 a 1 ó 1 a 0)
            // entonces consulto cual es el valor de pre: si es 1 es porque el pos es 0 y es una desensibilizacion, en contrario si pre es 0 es porque
            // pos indica que sensibilizó
            if(a.getElemento(i,0) == 1 && transicionesTemporales.getElemento(i,0) == 1) {
                if(pre_sens.getElemento(i,0) == 1){ //caso en que se dessensibilizó 
                    datoSensibilizadaConTiempo.asignarElemento(0,i,columnaTimeStamp); // seteo timestamp en nulo
                    datoSensibilizadaConTiempo.asignarElemento(-1,i,columnaIDhilo); //seteo el id del hilo como nulo, porque se desensibilizó
                    continue;
                }
                //caso en que se sensibiliza una nueva transicion, se setea el timestamp actual: inicio de sensibilizacion
                int actual = (int) System.currentTimeMillis()%offset;
                datoSensibilizadaConTiempo.asignarElemento(actual,i,columnaTimeStamp);
            }
        }
    }


    /*! \brief Metodo que setea el tiempo que debe dormir una transición antes de poderse disparar. Lo setea en
     *   la columnaTiempoDeDormida, de la matriz datoSensibilizadaConTiempo
     *  \param Matriz transicion = tiene un 1 en la transicion a disparar y 0 en las demas
     */
    public void setTiempoDormir(Matriz transicion) {
        long fecha = System.currentTimeMillis();
        Timestamp tstmp = new Timestamp(fecha);
        int ahora = (int)tstmp.getTime() - datoSensibilizadaConTiempo.getElemento(transicion.numeroTransicion(),columnaTimeStamp);
        int alpha = datoSensibilizadaConTiempo.getElemento(transicion.numeroTransicion(),columnaAlpha);
        int aux = (alpha - ahora);
        aux= aux<=0 ? 1 : aux;          //si el tiempo es menor que cero le coloca 1 ms (no le coloca cero por que la trataria como inmediata)
        datoSensibilizadaConTiempo.asignarElemento(aux,transicion.numeroTransicion(),columnaTiempoDeDormida);
    }
}
