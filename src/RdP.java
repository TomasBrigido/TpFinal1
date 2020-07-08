import java.io.File;
import java.io.IOException;


public class RdP{
    private Matriz sensibilizadas;
    private Matriz incidencia;
    private Matriz marcaActual;
    private Matriz marcaInicial;
    private int numeroDeTrancisiones ;
    private int numeroDePlazas ;
    private int contradorTareas;
    private int contradorMemoria;

    public RdP() throws IOException {

        File miDir = new File (".");
        String path = miDir.getCanonicalPath();

        GeneradorMatrices g = new GeneradorMatrices(path + "/src/incidencia.txt");
        incidencia = g.cargarDatos();

        numeroDeTrancisiones = g.determinarColumnas();
        numeroDePlazas = g.determinarFilas();

        sensibilizadas = new Matriz(numeroDeTrancisiones,1);

        g.setPath(path + "/src/marcaInicial.txt");
        marcaInicial = g.cargarDatos();



        marcaActual = new Matriz(numeroDeTrancisiones,1);
        marcaActual=marcaInicial;

    }

    public Matriz getSensibilizadas() {
        return sensibilizadas;
    }

    public Matriz getIncidencia() {
        return incidencia;
    }

    public Matriz getMarcaActual() {
        return marcaActual;
    }

    public Matriz getMarcaInicial() {
        return marcaInicial;
    }

    public int getNumeroDeTrancisiones() {
        return numeroDeTrancisiones;
    }

    public int getNumeroDePlazas() {
        return numeroDePlazas;
    }

    public boolean Disparar(Matriz transicion){

        if(true){
            System.out.println("SOY UNA TRANCISION TEMPORAL");
            /*
            Del vector de sensibilizados temporales identificar cual se esta disparando y si llego al tiempo alpha(booleano)

             */
            //return false;


        }



            System.out.println("Imprimo la matriz transicion;");
            transicion.imprimirMatriz();
            Matriz aux = new Matriz(numeroDePlazas, 1);
            aux = aux.multiplicar(incidencia, transicion);
            Matriz aux2 = new Matriz(numeroDePlazas, 1);
            aux2 = aux2.suma(marcaActual, aux);
            System.out.println("imprimo la matriz marca actual: ");
            aux2.imprimirMatriz();
            boolean m = aux2.valida();
            if (m) {
                marcaActual = aux2;
                return true;
            }
            return false;

        }

    public Matriz sensibilizadas(){
        Matriz transiciones = new Matriz(numeroDeTrancisiones,1);
        transiciones.asignarElemento(1,0,0);

        for(int i=0; i<numeroDeTrancisiones; i++) {
            Matriz aux = new Matriz(numeroDePlazas,1);
                    aux = aux.multiplicar(incidencia,transiciones);
            Matriz aux2 = new Matriz(numeroDePlazas,1);
                    aux2 = aux2.suma(marcaActual, aux);
            boolean m = aux2.valida();
            if(m){
                sensibilizadas.asignarElemento(1,i,0);
            }else{
                sensibilizadas.asignarElemento(0,i,0);
            }
            transiciones.rotar();

        }

        return sensibilizadas;
    }

    public int getContadorTarea(int i) {
        return 0;
    }

    public int estadoMemoria(int i) {
        return 0;
    }

    public boolean isFull(int n) {
        return false;
    }

}