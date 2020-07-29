public class Tarea implements Runnable{
    private Monitor m;
    private int transicion;
    private Matriz mTransicion;
    public int tiempoDormir;


    public Tarea(Monitor m, int transicion, int numeroDeTransiciones){
        this.m = m;
        this.transicion = transicion;
        mTransicion = new Matriz(numeroDeTransiciones,1);
        tiempoDormir = 0;


    }
    public Tarea(Monitor m, int transicion, int transicion1, int transicion2){
        this.m = m;
    }

    @Override
    public void run () {

        while (true) {
            m.dispararTransicion(mTransicion.transformarAVector(transicion));


            int tiempoDormir = m.getRedDePetri().getSensibilizadasConTiempo().getdatoSensibilizadaConTiempo().getElemento(transicion,4);
            if(tiempoDormir != 0) {
                System.out.println("voy a dormir: "+ transicion+"   " + tiempoDormir);

                try {
                    Thread.sleep(tiempoDormir);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }


}
