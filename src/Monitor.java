import java.util.concurrent.Semaphore;

public class Monitor {

	private Semaphore mutex;
	private RdP petri_net;
	private Colas colas;
	private Politicas politica;
	private boolean k;
	private boolean despierto; //Bandera para dar lugar en el monitor
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

	public boolean getEnd() {
		return end;
	}

	public Colas getColas(){
		return colas;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	/*
	Metodo que se encarga de organizar la entrada, salida y disparos de los hilos
	 */
	public void dispararTransicion(Matriz transicion)	{	//Matriz transicion = tiene un 1 en la transicion a disparar y 0 en las demas
		try {
			mutex.acquire();								//limita la entrada al monitor
			Logger.println("Entro al monitor el hilo : " + Thread.currentThread().getName(),false);
			k = true;

			while(k && !end){  //!end se utiliza para cerrar la "puerta" del monitor
				k = petri_net.Disparar(transicion);
				Logger.println("Pudo disparar? -> " + k,false);

				int comparacionHiloID = petri_net.getSensibilizadasConTiempo().getdatoSensibilizadaConTiempo().getElemento(transicion.numeroTransicion(),3); //col 3 = IdHilo

				if( comparacionHiloID == Thread.currentThread().getId()){ //Condicional para saber si me tengo que ir a dormir
					mutex.release();
					Logger.println("Voy a dormir",false);
					return;
				}

				if(k){ //Si se pudo disparar k = true sino k = false
                    Matriz and = new Matriz(petri_net.getNumeroDeTransiciones(),1).comparar(petri_net.sensibilizadas(),colas.quienesEstan()); //Matriz de comparacion entra trans sens e hilos en cola
					Logger.println("Imprimo la matriz de comparacion de selsibilizadas y hilos en cola",false);
					and.imprimirMatriz();
					int m = and.sumarElementos();
					Matriz proximo_disparo; // la creo para igualarla a la matriz and

					// Seleccion a un hilo para desperar si m>0 hay hilos para desperar
					if(m>0){
						proximo_disparo = and;
						if(m>1){	//Si m>1 hay mas de un hilo para despertar -> decide la política
							proximo_disparo = politica.cual(and);	//Selecciono que hilo desperar cuando hay mas de uno
							Logger.println("Eligio la transicion: ",false);
							proximo_disparo.imprimirMatriz();
						}
						int indice ;
						indice = proximo_disparo.numeroTransicion();//selecciono el hilo a desperar del arreglo
						Logger.println("Se desperto el hilo de la transicion: " + indice,false);
						colas.liberar(indice);//Despierto al hilo seleccionado
						despierto = true; //Pongo la bandera de despierto en true
						break;  // Me voy del monitor dejando mi lugar sin liberar el mutex

					}else{// Como m=0 no encontre ningun hilo para desperar me voy del monitor liberando el mutex
						k=false;
						despierto = false;
					}

				}else{	// para k == false if(!voyADormir)	//no pude disprar
					Logger.println("Me voy a dormir a la cola de mi transicion",false);
					mutex.release();
					colas.adquirir(transicion.numeroTransicion());
					System.out.println("Me desperte, k="+k);
				}
			}
			if(!despierto) {//Decido si me voy del monitor liberando o no el mutex //if(!despierto) = si no despierto a nadie
				Logger.println("Me voy del monitor sin despertar a ningun hilo",false);
				mutex.release();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}