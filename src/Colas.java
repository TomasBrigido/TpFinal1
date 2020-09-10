import java.util.concurrent.Semaphore; // hirrere
// grtr
public class Colas {
    private Matriz quienes;
    private Semaphore[] colas;
    int largoColaTransiciones;

    public Colas(int a){    //crea un arreglo de colas, una por cada transicion
        largoColaTransiciones = a;
        quienes = new Matriz(largoColaTransiciones,1);
        colas = new Semaphore[largoColaTransiciones];
        for(int i = 0; i<largoColaTransiciones; i++){
            colas[i]=new Semaphore(0);
        }
    }

    /*! \brief Verifica la cantidad de hilos esperando en la cola de cada transicion. Si hay uno o mas hilos esperando en la cola
    *          coloca un 1 en la posicion correspondiente al indice de transicion, caso contrario coloca un 0.
    *   \return Matriz (objeto) o vector columna compuesta de 1 y 0, en la que cada posicion corresponde a una cola de transicion.
    * */
    public Matriz quienesEstan(){
        for(int i = 0; i<largoColaTransiciones ;i++ ){
            int aux = colas[i].getQueueLength();
            if(aux>0) {
                quienes.asignarElemento(1,i,0);
            }else{
                quienes.asignarElemento(0,i,0);
            }
        }
        return quienes;
    }

    /*! \brief Despierta a un hilo que duerme en la cola determinada, por el indice pasado como parametro y aumenta el
    *          contador del semaforo.
    *   \param indice Entero que representa el numero de transicion (la cola de esa transicion).
    * */
    public void liberar(int indice){
        colas[indice].release();
    }

    /*! \brief El hilo que la ejecuta adquiere un lugar en el semaforo y se pone a dormir en la cola determinada por el
     *         indice pasado como parametro.
     *   \param indice Entero que representa el numero de transicion (la cola de esa transicion).
     * */
    public void adquirir(int indice) {
        try {
            colas[indice].acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}