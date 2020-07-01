import javax.swing.*;
import java.io.*;

public class Main {
  
    public static void main(String[] args) throws IOException {

        RdP red = new RdP();
        int a = red.numeroDeTrancisiones;
        Colas c1 = new Colas(a);
        Politicas p1 = new Politicas(red);
        Monitor m = new Monitor(red,c1,p1);



        Tarea t4 = new Tarea(m,3,a);
        Thread h4 = new Thread(t4);
        h4.start();

        try{
            Thread.currentThread().sleep(200);
        }catch (InterruptedException e){e.printStackTrace();}


        Tarea t1 = new Tarea(m,1,a);
        Thread h1 = new Thread(t1);
        h1.start();

        Tarea t2 = new Tarea(m,2,a);
        Thread h2 = new Thread(t2);
        h2.start();


        try{
            Thread.currentThread().sleep(200);
        }catch (InterruptedException e){e.printStackTrace();}


        Tarea t3 = new Tarea(m,0,a);
        Thread h3 = new Thread(t3);
        h3.start();


    }
}