package support;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static final String LOG_FILE_NAME = "squid_log.txt";

    public static void log(String tag, String message){
        /*try {
            File file = new File(LOG_FILE_NAME);
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, true);


        } catch (Exception ex){
            ex.printStackTrace();
        }*/

        try(FileWriter writer = new FileWriter(LOG_FILE_NAME, true)) {
            String text = tag+": " + message + "\n";
            writer.write(text);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
