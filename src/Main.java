import javax.swing.*;
import javax.swing.plaf.TableHeaderUI;
import java.io.*;
import java.sql.Timestamp;

import static java.lang.Thread.sleep;


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

        Thread thread_task[]= new Thread[15];


/*
        Tarea t0 = new Tarea(m,0,numeroDeTransiciones);
        Thread h0 = new Thread(t0);
        h0.start();

        Tarea t1 = new Tarea(m,1,numeroDeTransiciones);
        Thread h1 = new Thread(t1);
        h1.start();

        Tarea t2 = new Tarea(m,11,numeroDeTransiciones);
        Thread h2 = new Thread(t2);
        h2.start();

        Tarea t3 = new Tarea(m,3,numeroDeTransiciones);
        Thread h3 = new Thread(t3);
        h3.start();

        Tarea t4 = new Tarea(m,7,numeroDeTransiciones);
        Thread h4 = new Thread(t4);
        h4.start();
*/
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

        System.out.println("Finalizado wachin!");
        //Logger.println("Cantidad de ",false);
/*        while (true){

            System.out.println("*************************************** 5: " + m.contador5 + "*****************************************");
            System.out.println("*************************************** 6: " + m.contador6 + "*****************************************");
            System.out.println("*************************************** 7: " + m.contador7 + "*****************************************");
            System.out.println("*************************************** 8: " + m.contador8 + "*****************************************");
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }*/

        // try{
        //     Thread.sleep(1000);
        // }catch (InterruptedException e){
        //     e.printStackTrace();
        // }

    }
}