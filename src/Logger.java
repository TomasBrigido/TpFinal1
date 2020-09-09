import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class Logger {
    private static int[] contador = new int[17];
    private Logger(){ }

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

    public static void printT(int transicion){
        try{
            File miDir = new File(".");
            String path = miDir.getCanonicalPath();
            FileWriter writer_t = new FileWriter(path+"/src/logs/exec_t.txt",true);
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
            FileWriter writer_t = new FileWriter(miDir.getCanonicalPath()+"/src/logs/exec_t.txt",false);
            FileWriter writer = new FileWriter(miDir.getCanonicalPath()+"/src/logs/log_dev.txt",true);
            writer_t.write("");
            writer.write("\n*******************************************\n\n");
            writer_t.close();
            writer.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void printBalance(String text,boolean file){
        text= text.equals("") ? "\n":text;
        if(file){
            try{
                File miDir = new File(".");
                String path = miDir.getCanonicalPath();

                FileWriter writer = new FileWriter(path+"/src/logs/BalanceMemorias.txt",true);
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

    public static void printBalanceEnTxt(){
        printBalance("Datos de almacenamiento de memoria \n", true);
        printBalance("Tareas procesadas por el procesador 1: "+getBalanceProc(1),true);
        printBalance("Tareas procesadas por el procesador 2: "+getBalanceProc(2),true);
        printBalance(" ", true);
        printBalance("Tareas 1 procesadas por el procesador 1: "+getTareasProc(1,1),true);
        printBalance("Tareas 2 procesadas por el procesador 1: "+getTareasProc(1,2),true);
        printBalance("Tareas 1 procesadas por el procesador 2: "+getTareasProc(2,1),true);
        printBalance("Tareas 2 procesadas por el procesador 2: "+getTareasProc(2,2),true);
        printBalance(" ", true);
        printBalance("Datos almacenados en el slot de memoria 1: "+getBalanceMem(1),true);
        printBalance("Datos almacenados en el slot de memoria 2: "+getBalanceMem(2),true);
        printBalance(" ", true);
        printBalance("Porcentaje de tareas del procesador 1 con respecto al total: "+(getBalanceProc(1)*100/getSumaMemoria())+"%", true);
        printBalance("Porcentaje de tareas del procesador 2 con respecto al total: "+(getBalanceProc(2)*100/getSumaMemoria())+"%", true);
        printBalance(" ", true);
        printBalance("Porcentaje de tareas 1 del procesador 1 con respecto al total: "+(getTareasProc(1,1)*100/getSumaMemoria())+"%", true);
        printBalance("Porcentaje de tareas 2 del procesador 1 con respecto al total: "+(getTareasProc(1,2)*100/getSumaMemoria())+"%", true);
        printBalance("Porcentaje de tareas 1 del procesador 2 con respecto al total: "+(getTareasProc(2,1)*100/getSumaMemoria())+"%", true);
        printBalance("Porcentaje de tareas 2 del procesador 2 con respecto al total: "+(getTareasProc(2,2)*100/getSumaMemoria())+"%", true);
        printBalance("**********************************", true);
    }

    public static void updateContador(int t){
        contador[t]++;
    }


}
