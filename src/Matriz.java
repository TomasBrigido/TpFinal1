import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Matriz {

    private int[][] m;
    private int numeroDeFilas;
    private int numeroDeColumnas;



    //constructor que inicializa la matriz con 0s
    public Matriz(int numeroDeFilas, int numeroDeColumnas) {

        this.numeroDeFilas = numeroDeFilas;
        this.numeroDeColumnas = numeroDeColumnas;
        m = new int[numeroDeFilas][numeroDeColumnas];

        for (int i = 0; i < numeroDeFilas; i++) {
            for (int j = 0; j < numeroDeColumnas; j++) {
                m[i][j] = 0;
            }
        }
    }

    public int getFilas() {
        return numeroDeFilas;
    }

    public int getColumnas() {
        return numeroDeColumnas;
    }

    public int getElemento(int fila, int columna) {
        return m[fila][columna];
    }

    //asigna un elemento en la pocision dada de la matriz
    public void asignarElemento(int valor, int fila, int columna) {
        m[fila][columna] = valor;
    }

    //imprime la matriz
    public void imprimirMatriz() {
        String es = new String();

        for (int i = 0; i < numeroDeFilas; i++) {
            for (int j = 0; j < numeroDeColumnas; j++) {
                es += "[";
                es += m[i][j];
                es += "]";
            }
            es += '\n';
        }
        System.out.println();
        System.out.println(es);

    }

    //Compara 2 matrices y devuelve un vector de con 1s en la pocision donde hay valores en las 2 matrices y 0s en los demas lugares
    public Matriz comparar(Matriz a, Matriz b) {
        int and = 0;
        Matriz z = new Matriz(a.getFilas(), a.getColumnas());
        if ( a.getFilas() == b.getFilas()) {//a.getColumnas() == b.getColumnas() &&
            for (int i = 0; i < a.getFilas(); i++) {
                for (int j = 0; j < b.getColumnas(); j++) {
                    if (a.getElemento(i, j) > 0 && b.getElemento(i, j) > 0) {
                        and = 1;
                    } else {
                        and = 0;
                    }
                    z.asignarElemento(and, i, j);

                }
            }
        } else {
            System.out.println("Error en comprara las matrices");
        }
        return z;
    }

    //Suma la cantidad de elementos que hay en un vector, (no suma los valores de los elementos que hay en el vector)
    public int sumarElementos() {
        int suma = 0;
        for (int i = 0; i < this.getFilas(); i++) {
            for (int j = 0; j < this.getColumnas(); j++) {
                if (this.getElemento(i, j) > 0)
                    suma++;
            }
        }
        return suma;
    }

    // donde encuentra un 1 devuelve la posicion (devuelve la fila en donde esta el 1 ? bien o mal?)
    public int numeroTransicion() {
        for (int i = 0; i < this.getFilas(); i++) {
            for (int j = 0; j < this.getColumnas(); j++) {

                if (this.getElemento(i, j) == 1) {
                    return i;
                }
            }
        }
        return 0;
    }

    //Retorna false si hay un numero negativo en la matriz (en nuestro caso no queremos un -1) y retorna true si no hay numero negativo en la matriz
    public boolean valida() {
        for (int i = 0; i < this.numeroDeFilas; i++) {
            for (int j = 0; j < this.numeroDeColumnas; j++) {
                if (this.m[i][j] < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    //Transforma una matriz en nula
    public Matriz matrizNula(Matriz m) {
        int filas = m.getFilas();
        int columnas = m.getColumnas();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                m.asignarElemento(0, i, j);
            }
        }
        return m;
    }

    //Recibe como parametro el numero de transicion que se desea disparar y te da el vector del disparo
    public Matriz transformarAVector(int n) {
        Matriz transicion = new Matriz(this.getFilas(), 1);
        transicion.asignarElemento(1, n, 0);
        return transicion;
    }

    //Tiene que se un vector columna y lo rota para arriba
    public void rotar(){
        int aux = this.getElemento(this.numeroDeFilas-1,0);
        for(int i= this.numeroDeFilas-2; i >= 0; i-- ){
            int aux1 = this.getElemento(i,0);
            this.asignarElemento(aux1,i+1,0);
        }
        this.asignarElemento(aux,0,0);
    }
/*
    public void rotar() {
        int aux[][] = this.m;
        for (int i = this.numeroDeFilas - 1; i > 0; i--) {
            this.m[i][0] = aux[i - 1][0];
        }
        this.m[0][0] = aux[numeroDeFilas-1][0];
        this.imprimirMatriz();
    }
*/
    //El numero de columnas de la primera tiene que ser iguarl al numero de filas de la segunda te da una matriz de numero de filas de la primera por el numero de columnas de la segunda
    public static Matriz multiplicar(Matriz x, Matriz y) {// x 5filas 1columna  e y 1fila 5columnas
        Matriz z = new Matriz(x.getFilas(), y.getColumnas());
        int value;

        if (x.getColumnas() == y.getFilas()) {
            for (int i = 0; i < x.getFilas(); i++) {
                for (int j = 0; j < y.getColumnas(); j++) {
                    int sum = 0;
                    for (int k = 0; k < x.getColumnas(); k++) {
                        sum += x.getElemento(i, k) * y.getElemento(k, j);
                    }
                    value = sum;
                    z.asignarElemento(value, i, j);
                }
            }
            return z;
        } else {
            System.out.println(" ERROR METODO MULTIPICACION ERROR: The number of columns of the first Matriz and the number of rows of the second Matriz are not equivalent.");
            return z;
        }
    }

    //Suma de matrices, tienen que ser de la misma dimension
    public Matriz suma(Matriz x, Matriz y) {
        {
            Matriz z = new Matriz(x.getFilas(), x.getColumnas());
            int value;

            if (x.getFilas() == y.getFilas() && x.getColumnas() == y.getColumnas()) {
                for (int i = 0; i < x.getFilas(); i++) {
                    for (int j = 0; j < y.getColumnas(); j++) {
                        value = x.getElemento(i, j) + y.getElemento(i, j);        ///sumo elemento a elemento
                        z.asignarElemento(value, i, j);
                        //System.out.print("[" + z.getElemento(i,j) + "]");
                    }
                    //System.out.print('\n');
                }
                return z;
            } else {
                System.out.println("ERROR: las matrices no son iguales. error metodo SUMA");
                return z;
            }
        }
    }

    //Resta de martrices, tiene que ser de la misma dimension
    public static Matriz resta(Matriz x, Matriz y) {
        Matriz z = new Matriz(x.getFilas(), x.getColumnas());
        int value;
        if (x.getFilas() == y.getFilas() && x.getColumnas() == y.getColumnas()) {
            for (int i = 0; i < x.getFilas(); i++) {
                for (int j = 0; j < y.getColumnas(); j++) {
                    value = x.getElemento(i, j) - y.getElemento(i, j);    ///resto elemento a elemento
                    z.asignarElemento(value, i, j);
                    // System.out.print("[" + z.getElemento(i,j) + "]");
                }
                //System.out.print('\n');
            }
            return z;
        } else {
            System.out.println("ERROR: Las matrices no son iguales");
            return z;
        }
    }




}
/*
    public Matriz cargarDatos(File archivo) {
        m = new int[100][100];
        int numeroLinea = -1;
        int numeroColumna = 0;
        Matriz matriziti = null;
        try {
            BufferedReader lector = null;
            archivo = null;
            lector = new BufferedReader((new FileReader(archivo)));
            String linea = null;
            while ((linea = lector.readLine()) != null) {
                numeroLinea++;
                String[] fila = linea.split(" ");
                for (numeroColumna = 0; numeroColumna < fila.length; numeroColumna++) {
                    int valor = Integer.parseInt(fila[numeroColumna]);
                    m[numeroLinea][numeroColumna] = valor;
                }
                matriziti = new Matriz(numeroLinea + 1, numeroColumna);
                for (int i = 0; i <= numeroLinea; i++)
                    for (int j = 0; j < numeroColumna; j++) {
                        matriziti.asignarElemento(i, j, m[i][j]);
                    }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matriziti;
    }

    public Matriz cargarArchivo(String String_archivo){
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        File archivo = new File(path);
        path = archivo.getParent();
        path = path.substring(path.lastIndexOf("\\")+1,path.length())+String_archivo;
        File archivo2 = new File(path);
        Matriz matrix = cargarDatos(archivo2);
        return matrix;
    }

}
*/

/*
//Methods: Addition, subtraction and mutiplication operations were written as methods in order to operate on matricices. Both static and non-static methods were constructed.
//The static versions take two matricies as parameters, while the non-static counterparts only take one and operate it against m.
//Within all six methods a test is performed in order to check whether or not the two matrcicies are compatible or not.
//For addiition and subtraction the number of rows and columns must be the same for both matricies and a temporary Matriz is created to match those parameters.
//For multiplication, the number of columns of the first Matriz must be equivalent to the number of rows of the second Matriz and a temporary Matriz is created with the number of rows of the first Matriz and
//the number of columns of the second Matriz as parameters. If the matricies are not compatable an error message is printed.
  public Matriz() {
/*     Scanner keyboard = new Scanner(System.in);
    System.out.println();
    System.out.print("Please enter the number of rows: "); numberOfRows = keyboard.nextInt();
    System.out.print("Please enter the number of columns: "); numberOfColumns = keyboard.nextInt();
    m = new double[numberOfRows][numberOfColumns];
    System.out.println("Please assign the following elements: ");
    for(int i = 0; i < numberOfRows; i++)
        {
            for(int j = 0; j < numberOfColumns; j++)
                {
                    System.out.print("[" + i + "]" + "[" + j + "]: ");
                    m[i][j] = keyboard.nextDouble();
                }
        }
    }
    public static Matriz staticAdd(Matriz x, Matriz y) {
        Matriz z = new Matriz(x.getRows(), x.getColumns());
        double value;
        System.out.println();
        System.out.println("The sum of the matricices is: ");

        if (x.getRows() == y.getRows() && x.getColumns() == y.getColumns()) {
            for (int i = 0; i < x.getRows(); i++) {
                for (int j = 0; j < y.getColumns(); j++) {
                    value = x.getElement(i, j) + y.getElement(i, j);
                    z.assignElement(value, i, j);
                    System.out.print("[" + z.getElement(i, j) + "]");
                }
                System.out.print('\n');
            }
            return z;
        } else {
            System.out.println("ERROR: The number of rows and columns of the matricies are not equal.");
            return z;
        }
    }


    public static Matriz staticSubtract(Matriz x, Matriz y) {
        Matriz z = new Matriz(x.getRows(), x.getColumns());
        double value;
        System.out.println();
        System.out.println("The difference of the two matricices is: ");

        if (x.getRows() == y.getRows() && x.getColumns() == y.getColumns()) {
            for (int i = 0; i < x.getRows(); i++) {
                for (int j = 0; j < y.getColumns(); j++) {
                    value = x.getElement(i, j) - y.getElement(i, j);
                    z.assignElement(value, i, j);
                    System.out.print("[" + z.getElement(i, j) + "]");
                }
                System.out.print('\n');
            }
            return z;
        } else {
            System.out.println("ERROR: The number of rows and columns of the matricies are not equal.");
            return z;
        }
    }

    public static Matriz staticMultiply(Matriz x, Matriz y) {
        Matriz z = new Matriz(x.getRows(), y.getColumns());
        double value;
        System.out.println();
        System.out.println("The product of the matricices is: ");

        if (x.getColumns() == y.getRows()) {
            for (int i = 0; i < x.getRows(); i++) {
                for (int j = 0; j < y.getColumns(); j++) {
                    double sum = 0;
                    for (int k = 0; k < x.getRows(); k++) {
                        sum += x.getElement(i, k) * y.getElement(k, j);
                    }
                    value = sum;
                    z.assignElement(value, i, j);
                    System.out.print("[" + z.getElement(i, j) + "]");
                }
                System.out.print('\n');
            }
            return z;
        } else {
            System.out.println("ERROR: The number of columns of the first Matriz and the number of rows of the second Matriz are not equivalent.");
            return z;
        }
    }


    public Matriz add(Matriz x) {
        Matriz z = new Matriz(numberOfRows, numberOfColumns);
        double value;
        System.out.println();
        System.out.println("The sum of the matricices is: ");

        if (numberOfRows == x.getRows() && numberOfColumns == x.getColumns()) {
            for (int i = 0; i < x.getRows(); i++) {
                for (int j = 0; j < x.getColumns(); j++) {
                    value = m[i][j] + x.getElement(i, j);
                    z.assignElement(value, i, j);
                    System.out.print("[" + z.getElement(i, j) + "]");
                }
                System.out.print('\n');
            }
            return z;
        } else {
            System.out.println("ERROR: The number of rows and columns of the matricies are not equivalent.");
            return z;
        }
    }


    public Matriz subtract(Matriz x) {
        Matriz z = new Matriz(numberOfRows, numberOfColumns);
        double value;
        System.out.println();
        System.out.println("The difference of the two matricices is: ");

        if (numberOfRows == x.getRows() && numberOfColumns == x.getColumns()) {
            for (int i = 0; i < x.getRows(); i++) {
                for (int j = 0; j < x.getColumns(); j++) {
                    value = m[i][j] - x.getElement(i, j);
                    z.assignElement(value, i, j);
                    System.out.print("[" + z.getElement(i, j) + "]");
                }
                System.out.print('\n');
            }
            return z;
        } else {
            System.out.println("ERROR: The number of rows and columns of the matricies are not equivalent.");
            return z;
        }
    }

    public Matriz multiply(Matriz x) {
        Matriz z = new Matriz(numberOfRows, x.getColumns());
        double value;
        System.out.println();
        System.out.println("The product of the matricices is: ");

        if (numberOfColumns == x.getRows()) {
            for (int i = 0; i < numberOfRows; i++) {
                for (int j = 0; j < x.getColumns(); j++) {
                    double sum = 0;
                    for (int k = 0; k < numberOfRows; k++) {
                        sum += m[i][k] * x.getElement(k, j);
                    }
                    value = sum;
                    z.assignElement(value, i, j);
                    System.out.print("[" + z.getElement(i, j) + "]");
                }
                System.out.print('\n');
            }
            return z;
        } else {
            System.out.println("ERROR: The number of columns of the first Matriz and the number of rows of the second Matriz are not equivalent.");
            return z;
        }
    }

 */