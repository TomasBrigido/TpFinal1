import java.io.*;
import java.sql.Timestamp;
/*La clase Main se encarga de crear los hilos que disparan las transiciones y gestionan el monitor
y los objetos más importantes del programa: red (Red de petri), c1 (colas), p1 (políticas), m (monitor)*/

public class Main {
  
    public static void main(String[] args) throws IOException {
        Logger.clearLogs();
        RdP red = new RdP();
        //aca arrancaria un timestamp en la sensibilizada 0
        red.getSensibilizadasConTiempo().setNuevoTimeStamp(red.sensibilizadas());

        Colas c1 = new Colas(red.getNumeroDeTransiciones());
        Politicas p1 = new Politicas(red);
        Monitor m = new Monitor(red, c1, p1);
        int numeroDeTransiciones = red.getNumeroDeTransiciones();

        Thread[] thread_task = new Thread[15];

        long start=System.currentTimeMillis();
        int indice=0;
        for(int i = 0; i<17 ; i++){
            int[] a= {i};               //Creamos un arreglo para cada hilo para indicarle la transicion/es que dispara
            if(i==5){
                a= new int[]{i, 9};     //Este hilo dispara dos transiciones: la 5 y la 9
            }else if(i==6){
                a= new int[]{i,10};     //Este hilo dispara dos transiciones: la 6 y la 10
            }else if(i==10 || i==9){
                continue;
            }
            Tarea ti = new Tarea(m,a,numeroDeTransiciones); //crea la tarea (le pasamos el arreglo de transiciones)
            Thread hi = new Thread(ti);                     //Crea el hilo, le pasa la tarea
            thread_task[indice]=hi;                         //Colocamos los hilos en un arreglo
            hi.start();
            indice++;
        }

        for (int i=0;i<15;i++) {
            try {
                thread_task[i].join();                  //Hacemos un join para la finalizacion del programa
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long fin = System.currentTimeMillis();
        float res=(float)(fin-start)/1000;
        System.out.println("Tiempo total: "+res+"\n");  //Mostramos tiempo de ejecucion del programa

        Logger.printBalanceEnTxt();     //Imprimimos las estadísticas de cada procesador y memoria.
    }
}