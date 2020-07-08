import javax.swing.*;
import java.io.*;
import java.sql.Timestamp;


public class Main {
  
    public static void main(String[] args) throws IOException {

        RdP red = new RdP();
        /*
        Colas c1 = new Colas(red.getNumeroDeTrancisiones());
        Politicas p1 = new Politicas(red);
        Monitor m = new Monitor(red,c1,p1);

       // for (int i = 0; i<4; i++){
            Tarea t = new Tarea(m, 0, red.getNumeroDeTrancisiones());
            Thread h0 = new Thread(t);
            h0.start();

*/      red.sensibilizadasConTiempo.getSensibilizadaConTiempo().imprimirMatriz();
        long fecha = System.currentTimeMillis();
        Timestamp tsp = new Timestamp(fecha);
        System.out.println(tsp);

        try{
            Thread.sleep(2000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        long fecha2 = System.currentTimeMillis();
        Timestamp tsp2 = new Timestamp(fecha2);
        System.out.println(tsp2);

        long resta = tsp2.getTime() - tsp.getTime();
        System.out.println(resta);


    }
}