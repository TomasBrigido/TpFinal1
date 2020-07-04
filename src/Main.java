import javax.swing.*;
import java.io.*;

public class Main {
  
    public static void main(String[] args) throws IOException {

        RdP red = new RdP();
        Colas c1 = new Colas(red.getNumeroDeTrancisiones());
        Politicas p1 = new Politicas(red);
        Monitor m = new Monitor(red,c1,p1);

        for (int i = 0; i<4; i++){
            Tarea t = new Tarea(m, i, red.getNumeroDeTrancisiones(), false);
            Thread h0 = new Thread(t);
            h0.start();
        }
/*
        Tarea t1 = new Tarea(m,1,a,false);
        Thread h1 = new Thread(t1);

        Tarea t2 = new Tarea(m,2,a,false);
        Thread h2 = new Thread(t2);
*/
        //h1.start();
        //h2.start();


    }
}