import javax.swing.*;
import javax.swing.plaf.TableHeaderUI;
import java.io.*;
import java.sql.Timestamp;


public class Main {
  
    public static void main(String[] args) throws IOException {

        RdP red = new RdP();
        red.getSensibilizadasConTiempo().setNuevoTimeStamp(red.sensibilizadas());
        Colas c1 = new Colas(red.getNumeroDeTrancisiones());
        Politicas p1 = new Politicas(red);
        Monitor m = new Monitor(red, c1, p1);

        red.getSensibilizadasConTiempo().getdatoSensibilizadaConTiempo().imprimirMatriz();

        try {
            Thread.sleep(980);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        Matriz m1 = new Matriz(17,1);
        m1.asignarElemento(1,0,0);

        System.out.println(red.getSensibilizadasConTiempo().testVentanaDeTiempo(m1));

        red.getSensibilizadasConTiempo().setEsperando(m1);
        red.getSensibilizadasConTiempo().getdatoSensibilizadaConTiempo().imprimirMatriz();






    }
}