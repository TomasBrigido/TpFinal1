import java.util.concurrent.Semaphore;

public class Monitor {

	private Semaphore mutex;
	private RdP petri_net;
	private Colas colas;
	private Politicas politica;
	private boolean k;
	private boolean despierto;
	public int contador;

	public Monitor(RdP red, Colas colas, Politicas politica) {
		mutex = new Semaphore(1,true);
		this.petri_net = red;
		this.colas = colas;
		this.politica = politica;
		despierto = false;
	}

	public RdP getRedDePetri(){
		return petri_net;
	}

	public void dispararTransicion(Matriz transicion)	{
		try {
			mutex.acquire();
			System.out.println("Entro al monitor el hilo : " + Thread.currentThread().getName());
			k = true;

			while(k){
				k = petri_net.Disparar(transicion);
				System.out.println("Pudo disparar? -> " + k);

				int comparacionHiloID = petri_net.getSensibilizadasConTiempo().getdatoSensibilizadaConTiempo().getElemento(transicion.numeroTransicion(),3);

				if( comparacionHiloID == Thread.currentThread().getId()){
					mutex.release();
					System.out.println("Voy a dormir");
					return;
				}

				if(k){

				    if(transicion.numeroTransicion() == 11 || transicion.numeroTransicion() == 12 || transicion.numeroTransicion() == 13 || transicion.numeroTransicion() == 14){
                        contador ++;
                    }


					Matriz and = new Matriz(petri_net.getNumeroDeTransiciones(),1).comparar(petri_net.sensibilizadas(),colas.quienesEstan());//.comparar(sensibilizadas,quienes);
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
						System.out.println("Se desperto el hilo de la transicion: " + indice);
						colas.liberar(indice);
						despierto = true;
						break;

					}else{// para m==0
						k=false;
						despierto = false;
					}

				}else{	// para k == false if(!voyADormir)
					System.out.println("Me voy a dormir a la cola de mi transicion");
					mutex.release();
					colas.adquirir(transicion.numeroTransicion());
				}
			}
			if(!despierto) {
				System.out.println("Me voy del monitor sin despertar a ningun hilo");
				mutex.release();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}