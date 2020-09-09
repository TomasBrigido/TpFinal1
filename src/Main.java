import java.io.*;
import java.sql.Timestamp;


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
            int[] a= {i};
            if(i==5){
                a= new int[]{i, 9};
            }else if(i==6){
                a= new int[]{i,10};
            }else if(i==10 || i==9){
                continue;
            }
            Tarea ti = new Tarea(m,a,numeroDeTransiciones);
            Thread hi = new Thread(ti);
            thread_task[indice]=hi;
            hi.start();
            indice++;
        }

        for (int i=0;i<15;i++) {
            try {
                thread_task[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long fin = System.currentTimeMillis();
        float res=(float)(fin-start)/1000;
        System.out.println("Tiempo total: "+res+"\n");

        Logger.printBalanceEnTxt();
    }
}