import java.util.concurrent.Semaphore;
// grt
public class Colas {
    private Matriz quienes;
    private Semaphore colas[];
    int largoColaTransiciones;

    public Colas(int a){
        largoColaTransiciones = a;
        quienes = new Matriz(largoColaTransiciones,1);
        colas = new Semaphore[largoColaTransiciones];
        for(int i = 0; i<largoColaTransiciones; i++){
            colas[i]=new Semaphore(0);
        }
    }

    public Matriz quienesEstan(){
        for(int i = 0; i<largoColaTransiciones ;i++ ){
            int aux = colas[i].getQueueLength();
            if(aux>0) {
                quienes.asignarElemento(1,i,0);
                //setear el lugar i de quienes con el valor 1
            }
            else{
                quienes.asignarElemento(0,i,0);
                //setear el lugar i de quienes con el valor 0
            }
        }
        // el vector que te entrega tiene que tener unos y ceros, si hay valores mayores a 1 se toman como 1
        // para no confundir el valor de m cuando se pregunte
        return quienes;
    }

    public void liberar(int indice){
        colas[indice].release();
    }

    public void adquirir(int indice) {
        try {
            colas[indice].acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}