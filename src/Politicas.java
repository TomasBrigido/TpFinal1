import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;

public class Politicas {
    Random random = new Random();
    RdP rdp;
    Matriz matriz = new Matriz(0,0);

    public Politicas(RdP red) throws IOException {
        rdp = red;
    }

    /*
    Devuelve un vector de transiciones de un solo 1 y demas ceros. Ese 1 representa la transicion que segun la politica se va a disparar
    */
    public Matriz cual(Matriz a){
        ArrayList<Integer> listaTransiciones = new ArrayList<>();
        int contador = 0;
        for (int i = 0; i < a.getFilas()-1; i++){
            if (a.getElemento(i,0) > 0){
                listaTransiciones.add(contador);
            }
            contador++;
        }
        int n = random.nextInt(listaTransiciones.size());
        int transicionElegida = listaTransiciones.get(n);
        Matriz transicion = new Matriz(a.getFilas(),a.getColumnas());
        return transicion.transformarAVector(transicionElegida);
    }

    /*
     * Selecciona entre las dos posibles tarea que tiene el hilo y te devuelve la transicion a disparar
     * */
    public Matriz EleccionTarea(int cola){
        if (cola == 1){
            int tarea1 = rdp.getContadorTarea(0);
            int tarea2 = rdp.getContadorTarea(1);
            if (tarea1 < tarea2){
                return matriz.transformarAVector(8);
            }
            return matriz.transformarAVector(6);
        }else{
            int tarea3 = rdp.getContadorTarea(2);
            int tarea4 = rdp.getContadorTarea(3);
            if (tarea3 < tarea4){
                return matriz.transformarAVector(7);
            }
            return matriz.transformarAVector(5);
        }
    }

    /*
     * Selecciona la memoria con menos elementos
     * */
    public Matriz EleccionMemoria(){
        int memoria1 = rdp.estadoMemoria(1);
        int memoria2 = rdp.estadoMemoria(0);
        if(memoria1 < memoria2){
            return matriz.transformarAVector(14);
        }
        return matriz.transformarAVector(15);
    }

    /*
     * Selecciona entre (1 - 0) para decidir a que cola se hara la transicion, si una de las dos esta llena, va hacia la otra
     * Te devuelve la transicion (T1 or T2)
     * */
    public Matriz EleccionColaProceso(){
        int n = random.nextInt(1);
        boolean k = rdp.isFull(n);
        if (k){
            if (n == 1){
                return matriz.transformarAVector(1);
            }else{
                return matriz.transformarAVector(2);
            }
        }else{
            return matriz.transformarAVector(n + 1);
        }
    }


}