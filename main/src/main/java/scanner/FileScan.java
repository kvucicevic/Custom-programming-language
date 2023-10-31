package scanner;

import java.io.*;

public class FileScan {

    public void readFile(String fileName){
        FileReader fr = null;
        BufferedReader br = null;

        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            String line;
            String[] lines = new String[100];
            int i = 0;
            while((line = br.readLine()) != null){ /// LEVEL!! TO DETERMINE THE NUMBER OF TABS
                // analiza
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fr.close();
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
