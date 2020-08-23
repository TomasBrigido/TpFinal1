import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class Logger {

    private Logger(){ }

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
        text=text == "" ? "\n":text;
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
}
