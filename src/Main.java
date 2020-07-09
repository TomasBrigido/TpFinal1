import javax.swing.*;
import javax.swing.plaf.TableHeaderUI;
import java.io.*;
import java.sql.Timestamp;


public class Main {
  
    public static void main(String[] args) throws IOException {

        RdP red = new RdP();
        red.getSensibilizadasConTiempo().setNuevoTimeStamp(red.sensibilizadas());
        red.getSensibilizadasConTiempo().getdatoSensibilizadaConTiempo().imprimirMatriz();
        Colas c1 = new Colas(red.getNumeroDeTrancisiones());
        Politicas p1 = new Politicas(red);
        Monitor m = new Monitor(red, c1, p1);
        int numeroDeTransiciones = red.getNumeroDeTrancisiones();

        Tarea t1 = new Tarea(m,0,numeroDeTransiciones,true);
        Thread h1 = new Thread(t1);
        h1.start();

        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }







    }
}