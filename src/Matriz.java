public class Matriz {

    private int[][] m;
    private int numeroDeFilas;
    private int numeroDeColumnas;

    //Inicializa la matriz con 0s con las dimensiones dadas como parametros.
    public Matriz(int numeroDeFilas, int numeroDeColumnas) {
        this.numeroDeFilas = numeroDeFilas;
        this.numeroDeColumnas = numeroDeColumnas;
        m = new int[numeroDeFilas][numeroDeColumnas];

        for (int i = 0; i < numeroDeFilas; i++) {       //inicializa todos los valores en cero
            for (int j = 0; j < numeroDeColumnas; j++) {
                m[i][j] = 0;
            }
        }
    }

    /*! \brief Metodo que devuelve el valor entero que representa el numero de filas de la matriz.
     *  \return numero de filas de la matriz.
     */
    public int getFilas() {
        return numeroDeFilas;
    }

    /*! \brief Metodo que devuelve el valor entero que representa el numero de columnas de la matriz.
     *  \return numero de columnas de la matriz.
     */
    public int getColumnas() {
        return numeroDeColumnas;
    }

    /*! \brief Metodo que devuelve el valor del elemento dado por la fila y la columna pasadas como parametros.
        \param fila entero que representa el numero de fila en la matriz.
        \param columna entero que representa el numero de columna en la matriz.
        \return numero de filas de la matriz.
    */
    public int getElemento(int fila, int columna) {
        return m[fila][columna];
    }

    /*! \brief Asigna un valor a un elemento determinado, en la matriz.
        \param valor entero que se asignará en la posicion dada de la matriz.
        \param fila entero que representa el numero de fila en la matriz.
        \param columna entero que representa el numero de columna en la matriz.
        \return numero de filas de la matriz.
    */
    public void asignarElemento(int valor, int fila, int columna) {
        m[fila][columna] = valor;
    }

    /*! \brief Imprime los valores de la matriz completa por consola.  */
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
        Logger.println("",false);
        Logger.println(es,false);
    }

    /*! \brief Compara dos matrices de tipo vector vertical y devuelve un vector en el que el valor es 1 o 0 segun
     *         si hay valores en la misma posicion en ambas matrices. Se hace una operacion AND elemento a elemento.
     *   \param a primera de las matrices a comparar.
     *   \param b segunda matriz a comparar.
     *   \return Matriz (objeto) compuesta por 1s y 0s.
    */
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
            Logger.println("Error en comprara las matrices",false);
        }
        return z;
    }

    /*! \brief Suma la cantidad de elementos distintos de 0 que hay en una matriz.
     *   \return Cantidad de elementos no nulos en la matriz.
     */
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

    /*! \brief Devuelve la posicion en la matriz en la que encuentra un valor 1. Como se trata de un vector vertical,
     *          devuelve el numero de fila. Debe existir un unico valor 1 en la matriz.
     *  \return Posicion de aparicion del valor distinto de 0 o 0 en caso de no encontrarlo.
     */
    public int numeroTransicion() {
        for (int i = 0; i < this.getFilas(); i++) {
            for (int j = 0; j < this.getColumnas(); j++) {
                if (this.getElemento(i, j) == 1) {
                    return i;
                }
            }
        }
        return -1;
    }

    /*! \brief Verifica que en la matriz no existen valores negativos en cada uno de sus elementos.
        \return True si no existen dichos valores o False en caso que haya valores menores a 0.
     */
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

    /*! \brief Transforma una matriz determinada a una matriz de 0s conservando sus dimensiones.
        \param m Matriz a transformar en nula
        \return Matriz (objeto) nula con las mismas dimensiones de la matriz pasada como parametro.
    */
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

    /*! \brief Devuelve un vector de disparo de una columna de dimension. El valor pasado como parametro es la posicion del vector
     *         que tendrá el unico valor 1, los demas elementos serán 0s en el vector.
        \param n posicion en el vector.
        \return Matriz (objeto) con el mismo numero de filas y una columna.
     */
    public Matriz transformarAVector(int n) {
        Matriz transicion = new Matriz(this.getFilas(), 1);
        transicion.asignarElemento(1, n, 0);
        return transicion;
    }

    /*! \brief Rota en un vector columna constituido de un solo valor 1 y demas 0s, el valor 1 a la posicion siguiente hacia abajo.
     *         En el caso de ser el ultimo elemento del vector, rota hacia el elemento inicial del mismo.
     */
    public void rotar(){
        int aux = this.getElemento(this.numeroDeFilas-1,0);
        for(int i= this.numeroDeFilas-2; i >= 0; i-- ){
            int aux1 = this.getElemento(i,0);
            this.asignarElemento(aux1,i+1,0);
        }
        this.asignarElemento(aux,0,0);
    }

    /*! \brief Multiplica dos matrices de manera vectorial y devuelve la matriz resultante. En caso de que no se cumpla la regla
     *         de dimensiones en la multiplicacion (Numero de columnas de la primera tiene que ser igual al numero de filas de
     *         la segunda), devuelve una matriz nula junto con un error por consola.
     *  \param x matriz que premultiplica a y.
     *  \param y matriz que posmultiplica a x.
     *  \return Matriz (objeto) resultante de la multiplicacion vectorial.
     */
    public static Matriz multiplicar(Matriz x, Matriz y) {
        Matriz z = new Matriz(x.getFilas(), y.getColumnas());
        int value;

        if(x.getColumnas() == y.getFilas()) {
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
            Logger.println(" ERROR METODO MULTIPICACION ERROR: The number of columns of the first Matriz and the number of rows of the second Matriz are not equivalent.",false);
            return z;
        }
    }

    /*! \brief Suma dos matrices de forma elemento a elemento (posicion a posicion). Deben tener las misma dimensiones,
     *         sino devuelve una matriz nula con un error impreso en consola.
     *  \param x matriz, el primer sumando.
     *  \param y matriz, el segundo sumando.
     *  \return Matriz (objeto) resultante de la suma.
    */
    public Matriz suma(Matriz x, Matriz y) {
        Matriz z = new Matriz(x.getFilas(), x.getColumnas());
        int value;

        if (x.getFilas() == y.getFilas() && x.getColumnas() == y.getColumnas()) {
            for (int i = 0; i < x.getFilas(); i++) {
                for (int j = 0; j < y.getColumnas(); j++) {
                    value = x.getElemento(i, j) + y.getElemento(i, j);        ///sumo elemento a elemento
                    z.asignarElemento(value, i, j);
                    //Logger.print("[" + z.getElemento(i,j) + "]",false);
                }
                //Logger.print('\n',false);
            }
            return z;
        } else {
            Logger.println("ERROR: las matrices no son iguales. error metodo SUMA",false);
            return z;
        }
    }

    /*! \brief Resta dos matrices de forma elemento a elemento (posicion a posicion). Deben tener las misma dimensiones,
     *         sino devuelve una matriz nula con un error impreso en consola.
     *  \param x matriz, minuendo de la operacion.
     *  \param y matriz, sustraendo de la operacion.
     *  \return Matriz (objeto) resultante de la resta entre vectores.
    */
    public static Matriz resta(Matriz x, Matriz y) {
        Matriz z = new Matriz(x.getFilas(), x.getColumnas());
        int value;
        if (x.getFilas() == y.getFilas() && x.getColumnas() == y.getColumnas()) {
            for (int i = 0; i < x.getFilas(); i++) {
                for (int j = 0; j < y.getColumnas(); j++) {
                    value = x.getElemento(i, j) - y.getElemento(i, j);    ///resto elemento a elemento
                    z.asignarElemento(value, i, j);
                    // Logger.print("[" + z.getElemento(i,j) + "]",false);
                }
                //Logger.print('\n',false);
            }
            return z;
        } else {
            Logger.println("ERROR: Las matrices no son iguales",false);
            return z;
        }
    }

    public Matriz xor(Matriz pre, Matriz pos){
        Matriz z = new Matriz(pre.getFilas(), pre.getColumnas());

        if (pre.getFilas() != pos.getFilas() || pre.getColumnas() != pos.getColumnas()) {
            Logger.println("ERROR metodo XOR: las matrices no son de las mismas dimensiones",false);
            return z;
        }

        for (int i = 0; i < pre.getFilas(); i++) {
            for (int j = 0; j < pre.getColumnas(); j++) {
                boolean v_xor = pre.getElemento(i,0) != pos.getElemento(i,0);
                if(v_xor){
                    z.asignarElemento(1, i, j);
                }
            //Logger.print("[" + z.getElemento(i,j) + "]",false);
            }
            //Logger.print('\n',false);
        }
        return z;
    }

    public Matriz extractRow(Matriz a,int n_fila){
        Matriz fila = new Matriz(1, a.getColumnas());
        for(int i=0; i < a.getColumnas(); i++){
            fila.asignarElemento(getElemento(n_fila, i),0,i);
        }
        return fila;
    }
}