public class Tarea implements Runnable{
    private Monitor m;
    private int[] transicion;
    private Matriz mTransicion;
    public int tiempoDormir;
    private static final int TOTAL_TAREAS=1000;


    public Tarea(Monitor m, int[] transicion, int numeroDeTransiciones){    //Monitor, transición/es que dispara, cantidad total de transiciones
        this.m = m;
        this.transicion = transicion;
        mTransicion = new Matriz(numeroDeTransiciones,1);
        tiempoDormir = 0;
    }

    @Override
    public void run () {
        boolean no_fin=true;
        while (no_fin) {
            int i=0;
            while(i<transicion.length) {
                m.dispararTransicion(mTransicion.transformarAVector(transicion[i]));

                int tiempoDormir = m.getRedDePetri().getSensibilizadasConTiempo().getdatoSensibilizadaConTiempo().getElemento(transicion[i], 4);    //busca el tiempo que tiene que dormir desde la matriz correspondiente
                if (tiempoDormir != 0) {           //si tiempoDormir!=0, entonces tiene que dormir un cierto tiempo
                    Logger.println("voy a dormir: T" + transicion[i] + "   " + tiempoDormir, false);
                    try {
                        if(tiempoDormir>0) {
                            Thread.sleep(tiempoDormir); //Duerme el tiempo correcto
                        }else{
                            Thread.sleep(0);         //Si tiempo dormir llegara a dar negativo, duerme 0 ms
                        }
                        m.getRedDePetri().getSensibilizadasConTiempo().resetEsperando(mTransicion.transformarAVector(transicion[i])); //setea el tiempo de dormir en 0 y el id del hilo en -1
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {                             //Si tiempoDormir=0, entonces no duerme y continúa
                    if(m.getEnd()){break;} //se pone esto porque cuando va cerrando los hilos en aquellos que tiene dos transiciones para disparar, la segunda entra al monitor cambia el k a true se duerme y no despierta a nadie mas
                    Logger.updateContador(transicion[i]);  //Aumenta el contador para saber cuantas veces se disparó esa transicion
                    i++;
                }
            }
            no_fin=setEnd(transicion[0]);       //Setea la bandera para saber si ya terminó o no
       }
        Logger.println("Estoy terminando, hilo:"+transicion[0],true );
    }


    /*Setea la bandera no_fin, para saber si ya tiene que terminar el hilo o no. Diferencia si es la T0, para
    * que corte antes y se terminen de ejecutar todas las tareas que van avanzando por la red de petri.
    * Si se completaron las tareas y es el ultimo hilo el que está entrando, setea la bandera no_fin y además
    * despierta a otro hilo de alguna cola, para que muera también y depierte a otro sucesivamente.
    * Asi terminan todos los hilos y, por ende, el programa completo.
    * \return boolean: true= sigue la ejecucion de ese hilo, false= termina la ejecución del hilo*/
    private boolean setEnd(int thread){
        if(thread==0 && Logger.getContador(thread)>=TOTAL_TAREAS){
            return false;
        } else if((thread==15 || thread==16) && Logger.getSumaMemoria()>=TOTAL_TAREAS) {
            m.setEnd(true);     //método del monitor (no de Tarea)
            finishThreads();
            return false;
        } else if(Logger.getSumaMemoria()>=TOTAL_TAREAS && m.getEnd()){
            finishThreads();
            return false;
        }
        return true;
    }


    /* Despierta al primer hilo que encuentra dormido en la cola de algun semáforo*/
    private void finishThreads(){
        int t=m.getColas().quienesEstan().numeroTransicion();
        if(t!=-1 && m.getEnd()){
            Logger.println("Soy hilo: "+transicion[0]+";despertando a hilo: "+t,true );
            m.getColas().liberar(t);
        }
    }


}
