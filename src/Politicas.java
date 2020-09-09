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

}