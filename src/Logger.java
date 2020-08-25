import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class Logger {
    private static int[] contador = new int[17];
    private Logger(){ }

    public static void printT(int transicion){
        try{
            File miDir = new File(".");
            String path = miDir.getCanonicalPath();
            char t= (char) (transicion+97);
            FileWriter writer_t = new FileWriter(path+"/src/logs/exec_t.txt",true);
        	writer_t.write(t);
            //writer_t.write("T"+transicion);
        	writer_t.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void println(String text,boolean file){
        text= text.equals("") ? "\n":text;
        if(file){
            try{
                File miDir = new File(".");
                String path = miDir.getCanonicalPath();

                FileWriter writer = new FileWriter(path+"/src/logs/log_dev.txt",true);
                writer.write("\n");
                writer.write(text);
                writer.close();
            }catch(IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println(text);
        }
    }

    public static void clearLogs(){
        try{
            File miDir = new File(".");
            FileWriter writer_t = new FileWriter(miDir.getCanonicalPath()+"/src/logs/exec_t.txt",true);
            FileWriter writer = new FileWriter(miDir.getCanonicalPath()+"/src/logs/log_dev.txt",true);
            writer_t.write("\n*******************************************\n\n");
            writer.write("\n*******************************************\n\n");
            writer_t.close();
            writer.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateContador(int t){
        for(int i=0;i<contador.length;i++)
            contador[i]++;
    }

    public static int getContador(int indice){return contador[indice];}

    public static int[] getBalanceProc(){
        return new int[]{contador[4], contador[3]};
    }

    public static int[] getBalanceMem(){
        return new int[]{contador[12]+contador[14], contador[11]+contador[13]};
    }

    public static int[] getTareasProc(){
        return new int[]{contador[8],contador[10],contador[7],contador[9]};
    }

}
