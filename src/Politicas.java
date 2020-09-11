import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;

public class Politicas {
    Random random = new Random();
    RdP rdp;

    public Politicas(RdP red)  {
        rdp = red;
    }

    /*
    \brief Devuelve un vector de transiciones de un solo 1 y demas ceros. Ese 1 representa la transicion
    que segun la politica se va a disparar. Se selecciona aleatoriamente entre todos los hilos que se pueden diparar

    \param recibe la materiz and, con todos los hilos que estan durmiendo y se puedn disparar
    \return matriz transicion: tiene un 1 en la transición a disparar y cero en las demas.
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