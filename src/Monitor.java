import java.util.concurrent.Semaphore;

public class Monitor {

	private Semaphore mutex;
	private RdP petri_net;
	public Colas colas;//cambiar a private
	private Politicas politica;
	boolean k;
	private Matriz sensibilizadas;
	private Matriz quienes;
	boolean despierto ;



	public Monitor(RdP red, Colas colas, Politicas politica) {
		mutex = new Semaphore(1,true);
		this.petri_net = red;
		this.colas = colas;
		this.politica = politica;
		this.sensibilizadas = new Matriz(red.numeroDeTrancisiones,1); //de tamaño ? y de valores ?
		quienes = new Matriz(red.numeroDeTrancisiones,1);	//de tamaño ? y de valores ?
		despierto = false;

	}

	public void dispararTransicion(Matriz transicion) {//En lugar de pasarle una matriz pasarle un entero que se la transicion

		try {
			System.out.println("Intenta ingresar: " + Thread.currentThread().getName());
			mutex.acquire();
			System.out.println(Thread.currentThread().getName() + " Entro al monitor");
			k = true;

			while(k){
				k = petri_net.Disparar(transicion);

				System.out.println(k);
//ESTO ES PARA DESPERTAR EL HILO SIGUIENTE
				if(k){
					sensibilizadas = petri_net.sensibilizadas();
					System.out.println("sensibiilizadas: ");
					sensibilizadas.imprimirMatriz();
					quienes = colas.quienesEstan();
					System.out.println("Imprimo los hilos en cola");
					quienes.imprimirMatriz();

					// vector de transiciones estan sensibilizadas y que ademas hay hilos esperando para disparar eias.
					Matriz and = new Matriz(petri_net.numeroDeTrancisiones,1).comparar(sensibilizadas,quienes);
					System.out.println("Imprimo la matriz de comparacion: ");
					and.imprimirMatriz();

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


/*
	public void dispararTransicion(int transicion) {//En lugar de pasarle una matriz pasarle un entero como transicion

		try {
			mutex.acquire();
			 k = petri_net.Disparar(transicion);
			while(k){
				if(false){
					sensibilizadas = petri_net.sensibilizadas();
					quienes = colas.quienesEstan();

					// vector de transiciones estan sensibilizadas y que ademas hay hilos esperando para disparar eias.
					Matriz and = new Matriz(17,0).comparar(sensibilizadas,quienes);
					int m = and.sumarElementos();
					Matriz proximo_disparo;

					// aca se busca el hilo a despertar dentro de la cola correspondiente, se lo despierta y nos fuimos, queda el hilo despierto dentro del while linea 28
					if(m>0){
						proximo_disparo = and;
						if(m>1){
							proximo_disparo = politica.cual(and); //despertar a otro
						}

						indice = proximo_disparo.numeroTransicion();
						colas.liberar(indice);
						//mutex.release();?? o k==false; ?
					}else{ // para m==0
						k=false;
					}

				}else{	// para k == false
					mutex.release();//aumenta el semafoto en uno para que enrte otro hilo
					//colas.adquirir(indice); //cambiar indice por transicion.numero_transicion();	que hace el hilo si no hay nadie en la cola?
					//colas.adquirir(transicion);
				}
			}
		}
			 catch (InterruptedException e) {
				 e.printStackTrace();
		}

		colas.quienesEstan().imprimirMatriz();
		mutex.release();
		colas.adquirir(transicion);
	}

 */
}