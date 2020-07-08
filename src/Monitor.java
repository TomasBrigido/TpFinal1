import java.util.concurrent.Semaphore;

public class Monitor {

	private Semaphore mutex;
	private RdP petri_net;
	private Colas colas;
	private Politicas politica;
	private boolean k;
	private boolean despierto ;

	public Monitor(RdP red, Colas colas, Politicas politica) {
		mutex = new Semaphore(1,true);
		this.petri_net = red;
		this.colas = colas;
		this.politica = politica;
		despierto = false;

	}

	public void dispararTransicion(Matriz transicion,boolean temporal) {
		try {
			//System.out.println("Intenta ingresar: " + Thread.currentThread().getName());
			mutex.acquire();
			System.out.println(Thread.currentThread().getName() + " entro al monitor");
			k = true;

			while(k){
				k = petri_net.Disparar(transicion,temporal);
				System.out.println(k);
				if(k){
					Matriz and = new Matriz(petri_net.getNumeroDeTrancisiones(),1).comparar(petri_net.sensibilizadas(),colas.quienesEstan());//.comparar(sensibilizadas,quienes);
					int m = and.sumarElementos();
					Matriz proximo_disparo; // la creo para igualarla a la matriz and

					// aca se busca el hilo a despertar dentro de la cola correspondiente, se lo despierta y nos fuimos, queda el hilo despierto dentro del while linea 28
					if(m>0){
						proximo_disparo = and;
						if(m>1){
							proximo_disparo = politica.cual(and);//despertar a otro
							System.out.println("Eligio la transicion: ");
							proximo_disparo.imprimirMatriz();
						}
						int indice ;
						indice = proximo_disparo.numeroTransicion();//que hilo tiene que despertar en el arreglo de semaforos
						System.out.println(indice);
						colas.liberar(indice);
						despierto = true;
						break;
						//sacar hilo del monitor sin ejecutar mutex.realese();
					}else{// para m==0
						System.out.println("Entre al else");
						k=false;
						despierto = false;
					}

				}else{	// para k == false
					mutex.release();//aumenta el semafoto en uno para que enrte otro hilo
					//colas.adquirir(indice); //cambiar indice por transicion.numero_transicion();	que hace el hilo si no hay nadie en la cola?
					colas.adquirir(transicion.numeroTransicion());
				}
				//mutex.release();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			if(!despierto) {
				System.out.println("Entro al if del mutex");
				mutex.release();
			}
		}
	}

}