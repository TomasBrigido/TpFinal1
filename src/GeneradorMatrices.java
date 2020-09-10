import java.io.*;

public class GeneradorMatrices {

    private String linea;
    private BufferedReader lector = null;
    private File f;

    public GeneradorMatrices(String path) {
        f = new File(path);
    }


    /*Toma datos de un archivo y lo carga como una matriz
    * \return Matriz con los datos cargados*/
    public Matriz cargarDatos() {
        int f = determinarFilas();
        int c = determinarColumnas(); // Determina la cantidad de filas y de columnas
        Matriz matriz = new Matriz(f,c);


        int numeroLinea = 0;
        int numeroColumna;
        try {
            lector = new BufferedReader((new FileReader(this.f)));
            linea = null;
            while ((linea = lector.readLine()) != null) {
                String[] fila = linea.split(" ");
                for (numeroColumna = 0; numeroColumna < fila.length; numeroColumna++) {
                    int valor = Integer.parseInt(fila[numeroColumna]);
                    matriz.asignarElemento(valor, numeroLinea, numeroColumna);
                }
                numeroLinea++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matriz;
    }

    /*Determina la cantidad de columnas que tiene la matriz del archivo
    * \return numero de columnas*/
    public int determinarColumnas(){
        int num=-1;
        try {
            BufferedReader lector = new BufferedReader((new FileReader(this.f)));
            String linea = null;
            if((linea = lector.readLine()) != null) {
                String[] fila = linea.split(" ");
                num= fila.length;
            }
        } catch (FileNotFoundException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}

        return num;
    }

    /*Determina la cantidad de filas que tiene la matriz del archivo
    * \return numero de filas*/
    public int determinarFilas(){
        int num=0;
        try {
            BufferedReader reader = new BufferedReader((new FileReader(this.f)));
            while (reader.readLine() != null) {
                num++;
            }
        } catch (FileNotFoundException e) {e.printStackTrace();}
        catch (IOException e) {
            e.printStackTrace();
        }
        return num;
    }

    public void setPath(String p){
        f= new File(p);
    }
}
