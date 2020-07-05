public class Tarea implements Runnable{
    private Monitor m;
    private int transicion;
    private Matriz mTransicion;
    private boolean temporal;



    public Tarea(Monitor m, int transicion, int a,boolean temporal){
        this.m = m;
        this.transicion = transicion;
        mTransicion = new Matriz(a,1);
        this.temporal = temporal;
    }
    public Tarea(Monitor m, int transicion, int transicion1, int transicion2){
        this.m = m;
    }

    @Override
    public void run (){
        m.dispararTransicion(mTransicion.transformarAVector(transicion),temporal);
    }
}
