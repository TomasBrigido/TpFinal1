public class Tarea implements Runnable{
    private Monitor m;
    private int transicion;
    private Matriz mTransicion;
    private int tiempoDormir;
    private boolean temporal;

    public Tarea(Monitor m, int transicion, int a,boolean temporal){
        this.m = m;
        this.transicion = transicion;
        mTransicion = new Matriz(a,1);
        tiempoDormir = 0;
        this.temporal = temporal;

    }
    public Tarea(Monitor m, int transicion, int transicion1, int transicion2){
        this.m = m;
    }

    @Override
    public void run () {

        while (true) {

            m.dispararTransicion(mTransicion.transformarAVector(transicion),temporal);

            if(tiempoDormir>0){
                try{
                    Thread.sleep(tiempoDormir);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally{
                    tiempoDormir = 0;
                }

            }
        }
    }

    public void setTiempoDormir(int tiempo){
        tiempoDormir = tiempo;
    }


}
