public class Tarea implements Runnable{
    Monitor m;
    int transicion;
    Matriz mTransicion;


    public Tarea(Monitor m, int transicion, int a){
        this.m = m;
        this.transicion = transicion;
        mTransicion = new Matriz(a,1);

    }
    public Tarea(Monitor m, int transicion, int transicion1, int transicion2){
        this.m = m;
    }

    @Override
    public void run (){
            m.dispararTransicion(mTransicion.transformarAVector(transicion));
    }
}
