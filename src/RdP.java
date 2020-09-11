import java.io.File;
import java.io.IOException;

/*  La clase RdP (Red de Petri), contiene las matrices y los métodos que provocan el avance de la red y comprueban su correcto funcionamiento.
    Sus principales matrices son la de incidencia, la cual contiene los pesos de los arcos, las marcaActual y marcaInicial,
    que contienen la cantidad de tokens de las plazas y las sensibilizadas y sensibilizadasConTiempo que contienen las transiciones que pueden ser o no disparadas.
    Además se encuentran otras matrices o variables que sirven para comprobar el correcto funcionamiento de la red.

    Tambien tiene los metodos que generan el disparo de la transicion junto con el avance de la red y el metodo de comprobacion de sensibilizadas
 */
public class RdP {
    private Matriz sensibilizadas;
    private sensibilizadocontiempo sensibilizadasConTiempo;
    private Matriz incidencia;
    private Matriz marcaActual;
    private Matriz marcaInicial;
    private Matriz invPlaza;
    private int numeroDeTransiciones;
    private int numeroDePlazas;
    private int[] resPInv= new int[] {1,8,1,8,1,4,4,1}; // resultados de las ecuaciones de los P-Invariantes
    private boolean esperando;

    public RdP() throws IOException {

        sensibilizadasConTiempo = new sensibilizadocontiempo();

        File miDir = new File(".");
        String path = miDir.getCanonicalPath();

        GeneradorMatrices g = new GeneradorMatrices(path + "/src/matrices/incidencia.txt");
        incidencia = g.cargarDatos();

        numeroDeTransiciones = g.determinarColumnas();
        numeroDePlazas = g.determinarFilas();

        sensibilizadas = new Matriz(numeroDeTransiciones, 1);

        g.setPath(path + "/src/matrices/marcaInicial.txt");
        marcaInicial = g.cargarDatos();

        marcaActual = new Matriz(numeroDeTransiciones, 1);
        marcaActual = marcaInicial;

        g.setPath(path+"/src/matrices/invariantesPlaza.txt");
        invPlaza = g.cargarDatos();
    }

    public sensibilizadocontiempo getSensibilizadasConTiempo() {
        return sensibilizadasConTiempo;
    }

    public int getNumeroDeTransiciones() {
        return numeroDeTransiciones;
    }

    /*! \brief Metodo que verifica una a una que transiciones estan sensibilizadas.
     *          Para ello, genera un vector columna en el que cada indice pertenece a una transicion.
     *          El vector tiene un solo valor 1 y los demas son 0s; el valor 1 representa la transición que se quiere probar.
     *          Se realiza el disparo de la transicion y luego se verifica que la matriz resultante sea valida para la marca actual.
     *          Si es valida se va armando un vector columna con valor 1 en los indices en los que la transicion esta sensibilizada.
     *  \return Matriz (objeto) de transiciones sensibilizadas segun la logica de la red de Petri.
     */

    public Matriz sensibilizadas(){
        Matriz transiciones = new Matriz(numeroDeTransiciones,1);
        transiciones.asignarElemento(1,0,0);
        Matriz tmp = new Matriz(numeroDeTransiciones, 1);   //sus indices en 1 representan las transiciones sensibilizadas

        for(int i=0; i<numeroDeTransiciones; i++) {
            Matriz aux = new Matriz(numeroDePlazas,1);
            aux = aux.multiplicar(incidencia,transiciones);
            Matriz aux2 = new Matriz(numeroDePlazas,1);
            aux2 = aux2.suma(marcaActual, aux);
            boolean m = aux2.valida();              //Se fija que no hayan numeros negativos en la marca despues del disparo
            if(m){
                tmp.asignarElemento(1,i,0);
            }else{
                tmp.asignarElemento(0,i,0);
            }
            transiciones.rotar();                   //Se rota el uno hacia abajo, para comprobar la siguiente transicion
        }
        sensibilizadas=tmp;
        return tmp;
    }
    /*! \brief Metodo que genera el disparo de la transición que se envía como parámetro.
                Genera el avance correspondiente de la red, es decír modifica la marca actual.
     *  \return boolean: true -> si pudo disparar; false -> no pudo disparar
     */

    public boolean Disparar(Matriz transicion){ //Matriz transicion = tiene un 1 en la transicion a disparar y 0 en las demas
        boolean k = true;
        boolean temporal = false;
        boolean estaSensibilizada = false;

        if(sensibilizadasConTiempo.getdatoSensibilizadaConTiempo().getElemento(transicion.numeroTransicion(),0)!= 0 ){ //se fija el tiempo de alfa en mili segundos; si es cero es inmediata
            temporal = true;                                                                                                    //setea la vandera temporal en true
            Logger.println("Soy una transicion temporal",false);

        }

        if(sensibilizadas.getElemento(transicion.numeroTransicion(),0) == 1){   //se fija si está sensibilizada
            Logger.println("Mi transicion esta sensibilizada",false);
            estaSensibilizada = true;
        }

        if(temporal && estaSensibilizada) {     //pregunta si es temporal y está sensibilizada
            boolean ventana = sensibilizadasConTiempo.testVentanaDeTiempo(transicion);//veo si estoy en la ventana de tiempo
            if (ventana) {
                esperando = sensibilizadasConTiempo.testEsperando(transicion);//veo si no hay nadie durmiendo esperando esa trancision o si el hilo que entro viene de un sleep por esa misma trancision

                if (esperando) {
                    k = false;
                } else {
                    k = true; //k en true porque no hay nadie esperando para disparar y estoy dentro de la ventana de tiempo
                }
            } else {
                if(sensibilizadasConTiempo.getdatoSensibilizadaConTiempo().getElemento(transicion.numeroTransicion(), 3) == -1){
                    sensibilizadasConTiempo.setEsperando(transicion);//setear que estoy durmiendo por la trancision con el id
                    sensibilizadasConTiempo.setTiempoDormir(transicion);//setear el tiempo que tengo que dormir
                }
                k = false;
            }
        }else{
            Logger.println("Mi tansicion no esta sensibilizada o no soy temporal",false);
        }

        if(k){ //si el k es true es porque puedo dispara sino retorno false
            Matriz pre= sensibilizadas(); //Obtengo la matriz de sensibilizados pre disparo.

            Logger.println("Imprimo la matriz transicion;",false);
            transicion.imprimirMatriz();

            Matriz aux = new Matriz(numeroDePlazas, 1);
            aux = aux.multiplicar(incidencia, transicion);                  //multiplica la matriz de incidencia con la de transición
            Matriz aux2 = new Matriz(numeroDePlazas, 1);
            aux2 = aux2.suma(marcaActual, aux);                             //al resultado anterior lo suma con la marca actual para obtener la nueva marca

            Logger.println("imprimo la matriz marca actual: ",false);
            aux2.imprimirMatriz();

            boolean matrizValida = aux2.valida();                       //Comprueba que la matriz no tenga elementos negativos
            if (matrizValida) {
                if(temporal) {
                    sensibilizadasConTiempo.resetEsperando(transicion); //reseteo el id del hilo en la tracision y el tiempo de dormida
                }

                //Compruebo los invariantes de plaza en cada disparo
                checkPInv(aux2);

                // Imprimo transicion que se disparó
                Logger.printT(transicion.numeroTransicion());

                marcaActual = aux2;
                Matriz pos = sensibilizadas();
                sensibilizadasConTiempo.actualizarSensibilizadoT(pre,pos);  //Actualizo los tstamp de las transiciones que correspondan
                //sensibilizadasConTiempo.setNuevoTimeStamp(sensibilizadas());//arrancar los contadores de tiempo de las matrices con tiempo sensibilizadas y para los contadores de las trancisions que se desensibilzaron
                return true;
            }
            return false;
        }
        else return false;
    }

    /*! \brief  Metodo que verifica si se cumplen los invariantes de plaza en la ejecucion actual. Multiplica cada uno de
     *          los vectores fila de invariantes de plaza con el marcado actual. El resultado se compara con el determinado
     *           por la red. En caso de que sean distintos devuelve un assert.
     *  \param  mActual Objeto Matriz que representa el marcado actual de la red (luego de dispararse una transicion).
     *  \return Matriz (objeto) de transiciones sensibilizadas segun la logica de la red de Petri.
    */
    public void checkPInv(Matriz mActual){
        for(int i=0;i<invPlaza.getFilas();i++){
            Matriz a=invPlaza.extractRow(invPlaza, i);
            a=a.multiplicar(a, mActual);
            int res=a.getElemento(0, 0);
            assert res==resPInv[i]: String.format("Falla en los invariantes de plaza. El valor calculado es (%d) distinto al teorico (%d)", res, resPInv[i]);
        }
    }

}