import java.util.concurrent.Semaphore;

public class Monitor {

	private Semaphore mutex;
	private RdP petri_net;
	private Colas colas;
	private Politicas politica;
	private boolean k;
	private boolean despierto;
	private boolean end;

	public Monitor(RdP red, Colas colas, Politicas politica) {
		mutex = new Semaphore(1,true);
		this.petri_net = red;
		this.colas = colas;
		this.politica = politica;
		despierto = false;
		end=false;
	}

	public RdP getRedDePetri(){
		return petri_net;
	}

	public void dispararTransicion(Matriz transicion)	{
		try {
			mutex.acquire();
			Logger.println("Entro al monitor el hilo : " + Thread.currentThread().getName(),false);
			k = true;

			while(k){
				k = petri_net.Disparar(transicion);
				Logger.println("Pudo disparar? -> " + k,false);

				int comparacionHiloID = petri_net.getSensibilizadasConTiempo().getdatoSensibilizadaConTiempo().getElemento(transicion.numeroTransicion(),3);

				if( comparacionHiloID == Thread.currentThread().getId()){
					mutex.release();
					Logger.println("Voy a dormir",false);
					return;
				}

				if(k){
                    Matriz and = new Matriz(petri_net.getNumeroDeTransiciones(),1).comparar(petri_net.sensibilizadas(),colas.quienesEstan());
					Logger.println("Imprimo la matriz de comparacion de selsibilizadas y hilos en cola",false);
					and.imprimirMatriz();
					int m = and.sumarElementos();
					Matriz proximo_disparo; // la creo para igualarla a la matriz and

					// aca se busca el hilo a despertar dentro de la cola correspondiente, se lo despierta y nos fuimos, queda el hilo despierto dentro del while linea 28
					if(m>0){
						proximo_disparo = and;
						if(m>1){
							proximo_disparo = politica.cual(and);//despertar a otro
							Logger.println("Eligio la transicion: ",false);
							proximo_disparo.imprimirMatriz();
						}
						int indice ;
						indice = proximo_disparo.numeroTransicion();//que hilo tiene que despertar en el arreglo de semaforos
						Logger.println("Se desperto el hilo de la transicion: " + indice,false);
						colas.liberar(indice);
						despierto = true;
						break;

					}else{// para m==0
						k=false;
						despierto = false;
					}

				}else{	// para k == false if(!voyADormir)
					Logger.println("Me voy a dormir a la cola de mi transicion",false);
					mutex.release();
					colas.adquirir(transicion.numeroTransicion());
					System.out.println("Me desperte, k="+k);
				}
			}
			if(!despierto) {
				if(end){
					System.out.println("Finalizando hilo");
					return;
				}
				Logger.println("Me voy del monitor sin despertar a ningun hilo",false);
				mutex.release();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean getEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public Colas getColas(){
		return colas;
	}
}