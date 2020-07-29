import javax.swing.*;
import javax.swing.plaf.TableHeaderUI;
import java.io.*;
import java.sql.Timestamp;


public class Main {
  
    public static void main(String[] args) throws IOException {

        RdP red = new RdP();
        //aca arrancaria un timestamp en la sensibilizada 0
        red.getSensibilizadasConTiempo().setNuevoTimeStamp(red.sensibilizadas());

        Colas c1 = new Colas(red.getNumeroDeTransiciones());
        Politicas p1 = new Politicas(red);
        Monitor m = new Monitor(red, c1, p1);
        int numeroDeTransiciones = red.getNumeroDeTransiciones();
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
        for(int i = 0; i<17 ; i++){
            Tarea ti = new Tarea(m,i,numeroDeTransiciones);
            Thread hi = new Thread(ti);
            hi.start();
        }

        while (true){

            System.out.println("***************************************" + m.contador + "*****************************************");

            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }







        // try{
        //     Thread.sleep(1000);
        // }catch (InterruptedException e){
        //     e.printStackTrace();
        // }

        

    }
}