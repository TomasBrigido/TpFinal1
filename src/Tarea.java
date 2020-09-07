public class Tarea implements Runnable{
    private Monitor m;
    private int[] transicion;
    private Matriz mTransicion;
    public int tiempoDormir;
    private static final int TOTAL_TAREAS=5;


    public Tarea(Monitor m, int[] transicion, int numeroDeTransiciones){
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

                int tiempoDormir = m.getRedDePetri().getSensibilizadasConTiempo().getdatoSensibilizadaConTiempo().getElemento(transicion[i], 4);
                if (tiempoDormir > 0) {
                    Logger.println("voy a dormir: " + transicion[i] + "   " + tiempoDormir, false);
                    try {
                        Thread.sleep(tiempoDormir);
                        //setea el tiempo de dormir en 0 y el id del hilo en -1
                        m.getRedDePetri().getSensibilizadasConTiempo().resetEsperando(mTransicion.transformarAVector(transicion[i]));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else if(tiempoDormir<0) {

                }else{
                    if(m.getEnd()){break;} //se pone esto porque cuando va cerrando los hilos en aquellos que tiene dos transiciones para disparar, la segunda entra al monitor cambia el k a true se duerme y no despierta a nadie mas
                    Logger.updateContador(transicion[i]);
                    i++;
                }
            }
            no_fin=setEnd(transicion[0]);
       }
        Logger.println("Estoy terminando hilo:"+transicion[0],true );
    }

    private boolean setEnd(int thread){
        if(thread==0 && Logger.getContador(thread)>=TOTAL_TAREAS){
            return false;
        } else if((thread==15 || thread==16) && Logger.getSumaMemoria()>=TOTAL_TAREAS) {
            m.setEnd(true);
            finishThreads();
            return false;
        } else if(Logger.getSumaMemoria()>=TOTAL_TAREAS && m.getEnd()){
            finishThreads();
            return false;
        }
        return true;
    }

    private void finishThreads(){
        int t=m.getColas().quienesEstan().numeroTransicion();
        if(t!=-1 && m.getEnd()){
            Logger.println("Soy hilo: "+transicion[0]+";despertando a hilo: "+t,true );
            m.getColas().liberar(t);
        }
        return;
    }


}
