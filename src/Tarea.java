public class Tarea implements Runnable{
    private Monitor m;
    private int[] transicion;
    private Matriz mTransicion;
    public int tiempoDormir;


    public Tarea(Monitor m, int[] transicion, int numeroDeTransiciones){
        this.m = m;
        this.transicion = transicion;
        mTransicion = new Matriz(numeroDeTransiciones,1);
        tiempoDormir = 0;
    }

    @Override
    public void run () {
        while (true) {
            int i=0;
            while(i<transicion.length) {
                m.dispararTransicion(mTransicion.transformarAVector(transicion[i]));

                int tiempoDormir = m.getRedDePetri().getSensibilizadasConTiempo().getdatoSensibilizadaConTiempo().getElemento(transicion[i], 4);
                if (tiempoDormir != 0) {
                    Logger.println("voy a dormir: " + transicion[i] + "   " + tiempoDormir, false);
                    try {
                        Thread.sleep(tiempoDormir);
                        //setea el tiempo de dormir en 0 y el id del hilo en -1
                        m.getRedDePetri().getSensibilizadasConTiempo().resetEsperando(mTransicion.transformarAVector(transicion[i]));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    Logger.updateContador(transicion[i]);
                    i++;
                }
            }
        }
    }


}
