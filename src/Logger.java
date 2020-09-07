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
        	//writer_t.write(t);
            writer_t.write("T"+transicion);
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
            //FileWriter writer_t = new FileWriter(miDir.getCanonicalPath()+"/src/logs/exec_t.txt",true);
            FileWriter writer = new FileWriter(miDir.getCanonicalPath()+"/src/logs/log_dev.txt",true);
            //writer_t.write("\n*******************************************\n\n");
            writer.write("\n*******************************************\n\n");
            //writer_t.close();
            writer.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateContador(int t){
        //for(int i=0;i<contador.length;i++)
            contador[t]++;
    }

    public static int getContador(int indice){return contador[indice];}

    public static int getBalanceProc(int proc) {
        if(proc==1)
            return contador[3];
        else
            return contador[4];
    }

    public static int getBalanceMem(int num){
        if(num==2)
            return contador[13]+contador[14];
        else
            return contador[11]+contador[12];
        //return contador[12]+contador[14]+contador[11]+contador[13];
    }

    public static int getTareasProc(int proc, int tarea){
        if(proc==1 && tarea==1)
            return contador[9];
        if(proc==1 && tarea==2)
            return contador[7];
        if(proc==2 && tarea==1)
            return contador[10];
        if(proc==2 && tarea==2)
            return contador[8];
        else
            return -1;
    }

    public static int getSumaMemoria(){
        return contador[15]+contador[16];
    }

}
