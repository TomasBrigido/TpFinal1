import java.io.*;

public class GeneradorMatrices {

    private String linea;
    private BufferedReader lector = null;
    private File f;

    public GeneradorMatrices(String path) {
        f = new File(path);
    }

    public Matriz cargarDatos() {
        int f = determinarFilas();
        int c = determinarColumnas();
        Matriz matriz = new Matriz(f,c);

        // Determinar la cantidad de filas y de columnas
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
